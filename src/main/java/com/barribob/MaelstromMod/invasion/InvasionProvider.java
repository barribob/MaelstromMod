package com.barribob.MaelstromMod.invasion;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class InvasionProvider implements ICapabilitySerializable<NBTBase> {
    @CapabilityInject(IInvasion.class)
    public static final Capability<IInvasion> INVASION = null;

    private IInvasion instance = INVASION.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability.equals(INVASION);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability.equals(INVASION) ? INVASION.<T>cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return INVASION.getStorage().writeNBT(INVASION, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        INVASION.getStorage().readNBT(INVASION, instance, null, nbt);
    }

}
