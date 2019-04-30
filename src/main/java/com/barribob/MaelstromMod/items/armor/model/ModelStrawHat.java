package com.barribob.MaelstromMod.items.armor.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public class ModelStrawHat extends ModelBiped {
    public ModelRenderer hat4;
    public ModelRenderer strap;
    public ModelRenderer hat1;
    public ModelRenderer hat2;
    public ModelRenderer hat3;

    public ModelStrawHat() {
	this.textureWidth = 64;
        this.textureHeight = 128;
        this.strap = new ModelRenderer(this, 40, 64);
        this.strap.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strap.addBox(-4.5F, -8.5F, -2.0F, 9, 9, 1, 0.0F);
        this.hat4 = new ModelRenderer(this, 24, 64);
        this.hat4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hat4.addBox(-2.0F, -11.0F, -2.0F, 4, 1, 4, 0.0F);
        this.hat3 = new ModelRenderer(this, 30, 93);
        this.hat3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hat3.addBox(-4.0F, -10.0F, -4.0F, 8, 1, 8, 0.0F);
        this.hat2 = new ModelRenderer(this, 0, 93);
        this.hat2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hat2.addBox(-5.0F, -9.0F, -5.0F, 10, 1, 10, 0.0F);
        this.hat1 = new ModelRenderer(this, 0, 80);
        this.hat1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hat1.addBox(-6.0F, -8.0F, -6.0F, 12, 1, 12, 0.0F);
        this.bipedHead.addChild(this.strap);
        this.bipedHead.addChild(this.hat4);
        this.bipedHead.addChild(this.hat3);
        this.bipedHead.addChild(this.hat2);
        this.bipedHead.addChild(this.hat1);
    }
}
