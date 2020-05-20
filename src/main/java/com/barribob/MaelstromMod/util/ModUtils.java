package com.barribob.MaelstromMod.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromHealer;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.entity.particleSpawners.ParticleSpawnerSwordSwing;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.tileentity.MobSpawnerLogic.MobSpawnData;
import com.barribob.MaelstromMod.init.ModEnchantments;
import com.barribob.MaelstromMod.invasion.InvasionWorldSaveData;
import com.barribob.MaelstromMod.packets.MessageModParticles;
import com.barribob.MaelstromMod.particle.EnumModParticles;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.MapStorage;

public final class ModUtils {
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

    static {
	DF_0.setRoundingMode(RoundingMode.HALF_EVEN);
	ROUND.setRoundingMode(RoundingMode.HALF_EVEN);
    }

    public static Consumer<String> getPlayerAreaMessager(Entity entity) {
	return (message) -> {
	    if (message != "") {
		for (EntityPlayer player : entity.world.getPlayers(EntityPlayer.class, (p) -> p.getDistanceSq(entity) < 100)) {
		    player.sendMessage(new TextComponentString(TextFormatting.DARK_PURPLE + entity.getDisplayName().getFormattedText() + ": " + TextFormatting.WHITE)
			    .appendSibling(new TextComponentTranslation(ModUtils.LANG_CHAT + message)));
		}
	    }
	};
    }

    public static String translateDesc(String key, Object... params) {
	return I18n.format(ModUtils.LANG_DESC + key, params);
    }

    public static String translateDialog(String key) {
	return I18n.format(ModUtils.LANG_CHAT + key);
    }

    public static String getDisplayLevel(float level) {
	return ModUtils.translateDesc("level", "" + TextFormatting.DARK_PURPLE + Math.round(level));
    }

