package com.barribob.MaelstromMod.util;

import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.init.ModEnchantments;
import com.barribob.MaelstromMod.items.tools.ToolSword;
import com.google.common.collect.Multimap;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public final class ModUtils
{
    public static byte PARTICLE_BYTE = 12;
    public static byte SECOND_PARTICLE_BYTE = 14;
    public static byte THIRD_PARTICLE_BYTE = 15;
    public static final String LANG_DESC = Reference.MOD_ID + ".desc.";
    public static final String LANG_CHAT = Reference.MOD_ID + ".dialog.";

    public static String translateDesc(String key)
    {
	return I18n.format(ModUtils.LANG_DESC + key);
    }

    public static String translateDialog(String key)
    {
	return I18n.format(ModUtils.LANG_CHAT + key);
    }

    public static double getSwordEnchantmentDamage(ItemStack stack)
    {
	int power = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.sharpness_2, stack);
	float maxPower = ModEnchantments.sharpness_2.getMaxLevel();
	Multimap<String, AttributeModifier> multimap = stack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND);
	for (Entry<String, AttributeModifier> entry : multimap.entries())
	{
	    AttributeModifier attributemodifier = entry.getValue();
	    if (attributemodifier.getID() == ToolSword.getAttackDamageModifier())
	    {
		double multiplier = ((power / maxPower) * (ModConfig.progression_scale - 1));
		return attributemodifier.getAmount() * multiplier;
	    }
	}
	return 0;
    }

    /**
     * Determines if the chunk is already generated, in which case new structures
     * cannot be placed
     * 
     * @param box
     * @param world
     * @return
     */
    public static boolean chunksGenerated(StructureBoundingBox box, World world)
    {
	return world.isChunkGeneratedAt(box.minX >> 4, box.minZ >> 4) || world.isChunkGeneratedAt(box.minX >> 4, box.maxZ >> 4)
		|| world.isChunkGeneratedAt(box.maxX >> 4, box.minZ >> 4) || world.isChunkGeneratedAt(box.maxX >> 4, box.maxZ >> 4);
    }

    /**
     * Calls the function n times, passing in the ith iteration
     * 
     * @param n
     * @param func
     */
    public static void performNTimes(int n, Consumer<Integer> func)
    {
	for (int i = 0; i < n; i++)
	{
	    func.accept(i);
	}
    }

    /**
     * Returns all EntityLivingBase entities in a certain bounding box
     */
    public static List<EntityLivingBase> getEntitiesInBox(Entity entity, AxisAlignedBB bb)
    {
	List<Entity> list = entity.world.getEntitiesWithinAABBExcludingEntity(entity, bb);

	if (list != null)
	{
	    Predicate<Entity> isInstance = i -> i instanceof EntityLivingBase;
	    Function<Entity, EntityLivingBase> cast = i -> (EntityLivingBase) i;

	    return list.stream().filter(isInstance).map(cast).collect(Collectors.toList());
	}

	return null;
    }

    /**
     * Returns the entity's position in vector form
     */
    public static Vec3d entityPos(Entity entity)
    {
	return new Vec3d(entity.posX, entity.posY, entity.posZ);
    }

    /*
     * Generates a generator n times in a chunk
     */
    public static void generateN(World worldIn, Random rand, BlockPos pos, int n, int baseY, int randY, WorldGenerator gen)
    {
	randY = randY > 0 ? randY : 1;
	for (int i = 0; i < n; ++i)
	{
	    int x = rand.nextInt(16) + 8;
	    int y = rand.nextInt(randY) + baseY;
	    int z = rand.nextInt(16) + 8;
	    gen.generate(worldIn, rand, pos.add(x, y, z));
	}
    }

    public static BlockPos posToChunk(BlockPos pos)
    {
	return new BlockPos(pos.getX() / 16f, pos.getY(), pos.getZ() / 16f);
    }

    /**
     * Creates a Vec3 using the pitch and yaw of the entities rotation. Taken from
     * entity, so it can be used anywhere
     */
    public static Vec3d getVectorForRotation(float pitch, float yaw)
    {
	float f = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
	float f1 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
	float f2 = -MathHelper.cos(-pitch * 0.017453292F);
	float f3 = MathHelper.sin(-pitch * 0.017453292F);
	return new Vec3d(f1 * f2, f3, f * f2);
    }

    public static Vec3d yVec(float y)
    {
	return new Vec3d(0, y, 0);
    }

    public static void handleAreaImpact(float radius, Function<EntityLivingBase, Float> maxDamage, EntityLivingBase source, Vec3d pos, DamageSource damageSource)
    {
	handleAreaImpact(radius, maxDamage, source, pos, damageSource, 1, 0);
    }

    public static void handleAreaImpact(float radius, Function<EntityLivingBase, Float> maxDamage, EntityLivingBase source, Vec3d pos, DamageSource damageSource,
	    float knockbackFactor, int fireFactor)
    {
	handleAreaImpact(radius, maxDamage, source, pos, damageSource, knockbackFactor, fireFactor, true);
    }

    public static void handleAreaImpact(float radius, Function<EntityLivingBase, Float> maxDamage, EntityLivingBase source, Vec3d pos, DamageSource damageSource,
	    float knockbackFactor, int fireFactor, boolean damageDecay)
    {
	if (source == null)
	{
	    return;
	}
	List<Entity> list = source.world.getEntitiesWithinAABBExcludingEntity(source, new AxisAlignedBB(new BlockPos(pos), new BlockPos(pos)).grow(radius));

	if (list != null)
	{
	    Predicate<Entity> isInstance = i -> i instanceof EntityLivingBase;
	    Function<Entity, EntityLivingBase> cast = i -> (EntityLivingBase) i;

	    list.stream().filter(isInstance).map(cast).forEach((entity) -> {
		double avgEntitySize = entity.getEntityBoundingBox().getAverageEdgeLength() * 0.75;
		double radiusSq = Math.pow(radius + avgEntitySize, 2);
		double distanceFromExplosion = (float) entity.getEntityBoundingBox().getCenter().squareDistanceTo(pos);
		double damageFactor = damageDecay ? Math.max(0, Math.min(1, (radiusSq - distanceFromExplosion) / radiusSq)) : 1;
		double damage = maxDamage.apply(entity) * damageFactor;
		if (damage > 0 && distanceFromExplosion < radiusSq)
		{
		    entity.setFire(fireFactor);
		    entity.attackEntityFrom(damageSource, (float) damage);
		    double entitySizeFactor = avgEntitySize == 0 ? 1 : Math.max(0.5, Math.min(1, 1 / avgEntitySize));
		    Vec3d velocity = entity.getEntityBoundingBox().getCenter().subtract(pos).normalize().scale(damageFactor).scale(knockbackFactor).scale(entitySizeFactor);
		    entity.addVelocity(velocity.x, velocity.y, velocity.z);
		}
	    });
	}
    }

    public static void handleBulletImpact(Entity hitEntity, Projectile projectile, float damage, DamageSource damageSource)
    {
	handleBulletImpact(hitEntity, projectile, damage, damageSource, 0);
    }

    public static void handleBulletImpact(Entity hitEntity, Projectile projectile, float damage, DamageSource damageSource, int knockback)
    {
	handleBulletImpact(hitEntity, projectile, damage, damageSource, knockback, (p, e) -> {
	}, (p, e) -> {
	});
    }

    public static void handleBulletImpact(Entity hitEntity, Projectile projectile, float damage, DamageSource damageSource, int knockback,
	    BiConsumer<Projectile, Entity> beforeHit, BiConsumer<Projectile, Entity> afterHit)
    {
	handleBulletImpact(hitEntity, projectile, damage, damageSource, knockback, beforeHit, afterHit, true);
    }

    public static void handleBulletImpact(Entity hitEntity, Projectile projectile, float damage, DamageSource damageSource, int knockback,
	    BiConsumer<Projectile, Entity> beforeHit, BiConsumer<Projectile, Entity> afterHit, Boolean resetHurtTime)
    {
	if (hitEntity != null && projectile != null && projectile.shootingEntity != null && hitEntity != projectile.shootingEntity)
	{
	    beforeHit.accept(projectile, hitEntity);
	    if (projectile.isBurning() && !(hitEntity instanceof EntityEnderman))
	    {
		hitEntity.setFire(5);
	    }
	    if (resetHurtTime)
	    {
		hitEntity.hurtResistantTime = 0;
	    }
	    hitEntity.attackEntityFrom(damageSource, damage);
	    if (knockback > 0)
	    {
		float f1 = MathHelper.sqrt(projectile.motionX * projectile.motionX + projectile.motionZ * projectile.motionZ);

		if (f1 > 0.0F)
		{
		    hitEntity.addVelocity(projectile.motionX * knockback * 0.6000000238418579D / f1, 0.1D, projectile.motionZ * knockback * 0.6000000238418579D / f1);
		}
	    }
	    afterHit.accept(projectile, hitEntity);
	}
    }

    public static Vec3d getRelativeOffset(EntityLivingBase actor, Vec3d offset)
    {
	Vec3d look = ModUtils.getVectorForRotation(0, actor.renderYawOffset);
	Vec3d side = look.rotateYaw((float) Math.PI * 0.5f);
	return look.scale(offset.x).add(yVec((float) offset.y)).add(side.scale(offset.z));
    }

    public static void throwProjectile(EntityLivingBase actor, EntityLivingBase target, Projectile projectile)
    {
	throwProjectile(actor, target, projectile, 12.0f, 1.6f);
    }

    public static void throwProjectile(EntityLivingBase actor, EntityLivingBase target, Projectile projectile, float inaccuracy, float velocity, Vec3d offset)
    {
	Vec3d pos = projectile.getPositionVector().add(offset);
	projectile.setPosition(pos.x, pos.y, pos.z);
	throwProjectile(actor, target, projectile, inaccuracy, velocity);
    }

    public static void throwProjectile(EntityLivingBase actor, EntityLivingBase target, Projectile projectile, float inaccuracy, float velocity)
    {
	double d0 = target.posY + target.getEyeHeight() - 1.100000023841858D;
	double d1 = target.posX - projectile.posX;
	double d2 = d0 - projectile.posY;
	double d3 = target.posZ - projectile.posZ;
	float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
	projectile.shoot(d1, d2 + f, d3, velocity, inaccuracy);
	actor.world.spawnEntity(projectile);
    }

    /**
     * Credit to coolAlias
     * https://www.minecraftforge.net/forum/topic/22166-walking-on-water/
     * 
     * @param entity
     * @param world
     */
    public static void walkOnWater(EntityLivingBase entity, World world)
    {
	BlockPos pos = new BlockPos(entity.posX, Math.floor(entity.getEntityBoundingBox().minY), entity.posZ);
	if (world.getBlockState(pos).getBlock() == Blocks.WATER || world.getBlockState(pos).getBlock() == Blocks.FLOWING_WATER)
	{
	    if (entity.motionY < 0)
	    {
		entity.posY += -entity.motionY; // player is falling, so motionY is negative, but we want to reverse that
		entity.motionY = 0.0D; // no longer falling
	    }
	    entity.fallDistance = 0.0F; // otherwise I believe it adds up, which may surprise you when you come down
	    entity.onGround = true;
	}
    }
}
