package com.barribob.MaelstromMod.invasion;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.util.GenUtils;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.util.teleporter.NexusToOverworldTeleporter;
import com.barribob.MaelstromMod.world.gen.WorldGenCustomStructures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

public class InvasionUtils {
    public static int TOWER_RADIUS = 50;
    public static int NUM_CIRCLE_POINTS = 16;
    public static int BED_DISTANCE = 75;
    public static int MAX_LAND_VARIATION = 8;

    public static boolean trySpawnInvasionTower(BlockPos centralPos, World world) {
        BlockPos structureSize = WorldGenCustomStructures.invasionTower.getSize(world);
        BlockPos halfStructureSize = new BlockPos(structureSize.getX() * 0.5f, 0, structureSize.getZ() * 0.5f);
        BlockPos quarterStructureSize = new BlockPos(halfStructureSize.getX() * 0.5f, 0, halfStructureSize.getZ() * 0.5f);

        Function<Vec3d, BlockPos> toTowerPos = pos -> {
            BlockPos pos2 = centralPos.add(pos.x, 0, pos.y).subtract(halfStructureSize);
            int y = ModUtils.getAverageGroundHeight(world, pos2.getX() + quarterStructureSize.getX(),
                    pos2.getZ() + quarterStructureSize.getZ(), halfStructureSize.getX(), halfStructureSize.getZ(), MAX_LAND_VARIATION);
            return new BlockPos(pos2.getX(), y, pos2.getZ());
        };

        Predicate<BlockPos> notTooHigh = pos -> pos.getY() <= NexusToOverworldTeleporter.yPortalOffset - structureSize.getY();

        Predicate<BlockPos> inLiquid = pos -> !world.containsAnyLiquid(new AxisAlignedBB(pos, structureSize.add(pos)));

        Predicate<BlockPos> noBaseNearby = pos -> world.playerEntities.stream().noneMatch((p) -> {
            if (p.getBedLocation() == null || world.getSpawnPoint().equals(p.getBedLocation())) {
                return false;
            }
            return pos.distanceSq(p.getBedLocation()) < Math.pow(BED_DISTANCE, 2);
        });

        BinaryOperator<BlockPos> minVariation = (prevPos, newPos) -> {
            int prevVariation = GenUtils.getTerrainVariation(world, prevPos.getX(), prevPos.getZ(), prevPos.getX(), structureSize.getZ());
            int newVariation = GenUtils.getTerrainVariation(world, newPos.getX(), newPos.getZ(), newPos.getX(), structureSize.getZ());
            return prevVariation > newVariation ? newPos : prevPos;
        };

        Optional<BlockPos> towerPos = ModUtils.circlePoints(TOWER_RADIUS, NUM_CIRCLE_POINTS).stream()
                .map(toTowerPos)
                .filter(pos -> pos.getY() != -1)
                .filter(notTooHigh)
                .filter(inLiquid)
                .filter(noBaseNearby)
                .reduce(minVariation);

        towerPos.ifPresent(blockPos -> WorldGenCustomStructures.invasionTower.generateStructure(world, blockPos, Rotation.NONE));
        return towerPos.isPresent();
    }

    public static EntityPlayer getPlayerClosestToOrigin(World world) {
        return world.playerEntities.stream().reduce(world.playerEntities.get(0),
                (p1, p2) -> p1.getDistanceSq(BlockPos.ORIGIN) < p2.getDistanceSq(BlockPos.ORIGIN) ? p1 : p2);
    }

    public static void sendInvasionMessage(World world, String translation) {
        world.playerEntities.forEach((p) -> p.sendMessage(
                new TextComponentString("" + TextFormatting.DARK_PURPLE + new TextComponentTranslation(translation).getFormattedText())));
    }

    public static MultiInvasionWorldSavedData getInvasionData(World world) {
        MapStorage storage = world.getMapStorage();
        MultiInvasionWorldSavedData instance = (MultiInvasionWorldSavedData) storage.getOrLoadData(MultiInvasionWorldSavedData.class, MultiInvasionWorldSavedData.DATA_NAME);

        if (instance == null) {
            instance = new MultiInvasionWorldSavedData();
            storage.setData(MultiInvasionWorldSavedData.DATA_NAME, instance);
        }
        return instance;
    }

    public static boolean hasMultipleInvasionsConfigured() {
        return Main.invasionsConfig.getConfigList("invasions").size() > 0;
    }
}