package com.barribob.MaelstromMod.sounds;

import com.barribob.MaelstromMod.init.ModDimensions;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DarkNexusWindSound extends MovingSound {
    private final EntityPlayerSP player;
    private int time;

    public DarkNexusWindSound(EntityPlayerSP player) {
        super(SoundEvents.ITEM_ELYTRA_FLYING, SoundCategory.PLAYERS);
        this.player = player;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 0.1F;
    }

    @Override
    public void update() {
        ++this.time;

        if (!this.player.isDead && (this.time <= 20 || this.player.dimension == ModDimensions.DARK_NEXUS.getId())) {
            this.xPosF = (float) this.player.posX;
            this.yPosF = (float) this.player.posY;
            this.zPosF = (float) this.player.posZ;
            float velocity = MathHelper.sqrt(this.player.motionX * this.player.motionX + this.player.motionZ * this.player.motionZ + this.player.motionY * this.player.motionY);
            float f1 = velocity / 2.0F;

            this.volume = 0.1f + MathHelper.clamp(f1 * f1, 0.0F, 1.0F);

            if (this.time < 20) {
                this.volume = 0.0F;
            } else if (this.time < 40) {
                this.volume = (float) (this.volume * ((this.time - 20) / 20.0D));
            }

            if (this.volume > 0.8F) {
                this.pitch = 1.0F + (this.volume - 0.8F);
            } else {
                this.pitch = 1.0F;
            }
        } else {
            this.donePlaying = true;
        }
    }
}