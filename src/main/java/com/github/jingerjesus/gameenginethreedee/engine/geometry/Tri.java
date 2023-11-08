package com.github.jingerjesus.gameenginethreedee.engine.geometry;

import javafx.scene.paint.Color;

import java.util.function.Function;

public class Tri {
    private Vec[] vecs;

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

    public Tri(Vec p0, Vec p1, Vec p2) {
        vecs = new Vec[]{p0, p1, p2};
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


        double d0 = dist.apply(in.getVecs()[0]);
        double d1 = dist.apply(in.getVecs()[1]);
        double d2 = dist.apply(in.getVecs()[2]);

        //You have no idea what this does. Godspeed, future you.

        if (d0 >= 0) { insidePoints[inPointCount++] = in.getVecs()[0]; }
        else { outsidePoints[outPointCount++] = in.getVecs()[0]; }
        if (d1 >= 0) { insidePoints[inPointCount++] = in.getVecs()[1]; }
        else { outsidePoints[outPointCount++] = in.getVecs()[1]; }
        if (d2 >= 0) { insidePoints[inPointCount++] = in.getVecs()[2]; }
        else { outsidePoints[outPointCount++] = in.getVecs()[2]; }

        if (inPointCount == 0) {
            return new Tri[0];
        } else if (inPointCount == 3) {
            return new Tri[] {in};
        } else if (inPointCount == 1 && outPointCount == 2) {
            double shaded = in.getShading();

            Tri newTri = new Tri(
                    insidePoints[0],
                    Vec.intersectPlane(pPoint, pNorm, insidePoints[0], outsidePoints[0]),
                    Vec.intersectPlane(pPoint, pNorm, insidePoints[0], outsidePoints[1])
            );
            newTri.setShading(shaded);
            return new Tri[]{newTri};
        } else if (inPointCount == 2 && outPointCount == 1) {
            double shaded = in.getShading();

            Tri newTri1 = new Tri(
                    insidePoints[0],
                    insidePoints[1],
                    Vec.intersectPlane(pPoint, pNorm, insidePoints[0], outsidePoints[0] )
            );
            Tri newTri2 = new Tri(
                    insidePoints[1],
                    newTri1.getVecs()[2],
                    Vec.intersectPlane(pPoint, pNorm, insidePoints[1], outsidePoints[0])
            );

            newTri1.setShading(shaded);
            newTri2.setShading(shaded);

            return new Tri[]{newTri1,newTri2};

        } else return null;
    }
}
