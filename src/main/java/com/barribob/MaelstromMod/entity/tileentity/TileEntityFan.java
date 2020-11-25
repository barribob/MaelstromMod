package com.barribob.MaelstromMod.entity.tileentity;

import com.barribob.MaelstromMod.blocks.BlockFan;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class TileEntityFan extends TileEntity implements ITickable {
    @Override
    public void update() {
        EnumFacing facing = BlockFan.getFacing(this.getBlockMetadata());
        boolean triggered = (this.getBlockMetadata() & 8) > 0;
        float maxDistance = 16;
        float distance = 0;

        // Take into consideration any blocks in front of the fan
        for (distance = 1; distance <= maxDistance; distance++) {
            BlockPos pos = this.getPos().add(new BlockPos(facing.getFrontOffsetX() * distance, facing.getFrontOffsetY() * distance, facing.getFrontOffsetZ() * distance));
            IBlockState block = world.getBlockState(pos);
            if (block.isFullBlock() || block.isFullCube() || block.isBlockNormalCube() || block.isSideSolid(world, pos, facing.getOpposite())
                    || block.isSideSolid(world, pos, facing)) {
                break;
            }
        }

        double strength = facing.getFrontOffsetY() != 0 ? 0.5 : 0.3;
        if (triggered) {
            AxisAlignedBB box = new AxisAlignedBB(pos, pos.add(1, 1, 1)).expand(facing.getFrontOffsetX() * distance, facing.getFrontOffsetY() * distance,
                    facing.getFrontOffsetZ() * distance);
            List<Entity> list = this.world.getEntitiesWithinAABB(Entity.class, box);

            if (list != null) {
                for (Entity entity : list) {
                    Vec3d vel = new Vec3d(facing.getDirectionVec()).scale(strength / Math.sqrt(entity.getDistanceSq(this.pos.add(0.5, 0.5, 0.5))));
                    vel.scale(1 / entity.getEntityBoundingBox().getAverageEdgeLength()); // Take into consideration the entity's size
                    entity.addVelocity(vel.x, vel.y, vel.z);
                    entity.fallDistance = 0;
                }
            }
        }
    }
}
