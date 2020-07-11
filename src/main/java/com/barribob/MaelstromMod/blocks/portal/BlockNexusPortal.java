package com.barribob.MaelstromMod.blocks.portal;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.util.teleporter.NexusToOverworldTeleporter;
import com.barribob.MaelstromMod.util.teleporter.ToNexusTeleporter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;

/**
 * The portal block for the nexus dimension
 */
public class BlockNexusPortal extends BlockPortal {
    public BlockNexusPortal(String name) {
        super(name, ModConfig.world.nexus_dimension_id, 0);
        this.setBlockUnbreakable();
        this.setLightLevel(0.5f);
        this.setLightOpacity(0);
    }

    @Override
    protected Teleporter getEntranceTeleporter(World world) {
        return new ToNexusTeleporter(world.getMinecraftServer().getWorld(ModConfig.world.nexus_dimension_id), new BlockPos(70, 80, 103));
    }

    @Override
    protected Teleporter getExitTeleporter(World world) {
        return new NexusToOverworldTeleporter(world.getMinecraftServer().getWorld(0));
    }
}