package com.barribob.MaelstromMod.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.entity.particleSpawners.ParticleSpawnerSwordSwing;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.init.ModEnchantments;
import com.barribob.MaelstromMod.invasion.InvasionWorldSaveData;
import com.barribob.MaelstromMod.packets.MessageModParticles;
import com.barribob.MaelstromMod.particle.EnumModParticles;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.MapStorage;

public final class ModUtils
{
    public static char AZURE_SYMBOL = '\u03A6';
    public static char GOLDEN_SYMBOL = '\u03A9';
    public static char CRIMSON_SYMBOL = '\u03A3';

    public static byte PARTICLE_BYTE = 12;
    public static byte SECOND_PARTICLE_BYTE = 14;
    public static byte THIRD_PARTICLE_BYTE = 15;
    public static byte FOURTH_PARTICLE_BYTE = 16;

    /**
     * This is only for the maelstrom mob death particles so it doesn't intersect with the other particle bytes.
     */
    public static byte MAELSTROM_PARTICLE_BYTE = 17;
    public static final String LANG_DESC = Reference.MOD_ID + ".desc.";
    public static final String LANG_CHAT = Reference.MOD_ID + ".dialog.";
    public static final DecimalFormat DF_0 = new DecimalFormat("0.0");
    public static final DecimalFormat ROUND = new DecimalFormat("0");
    public static final ResourceLocation PARTICLE = new ResourceLocation(Reference.MOD_ID + ":textures/particle/particles.png");

    static
    {
	DF_0.setRoundingMode(RoundingMode.HALF_EVEN);
	ROUND.setRoundingMode(RoundingMode.HALF_EVEN);
    }

    public static Consumer<String> getPlayerAreaMessager(Entity entity)
    {
	return (message) -> {
	    if (message != "")
	    {
		for (EntityPlayer player : entity.world.getPlayers(EntityPlayer.class, (p) -> p.getDistanceSq(entity) < 100))
		{
		    player.sendMessage(new TextComponentString(TextFormatting.DARK_PURPLE + entity.getDisplayName().getFormattedText() + ": " + TextFormatting.WHITE)
			    .appendSibling(new TextComponentTranslation(ModUtils.LANG_CHAT + message)));
		}
	    }
	};
    }

    public static String translateDesc(String key, Object... params)
    {
	return I18n.format(ModUtils.LANG_DESC + key, params);
    }

    public static String translateDialog(String key)
    {
	return I18n.format(ModUtils.LANG_CHAT + key);
    }

    public static String getDisplayLevel(float level)
    {
	return ModUtils.translateDesc("level", "" + TextFormatting.DARK_PURPLE + Math.round(level));
    }

    public static String getElementalTooltip(Element element)
    {
	return ModUtils.translateDesc("elemental_damage_desc",
		"x" + ModUtils.DF_0.format(ModConfig.balance.elemental_factor),
		element.textColor + element.symbol + TextFormatting.GRAY);
    }

    /**
     * Determines if the chunk is already generated, in which case new structures cannot be placed
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
     * Creates a Vec3 using the pitch and yaw of the entities rotation. Taken from entity, so it can be used anywhere
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

    private static Vec3d getCenter(AxisAlignedBB box)
    {
	return new Vec3d(box.minX + (box.maxX - box.minX) * 0.5D, box.minY + (box.maxY - box.minY) * 0.5D, box.minZ + (box.maxZ - box.minZ) * 0.5D);
    }

    public static void handleAreaImpact(float radius, Function<EntityLivingBase, Float> maxDamage, EntityLivingBase source, Vec3d pos, DamageSource damageSource,
	    float knockbackFactor, int fireFactor, boolean damageDecay)
    {
	if (source == null)
	{
	    return;
	}
	List<Entity> list = source.world.getEntitiesWithinAABBExcludingEntity(source, new AxisAlignedBB(pos.x, pos.y, pos.z, pos.x, pos.y, pos.z).grow(radius));

	if (list != null)
	{
	    Predicate<Entity> isInstance = i -> i instanceof EntityLivingBase;
	    Function<Entity, EntityLivingBase> cast = i -> (EntityLivingBase) i;
	    double radiusSq = Math.pow(radius, 2);

	    list.stream().filter(isInstance).map(cast).forEach((entity) -> {

		// Get the hitbox size of the entity because otherwise explosions are less
		// effective against larger mobs
		double avgEntitySize = entity.getEntityBoundingBox().getAverageEdgeLength() * 0.75;

		// Choose the closest distance from the center or the head to encourage
		// headshots
		double distance = Math.min(Math.min(getCenter(entity.getEntityBoundingBox()).distanceTo(pos),
			entity.getPositionVector().add(ModUtils.yVec(entity.getEyeHeight())).distanceTo(pos)),
			entity.getPositionVector().distanceTo(pos));

		// Subtracting the average size makes it so that the full damage can be dealt
		// with a direct hit
		double adjustedDistance = Math.max(distance - avgEntitySize, 0);
		double adjustedDistanceSq = Math.pow(adjustedDistance, 2);
		double damageFactor = damageDecay ? Math.max(0, Math.min(1, (radiusSq - adjustedDistanceSq) / radiusSq)) : 1;

		// Damage decays by the square to make missed impacts less powerful
		double damageFactorSq = Math.pow(damageFactor, 2);
		double damage = maxDamage.apply(entity) * damageFactorSq;
		if (damage > 0 && adjustedDistanceSq < radiusSq)
		{
		    entity.setFire((int) (fireFactor * damageFactorSq));
		    entity.attackEntityFrom(damageSource, (float) damage);
		    double entitySizeFactor = avgEntitySize == 0 ? 1 : Math.max(0.5, Math.min(1, 1 / avgEntitySize));
		    double entitySizeFactorSq = Math.pow(entitySizeFactor, 2);

		    // Velocity depends on the entity's size and the damage dealt squared
		    Vec3d velocity = getCenter(entity.getEntityBoundingBox()).subtract(pos).normalize().scale(damageFactorSq).scale(knockbackFactor).scale(entitySizeFactorSq);
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
     * Credit to coolAlias https://www.minecraftforge.net/forum/topic/22166-walking-on-water/
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

    /**
     * The function that calculates the mob damage for any leveled mob
     * 
     * @param baseAttackDamage
     * @param level
     * @return
     */
    public static float getMobDamage(double baseAttackDamage, double healthScaledAttackFactor, float maxHealth, float health, float level, Element element)
    {
	double leveledAttack = baseAttackDamage * LevelHandler.getMultiplierFromLevel(level) * ModConfig.balance.mob_damage;
	double healthScaledAttack = leveledAttack * healthScaledAttackFactor * (((maxHealth * 0.5) - health) / maxHealth);
	double elementalScale = element != Element.NONE ? ModConfig.balance.elemental_factor : 1;
	return (float) ((healthScaledAttack + leveledAttack) * elementalScale);
    }

