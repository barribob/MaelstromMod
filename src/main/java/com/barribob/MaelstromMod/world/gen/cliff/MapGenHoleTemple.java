package com.barribob.MaelstromMod.world.gen.cliff;

import com.barribob.MaelstromMod.util.GenUtils;
import com.barribob.MaelstromMod.world.dimension.WorldChunkGenerator;
import com.barribob.MaelstromMod.world.gen.MapGenModStructure;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.Random;

public class MapGenHoleTemple extends MapGenModStructure {
    WorldChunkGenerator gen;

    public MapGenHoleTemple(int spacing, int offset, int odds, WorldChunkGenerator gen) {
        super(spacing, offset, odds);
        this.gen = gen;
    }

    @Override
    public String getStructureName() {
        return "Cliff Hole Temple";
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        return new MapGenHoleTemple.Start(this.world, this.rand, chunkX, chunkZ, gen);
    }

    public static class Start extends StructureStart {
        WorldChunkGenerator gen;

        public Start() {
        }

        public Start(World worldIn, Random random, int chunkX, int chunkZ, WorldChunkGenerator gen) {
            super(chunkX, chunkZ);
            this.gen = gen;
            this.create(worldIn, random, chunkX, chunkZ);
        }

        private void create(World worldIn, Random rnd, int chunkX, int chunkZ) {
            Random random = new Random(chunkX + chunkZ * 10387313);
            Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];

            BlockPos blockpos = new BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8);
            HoleTempleTemplate template = new HoleTempleTemplate(worldIn.getSaveHandler().getStructureTemplateManager(), "hole_temple", blockpos, rotation, chunkZ, true);
            int y = GenUtils.getGroundHeight(template, gen, rotation);
            template.offset(0, y - 21, 0);

            this.components.add(template);
            this.updateBoundingBox();
        }
    }
}
