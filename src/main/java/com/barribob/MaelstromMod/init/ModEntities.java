package com.barribob.MaelstromMod.init;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.entities.EntityAzureGolem;
import com.barribob.MaelstromMod.entity.entities.EntityAzureVillager;
import com.barribob.MaelstromMod.entity.entities.EntityBeast;
import com.barribob.MaelstromMod.entity.entities.EntityDreamElk;
import com.barribob.MaelstromMod.entity.entities.EntityHorror;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromIllager;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMage;
import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBeastAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBullet;
import com.barribob.MaelstromMod.entity.projectile.ProjectileHorrorAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMaelstromCannon;
import com.barribob.MaelstromMod.entity.projectile.ProjectileQuake;
import com.barribob.MaelstromMod.entity.projectile.ProjectileShadeAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileWillOTheWisp;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityDisappearingSpawner;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityMalestromSpawner;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityMegaStructure;
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
    public static final int MAELSTROM_ILLAGER_ID = 104;
    public static final int AZURE_VILLAGER_ID = 105;
    public static final int MAELSTROM_MAGE_ID = 106;
    public static final int AZURE_GOLEM_ID = 107;

    public static final int PROJECTILE_ID = 200;
    public static final int SHADE_ATTACK_ID = 201;
    public static final int HORROR_ATTACK_ID = 202;
    public static final int BEAST_ATTACK_ID = 203;
    public static final int BULLET_ID = 204;
    public static final int MAELSTROM_CANNON_ID = 205;
    public static final int WILL_O_THE_WISP_ID = 206;
    public static final int QUAKE_ID = 207;

    public static void registerEntities()
    {
	registerEntity("shade", EntityShade.class, SHADE_ID, 30, 6433126, 3221816);
	registerEntity("horror", EntityHorror.class, HORROR_ID, 30, 6433126, 3221816);
	registerEntity("dream_elk", EntityDreamElk.class, DREAM_ELK_ID, 30, 7248383, 7236306);
	registerEntity("beast", EntityBeast.class, BEAST_ID, 30, 6433126, 3221816);
	registerEntity("maelstrom_illager", EntityMaelstromIllager.class, MAELSTROM_ILLAGER_ID, 30, 6433126, 3221816);
	registerEntity("azure_villager", EntityAzureVillager.class, AZURE_VILLAGER_ID, 30, 7248383, 7236306);
	registerEntity("maelstrom_mage", EntityMaelstromMage.class, MAELSTROM_MAGE_ID, 30, 6433126, 3221816);
	registerEntity("azure_golem", EntityAzureGolem.class, AZURE_GOLEM_ID, 30, 7248383, 7236306);

	registerProjectile("projectile", Projectile.class, PROJECTILE_ID, 30);
	registerProjectile("shade_attack", ProjectileShadeAttack.class, SHADE_ATTACK_ID, 30);
	registerProjectile("horror_attack", ProjectileHorrorAttack.class, HORROR_ATTACK_ID, 30);
	registerProjectile("beast_attack", ProjectileBeastAttack.class, BEAST_ATTACK_ID, 30);
	registerProjectile("bullet", ProjectileBullet.class, BULLET_ID, 30);
	registerProjectile("maelstrom_cannon", ProjectileMaelstromCannon.class, MAELSTROM_CANNON_ID, 30);
	registerProjectile("will-o-the-wisp", ProjectileWillOTheWisp.class, WILL_O_THE_WISP_ID, 30);
	registerProjectile("quake", ProjectileQuake.class, QUAKE_ID, 30);

	registerTileEntity(TileEntityMalestromSpawner.class, "spawner");
	registerTileEntity(TileEntityDisappearingSpawner.class, "maelstrom_spawner");
	registerTileEntity(TileEntityMegaStructure.class, "mega_structure");
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