    public static String getElementalTooltip(Element element) {
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
    public static boolean chunksGenerated(StructureBoundingBox box, World world) {
	return world.isChunkGeneratedAt(box.minX >> 4, box.minZ >> 4) || world.isChunkGeneratedAt(box.minX >> 4, box.maxZ >> 4)
		|| world.isChunkGeneratedAt(box.maxX >> 4, box.minZ >> 4) || world.isChunkGeneratedAt(box.maxX >> 4, box.maxZ >> 4);
    }

    /**
     * Calls the function n times, passing in the ith iteration
     * 
     * @param n
     * @param func
     */
    public static void performNTimes(int n, Consumer<Integer> func) {
	for (int i = 0; i < n; i++) {
	    func.accept(i);
	}
    }

    /**
     * Returns all EntityLivingBase entities in a certain bounding box
     */
    public static List<EntityLivingBase> getEntitiesInBox(Entity entity, AxisAlignedBB bb) {
	List<Entity> list = entity.world.getEntitiesWithinAABBExcludingEntity(entity, bb);

	if (list != null) {
	    Predicate<Entity> isInstance = i -> i instanceof EntityLivingBase;
	    Function<Entity, EntityLivingBase> cast = i -> (EntityLivingBase) i;

	    return list.stream().filter(isInstance).map(cast).collect(Collectors.toList());
	}

	return null;
    }

    /**
     * Returns the entity's position in vector form
     */
    public static Vec3d entityPos(Entity entity) {
	return new Vec3d(entity.posX, entity.posY, entity.posZ);
    }

    /*
     * Generates a generator n times in a chunk
     */
    public static void generateN(World worldIn, Random rand, BlockPos pos, int n, int baseY, int randY, WorldGenerator gen) {
	randY = randY > 0 ? randY : 1;
	for (int i = 0; i < n; ++i) {
	    int x = rand.nextInt(16) + 8;
	    int y = rand.nextInt(randY) + baseY;
	    int z = rand.nextInt(16) + 8;
	    gen.generate(worldIn, rand, pos.add(x, y, z));
	}
    }

    public static BlockPos posToChunk(BlockPos pos) {
	return new BlockPos(pos.getX() / 16f, pos.getY(), pos.getZ() / 16f);
    }

    /**
     * Creates a Vec3 using the pitch and yaw of the entities rotation. Taken from entity, so it can be used anywhere
     */
    public static Vec3d getVectorForRotation(float pitch, float yaw) {
	float f = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
	float f1 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
	float f2 = -MathHelper.cos(-pitch * 0.017453292F);
	float f3 = MathHelper.sin(-pitch * 0.017453292F);
	return new Vec3d(f1 * f2, f3, f * f2);
    }

    public static Vec3d yVec(double heightAboveGround) {
	return new Vec3d(0, heightAboveGround, 0);
    }

    public static void handleAreaImpact(float radius, Function<Entity, Float> maxDamage, EntityLivingBase source, Vec3d pos, DamageSource damageSource) {
	handleAreaImpact(radius, maxDamage, source, pos, damageSource, 1, 0);
    }

    public static void handleAreaImpact(float radius, Function<Entity, Float> maxDamage, EntityLivingBase source, Vec3d pos, DamageSource damageSource,
	    float knockbackFactor, int fireFactor) {
	handleAreaImpact(radius, maxDamage, source, pos, damageSource, knockbackFactor, fireFactor, true);
    }

    private static Vec3d getCenter(AxisAlignedBB box) {
	return new Vec3d(box.minX + (box.maxX - box.minX) * 0.5D, box.minY + (box.maxY - box.minY) * 0.5D, box.minZ + (box.maxZ - box.minZ) * 0.5D);
    }

    public static void handleAreaImpact(float radius, Function<Entity, Float> maxDamage, EntityLivingBase source, Vec3d pos, DamageSource damageSource,
	    float knockbackFactor, int fireFactor, boolean damageDecay) {
	if (source == null) {
	    return;
	}
	List<Entity> list = source.world.getEntitiesWithinAABBExcludingEntity(source, new AxisAlignedBB(pos.x, pos.y, pos.z, pos.x, pos.y, pos.z).grow(radius));

	if (list != null) {
	    Predicate<Entity> isInstance = i -> i instanceof EntityLivingBase || i instanceof MultiPartEntityPart || i.canBeCollidedWith();
	    double radiusSq = Math.pow(radius, 2);

	    list.stream().filter(isInstance).forEach((entity) -> {

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
		if (damage > 0 && adjustedDistanceSq < radiusSq) {
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

    public static void handleBulletImpact(Entity hitEntity, Projectile projectile, float damage, DamageSource damageSource) {
	handleBulletImpact(hitEntity, projectile, damage, damageSource, 0);
    }

    public static void handleBulletImpact(Entity hitEntity, Projectile projectile, float damage, DamageSource damageSource, int knockback) {
	handleBulletImpact(hitEntity, projectile, damage, damageSource, knockback, (p, e) -> {
	}, (p, e) -> {
	});
    }

    public static void handleBulletImpact(Entity hitEntity, Projectile projectile, float damage, DamageSource damageSource, int knockback,
	    BiConsumer<Projectile, Entity> beforeHit, BiConsumer<Projectile, Entity> afterHit) {
	handleBulletImpact(hitEntity, projectile, damage, damageSource, knockback, beforeHit, afterHit, true);
    }

    public static void handleBulletImpact(Entity hitEntity, Projectile projectile, float damage, DamageSource damageSource, int knockback,
	    BiConsumer<Projectile, Entity> beforeHit, BiConsumer<Projectile, Entity> afterHit, Boolean resetHurtTime) {
	if (hitEntity != null && projectile != null && projectile.shootingEntity != null && hitEntity != projectile.shootingEntity) {
	    beforeHit.accept(projectile, hitEntity);
	    if (projectile.isBurning() && !(hitEntity instanceof EntityEnderman)) {
		hitEntity.setFire(5);
	    }
	    if (resetHurtTime) {
		hitEntity.hurtResistantTime = 0;
	    }
	    hitEntity.attackEntityFrom(damageSource, damage);
	    if (knockback > 0) {
		float f1 = MathHelper.sqrt(projectile.motionX * projectile.motionX + projectile.motionZ * projectile.motionZ);

		if (f1 > 0.0F) {
		    hitEntity.addVelocity(projectile.motionX * knockback * 0.6000000238418579D / f1, 0.1D, projectile.motionZ * knockback * 0.6000000238418579D / f1);
		}
	    }
	    afterHit.accept(projectile, hitEntity);
	}
    }

    public static Vec3d getRelativeOffset(EntityLivingBase actor, Vec3d offset) {
	Vec3d look = ModUtils.getVectorForRotation(0, actor.renderYawOffset);
	Vec3d side = look.rotateYaw((float) Math.PI * 0.5f);
	return look.scale(offset.x).add(yVec((float) offset.y)).add(side.scale(offset.z));
    }

    /**
     * Returns the xyz offset using the axis as the relative base
     * 
     * @param axis
     * @param offset
     * @return
     */
    public static Vec3d getAxisOffset(Vec3d axis, Vec3d offset) {
	Vec3d forward = axis.normalize().scale(offset.x);
	Vec3d side = axis.crossProduct(new Vec3d(0, 1, 0)).normalize().scale(offset.z);
	Vec3d up = axis.crossProduct(side).normalize().scale(offset.y);
	return forward.add(side).add(up);
    }

    public static void throwProjectile(EntityLivingBase actor, EntityLivingBase target, Projectile projectile) {
	throwProjectile(actor, target, projectile, 12.0f, 1.6f);
    }

    public static void throwProjectile(EntityLivingBase actor, EntityLivingBase target, Projectile projectile, float inaccuracy, float velocity, Vec3d offset) {
	Vec3d pos = projectile.getPositionVector().add(offset);
	projectile.setPosition(pos.x, pos.y, pos.z);
	throwProjectile(actor, target, projectile, inaccuracy, velocity);
    }

    public static void throwProjectile(EntityLivingBase actor, EntityLivingBase target, Projectile projectile, float inaccuracy, float velocity) {
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
    public static void walkOnWater(EntityLivingBase entity, World world) {
	BlockPos pos = new BlockPos(entity.posX, Math.floor(entity.getEntityBoundingBox().minY), entity.posZ);
	if (world.getBlockState(pos).getBlock() == Blocks.WATER || world.getBlockState(pos).getBlock() == Blocks.FLOWING_WATER) {
	    if (entity.motionY < 0) {
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
    public static float getMobDamage(double baseAttackDamage, double healthScaledAttackFactor, float maxHealth, float health, float level, Element element) {
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
    public static boolean isBetween(int a, int b, int v) {
	if (a > b) {
	    int t = a;
	    a = b;
	    b = t;
	}
	return v >= a && v < b;
    }

    public static int calculateGenerationHeight(World world, int x, int z) {
	return world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).getY();
    }

    /**
     * Returns -1 if the variation is too much
     */
    public static int getAverageGroundHeight(World world, int x, int z, int sizeX, int sizeZ, int maxVariation) {
	sizeX = x + sizeX;
	sizeZ = z + sizeZ;
	int corner1 = calculateGenerationHeight(world, x, z);
	int corner2 = calculateGenerationHeight(world, sizeX, z);
	int corner3 = calculateGenerationHeight(world, x, sizeZ);
	int corner4 = calculateGenerationHeight(world, sizeX, sizeZ);

	int max = Math.max(Math.max(corner3, corner4), Math.max(corner1, corner2));
	int min = Math.min(Math.min(corner3, corner4), Math.min(corner1, corner2));
	if (max - min > maxVariation) {
	    return -1;
	}
	return min;
    }

    public static String getDamageTooltip(float damage) {
	return ModUtils.translateDesc("damage_tooltip", "" + TextFormatting.BLUE + DF_0.format(damage) + TextFormatting.GRAY);
    }

    public static String getCooldownTooltip(float cooldown) {
	return ModUtils.translateDesc("gun_reload_time", TextFormatting.BLUE + "" + DF_0.format(cooldown * 0.05) + TextFormatting.GRAY);
    }

    public static float getEnchantedDamage(ItemStack stack, float level, float damage) {
	float maxPower = ModEnchantments.gun_power.getMaxLevel();
	float power = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.gun_power, stack);
	float maxDamageBonus = (float) Math.pow(ModConfig.balance.progression_scale, 2); // Maximum damage is two levels above
	float enchantmentBonus = 1 + ((power / maxPower) * (maxDamageBonus - 1));
	return damage * enchantmentBonus * LevelHandler.getMultiplierFromLevel(level);
    }

    public static int getGunAmmoUse(float level) {
	return Math.round(LevelHandler.getMultiplierFromLevel(level));
    }

    public static InvasionWorldSaveData getInvasionData(World world) {
	MapStorage storage = world.getMapStorage();
	InvasionWorldSaveData instance = (InvasionWorldSaveData) storage.getOrLoadData(InvasionWorldSaveData.class, InvasionWorldSaveData.DATA_NAME);

	if (instance == null) {
	    instance = new InvasionWorldSaveData();
	    storage.setData(InvasionWorldSaveData.DATA_NAME, instance);
	}
	return instance;
    }

    public static float calculateElementalDamage(Element mobElement, Element damageElement, float amount) {
	if (mobElement.matchesElement(damageElement)) {
	    return amount * 1.5f;
	}
	return amount;
    }

    public static void doSweepAttack(EntityPlayer player, @Nullable EntityLivingBase target, Element element, Consumer<EntityLivingBase> perEntity) {
	doSweepAttack(player, target, element, perEntity, 9, 1);
    }

    public static void doSweepAttack(EntityPlayer player, @Nullable EntityLivingBase target, Element element, Consumer<EntityLivingBase> perEntity, float maxDistanceSq, float areaSize) {
	float attackDamage = (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
	float sweepDamage = Math.min(0.15F + EnchantmentHelper.getSweepingDamageRatio(player), 1) * attackDamage;

	AxisAlignedBB box;

	if (target != null) {
	    box = target.getEntityBoundingBox();
	}
	else {
	    Vec3d center = ModUtils.getAxisOffset(player.getLookVec(), new Vec3d(areaSize * 1.5, 0, 0)).add(player.getPositionEyes(1));
	    box = makeBox(center, center).grow(areaSize * 0.5, areaSize, areaSize * 0.5);
	}

	for (EntityLivingBase entitylivingbase : player.world.getEntitiesWithinAABB(EntityLivingBase.class, box.grow(areaSize, 0.25D, areaSize))) {
	    if (entitylivingbase != player && entitylivingbase != target && !player.isOnSameTeam(entitylivingbase) && player.getDistanceSq(entitylivingbase) < maxDistanceSq) {
		entitylivingbase.knockBack(player, 0.4F, MathHelper.sin(player.rotationYaw * 0.017453292F), (-MathHelper.cos(player.rotationYaw * 0.017453292F)));
		entitylivingbase.attackEntityFrom(ModDamageSource.causeElementalPlayerDamage(player, element), sweepDamage);
		perEntity.accept(entitylivingbase);
	    }
	}

	player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 0.9F);

	// Spawn colored sweep particles
	if (!player.world.isRemote) {
	    Main.network.sendTo(new MessageModParticles(EnumModParticles.SWEEP_ATTACK, box.getCenter(), Vec3d.ZERO, element.sweepColor), (EntityPlayerMP) player);
	}

	Entity particle = new ParticleSpawnerSwordSwing(player.world);
	ModUtils.setEntityPosition(particle, box.getCenter());
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
    public static void circleCallback(float radius, int points, Consumer<Vec3d> particleSpawner) {
	float degrees = 360f / points;
	for (int i = 0; i < points; i++) {
	    double radians = Math.toRadians(i * degrees);
	    Vec3d offset = new Vec3d(Math.sin(radians), Math.cos(radians), 0).scale(radius);
	    particleSpawner.accept(offset);
	}
    }

    /*
     * Does the elemental and leveled calculations for damage
     */
    public static float getArmoredDamage(DamageSource source, float amount, float level, Element element) {
	amount *= ModConfig.balance.mob_armor;

	if (!source.isUnblockable()) {
	    if (element != element.NONE) {
		amount /= ModConfig.balance.elemental_factor;
	    }
	    amount = amount * LevelHandler.getArmorFromLevel(level);
	}

	if (source instanceof IElement) {
	    amount = ModUtils.calculateElementalDamage(element, ((IElement) source).getElement(), amount);
	}

	return amount;
    }

    public static void leapTowards(EntityLivingBase entity, Vec3d target, float horzVel, float yVel) {
	Vec3d dir = target.subtract(entity.getPositionVector()).normalize();
	Vec3d leap = new Vec3d(dir.x, 0, dir.z).normalize().scale(horzVel).add(ModUtils.yVec(yVel));
	entity.motionX += leap.x;
	if (entity.motionY < 0.1) {
	    entity.motionY += leap.y;
	}
	entity.motionZ += leap.z;

	// Normalize to make sure the velocity doesn't go beyond what we expect
	double horzMag = Math.sqrt(Math.pow(entity.motionX, 2) + Math.pow(entity.motionZ, 2));
	double scale = horzVel / horzMag;
	if (scale < 1) {
	    entity.motionX *= scale;
	    entity.motionZ *= scale;
	}
    }

    /**
     * Calls a function that linearly interpolates between two points. Includes both ends of the line
     * 
     * @param start
     * @param end
     * @param points
     * @param callback
     */
    public static void lineCallback(Vec3d start, Vec3d end, int points, BiConsumer<Vec3d, Integer> callback) {
	Vec3d dir = end.subtract(start).scale(1 / (float) (points - 1));
	Vec3d pos = start;
	for (int i = 0; i < points; i++) {
	    callback.accept(pos, i);
	    pos = pos.add(dir);
	}
    }

    public static int tryParseInt(String s, int defaultValue) {
	try {
	    return Integer.parseInt(s);
	}
	catch (NumberFormatException e) {
	    return defaultValue;
	}
    }

    public static float clamp(double value, double min, double max) {
	return (float) Math.max(min, Math.min(max, value));
    }

    public static Vec3d findEntityGroupCenter(Entity mob, double d) {
	Vec3d groupCenter = mob.getPositionVector();
	float numMobs = 1;
	for (EntityLivingBase entity : ModUtils.getEntitiesInBox(mob, new AxisAlignedBB(mob.getPosition()).grow(d))) {
	    if (entity instanceof EntityMaelstromMob && !(entity instanceof EntityMaelstromHealer)) {
		groupCenter = groupCenter.add(entity.getPositionVector());
		numMobs += 1;
	    }
	}

	return groupCenter.scale(1 / numMobs);
    }

    public static boolean isAirBelow(World world, BlockPos pos, int blocksBelow) {
	boolean hasGround = false;
	for (int i = 0; i >= -blocksBelow; i--) {
	    if (!world.isAirBlock(pos.add(new BlockPos(0, i, 0)))) {
		hasGround = true;
	    }
	}
	return !hasGround;
    }

    public static void facePosition(Vec3d pos, Entity entity, float maxYawIncrease, float maxPitchIncrease) {
	double d0 = pos.x - entity.posX;
	double d2 = pos.z - entity.posZ;
	double d1 = pos.y - entity.posY;

	double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
	float f = (float) (MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
	float f1 = (float) (-(MathHelper.atan2(d1, d3) * (180D / Math.PI)));
	entity.rotationPitch = updateRotation(entity.rotationPitch, f1, maxPitchIncrease);
	entity.rotationYaw = updateRotation(entity.rotationYaw, f, maxYawIncrease);
    }

    private static float updateRotation(float angle, float targetAngle, float maxIncrease) {
	float f = MathHelper.wrapDegrees(targetAngle - angle);

	if (f > maxIncrease) {
	    f = maxIncrease;
	}

	if (f < -maxIncrease) {
	    f = -maxIncrease;
	}

	return angle + f;
    }

    /**
     * Rotate a normalized vector around an axis by given degrees https://stackoverflow.com/questions/31225062/rotating-a-vector-by-angle-and-axis-in-java
     * 
     * @param vec
     * @param axis
     * @param degrees
     * @return
     */
    public static Vec3d rotateVector(Vec3d vec, Vec3d axis, double degrees) {
	double theta = Math.toRadians(degrees);
	double x, y, z;
	double u, v, w;
	x = vec.x;
	y = vec.y;
	z = vec.z;
	u = axis.x;
	v = axis.y;
	w = axis.z;
	double xPrime = u * (u * x + v * y + w * z) * (1d - Math.cos(theta))
		+ x * Math.cos(theta)
		+ (-w * y + v * z) * Math.sin(theta);
	double yPrime = v * (u * x + v * y + w * z) * (1d - Math.cos(theta))
		+ y * Math.cos(theta)
		+ (w * x - u * z) * Math.sin(theta);
	double zPrime = w * (u * x + v * y + w * z) * (1d - Math.cos(theta))
		+ z * Math.cos(theta)
		+ (-v * x + u * y) * Math.sin(theta);
	return new Vec3d(xPrime, yPrime, zPrime).normalize();
    }

    // http://www.java-gaming.org/index.php/topic,28253
    public static double unsignedAngle(Vec3d a, Vec3d b) {
	double dot = a.dotProduct(b);
	double cos = dot / (a.lengthVector() * b.lengthVector());
	return Math.acos(cos);
    }

    public static double toPitch(Vec3d vec) {
	double angleBetweenYAxis = Math.toDegrees(unsignedAngle(vec, new Vec3d(0, 1, 0)));
	return angleBetweenYAxis - 90;
    }

    /**
     * Taken from EntityDragon. Destroys blocks in a bounding box. Returns whether it failed to destroy some blocks.
     * 
     * @param box
     * @param world
     * @param entity
     * @return
     */
    public static boolean destroyBlocksInAABB(AxisAlignedBB box, World world, Entity entity) {
	int i = MathHelper.floor(box.minX);
	int j = MathHelper.floor(box.minY);
	int k = MathHelper.floor(box.minZ);
	int l = MathHelper.floor(box.maxX);
	int i1 = MathHelper.floor(box.maxY);
	int j1 = MathHelper.floor(box.maxZ);
	boolean failedToDestroySomeBlocks = false;
	boolean destroyedBlocks = false;

	for (int k1 = i; k1 <= l; ++k1) {
	    for (int l1 = j; l1 <= i1; ++l1) {
		for (int i2 = k; i2 <= j1; ++i2) {
		    BlockPos blockpos = new BlockPos(k1, l1, i2);
		    IBlockState iblockstate = world.getBlockState(blockpos);
		    Block block = iblockstate.getBlock();

		    if (!block.isAir(iblockstate, world, blockpos) && iblockstate.getMaterial() != Material.FIRE) {
			if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(world, entity)) {
			    failedToDestroySomeBlocks = true;
			}
			else {
			    if (block != Blocks.COMMAND_BLOCK && block != Blocks.REPEATING_COMMAND_BLOCK && block != Blocks.CHAIN_COMMAND_BLOCK && block != Blocks.BEDROCK && !(block instanceof BlockLiquid)) {
				destroyedBlocks = world.setBlockToAir(blockpos) || destroyedBlocks;
			    }
			    else {
				failedToDestroySomeBlocks = true;
			    }
			}
		    }
		}
	    }
	}

	if (destroyedBlocks) {
	    world.playSound((EntityPlayer) null, entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, 1.0f + ModRandom.getFloat(0.3f));
	    world.setEntityState(entity, THIRD_PARTICLE_BYTE); // Right now this is just for the Gauntlet particles
	}

	return failedToDestroySomeBlocks;
    }

    /**
     * Pitch yaw converter above doesn't seem to work in all cases, so this is a subsitute function
     * 
     * @param pitch
     * @param yaw
     * @return
     */
    public static Vec3d getLookVec(float pitch, float yaw) {
	Vec3d yawVec = ModUtils.rotateVector(new Vec3d(0, 0, -1), new Vec3d(0, 1, 0), -yaw);
	return ModUtils.rotateVector(yawVec, yawVec.crossProduct(new Vec3d(0, 1, 0)), pitch);
    }

    /**
     * Finds all entities that collide with the line specified by two vectors, excluding a certain entity
     * 
     * @param start
     * @param end
     * @param world
     * @param toExclude
     * @return
     */
    public static List<Entity> findEntitiesInLine(Vec3d start, Vec3d end, World world, @Nullable Entity toExclude) {
	return world.getEntitiesInAABBexcluding(toExclude, new AxisAlignedBB(start.x, start.y, start.z, end.x, end.y, end.z), (e) -> {
	    RayTraceResult raytraceresult = e.getEntityBoundingBox().calculateIntercept(start, end);
	    return raytraceresult != null;
	});
    }

    /**
     * Taken from {@code EntityLivingBase#travel(float, float, float)} The purpose is to let my custom elytras still have the fly into wall damage
     * 
     * @param strafe
     * @param vertical
     * @param forward
     * @param entity
     */
    public static void handleElytraTravel(EntityLivingBase entity) {
	if (entity.isServerWorld() || entity.canPassengerSteer()) {
	    if (!entity.isInWater() || entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isFlying) {
		if (!entity.isInLava() || entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isFlying) {
		    if (entity.motionY > -0.5D) {
			entity.fallDistance = 1.0F;
		    }

		    Vec3d vec3d = entity.getLookVec();
		    float f = entity.rotationPitch * 0.017453292F;
		    double d6 = Math.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z);
		    double d8 = Math.sqrt(entity.motionX * entity.motionX + entity.motionZ * entity.motionZ);
		    double d1 = vec3d.lengthVector();
		    float f4 = MathHelper.cos(f);
		    f4 = (float) ((double) f4 * (double) f4 * Math.min(1.0D, d1 / 0.4D));
		    entity.motionY += -0.08D + f4 * 0.06D;

		    if (entity.motionY < 0.0D && d6 > 0.0D) {
			double d2 = entity.motionY * -0.1D * f4;
			entity.motionY += d2;
			entity.motionX += vec3d.x * d2 / d6;
			entity.motionZ += vec3d.z * d2 / d6;
		    }

		    if (f < 0.0F) {
			double d10 = d8 * (-MathHelper.sin(f)) * 0.04D;
			entity.motionY += d10 * 3.2D;
			entity.motionX -= vec3d.x * d10 / d6;
			entity.motionZ -= vec3d.z * d10 / d6;
		    }

		    if (d6 > 0.0D) {
			entity.motionX += (vec3d.x / d6 * d8 - entity.motionX) * 0.1D;
			entity.motionZ += (vec3d.z / d6 * d8 - entity.motionZ) * 0.1D;
		    }

		    entity.motionX *= 0.9900000095367432D;
		    entity.motionY *= 0.9800000190734863D;
		    entity.motionZ *= 0.9900000095367432D;
		    entity.move(MoverType.SELF, entity.motionX, entity.motionY, entity.motionZ);

		    if (entity.collidedHorizontally && !entity.world.isRemote) {
			double d11 = Math.sqrt(entity.motionX * entity.motionX + entity.motionZ * entity.motionZ);
			double d3 = d8 - d11;
			float f5 = (float) (d3 * 10.0D - 3.0D);

			if (f5 > 0.0F) {
			    entity.playSound(SoundEvents.ENTITY_PLAYER_SMALL_FALL, 1.0F, 1.0F);
			    entity.attackEntityFrom(DamageSource.FLY_INTO_WALL, f5);
			}
		    }
		}
	    }
	}
    }

    /**
     * Attempts to spawn a mob around the actor within a certain range. Returns null if the spawning failed. Otherwise returns the spawned mob
     * 
     * @param actor
     * @param target
     * @param mob
     * @param range
     * @return
     */
    public static EntityLeveledMob spawnMob(World world, BlockPos pos, float level, MobSpawnData[] mobs, int weights[], BlockPos range) {
	Random random = new Random();
	MobSpawnData data = ModRandom.choice(mobs, random, weights).next();
	int tries = 100;
	for (int i = 0; i < tries; i++) {
	    // Find a random position to spawn the enemy
	    int i1 = pos.getX() + ModRandom.range(0, range.getX()) * ModRandom.randSign();
	    int k1 = pos.getZ() + ModRandom.range(0, range.getY()) * ModRandom.randSign();

	    int y = range.getY();
	    while (y > -range.getY()) {
		if (!world.isAirBlock(new BlockPos(i1, pos.getY() + y - 1, k1))) {
		    break;
		}
		y--;
	    }

	    int j1 = pos.getY() + y;

	    if (world.getBlockState(new BlockPos(i1, j1 - 1, k1)).isSideSolid(world, new BlockPos(i1, j1 - 1, k1), net.minecraft.util.EnumFacing.UP)) {
		Entity mob = EntityList.createEntityByIDFromName(new ResourceLocation(data.mobId), world);

		if (mob == null) {
		    System.out.println("Failed to spawn entity with id" + data.mobId);
		    return null;
		}

		mob.setLocationAndAngles(i1, j1, k1, random.nextFloat() * 360.0F, 0.0F);

		// Make sure that the position is a proper spawning position
		if (!world.isAnyPlayerWithinRangeAt(i1, j1, k1, 3.0D) && world.getCollisionBoxes(mob, mob.getEntityBoundingBox()).isEmpty() && !world.containsAnyLiquid(mob.getEntityBoundingBox())) {

		    if (mob instanceof EntityLeveledMob) {

			EntityLeveledMob leveledMob = (EntityLeveledMob) mob;

			// Spawn the entity
			world.spawnEntity(leveledMob);
			leveledMob.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(mob)), (IEntityLivingData) null);
			leveledMob.spawnExplosionParticle();

			leveledMob.setElement(ModRandom.choice(data.possibleElements, random, data.elementalWeights).next());
			leveledMob.setLevel(level);

			return leveledMob;
		    }
		}
	    }
	}
	return null;
    }

    public static void setEntityPosition(Entity entity, Vec3d vec) {
	entity.setPosition(vec.x, vec.y, vec.z);
    }

    public static void setEntityVelocity(Entity entity, Vec3d vec) {
	entity.setVelocity(vec.x, vec.y, vec.z);
    }

    public static void addEntityVelocity(Entity entity, Vec3d vec) {
	entity.addVelocity(vec.x, vec.y, vec.z);
    }

    public static Vec3d getEntityVelocity(Entity entity) {
	return new Vec3d(entity.motionX, entity.motionY, entity.motionZ);
    }

    /**
     * Removes all entity ai of a certain class type.
     */
    public static <T extends EntityAIBase> void removeTaskOfType(EntityAITasks tasks, Class<T> clazz) {
	Set<EntityAIBase> toRemove = Sets.newHashSet();

	for (EntityAITaskEntry entry : tasks.taskEntries) {
	    if (clazz.isInstance(entry.action)) {
		toRemove.add(entry.action);
	    }
	}

	for (EntityAIBase ai : toRemove) {
	    tasks.removeTask(ai);
	}
    }

    /**
     * Finds the first solid block below the specified position and returns the position of that block
     */
    public static BlockPos findGroundBelow(World world, BlockPos pos) {
	for (int i = pos.getY(); i > 0; i--) {
	    BlockPos tempPos = new BlockPos(pos.getX(), i, pos.getZ());
	    if (world.getBlockState(tempPos).isFullCube()) {
		return tempPos;
	    }
	}
	return new BlockPos(pos.getX(), 0, pos.getZ());
    }

    /**
     * Because the stupid constructor is client side only
     */
    public static AxisAlignedBB makeBox(Vec3d pos1, Vec3d pos2) {
	return new AxisAlignedBB(pos1.x, pos1.y, pos1.z, pos2.x, pos2.y, pos2.z);
    }

    /**
     * Lets entities see you through glass
     */
    public static boolean canEntityBeSeen(Entity viewer, Entity target) {
	RayTraceResult result = viewer.world.rayTraceBlocks(viewer.getPositionEyes(1), target.getPositionEyes(1), false, true, false);
	if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
	    IBlockState blockState = viewer.world.getBlockState(result.getBlockPos());
	    if (blockState.isFullCube()) {
		return true;
	    }
	}
	return true;
    }
}
