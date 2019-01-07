package com.barribob.MaelstromMod.entity.entities;

import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityMaelstromStray extends EntityStray
{
    public EntityMaelstromStray(World worldIn)
    {
	super(worldIn);
    }
    
    protected EntityArrow getArrow(float p_190726_1_)
    {
        EntityArrow entityarrow = super.getArrow(p_190726_1_);

        if (entityarrow instanceof EntityTippedArrow)
        {
            ((EntityTippedArrow)entityarrow).addEffect(new PotionEffect(MobEffects.BLINDNESS, 300));
        }

        return entityarrow;
    }
}
