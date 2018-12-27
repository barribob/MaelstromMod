package com.barribob.MaelstromMod.init;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.entities.EntityBeast;
import com.barribob.MaelstromMod.entity.entities.EntityDreamElk;
import com.barribob.MaelstromMod.entity.entities.EntityHorror;
import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.entity.entities.TileEntityMalestromSpawner;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBeastAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileHorrorAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileShadeAttack;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * 
 * Lists all of the entities in the mod
 *
 */
public class ModEntities
{
    public static final int SHADE_ID = 100;
    public static final int HORROR_ID = 101;
    public static final int DREAM_ELK_ID = 102;
    public static final int BEAST_ID = 103;

    public static final int SHADE_ATTACK_ID = 200;
    public static final int HORROR_ATTACK_ID = 201;
    public static final int BEAST_ATTACK_ID = 202;

    public static void registerEntities()
    {
	registerEntity("shade", EntityShade.class, SHADE_ID, 30, 6433126, 3221816);
	registerEntity("horror", EntityHorror.class, HORROR_ID, 30, 6433126, 3221816);
	registerEntity("dream_elk", EntityDreamElk.class, DREAM_ELK_ID, 30, 7248383, 7236306);
	registerEntity("beast", EntityBeast.class, BEAST_ID, 30, 6433126, 3221816);
	registerProjectile("shade_attack", ProjectileShadeAttack.class, SHADE_ATTACK_ID, 30);
	registerProjectile("horror_attack", ProjectileHorrorAttack.class, HORROR_ATTACK_ID, 30);
	registerProjectile("beast_attack", ProjectileBeastAttack.class, BEAST_ATTACK_ID, 30);
	registerTileEntity(TileEntityMalestromSpawner.class, "spawner");
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2)
    {
	EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, Main.instance, range, 1, true, color1,
		color2);
    }

    private static void registerProjectile(String name, Class<? extends Entity> entity, int id, int range)
    {
	EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, Main.instance, range, 1, true);
    }
    
    private static void registerTileEntity(Class<? extends TileEntity> entity, String name)
    {
	GameRegistry.registerTileEntity(entity, new ResourceLocation(Reference.MOD_ID + ":" + name));
    }
}
