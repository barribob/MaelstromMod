package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityDreamElk;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * ModelHorse - Either Mojang or a mod author Created using Tabula 7.0.0
 */
public class ModelDreamElk extends ModelBase
{
    public ModelRenderer frontLeftThigh;
    public ModelRenderer frontRightThigh;
    public ModelRenderer body;
    public ModelRenderer backLeftThigh;
    public ModelRenderer neck;
    public ModelRenderer tail1;
    public ModelRenderer backRightThigh;
    public ModelRenderer frontLeftLeg;
    public ModelRenderer frontRightLeg;
    public ModelRenderer backLeftLeg;
    public ModelRenderer head;
    public ModelRenderer mane;
    public ModelRenderer upperSnout;
    public ModelRenderer lowerSnout;
    public ModelRenderer rightEar;
    public ModelRenderer leftEar;
    public ModelRenderer lefthorn1;
    public ModelRenderer horn2;
    public ModelRenderer horn3;
    public ModelRenderer horn4;
    public ModelRenderer horn5;
    public ModelRenderer horn6;
    public ModelRenderer horn7;
    public ModelRenderer horn8;
    public ModelRenderer horn9;
    public ModelRenderer horn10;
    public ModelRenderer horn11;
    public ModelRenderer horn12;
    public ModelRenderer horn13;
    public ModelRenderer horn14;
    public ModelRenderer horn15;
    public ModelRenderer horn16;
    public ModelRenderer righthorn16;
    public ModelRenderer righthorn15;
    public ModelRenderer righthorn2;
    public ModelRenderer righthorn14;
    public ModelRenderer righthorn3;
    public ModelRenderer righthorn4;
    public ModelRenderer righthorn5;
    public ModelRenderer righthorn6;
    public ModelRenderer righthorn13;
    public ModelRenderer righthorn7;
    public ModelRenderer righthorn8;
    public ModelRenderer righthorn9;
    public ModelRenderer righthorn10;
    public ModelRenderer righthorn11;
    public ModelRenderer righthorn12;
    public ModelRenderer backRightLeg;

    /**
     * Used to keep track of animation
     */
    private float neckRotationX;
    float defaultNeckRotation = 0.7285004297824331F;

