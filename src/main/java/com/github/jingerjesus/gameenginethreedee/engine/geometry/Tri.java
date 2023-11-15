package com.github.jingerjesus.gameenginethreedee.engine.geometry;

import com.github.jingerjesus.gameenginethreedee.engine.interactables.Room;
import com.github.jingerjesus.gameenginethreedee.engine.interactables.Texture;
import com.github.jingerjesus.gameenginethreedee.engine.peripherals.GraphicsScreen;
import javafx.scene.paint.Color;

import java.util.function.Function;

public class Tri {
    private Vec[] vecs;
    private Vec[] texs = new Vec[] {new Vec(), new Vec(), new Vec()};

    private Texture texture;

    private double shading;
    private Color color;

    public Tri() {
        vecs = new Vec[3];
    }

    public Tri(Vec[] vin) {
        vecs = new Vec[3];
        for (int i = 0; i < 3; i ++) {
            vecs[i] = vin[i];
        }
    }

    public Tri(Vec[] vin, Vec[] tin) {
        vecs = new Vec[3];
        texs = new Vec[3];
        for (int i = 0; i < 3; i ++) {
            vecs[i] = vin[i];
            texs[i] = tin[i];
        }
    }

    public Tri(Vec p0, Vec p1, Vec p2) {
        vecs = new Vec[]{p0, p1, p2};
    }
    public Tri(Vec p0, Vec p1, Vec p2, Vec t0, Vec t1, Vec t2) {
        vecs = new Vec[]{p0, p1, p2};
        texs = new Vec[]{t0, t1, t2};
    }

    public Vec[] getVecs() {return vecs;}

    public void setShading(double shaded) {
        shading = shaded;
    }

    public double getShading() {
        return shading;
    }

    public void setColor(Color c) {
        color = c;
    }

    public Color getShadedColor() {
        return Color.rgb(
                (int)(color.getRed() * shading),
                (int)(color.getGreen() * shading),
                (int)(color.getBlue() * shading)
        );
    }

    public double getMidpointZ() {
        double sum = 0;

        for (int i = 0; i < 3; i ++) {
            sum += vecs[i].getZ();
        }

        sum /= 3;

        return sum;
    }

