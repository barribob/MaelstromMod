package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityHealerOrb extends Entity {
    private EntityMaelstromMob owner;
    private Entity target;
    private Vec3d tempTarget;
    @Nullable
    private UUID ownerUniqueId;
    private BlockPos ownerBlockPos;
    @Nullable
    private UUID targetUniqueId;
    private BlockPos targetBlockPos;

    public EntityHealerOrb(World worldIn) {
        super(worldIn);
        this.setSize(0.3125F, 0.3125F);
        this.noClip = true;
    }

    @SideOnly(Side.CLIENT)
    public EntityHealerOrb(World worldIn, double x, double y, double z, double motionXIn, double motionYIn, double motionZIn) {
        this(worldIn);
        this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
        this.motionX = motionXIn;
        this.motionY = motionYIn;
        this.motionZ = motionZIn;
    }

    public EntityHealerOrb(World worldIn, EntityMaelstromMob ownerIn, Entity targetIn) {
        this(worldIn);
        this.owner = ownerIn;
        this.setLocationAndAngles(ownerIn.posX, owner.posY, owner.posZ, this.rotationYaw, this.rotationPitch);
        this.target = targetIn;
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        if (this.owner != null) {
            BlockPos blockpos = new BlockPos(this.owner);
            NBTTagCompound nbttagcompound = NBTUtil.createUUIDTag(this.owner.getUniqueID());
            nbttagcompound.setInteger("X", blockpos.getX());
            nbttagcompound.setInteger("Y", blockpos.getY());
            nbttagcompound.setInteger("Z", blockpos.getZ());
            compound.setTag("Owner", nbttagcompound);
        }

        if (this.target != null) {
            BlockPos blockpos1 = new BlockPos(this.target);
            NBTTagCompound nbttagcompound1 = NBTUtil.createUUIDTag(this.target.getUniqueID());
            nbttagcompound1.setInteger("X", blockpos1.getX());
            nbttagcompound1.setInteger("Y", blockpos1.getY());
            nbttagcompound1.setInteger("Z", blockpos1.getZ());
            compound.setTag("Target", nbttagcompound1);
        }

        compound.setDouble("TXD", this.tempTarget.x);
        compound.setDouble("TYD", this.tempTarget.y);
        compound.setDouble("TZD", this.tempTarget.z);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        this.tempTarget = new Vec3d(compound.getDouble("TXD"), compound.getDouble("TYD"), compound.getDouble("TZD"));

        if (compound.hasKey("Owner", 10)) {
            NBTTagCompound nbttagcompound = compound.getCompoundTag("Owner");
            this.ownerUniqueId = NBTUtil.getUUIDFromTag(nbttagcompound);
            this.ownerBlockPos = new BlockPos(nbttagcompound.getInteger("X"), nbttagcompound.getInteger("Y"), nbttagcompound.getInteger("Z"));
        }

        if (compound.hasKey("Target", 10)) {
            NBTTagCompound nbttagcompound1 = compound.getCompoundTag("Target");
            this.targetUniqueId = NBTUtil.getUUIDFromTag(nbttagcompound1);
            this.targetBlockPos = new BlockPos(nbttagcompound1.getInteger("X"), nbttagcompound1.getInteger("Y"), nbttagcompound1.getInteger("Z"));
        }
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public void onUpdate() {
        if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            this.setDead();
        } else {
            super.onUpdate();

            if (!this.world.isRemote) {
                if (this.tempTarget == null) {
                    this.findNextTargetPosition();
                }

                /**
                 * My guess is that this reloads the target from nbt
                 */
                if (this.target == null && this.targetUniqueId != null) {
                    for (EntityLivingBase entitylivingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.targetBlockPos.add(-2, -2, -2), this.targetBlockPos.add(2, 2, 2)))) {
                        if (entitylivingbase.getUniqueID().equals(this.targetUniqueId)) {
                            this.target = entitylivingbase;
                            break;
                        }
                    }

                    this.targetUniqueId = null;
                }

                /**
                 * Reload owner from nbt
                 */
                if (this.owner == null && this.ownerUniqueId != null) {
                    for (EntityLivingBase entity : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.ownerBlockPos.add(-2, -2, -2), this.ownerBlockPos.add(2, 2, 2)))) {
                        if (entity.getUniqueID().equals(this.ownerUniqueId) && entity instanceof EntityMaelstromMob) {
                            this.owner = (EntityMaelstromMob) entity;
                            break;
                        }
                    }

                    this.ownerUniqueId = null;
                }

                /**
                 * Default just flow down if no target
                 */
                if (this.target == null || !this.target.isEntityAlive() || this.target instanceof EntityPlayer && ((EntityPlayer) this.target).isSpectator()) {

                    this.target = ModUtils.closestEntityExcluding(this, this.getEntityBoundingBox().grow(10), (entity) -> EntityMaelstromMob.isMaelstromMob(entity) && entity != this.owner);

                    if (!this.hasNoGravity()) {
                        this.motionY -= 0.04D;
                        this.motionX = 0;
                        this.motionZ = 0;
                    }
                } else {
                    Vec3d direction = this.tempTarget.subtract(getPositionVector()).normalize();
                    this.motionX = direction.x * 0.3;
                    this.motionY = direction.y * 0.7;
                    this.motionZ = direction.z * 0.3;
                }

                /**
                 * Hit detection
                 */
                RayTraceResult raytraceresult = ProjectileHelper.forwardsRaycast(this, true, false, this.owner);

                if (raytraceresult != null && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                    this.bulletHit(raytraceresult);
                }
            }

            /**
             * Not entirely sure, but this seems to manually update the position based on the motion
             */
            this.setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            ProjectileHelper.rotateTowardsMovement(this, 0.5F);

            if (this.world.isRemote) {
                ParticleManager.spawnEffect(world, this.getPositionVector(), ModColors.PURPLE);
            } else if (this.target != null && !this.target.isDead) {
                /**
                 * Look ahead 2 blocks and if there are blocks in the way, attempt to change direction
                 */
                Vec3d direction = this.tempTarget.subtract(getPositionVector()).normalize();
                Vec3d lookahead = this.getPositionVector().add(direction.scale(2));
                RayTraceResult res = world.rayTraceBlocks(this.getPositionVector(), lookahead, false, true, true);

                double distanceFromTarget = this.getPositionVector().squareDistanceTo(this.tempTarget);

                if ((res != null && res.typeOfHit == RayTraceResult.Type.BLOCK) || distanceFromTarget < 1) {
                    this.findNextTargetPosition();
                }
            }
        }
    }

    /**
     * Find the next direction to move. Prioritizes horizontal movement over vertical movement
     *
     * @return
     */
    public void findNextTargetPosition() {
        Vec3d targetPos = this.target.getPositionVector().add(new Vec3d(target.motionX, target.motionY, target.motionZ).scale(3));
        Vec3d diff = targetPos.subtract(this.getPositionVector());
        double xMag = Math.abs(diff.x);
        double zMag = Math.abs(diff.z);

        if (xMag + zMag > 0.8) // If were close enough, just go directly to the entity
        {
            // Randomly choose x or z position, with higher weight on the larger magnitude
            // This is random mostly because it shouldn't keep choosing a direction if it gets stuck
            if (rand.nextDouble() * xMag > rand.nextDouble() * zMag) {
                targetPos = new Vec3d(targetPos.x, this.posY, this.posZ); // Go in the x direction only
            } else {
                targetPos = new Vec3d(this.posX, this.posY, targetPos.z); // Go in the z direction only
            }
        }

        this.tempTarget = targetPos;
    }

    @Override
    public boolean isBurning() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        return distance < 16384.0D;
    }

    @Override
    public float getBrightness() {
        return 1.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return 15728880;
    }

    protected void bulletHit(RayTraceResult result) {
        Entity entity = ModUtils.getLivingEntity(result.entityHit);
        if (EntityMaelstromMob.isMaelstromMob(entity) && owner != null) {
            world.setEntityState(this, ModUtils.PARTICLE_BYTE);
            this.playSound(SoundEvents.ENTITY_ILLAGER_CAST_SPELL, 1.0F, 1.0F);
            ((EntityMaelstromMob) entity).addPotionEffect(new PotionEffect(MobEffects.SPEED, 100));
            ((EntityMaelstromMob) entity).heal((float) (owner.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 4));
        } else if (result.entityHit != null && owner != null) {
            result.entityHit.attackEntityFrom(ModDamageSource.causeElementalThrownDamage(this, owner, owner.getElement()), owner.getAttack());
        }

        this.setDead();
    }

    /**
     * Spawn impact healing particles
     */
    @Override
    public void handleStatusUpdate(byte id) {
        if (id == ModUtils.PARTICLE_BYTE) {
            for (int i = -5; i < 2; i++) {
                final float yOff = i * 0.5f;
                ModUtils.circleCallback(1, 20, (pos) -> {
                    pos = new Vec3d(pos.x, yOff, pos.y);
                    ParticleManager.spawnDust(world, pos.add(this.getPositionVector()), ModColors.PURPLE, ModUtils.yVec(0.1f), ModRandom.range(10, 20));
                });
            }
        }
        super.handleStatusUpdate(id);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!this.world.isRemote) {
            ((WorldServer) this.world).spawnParticle(EnumParticleTypes.CRIT, this.posX, this.posY, this.posZ, 15, 0.2D, 0.2D, 0.2D, 0.0D);
            this.setDead();
        }

        return true;
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }
}
