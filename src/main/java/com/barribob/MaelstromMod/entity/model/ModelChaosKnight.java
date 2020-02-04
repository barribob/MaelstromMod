package com.barribob.MaelstromMod.entity.model;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ModelChaosKnight extends ModelAnimated
{
    private final ModelRenderer root;
    public final ModelRenderer Chest1;
    private final ModelRenderer Chest2;
    public final ModelRenderer leftShoulder;
    private final ModelRenderer Rarm1;
    private final ModelRenderer Rarm2;
    private final ModelRenderer sheild1;
    private final ModelRenderer sheild2;
    private final ModelRenderer sheild3;
    private final ModelRenderer sheild4;
    private final ModelRenderer sheild5;
    private final ModelRenderer sheild6;
    private final ModelRenderer sheild7;
    private final ModelRenderer sheild8;
    private final ModelRenderer sheild9;
    public final ModelRenderer rightShoulder;
    private final ModelRenderer Larm1;
    private final ModelRenderer Larm2;
    public final ModelRenderer axe0;
    private final ModelRenderer axe1;
    private final ModelRenderer shaft;
    private final ModelRenderer axe2;
    private final ModelRenderer axe3;
    private final ModelRenderer axe4;
    private final ModelRenderer axe5;
    private final ModelRenderer axe6;
    private final ModelRenderer axe7;
    private final ModelRenderer axe8;
    private final ModelRenderer axe9;
    private final ModelRenderer axe10;
    private final ModelRenderer axe11;
    private final ModelRenderer Head;
    private final ModelRenderer helmet1;
    private final ModelRenderer helmet2;
    private final ModelRenderer horn1;
    private final ModelRenderer horn3;
    private final ModelRenderer horn4;
    private final ModelRenderer horn5;
    private final ModelRenderer horn6;
    private final ModelRenderer horn7;
    private final ModelRenderer horn8;
    private final ModelRenderer horn9;
    private final ModelRenderer horn10;
    private final ModelRenderer horn2;
    private final ModelRenderer horn11;
    private final ModelRenderer Shoulderarmor1;
    private final ModelRenderer Shoulderarmor2;
    private final ModelRenderer Shoulderarmor3;
    private final ModelRenderer Shoulderarmor4;
    private final ModelRenderer Shoulderarmor5;
    private final ModelRenderer Shoulderarmor6;
    private final ModelRenderer armorplate;
    private final ModelRenderer armorplate2;
    private final ModelRenderer armorplate3;
    private final ModelRenderer Donglecloth;
    private final ModelRenderer Thunderthigh1;
    private final ModelRenderer Leg1;
    private final ModelRenderer Foot1;
    private final ModelRenderer legarmor2;
    private final ModelRenderer Thunderthigh2;
    private final ModelRenderer leg2;
    private final ModelRenderer Foot2;
    private final ModelRenderer legarmor1;

    public ModelChaosKnight()
    {
	textureWidth = 140;
	textureHeight = 100;

	root = new ModelRenderer(this);
	root.setRotationPoint(0.0F, 0.0F, 0.0F);

	Chest1 = new ModelRenderer(this);
	Chest1.setRotationPoint(0.0F, 5.5F, 0.0F);
	setRotationAngle(Chest1, 0.0F, -3.1416F, 0.0F);
	root.addChild(Chest1);
	Chest1.cubeList.add(new ModelBox(Chest1, 0, 0, -8.5F, -14.0F, -4.0F, 17, 10, 8, 0.0F, true));

	Chest2 = new ModelRenderer(this);
	Chest2.setRotationPoint(0.0F, -4.0F, 0.0F);
	setRotationAngle(Chest2, 0.0698F, 0.0F, 0.0F);
	Chest1.addChild(Chest2);
	Chest2.cubeList.add(new ModelBox(Chest2, 98, 0, -5.5F, -0.279F, -3.9903F, 11, 7, 7, 0.0F, false));

	leftShoulder = new ModelRenderer(this);
	leftShoulder.setRotationPoint(-11.3F, -11.0F, -0.5F);
	Chest1.addChild(leftShoulder);
	leftShoulder.cubeList.add(new ModelBox(leftShoulder, 45, 14, -2.0F, -2.721F, -1.9903F, 5, 7, 5, 0.0F, true));

	Rarm1 = new ModelRenderer(this);
	Rarm1.setRotationPoint(3.1F, 4.0F, 2.0F);
	leftShoulder.addChild(Rarm1);
	Rarm1.cubeList.add(new ModelBox(Rarm1, 121, 34, -3.99F, 0.279F, -3.7903F, 4, 5, 4, 0.0F, true));

	Rarm2 = new ModelRenderer(this);
	Rarm2.setRotationPoint(0.1F, 6.8F, 3.7F);
	setRotationAngle(Rarm2, 0.0698F, 0.0F, 0.0F);
	leftShoulder.addChild(Rarm2);
	Rarm2.cubeList.add(new ModelBox(Rarm2, 59, 35, -1.0F, 0.0F, -6.0F, 4, 11, 5, 0.0F, true));

	sheild1 = new ModelRenderer(this);
	sheild1.setRotationPoint(0.0F, 2.0F, 0.0F);
	Rarm2.addChild(sheild1);
	sheild1.cubeList.add(new ModelBox(sheild1, 22, 35, -2.0F, 0.0F, -8.0F, 1, 10, 11, 0.0F, true));

	sheild2 = new ModelRenderer(this);
	sheild2.setRotationPoint(-1.0F, 3.5F, -6.0F);
	Rarm2.addChild(sheild2);
	sheild2.cubeList.add(new ModelBox(sheild2, 98, 90, -1.0F, 0.0F, -4.0F, 1, 7, 2, 0.0F, true));

	sheild3 = new ModelRenderer(this);
	sheild3.setRotationPoint(-1.0F, 5.4F, -7.0F);
	Rarm2.addChild(sheild3);
	sheild3.cubeList.add(new ModelBox(sheild3, 118, 85, -1.0F, 0.0F, -4.0F, 1, 3, 1, 0.0F, true));

	sheild4 = new ModelRenderer(this);
	sheild4.setRotationPoint(-1.0F, 1.0F, 1.0F);
	Rarm2.addChild(sheild4);
	sheild4.cubeList.add(new ModelBox(sheild4, 109, 14, -1.0F, 0.0F, -4.0F, 1, 1, 6, 0.0F, true));

	sheild5 = new ModelRenderer(this);
	sheild5.setRotationPoint(-1.0F, 12.0F, 1.0F);
	Rarm2.addChild(sheild5);
	sheild5.cubeList.add(new ModelBox(sheild5, 49, 26, -1.0F, 0.0F, -4.0F, 1, 1, 6, 0.0F, true));

	sheild6 = new ModelRenderer(this);
	sheild6.setRotationPoint(-0.7F, 4.0F, 0.0F);
	Rarm2.addChild(sheild6);
	sheild6.cubeList.add(new ModelBox(sheild6, 77, 38, -2.0F, 0.0F, -8.0F, 1, 6, 10, 0.0F, true));

	sheild7 = new ModelRenderer(this);
	sheild7.setRotationPoint(-0.4F, 3.0F, 5.0F);
	Rarm2.addChild(sheild7);
	sheild7.cubeList.add(new ModelBox(sheild7, 46, 46, -2.0F, 0.0F, -8.0F, 1, 8, 6, 0.0F, true));

	sheild8 = new ModelRenderer(this);
	sheild8.setRotationPoint(0.0F, 2.0F, 10.0F);
	Rarm2.addChild(sheild8);
	sheild8.cubeList.add(new ModelBox(sheild8, 134, 0, -2.0F, 0.0F, -7.0F, 1, 10, 1, 0.0F, true));

	sheild9 = new ModelRenderer(this);
	sheild9.setRotationPoint(0.0F, 4.5F, 12.0F);
	Rarm2.addChild(sheild9);
	sheild9.cubeList.add(new ModelBox(sheild9, 116, 92, -2.0F, 0.0F, -8.0F, 1, 5, 1, 0.0F, true));

	rightShoulder = new ModelRenderer(this);
	rightShoulder.setRotationPoint(10.3F, -12.0F, 0.0F);
	Chest1.addChild(rightShoulder);
	rightShoulder.cubeList.add(new ModelBox(rightShoulder, 65, 14, -2.0F, -0.721F, -2.4903F, 5, 6, 5, 0.0F, true));

	Larm1 = new ModelRenderer(this);
	Larm1.setRotationPoint(2.1F, 5.0F, -0.5F);
	rightShoulder.addChild(Larm1);
	Larm1.cubeList.add(new ModelBox(Larm1, 0, 50, -4.01F, 0.279F, -1.5F, 4, 5, 4, 0.0F, true));

	Larm2 = new ModelRenderer(this);
	Larm2.setRotationPoint(0.1F, 7.8F, 0.0F);
	rightShoulder.addChild(Larm2);
	Larm2.cubeList.add(new ModelBox(Larm2, 60, 51, -2.0F, 0.0F, -2.5F, 4, 11, 5, 0.0F, true));

	axe0 = new ModelRenderer(this);
	axe0.setRotationPoint(0.3F, 8.8F, -0.1F);
	Larm2.addChild(axe0);
	axe0.cubeList.add(new ModelBox(axe0, 68, 38, -1.0F, 0.0F, -5.0F, 1, 1, 31, 0.0F, true));

	axe1 = new ModelRenderer(this);
	axe1.setRotationPoint(0.5F, -0.5F, 22.8F);
	axe0.addChild(axe1);
	axe1.cubeList.add(new ModelBox(axe1, 12, 31, -2.0F, 0.0F, -3.0F, 2, 2, 6, 0.0F, true));

	shaft = new ModelRenderer(this);
	shaft.setRotationPoint(0.5F, -0.5F, -2.4F);
	axe0.addChild(shaft);
	shaft.cubeList.add(new ModelBox(shaft, 42, 0, -2.0F, 0.0F, -3.0F, 2, 2, 2, 0.0F, true));

	axe2 = new ModelRenderer(this);
	axe2.setRotationPoint(0.0F, -2.5F, 23.3F);
	axe0.addChild(axe2);
	axe2.cubeList.add(new ModelBox(axe2, 125, 47, -1.0F, -1.0F, -3.0F, 1, 8, 5, 0.0F, true));

	axe3 = new ModelRenderer(this);
	axe3.setRotationPoint(0.0F, -4.5F, 22.3F);
	axe0.addChild(axe3);
	axe3.cubeList.add(new ModelBox(axe3, 101, 52, -1.0F, 0.0F, -3.0F, 1, 1, 6, 0.0F, true));

	axe4 = new ModelRenderer(this);
	axe4.setRotationPoint(0.0F, 4.5F, 22.3F);
	axe0.addChild(axe4);
	axe4.cubeList.add(new ModelBox(axe4, 10, 53, -1.0F, 0.0F, -3.0F, 1, 1, 6, 0.0F, true));

	axe5 = new ModelRenderer(this);
	axe5.setRotationPoint(0.0F, 5.5F, 20.8F);
	axe0.addChild(axe5);
	axe5.cubeList.add(new ModelBox(axe5, 107, 52, -1.0F, 0.0F, -3.0F, 1, 1, 8, 0.0F, true));

	axe6 = new ModelRenderer(this);
	axe6.setRotationPoint(0.0F, -5.5F, 20.8F);
	axe0.addChild(axe6);
	axe6.cubeList.add(new ModelBox(axe6, 78, 54, -1.0F, 0.0F, -3.0F, 1, 1, 8, 0.0F, true));

	axe7 = new ModelRenderer(this);
	axe7.setRotationPoint(0.0F, -6.5F, 21.6F);
	axe0.addChild(axe7);
	axe7.cubeList.add(new ModelBox(axe7, 17, 56, -1.0F, 0.0F, -3.0F, 1, 1, 7, 0.0F, true));

	axe8 = new ModelRenderer(this);
	axe8.setRotationPoint(0.0F, 6.5F, 21.6F);
	axe0.addChild(axe8);
	axe8.cubeList.add(new ModelBox(axe8, 33, 56, -1.0F, 0.0F, -3.0F, 1, 1, 7, 0.0F, true));

	axe9 = new ModelRenderer(this);
	axe9.setRotationPoint(1.0F, 1.0F, 22.4F);
	axe0.addChild(axe9);
	axe9.cubeList.add(new ModelBox(axe9, 0, 60, -3.0F, -1.0F, -3.0F, 3, 1, 7, 0.0F, true));

	axe10 = new ModelRenderer(this);
	axe10.setRotationPoint(0.0F, -7.5F, 22.6F);
	axe0.addChild(axe10);
	axe10.cubeList.add(new ModelBox(axe10, 73, 84, -1.0F, 0.0F, -3.0F, 1, 1, 5, 0.0F, true));

	axe11 = new ModelRenderer(this);
	axe11.setRotationPoint(0.0F, 7.5F, 22.6F);
	axe0.addChild(axe11);
	axe11.cubeList.add(new ModelBox(axe11, 92, 0, -1.0F, 0.0F, -3.0F, 1, 1, 5, 0.0F, true));

	Head = new ModelRenderer(this);
	Head.setRotationPoint(0.0F, -14.0F, 2.0F);
	Chest1.addChild(Head);
	Head.cubeList.add(new ModelBox(Head, 85, 14, -4.0F, -8.0F, -6.0F, 8, 8, 8, 0.0F, true));

	helmet1 = new ModelRenderer(this);
	helmet1.setRotationPoint(2.5F, -8.5F, -2.5F);
	Head.addChild(helmet1);
	helmet1.cubeList.add(new ModelBox(helmet1, 40, 60, -5.0F, 0.0F, -4.0F, 5, 10, 9, 0.0F, true));

	helmet2 = new ModelRenderer(this);
	helmet2.setRotationPoint(1.0F, -9.3F, -1.7F);
	Head.addChild(helmet2);
	helmet2.cubeList.add(new ModelBox(helmet2, 11, 64, -2.0F, 0.0F, -4.0F, 2, 12, 9, 0.0F, false));

	horn1 = new ModelRenderer(this);
	horn1.setRotationPoint(-3.0F, -6.4F, 0.6F);
	setRotationAngle(horn1, 0.0F, -0.1571F, 0.0559F);
	Head.addChild(horn1);
	horn1.cubeList.add(new ModelBox(horn1, 26, 56, -3.6257F, 0.0F, -3.9508F, 3, 3, 3, 0.0F, true));

	horn3 = new ModelRenderer(this);
	horn3.setRotationPoint(-3.2F, 0.5F, 0.5F);
	setRotationAngle(horn3, 0.0F, 0.3316F, -0.1501F);
	horn1.addChild(horn3);
	horn3.cubeList.add(new ModelBox(horn3, 60, 14, -1.2988F, -0.0936F, -3.9369F, 3, 2, 2, 0.0F, true));

	horn4 = new ModelRenderer(this);
	horn4.setRotationPoint(-2.4F, 0.4F, 0.0F);
	setRotationAngle(horn4, -0.0175F, 0.5934F, -0.2793F);
	horn3.addChild(horn4);
	horn4.cubeList.add(new ModelBox(horn4, 80, 14, 0.7817F, 0.1535F, -2.8703F, 3, 2, 2, 0.0F, true));

	horn5 = new ModelRenderer(this);
	horn5.setRotationPoint(-2.2F, 0.1F, 0.0F);
	setRotationAngle(horn5, -0.0698F, 0.6318F, 0.1885F);
	horn4.addChild(horn5);
	horn5.cubeList.add(new ModelBox(horn5, 129, 25, 1.9234F, -0.3218F, -0.7096F, 3, 2, 2, 0.0F, true));

	horn6 = new ModelRenderer(this);
	horn6.setRotationPoint(-2.2F, 0.1F, 0.0F);
	setRotationAngle(horn6, -0.0698F, 0.6318F, 0.2583F);
	horn5.addChild(horn6);
	horn6.cubeList.add(new ModelBox(horn6, 0, 29, 1.4137F, -1.4231F, 1.5236F, 3, 2, 2, 0.0F, true));

	horn7 = new ModelRenderer(this);
	horn7.setRotationPoint(-2.1F, 0.1F, -0.4F);
	setRotationAngle(horn7, 0.274F, 0.3351F, 0.3456F);
	horn6.addChild(horn7);
	horn7.cubeList.add(new ModelBox(horn7, 22, 31, 0.077F, -1.7699F, 2.9247F, 3, 2, 2, 0.0F, true));

	horn8 = new ModelRenderer(this);
	horn8.setRotationPoint(-1.7F, 0.7F, 0.3F);
	setRotationAngle(horn8, 0.274F, 0.1955F, 0.3456F);
	horn7.addChild(horn8);
	horn8.cubeList.add(new ModelBox(horn8, 48, 0, -0.2392F, -1.4329F, 3.6562F, 1, 1, 1, 0.0F, true));

	horn9 = new ModelRenderer(this);
	horn9.setRotationPoint(-0.89F, -0.03F, 0.01F);
	setRotationAngle(horn9, -0.0227F, 0.1082F, 0.3456F);
	horn8.addChild(horn9);
	horn9.cubeList.add(new ModelBox(horn9, 99, 0, -1.9658F, -1.6885F, 3.6224F, 2, 1, 1, 0.0F, true));

	horn10 = new ModelRenderer(this);
	horn10.setRotationPoint(-0.89F, -0.03F, 0.01F);
	setRotationAngle(horn10, -0.0401F, -0.0087F, 0.3456F);
	horn9.addChild(horn10);
	horn10.cubeList.add(new ModelBox(horn10, 127, 0, -2.4963F, -1.6769F, 3.5641F, 2, 1, 1, 0.0F, true));

	horn2 = new ModelRenderer(this);
	horn2.setRotationPoint(3.0F, -6.4F, 0.6F);
	setRotationAngle(horn2, 0.0F, 0.0F, -0.1396F);
	Head.addChild(horn2);
	horn2.cubeList.add(new ModelBox(horn2, 122, 60, 0.0F, 0.0F, -4.0F, 3, 3, 3, 0.0F, true));

	horn11 = new ModelRenderer(this);
	horn11.setRotationPoint(3.2F, 0.5F, 0.5F);
	setRotationAngle(horn11, 0.0F, -0.3316F, 0.1501F);
	horn2.addChild(horn11);
	horn11.cubeList.add(new ModelBox(horn11, 32, 31, -2.3023F, 0.0F, -3.7821F, 3, 2, 2, 0.0F, true));

	Shoulderarmor1 = new ModelRenderer(this);
	Shoulderarmor1.setRotationPoint(-3.0F, -7.0F, -0.5F);
	setRotationAngle(Shoulderarmor1, 0.0F, 0.0F, 0.4136F);
	Chest1.addChild(Shoulderarmor1);
	Shoulderarmor1.cubeList.add(new ModelBox(Shoulderarmor1, 117, 14, -5.0F, -7.0F, -4.01F, 2, 1, 9, 0.0F, true));

	Shoulderarmor2 = new ModelRenderer(this);
	Shoulderarmor2.setRotationPoint(-5.0F, -7.0F, -0.5F);
	setRotationAngle(Shoulderarmor2, 0.0F, 0.0F, 0.4136F);
	Chest1.addChild(Shoulderarmor2);
	Shoulderarmor2.cubeList.add(new ModelBox(Shoulderarmor2, 0, 18, -5.0F, -7.0F, -3.99F, 4, 2, 9, 0.0F, true));

	Shoulderarmor3 = new ModelRenderer(this);
	Shoulderarmor3.setRotationPoint(-7.0F, -7.0F, -0.5F);
	setRotationAngle(Shoulderarmor3, 0.0F, 0.0F, 0.3962F);
	Chest1.addChild(Shoulderarmor3);
	Shoulderarmor3.cubeList.add(new ModelBox(Shoulderarmor3, 17, 20, -5.0F, -7.0F, -4.0F, 6, 2, 9, 0.0F, true));

	Shoulderarmor4 = new ModelRenderer(this);
	Shoulderarmor4.setRotationPoint(-9.0F, -7.0F, -0.48F);
	setRotationAngle(Shoulderarmor4, 0.0F, 0.0F, 0.3787F);
	Chest1.addChild(Shoulderarmor4);
	Shoulderarmor4.cubeList.add(new ModelBox(Shoulderarmor4, 57, 25, -5.0F, -7.0F, -3.5F, 8, 2, 8, 0.0F, true));

	Shoulderarmor5 = new ModelRenderer(this);
	Shoulderarmor5.setRotationPoint(-10.7F, -7.0F, -0.5F);
	setRotationAngle(Shoulderarmor5, 0.0F, 0.0F, 0.3264F);
	Chest1.addChild(Shoulderarmor5);
	Shoulderarmor5.cubeList.add(new ModelBox(Shoulderarmor5, 110, 25, -5.0F, -7.0F, -3.0F, 6, 2, 7, 0.0F, true));

	Shoulderarmor6 = new ModelRenderer(this);
	Shoulderarmor6.setRotationPoint(-12.1F, -7.0F, -0.5F);
	setRotationAngle(Shoulderarmor6, 0.0F, 0.0F, 0.274F);
	Chest1.addChild(Shoulderarmor6);
	Shoulderarmor6.cubeList.add(new ModelBox(Shoulderarmor6, 83, 30, -5.0F, -7.0F, -2.5F, 8, 2, 6, 0.0F, true));

	armorplate = new ModelRenderer(this);
	armorplate.setRotationPoint(11.0F, -14.0F, -0.7F);
	setRotationAngle(armorplate, 0.0F, 0.0F, 0.3142F);
	Chest1.addChild(armorplate);
	armorplate.cubeList.add(new ModelBox(armorplate, 37, 26, -1.0F, 0.0F, -4.0F, 1, 10, 10, 0.0F, true));

	armorplate2 = new ModelRenderer(this);
	armorplate2.setRotationPoint(11.0F, -14.0F, -0.7F);
	setRotationAngle(armorplate2, 0.0F, 0.0F, 0.3142F);
	Chest1.addChild(armorplate2);
	armorplate2.cubeList.add(new ModelBox(armorplate2, 0, 29, -7.0F, -0.5F, -4.0F, 1, 11, 10, 0.0F, true));

	armorplate3 = new ModelRenderer(this);
	armorplate3.setRotationPoint(11.0F, -13.0F, -0.2F);
	setRotationAngle(armorplate3, 0.0F, 0.0F, 0.3142F);
	Chest1.addChild(armorplate3);
	armorplate3.cubeList.add(new ModelBox(armorplate3, 102, 34, -6.4F, -0.5F, -4.0F, 5, 9, 9, 0.0F, true));

	Donglecloth = new ModelRenderer(this);
	Donglecloth.setRotationPoint(0.0F, 10.2054F, -5.5392F);
	setRotationAngle(Donglecloth, 2.8798F, 0.0F, 0.0F);
	root.addChild(Donglecloth);
	Donglecloth.cubeList.add(new ModelBox(Donglecloth, 0, 75, -2.5F, -3.7071F, -1.2247F, 5, 6, 0, 0.0F, true));

	Thunderthigh1 = new ModelRenderer(this);
	Thunderthigh1.setRotationPoint(0.0F, 8.5F, -1.0F);
	setRotationAngle(Thunderthigh1, 0.0F, 3.1416F, 0.0F);
	root.addChild(Thunderthigh1);
	Thunderthigh1.cubeList.add(new ModelBox(Thunderthigh1, 50, 0, -6.0F, 0.0F, -4.0F, 6, 8, 6, 0.0F, true));

	Leg1 = new ModelRenderer(this);
	Leg1.setRotationPoint(-3.5F, 7.8F, -3.2F);
	Thunderthigh1.addChild(Leg1);
	Leg1.cubeList.add(new ModelBox(Leg1, 68, 70, -2.5019F, 0.2F, -0.8F, 5, 6, 5, 0.0F, true));

	Foot1 = new ModelRenderer(this);
	Foot1.setRotationPoint(0.2F, 6.0F, -0.6F);
	Leg1.addChild(Foot1);
	Foot1.cubeList.add(new ModelBox(Foot1, 101, 61, -2.7088F, -0.3F, -0.2F, 5, 2, 6, 0.0F, true));

	legarmor2 = new ModelRenderer(this);
	legarmor2.setRotationPoint(-6.0F, 0.0F, -8.0F);
	setRotationAngle(legarmor2, 0.0F, 0.0F, 0.1222F);
	Thunderthigh1.addChild(legarmor2);
	legarmor2.cubeList.add(new ModelBox(legarmor2, 88, 70, -0.2333F, -1.182F, 3.0F, 4, 10, 8, 0.0F, true));

	Thunderthigh2 = new ModelRenderer(this);
	Thunderthigh2.setRotationPoint(0.0F, 8.5F, -1.0F);
	setRotationAngle(Thunderthigh2, 0.0F, 3.1416F, 0.0F);
	root.addChild(Thunderthigh2);
	Thunderthigh2.cubeList.add(new ModelBox(Thunderthigh2, 50, 0, 0.0F, 0.0F, -4.0F, 6, 8, 6, 0.0F, true));

	leg2 = new ModelRenderer(this);
	leg2.setRotationPoint(2.5F, 7.8F, -3.2F);
	Thunderthigh2.addChild(leg2);
	leg2.cubeList.add(new ModelBox(leg2, 112, 70, -2.0F, 0.2F, -0.8F, 5, 6, 5, 0.0F, true));

	Foot2 = new ModelRenderer(this);
	Foot2.setRotationPoint(0.2F, 6.0F, -0.6F);
	leg2.addChild(Foot2);
	Foot2.cubeList.add(new ModelBox(Foot2, 41, 79, -2.1302F, -0.3F, -0.3046F, 5, 2, 6, 0.0F, true));

	legarmor1 = new ModelRenderer(this);
	legarmor1.setRotationPoint(6.0F, 0.0F, -7.0F);
	setRotationAngle(legarmor1, 0.0F, 0.0F, -0.1396F);
	Thunderthigh2.addChild(legarmor1);
	legarmor1.cubeList.add(new ModelBox(legarmor1, 25, 79, -3.7636F, -1.1779F, 2.0F, 4, 10, 8, 0.0F, true));
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
	float limbSwingFactor = 1.5f;
	this.Leg1.rotateAngleX = -limbSwingFactor * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
	this.leg2.rotateAngleX = limbSwingFactor * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;

	this.leftShoulder.rotateAngleX = (-0.2F + limbSwingFactor * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
	this.rightShoulder.rotateAngleX = (-0.2F - limbSwingFactor * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
	super.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTickTime);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
	this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
	this.Head.rotateAngleX = headPitch * 0.017453292F;
    }

    private float triangleWave(float p_78172_1_, float p_78172_2_)
    {
	return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
	root.render(f5);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
	modelRenderer.rotateAngleX = x;
	modelRenderer.rotateAngleY = y;
	modelRenderer.rotateAngleZ = z;
    }
}