    public static Tri[] clipAgainstPlane(Vec pPoint, Vec pNorm, Tri in) {
        pNorm = pNorm.normalize();

        Vec finalPNorm = pNorm;
        Function<Vec, Double> dist = p -> {
            Vec n = p.normalize(); // Assuming Vector_Normalise is defined
            return (finalPNorm.getX() * p.getX() + finalPNorm.getY() * p.getY() + finalPNorm.getZ() * p.getZ() - Vec.DotProductOf(finalPNorm, pPoint));
        };

        Vec[] insidePoints = new Vec[3]; int inPointCount = 0;
        Vec[] outsidePoints = new Vec[3]; int outPointCount = 0;

        Vec[] insideTexs = new Vec[3]; int inTexCount = 0;
        Vec[] outsideTexs = new Vec[3]; int outTexCount = 0;


        double d0 = dist.apply(in.getVecs()[0]);
        double d1 = dist.apply(in.getVecs()[1]);
        double d2 = dist.apply(in.getVecs()[2]);

        //You have no idea what this does. Godspeed, future you.

        if (d0 >= 0) { insidePoints[inPointCount++] = in.getVecs()[0]; insideTexs[inTexCount++] = in.getTexs()[0];}
        else { outsidePoints[outPointCount++] = in.getVecs()[0]; outsideTexs[outTexCount++] = in.getTexs()[0];}
        if (d1 >= 0) { insidePoints[inPointCount++] = in.getVecs()[1]; insideTexs[inTexCount++] = in.getTexs()[1];}
        else { outsidePoints[outPointCount++] = in.getVecs()[1]; outsideTexs[outTexCount++] = in.getTexs()[1];}
        if (d2 >= 0) { insidePoints[inPointCount++] = in.getVecs()[2]; insideTexs[inTexCount++] = in.getTexs()[2];}
        else { outsidePoints[outPointCount++] = in.getVecs()[2]; outsideTexs[outTexCount++] = in.getTexs()[2];}

        if (inPointCount == 0) {
            return new Tri[0];
        } else if (inPointCount == 3) {
            return new Tri[] {in};
        } else if (inPointCount == 1 && outPointCount == 2) {

            double shaded = in.getShading();

            double t;

            Vec p1, p2;
            Vec t1, t2;

            p1 = Vec.intersectPlane(pPoint, pNorm, insidePoints[0], outsidePoints[0]);
            t = p1.getW();
            t1 = new Vec(
                    t * (outsideTexs[0].getX() - insideTexs[0].getX()) + insideTexs[0].getX(),
                    t * (outsideTexs[0].getY() - insideTexs[0].getY()) + insideTexs[0].getY()
            );
            t1.setW(
                    //I may be stupid. if something breaks, look here first.
                    t * (outsideTexs[0].getW() - insideTexs[0].getW()) + insideTexs[0].getW()
            );

            p2 = Vec.intersectPlane(pPoint, pNorm, insidePoints[0], outsidePoints[1]);
            t2 = new Vec(
                    t * (outsideTexs[1].getX() - insideTexs[0].getX()) + insideTexs[0].getX(),
                    t * (outsideTexs[1].getY() - insideTexs[0].getY()) + insideTexs[0].getY()
            );

            t2.setW(
                    //I may be stupid. if something breaks, look here first.
                    t * (outsideTexs[1].getW() - insideTexs[0].getW()) + insideTexs[0].getW()
            );

            Tri newTri = new Tri(
                    insidePoints[0],
                    p1,
                    p2,

                    //textures
                    insideTexs[0],
                    t1,
                    t2
            );
            newTri.setShading(shaded);
            return new Tri[]{newTri};
        } else if (inPointCount == 2 && outPointCount == 1) {
            double shaded = in.getShading();
            double t;

            Vec p3, p4;
            Vec t3, t4;


            p3 = Vec.intersectPlane(pPoint, pNorm, insidePoints[0], outsidePoints[0]);
            t = p3.getW();
            t3 = new Vec(
                    t * (outsideTexs[0].getX() - insideTexs[0].getX()) + insideTexs[0].getX(),
                    t * (outsideTexs[0].getY() - insideTexs[0].getY()) + insideTexs[0].getY()
            );
            t3.setW(
                    t * (outsideTexs[0].getW() - insideTexs[0].getW()) + insideTexs[0].getW()
            );

            p4 = Vec.intersectPlane(pPoint, pNorm, insidePoints[1], outsidePoints[0]);
            t = p4.getW();
            t4 = new Vec(
                    t * (outsideTexs[0].getX() - insideTexs[1].getX()) + insideTexs[1].getX(),
                    t * (outsideTexs[0].getY() - insideTexs[1].getY()) + insideTexs[1].getY()
            );
            t3.setW(
                    t * (outsideTexs[0].getW() - insideTexs[1].getW()) + insideTexs[1].getW()
            );


            Tri newTri1 = new Tri(
                    insidePoints[0],
                    insidePoints[1],
                    p3,

                    //textures
                    insideTexs[0],
                    insideTexs[1],
                    t3

            );
            Tri newTri2 = new Tri(
                    insidePoints[1],
                    newTri1.getVecs()[2],
                    p4,

                    //textures
                    insideTexs[0],
                    newTri1.getTexs()[2],
                    t4

            );

            newTri1.setShading(shaded);
            newTri2.setShading(shaded);

            return new Tri[]{newTri1,newTri2};

        } else return null;
    }

    public void setTexs(Vec[] tin) {
        texs = tin;
    }
    public Vec[] getTexs() {return texs;}

    public Texture getTexture() {return texture;}
}