    public ModelDreamElk()
    {
	this.textureWidth = 128;
	this.textureHeight = 128;
	this.righthorn7 = new ModelRenderer(this, 0, 0);
	this.righthorn7.setRotationPoint(-5.7F, -4.5F, 11.6F);
	this.righthorn7.addBox(-4.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(righthorn7, -0.091106186954104F, 1.0927506446736497F, 2.1855012893472994F);
	this.horn10 = new ModelRenderer(this, 0, 0);
	this.horn10.setRotationPoint(6.5F, -3.7F, 14.2F);
	this.horn10.addBox(-0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(horn10, 0.5462880558742251F, -1.5481070465189704F, -2.1855012893472994F);
	this.frontRightLeg = new ModelRenderer(this, 60, 41);
	this.frontRightLeg.setRotationPoint(0.0F, 7.0F, 0.0F);
	this.frontRightLeg.addBox(-1.1F, 0.0F, -1.6F, 3, 8, 3, 0.0F);
	this.horn7 = new ModelRenderer(this, 0, 0);
	this.horn7.setRotationPoint(5.7F, -4.5F, 11.6F);
	this.horn7.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(horn7, 0.091106186954104F, -1.0927506446736497F, -2.1855012893472994F);
	this.horn14 = new ModelRenderer(this, 0, 80);
	this.horn14.setRotationPoint(7.4F, -5.7F, 3.7F);
	this.horn14.addBox(0.0F, -0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(horn14, 0.0F, -0.22759093446006054F, -1.6390387005478748F);
	this.horn4 = new ModelRenderer(this, 0, 86);
	this.horn4.setRotationPoint(9.2F, -7.2F, 6.5F);
	this.horn4.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
	this.setRotateAngle(horn4, 0.0F, -0.7740535232594852F, -1.3203415791337103F);
	this.horn16 = new ModelRenderer(this, 0, 86);
	this.horn16.setRotationPoint(1.1F, -4.8F, 1.5F);
	this.horn16.addBox(-0.7F, -0.0F, 0.0F, 5, 1, 1, 0.0F);
	this.setRotateAngle(horn16, -0.091106186954104F, 0.4553564018453205F, -1.593485607070823F);
	this.righthorn13 = new ModelRenderer(this, 0, 84);
	this.righthorn13.setRotationPoint(-5.7F, -4.6F, 10.8F);
	this.righthorn13.addBox(-4.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(righthorn13, -0.091106186954104F, 0.27314402793711257F, 2.1855012893472994F);
	this.tail1 = new ModelRenderer(this, 44, 0);
	this.tail1.setRotationPoint(0.0F, 3.0F, 14.0F);
	this.tail1.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 3, 0.0F);
	this.setRotateAngle(tail1, -1.3089969389957472F, 0.0F, 0.0F);
	this.lefthorn1 = new ModelRenderer(this, 0, 0);
	this.lefthorn1.setRotationPoint(0.0F, -2.0F, 0.0F);
	this.lefthorn1.addBox(-5.0F, -5.0F, 1.5F, 10, 2, 2, 0.0F);
	this.backLeftThigh = new ModelRenderer(this, 78, 29);
	this.backLeftThigh.setRotationPoint(4.0F, 9.0F, 11.0F);
	this.backLeftThigh.addBox(-2.5F, -2.0F, -2.5F, 4, 9, 5, 0.0F);
	this.rightEar = new ModelRenderer(this, 60, 0);
	this.rightEar.setRotationPoint(0.0F, 0.0F, -0.0F);
	this.rightEar.addBox(-4.25F, -4.0F, 2.7F, 2, 2, 1, 0.0F);
	this.backLeftLeg = new ModelRenderer(this, 78, 43);
	this.backLeftLeg.setRotationPoint(0.0F, 7.0F, 0.0F);
	this.backLeftLeg.addBox(-2.0F, 0.0F, -1.5F, 3, 8, 3, 0.0F);
	this.head = new ModelRenderer(this, 73, 0);
	this.head.setRotationPoint(0.0F, -8.1F, 0.0F);
	this.head.addBox(-2.5F, -5.1F, -3.2F, 5, 5, 7, 0.0F);
	this.setRotateAngle(head, -0.36302848441482055F, 0.0F, 0.0F);
	this.horn3 = new ModelRenderer(this, 0, 0);
	this.horn3.setRotationPoint(7.8F, -5.7F, 3.7F);
	this.horn3.addBox(0.0F, -0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(horn3, 0.0F, -0.8651597102135892F, -0.8196066167365371F);
	this.frontRightThigh = new ModelRenderer(this, 60, 29);
	this.frontRightThigh.setRotationPoint(-4.0F, 9.0F, -8.0F);
	this.frontRightThigh.addBox(-1.1F, -1.0F, -2.1F, 3, 8, 4, 0.0F);
	this.frontLeftLeg = new ModelRenderer(this, 44, 41);
	this.frontLeftLeg.setRotationPoint(0.0F, 7.0F, 0.0F);
	this.frontLeftLeg.addBox(-1.9F, 0.0F, -1.6F, 3, 8, 3, 0.0F);
	this.righthorn16 = new ModelRenderer(this, 0, 86);
	this.righthorn16.setRotationPoint(-1.1F, -4.8F, 1.5F);
	this.righthorn16.addBox(-0.7F, -1.0F, 0.0F, 5, 1, 1, 0.0F);
	this.setRotateAngle(righthorn16, 0.136659280431156F, 0.4553564018453205F, -1.593485607070823F);
	this.righthorn5 = new ModelRenderer(this, 0, 0);
	this.righthorn5.setRotationPoint(-7.6F, -5.9F, 4.8F);
	this.righthorn5.addBox(-4.0F, -0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(righthorn5, 0.0F, 1.9123572614101867F, 0.8196066167365371F);
	this.upperSnout = new ModelRenderer(this, 24, 18);
	this.upperSnout.setRotationPoint(0.0F, 0.02F, 0.02F);
	this.upperSnout.addBox(-2.0F, -5.0F, -6.5F, 4, 2, 5, 0.0F);
	this.horn5 = new ModelRenderer(this, 0, 0);
	this.horn5.setRotationPoint(7.6F, -5.9F, 4.8F);
	this.horn5.addBox(0.0F, -0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(horn5, 0.0F, -1.9123572614101867F, -0.8196066167365371F);
	this.righthorn4 = new ModelRenderer(this, 0, 82);
	this.righthorn4.setRotationPoint(-9.2F, -7.2F, 6.5F);
	this.righthorn4.addBox(-5.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
	this.setRotateAngle(righthorn4, 0.0F, 0.7740535232594852F, 1.3203415791337103F);
	this.righthorn10 = new ModelRenderer(this, 0, 0);
	this.righthorn10.setRotationPoint(-6.3F, -3.4F, 14.2F);
	this.righthorn10.addBox(-4.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(righthorn10, -0.5462880558742251F, 1.5481070465189704F, 2.1855012893472994F);
	this.righthorn12 = new ModelRenderer(this, 0, 84);
	this.righthorn12.setRotationPoint(-6.8F, -3.7F, 18.2F);
	this.righthorn12.addBox(-4.0F, -0.2F, -0.1F, 4, 1, 1, 0.0F);
	this.setRotateAngle(righthorn12, -0.8196066167365371F, 1.7704619932230479F, 1.7756979809790308F);
	this.body = new ModelRenderer(this, 0, 34);
	this.body.setRotationPoint(0.0F, 11.0F, 9.0F);
	this.body.addBox(-5.0F, -8.0F, -19.0F, 10, 10, 24, 0.0F);
	this.righthorn3 = new ModelRenderer(this, 0, 0);
	this.righthorn3.setRotationPoint(-7.8F, -5.7F, 3.7F);
	this.righthorn3.addBox(-4.0F, -0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(righthorn3, 0.0F, 0.8651597102135892F, 0.8196066167365371F);
	this.righthorn9 = new ModelRenderer(this, 0, 0);
	this.righthorn9.setRotationPoint(-5.6F, -4.3F, 10.8F);
	this.righthorn9.addBox(-4.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(righthorn9, 0.0015707963267948967F, 1.80065618928255F, 2.1855012893472994F);
	this.neck = new ModelRenderer(this, 0, 12);
	this.neck.setRotationPoint(0.0F, 7.0F, -11.5F);
	this.neck.addBox(-1.95F, -10.0F, -3.0F, 4, 17, 7, 0.0F);
	this.setRotateAngle(neck, 0.7285004297824331F, 0.0F, 0.0F);
	this.righthorn8 = new ModelRenderer(this, 0, 84);
	this.righthorn8.setRotationPoint(-4.8F, -5.8F, 14.7F);
	this.righthorn8.addBox(-4.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(righthorn8, -0.091106186954104F, 0.6829473363053812F, 2.231054382824351F);
	this.righthorn15 = new ModelRenderer(this, 0, 80);
	this.righthorn15.setRotationPoint(-3.9F, -4.8F, 1.5F);
	this.righthorn15.addBox(-0.7F, -1.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(righthorn15, 0.091106186954104F, 0.5918411493512771F, -1.593485607070823F);
	this.backRightLeg = new ModelRenderer(this, 96, 43);
	this.backRightLeg.setRotationPoint(0.0F, 7.0F, 0.0F);
	this.backRightLeg.addBox(-1.0F, 0.0F, -1.5F, 3, 8, 3, 0.0F);
	this.backRightThigh = new ModelRenderer(this, 96, 29);
	this.backRightThigh.setRotationPoint(-4.0F, 9.0F, 11.0F);
	this.backRightThigh.addBox(-1.5F, -2.0F, -2.5F, 4, 9, 5, 0.0F);
	this.frontLeftThigh = new ModelRenderer(this, 44, 29);
	this.frontLeftThigh.setRotationPoint(4.0F, 9.0F, -8.0F);
	this.frontLeftThigh.addBox(-1.9F, -1.0F, -2.1F, 3, 8, 4, 0.0F);
	this.horn9 = new ModelRenderer(this, 0, 0);
	this.horn9.setRotationPoint(5.6F, -4.3F, 10.8F);
	this.horn9.addBox(-0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(horn9, -0.0015707963267948967F, -1.9123572614101867F, -2.1855012893472994F);
	this.horn2 = new ModelRenderer(this, 0, 0);
	this.horn2.setRotationPoint(4.5F, -4.8F, 1.5F);
	this.horn2.addBox(0.0F, -0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(horn2, 0.0F, -0.5462880558742251F, -0.22759093446006054F);
	this.righthorn2 = new ModelRenderer(this, 0, 0);
	this.righthorn2.setRotationPoint(-4.5F, -4.8F, 1.5F);
	this.righthorn2.addBox(-4.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(righthorn2, 0.0F, 0.5918411493512771F, 0.22759093446006054F);
	this.horn11 = new ModelRenderer(this, 0, 80);
	this.horn11.setRotationPoint(7.0F, -4.1F, 17.5F);
	this.horn11.addBox(-0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(horn11, 0.8196066167365371F, -1.0016444577195458F, -1.7756979809790308F);
	this.horn13 = new ModelRenderer(this, 0, 80);
	this.horn13.setRotationPoint(5.7F, -4.6F, 10.8F);
	this.horn13.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(horn13, 0.091106186954104F, -0.27314402793711257F, -2.1855012893472994F);
	this.horn15 = new ModelRenderer(this, 0, 80);
	this.horn15.setRotationPoint(3.9F, -4.8F, 1.5F);
	this.horn15.addBox(-0.7F, -0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(horn15, -0.091106186954104F, 0.5918411493512771F, -1.593485607070823F);
	this.righthorn6 = new ModelRenderer(this, 0, 0);
	this.righthorn6.setRotationPoint(-6.1F, -4.4F, 8.3F);
	this.righthorn6.addBox(-4.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(righthorn6, -0.091106186954104F, 1.4100515026862188F, 2.1855012893472994F);
	this.righthorn11 = new ModelRenderer(this, 0, 84);
	this.righthorn11.setRotationPoint(-6.7F, -4.0F, 17.5F);
	this.righthorn11.addBox(-4.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(righthorn11, -0.8196066167365371F, 1.0016444577195458F, 1.7756979809790308F);
	this.horn8 = new ModelRenderer(this, 0, 80);
	this.horn8.setRotationPoint(4.8F, -5.8F, 14.7F);
	this.horn8.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(horn8, 0.091106186954104F, -0.6829473363053812F, -2.231054382824351F);
	this.horn6 = new ModelRenderer(this, 0, 0);
	this.horn6.setRotationPoint(6.1F, -4.4F, 8.3F);
	this.horn6.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(horn6, 0.091106186954104F, -1.4100515026862188F, -2.1855012893472994F);
	this.leftEar = new ModelRenderer(this, 60, 0);
	this.leftEar.setRotationPoint(0.0F, 2.8F, -0.0F);
	this.leftEar.addBox(2.45F, -6.9F, 2.8F, 2, 2, 1, 0.0F);
	this.mane = new ModelRenderer(this, 58, 0);
	this.mane.setRotationPoint(0.1F, 0.0F, 0.0F);
	this.mane.addBox(-1.0F, -10.0F, -4.0F, 2, 16, 1, 0.0F);
	this.horn12 = new ModelRenderer(this, 0, 80);
	this.horn12.setRotationPoint(7.1F, -4.0F, 18.3F);
	this.horn12.addBox(0.0F, -0.2F, -0.1F, 4, 1, 1, 0.0F);
	this.setRotateAngle(horn12, 0.8196066167365371F, -1.7704619932230479F, -1.7756979809790308F);
	this.lowerSnout = new ModelRenderer(this, 24, 27);
	this.lowerSnout.setRotationPoint(0.0F, 0.0F, 0.0F);
	this.lowerSnout.addBox(-2.0F, -3.0F, -6.5F, 4, 2, 4, 0.0F);
	this.righthorn14 = new ModelRenderer(this, 0, 84);
	this.righthorn14.setRotationPoint(-7.4F, -5.7F, 3.7F);
	this.righthorn14.addBox(-4.0F, -0.0F, 0.0F, 4, 1, 1, 0.0F);
	this.setRotateAngle(righthorn14, 0.0F, 0.22759093446006054F, 1.6390387005478748F);
	this.lefthorn1.addChild(this.righthorn7);
	this.lefthorn1.addChild(this.horn10);
	this.frontRightThigh.addChild(this.frontRightLeg);
	this.lefthorn1.addChild(this.horn7);
	this.lefthorn1.addChild(this.horn14);
	this.lefthorn1.addChild(this.horn4);
	this.lefthorn1.addChild(this.horn16);
	this.lefthorn1.addChild(this.righthorn13);
	this.head.addChild(this.lefthorn1);
	this.head.addChild(this.rightEar);
	this.backLeftThigh.addChild(this.backLeftLeg);
	this.neck.addChild(this.head);
	this.lefthorn1.addChild(this.horn3);
	this.frontLeftThigh.addChild(this.frontLeftLeg);
	this.lefthorn1.addChild(this.righthorn16);
	this.lefthorn1.addChild(this.righthorn5);
	this.head.addChild(this.upperSnout);
	this.lefthorn1.addChild(this.horn5);
	this.lefthorn1.addChild(this.righthorn4);
	this.lefthorn1.addChild(this.righthorn10);
	this.lefthorn1.addChild(this.righthorn12);
	this.lefthorn1.addChild(this.righthorn3);
	this.lefthorn1.addChild(this.righthorn9);
	this.lefthorn1.addChild(this.righthorn8);
	this.lefthorn1.addChild(this.righthorn15);
	this.backRightThigh.addChild(this.backRightLeg);
	this.lefthorn1.addChild(this.horn9);
	this.lefthorn1.addChild(this.horn2);
	this.lefthorn1.addChild(this.righthorn2);
	this.lefthorn1.addChild(this.horn11);
	this.lefthorn1.addChild(this.horn13);
	this.lefthorn1.addChild(this.horn15);
	this.lefthorn1.addChild(this.righthorn6);
	this.lefthorn1.addChild(this.righthorn11);
	this.lefthorn1.addChild(this.horn8);
	this.lefthorn1.addChild(this.horn6);
	this.head.addChild(this.leftEar);
	this.neck.addChild(this.mane);
	this.lefthorn1.addChild(this.horn12);
	this.head.addChild(this.lowerSnout);
	this.lefthorn1.addChild(this.righthorn14);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
	this.tail1.render(f5);
	this.backLeftThigh.render(f5);
	this.frontRightThigh.render(f5);
	this.body.render(f5);
	this.neck.render(f5);
	this.backRightThigh.render(f5);
	this.frontLeftThigh.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
	modelRenderer.rotateAngleX = x;
	modelRenderer.rotateAngleY = y;
	modelRenderer.rotateAngleZ = z;
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third
     * float params here are the same second and third as in the setRotationAngles
     * method.
     */
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
	super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
	this.neckRotationX = ((EntityDreamElk) entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used
     * for animating the movement of arms and legs, where par1 represents the
     * time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor,
	    Entity entityIn)
    {
	this.frontLeftThigh.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	this.frontRightThigh.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
	this.backLeftThigh.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
	this.backRightThigh.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	
	/**
	 * Only rotate the neck when not bucking or eating grass (otherwise it would look weird)
	 */
	if (this.neckRotationX == 0)
	{
	    this.neck.rotateAngleY = Math.min(Math.max(netHeadYaw * 0.017453292F, -0.10F * (float) Math.PI), 0.10F * (float) Math.PI);
	    this.neck.rotateAngleX = this.defaultNeckRotation;
	}
	else
	{
	    this.neck.rotateAngleX = this.neckRotationX + this.defaultNeckRotation;
	}
    }
}
