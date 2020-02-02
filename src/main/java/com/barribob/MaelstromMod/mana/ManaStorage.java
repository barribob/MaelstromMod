package com.barribob.MaelstromMod.mana;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ManaStorage implements IStorage<IMana>
{
    private static final String locked = "locked";
    private static final String mana = "mana";
    private static final String recentlyConsumed = "recentlyConsumed";

    @Override
    public NBTBase writeNBT(Capability<IMana> capability, IMana instance, EnumFacing side)
    {
	NBTTagCompound nbt = new NBTTagCompound();
	nbt.setFloat(mana, instance.getMana());
	nbt.setBoolean(locked, instance.isLocked());
	nbt.setBoolean(recentlyConsumed, instance.isRecentlyConsumed());
	return nbt;
    }

    @Override
    public void readNBT(Capability<IMana> capability, IMana instance, EnumFacing side, NBTBase nbt)
    {
	if (nbt instanceof NBTTagCompound)
	{
	    NBTTagCompound compound = (NBTTagCompound) nbt;
	    if (compound.hasKey(mana) && compound.hasKey(locked))
	    {
		instance.setLocked(compound.getBoolean(locked));
		instance.set(compound.getFloat(mana));
		instance.setRecentlyConsumed(compound.getBoolean(recentlyConsumed));
	    }
	}
    }
}
