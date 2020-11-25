package com.barribob.MaelstromMod.items.armor.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

/**
 * ModelPlayer - Either Mojang or a mod author
 * Created using Tabula 7.0.0
 */
public class ModelNyanHelmet extends ModelBiped {
    public ModelRenderer helmet;
    public ModelRenderer rightEar1;
    public ModelRenderer rightEar2;
    public ModelRenderer rightEar1_1;
    public ModelRenderer rightEar2_1;

    public ModelNyanHelmet() {
        this.textureWidth = 64;
        this.textureHeight = 128;
        this.rightEar2 = new ModelRenderer(this, 30, 0);
        this.rightEar2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightEar2.addBox(2.5F, -11.5F, -2.5F, 2, 3, 1, 0.0F);
        this.rightEar2_1 = new ModelRenderer(this, 24, 0);
        this.rightEar2_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightEar2_1.addBox(-4.5F, -11.5F, -2.5F, 2, 3, 1, 0.0F);
        this.rightEar1_1 = new ModelRenderer(this, 0, 0);
        this.rightEar1_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightEar1_1.addBox(-2.5F, -10.5F, -2.5F, 1, 2, 1, 0.0F);
        this.rightEar1 = new ModelRenderer(this, 4, 0);
        this.rightEar1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightEar1.addBox(1.5F, -10.5F, -2.5F, 1, 2, 1, 0.0F);
        this.helmet = new ModelRenderer(this, 23, 7);
        this.helmet.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.helmet.addBox(-4.5F, -8.5F, -4.5F, 9, 9, 9, 0.0F);
        this.bipedHead.addChild(this.rightEar2);
        this.bipedHead.addChild(this.rightEar2_1);
        this.bipedHead.addChild(this.rightEar1_1);
        this.bipedHead.addChild(this.rightEar1);
        this.bipedHead.addChild(this.helmet);
    }
}
