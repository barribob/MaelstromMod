package com.barribob.MaelstromMod.mana;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ManaStorage implements IStorage<IMana>
{
    @Override
    public NBTBase writeNBT(Capability<IMana> capability, IMana instance, EnumFacing side)
    {
	return new NBTTagFloat(instance.getMana());
    }

    @Override
    public void readNBT(Capability<IMana> capability, IMana instance, EnumFacing side, NBTBase nbt)
    {
	instance.set(((NBTTagFloat) nbt).getFloat());
    }
}
