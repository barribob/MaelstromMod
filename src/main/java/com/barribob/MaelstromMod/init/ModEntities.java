package com.barribob.MaelstromMod.init;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.entity.projectile.ProjectileShadeAttack;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
 * 
 * Lists all of the entities in the mod
 *
 */
public class ModEntities
{
    public static final int ENTITY_SHADE = 100;
    public static final int ENTITY_SHADE_ATTACK = 200;

    public static void registerEntities()
    {
	registerEntity("shade", EntityShade.class, ENTITY_SHADE, 20, 6433126, 3221816);
	registerProjectile("shade_attack", ProjectileShadeAttack.class, ENTITY_SHADE_ATTACK, 30);
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
}