    /**
     * Determine if a >= v < b
     * 
     * @param a
     * @param b
     * @param v
     * @return
     */
    public static boolean isBetween(int a, int b, int v)
    {
	if (a > b)
	{
	    int t = a;
	    a = b;
	    b = t;
	}
	return v >= a && v < b;
    }

    public static int calculateGenerationHeight(World world, int x, int z)
    {
	return world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).getY();
    }

    /**
     * Returns -1 if the variation is too much
     */
    public static int getAverageGroundHeight(World world, int x, int z, int sizeX, int sizeZ, int maxVariation)
    {
	sizeX = x + sizeX;
	sizeZ = z + sizeZ;
	int corner1 = calculateGenerationHeight(world, x, z);
	int corner2 = calculateGenerationHeight(world, sizeX, z);
	int corner3 = calculateGenerationHeight(world, x, sizeZ);
	int corner4 = calculateGenerationHeight(world, sizeX, sizeZ);

	int max = Math.max(Math.max(corner3, corner4), Math.max(corner1, corner2));
	int min = Math.min(Math.min(corner3, corner4), Math.min(corner1, corner2));
	if (max - min > maxVariation)
	{
	    return -1;
	}
	return min;
    }

    public static String getDamageTooltip(float damage)
    {
	return ModUtils.translateDesc("damage_tooltip", "" + TextFormatting.BLUE + DF_0.format(damage) + TextFormatting.GRAY);
    }

    public static String getCooldownTooltip(float cooldown)
    {
	return ModUtils.translateDesc("gun_reload_time", TextFormatting.BLUE + "" + DF_0.format(cooldown * 0.05) + TextFormatting.GRAY);
    }

    public static float getEnchantedDamage(ItemStack stack, float level, float damage)
    {
	float maxPower = ModEnchantments.gun_power.getMaxLevel();
	float power = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.gun_power, stack);
	float maxDamageBonus = (float) Math.pow(ModConfig.balance.progression_scale, 2); // Maximum damage is two levels above
	float enchantmentBonus = 1 + ((power / maxPower) * (maxDamageBonus - 1));
	return damage * enchantmentBonus * LevelHandler.getMultiplierFromLevel(level);
    }

    public static int getGunAmmoUse(float level)
    {
	return Math.round(LevelHandler.getMultiplierFromLevel(level));
    }

    public static InvasionWorldSaveData getInvasionData(World world)
    {
	MapStorage storage = world.getMapStorage();
	InvasionWorldSaveData instance = (InvasionWorldSaveData) storage.getOrLoadData(InvasionWorldSaveData.class, InvasionWorldSaveData.DATA_NAME);

	if (instance == null)
	{
	    instance = new InvasionWorldSaveData();
	    storage.setData(InvasionWorldSaveData.DATA_NAME, instance);
	}
	return instance;
    }

    public static float calculateElementalDamage(Element mobElement, Element damageElement, float amount)
    {
	if (mobElement.matchesElement(damageElement))
	{
	    return amount * 1.5f;
	}
	return amount;
    }

    public static void doSweepAttack(EntityPlayer player, EntityLivingBase target, Element element, Consumer<EntityLivingBase> perEntity)
    {
	doSweepAttack(player, target, element, perEntity, 9, 1);
    }

    public static void doSweepAttack(EntityPlayer player, EntityLivingBase target, Element element, Consumer<EntityLivingBase> perEntity, float maxDistanceSq, float areaSize)
    {
	float attackDamage = (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
	float sweepDamage = Math.min(0.15F + EnchantmentHelper.getSweepingDamageRatio(player), 1) * attackDamage;

	for (EntityLivingBase entitylivingbase : player.world.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().grow(areaSize, 0.25D, areaSize)))
	{
	    if (entitylivingbase != player && entitylivingbase != target && !player.isOnSameTeam(entitylivingbase) && player.getDistanceSq(entitylivingbase) < maxDistanceSq)
	    {
		entitylivingbase.knockBack(player, 0.4F, MathHelper.sin(player.rotationYaw * 0.017453292F), (-MathHelper.cos(player.rotationYaw * 0.017453292F)));
		entitylivingbase.attackEntityFrom(ModDamageSource.causeElementalPlayerDamage(player, element), sweepDamage);
		perEntity.accept(entitylivingbase);
	    }
	}

	player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 0.9F);

	// Spawn colored sweep particles
	if (!player.world.isRemote)
	{
	    double d0 = (-MathHelper.sin(player.rotationYaw * 0.017453292F));
	    double d1 = MathHelper.cos(player.rotationYaw * 0.017453292F);
	    Main.network.sendTo(new MessageModParticles(EnumModParticles.SWEEP_ATTACK, (float) (player.posX + d0), (float) (player.posY + player.height * 0.5D),
		    (float) (player.posZ + d1), (float) d0, 0.0f, (float) d1, (float) element.sweepColor.x, (float) element.sweepColor.y, (float) element.sweepColor.z),
		    (EntityPlayerMP) player);
	}

	Entity particle = new ParticleSpawnerSwordSwing(player.world);
	particle.copyLocationAndAnglesFrom(target);
	player.world.spawnEntity(particle);
    }

    /**
     * Provides multiple points in a circle via a callback
     * 
     * @param radius
     *            The radius of the circle
     * @param points
     *            The number of points around the circle
     * @param particleSpawner
     */
    public static void circleCallback(float radius, int points, Consumer<Vec3d> particleSpawner)
    {
	float degrees = 360f / points;
	for (int i = 0; i < points; i++)
	{
	    double radians = Math.toRadians(i * degrees);
	    Vec3d offset = new Vec3d(Math.sin(radians), Math.cos(radians), 0).scale(radius);
	    particleSpawner.accept(offset);
	}
    }

    /*
     * Does the elemental and leveled calculations for damage
     */
    public static float getArmoredDamage(DamageSource source, float amount, float level, Element element)
    {
	amount *= ModConfig.balance.mob_armor;

	if (!source.isUnblockable())
	{
	    if (element != element.NONE)
	    {
		amount /= ModConfig.balance.elemental_factor;
	    }
	    amount = amount * LevelHandler.getArmorFromLevel(level);
	}

	if (source instanceof IElement)
	{
	    amount = ModUtils.calculateElementalDamage(element, ((IElement) source).getElement(), amount);
	}

	return amount;
    }

    public static void leapTowards(EntityLivingBase entity, Vec3d target, float horzVel, float yVel)
    {
	Vec3d dir = target.subtract(entity.getPositionVector()).normalize();
	Vec3d leap = new Vec3d(dir.x, 0, dir.z).normalize().scale(horzVel).add(ModUtils.yVec(yVel));
	entity.motionX += leap.x;
	if (entity.motionY < 0.1)
	{
	    entity.motionY += leap.y;
	}
	entity.motionZ += leap.z;
    }

    /**
     * Calls a function that linearly interpolates between two points. Includes both ends of the line
     * 
     * @param start
     * @param end
     * @param points
     * @param callback
     */
    public static void lineCallback(Vec3d start, Vec3d end, int points, BiConsumer<Vec3d, Integer> callback)
    {
	Vec3d dir = end.subtract(start).scale(1 / (float) (points - 1));
	Vec3d pos = start;
	for (int i = 0; i < points; i++)
	{
	    callback.accept(pos, i);
	    pos = pos.add(dir);
	}
    }

    public static int tryParseInt(String s, int defaultValue)
    {
	try
	{
	    return Integer.parseInt(s);
	}
	catch (NumberFormatException e)
	{
	    return defaultValue;
	}
    }

    public static float clamp(double value, double min, double max)
    {
	return (float) Math.max(min, Math.min(max, value));
    }

    public static Vec3d findEntityGroupCenter(Entity mob, int boxDistance)
    {
	Vec3d groupCenter = mob.getPositionVector();
	float numMobs = 1;
	for (EntityLivingBase entity : ModUtils.getEntitiesInBox(mob, new AxisAlignedBB(mob.getPosition()).grow(boxDistance)))
	{
	    if (entity instanceof EntityMaelstromMob)
	    {
		groupCenter = groupCenter.add(entity.getPositionVector());
		numMobs += 1;
	    }
	}

	return groupCenter.scale(1 / numMobs);
    }
}
