package com.barribob.MaelstromMod.invasion;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class InvasionStorage implements IStorage<IInvasion> {
    public static final String invaded = "invaded";
    public static final String timeUntilInvasion = "invasion_time";

    @Override
    public NBTBase writeNBT(Capability<IInvasion> capability, IInvasion instance, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setBoolean(invaded, instance.isInvaded());
        nbt.setLong(timeUntilInvasion, instance.getInvasionTime());
        return nbt;
    }

    @Override
    public void readNBT(Capability<IInvasion> capability, IInvasion instance, EnumFacing side, NBTBase nbt) {
        if (nbt instanceof NBTTagCompound) {
            NBTTagCompound compound = (NBTTagCompound) nbt;
            if (compound.hasKey(invaded) && compound.hasKey(timeUntilInvasion)) {
                instance.setInvaded(compound.getBoolean(invaded));
                instance.setInvasionTime(compound.getLong(timeUntilInvasion));
            }
        }
    }
}
