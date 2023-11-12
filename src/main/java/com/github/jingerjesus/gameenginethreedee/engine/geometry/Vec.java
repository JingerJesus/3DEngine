package com.github.jingerjesus.gameenginethreedee.engine.geometry;

import com.github.jingerjesus.gameenginethreedee.engine.interactables.Room;

public class Vec {
    private double x,y,z,w = 1;

    public Vec() {
        x = 0.0; y = 0.0; z = 0.0;
    }

    public Vec(double i, double i1, double i2) {
        x = i; y = i1; z = i2;
    }
    public Vec(double i, double i1, double i2, double i3) {
        x = i; y = i1; z = i2; w = i3;
    }

    public Vec(double[] in) {
        if (in.length == 3) new Vec(in[0], in[1], in[2]);
        else if (in.length == 4) {
            x = in[0]; y = in[1]; z = in[2]; w = in[3];
        }
    }

    public Vec(double u, double v) {
        x = u; y = v; z = 0;
    }

    public double getX() {return this.x;}
    public double getY() {return y;}
    public double getZ() {return z;}

    public double[] getVector() {
        return new double[]{x, y, z, w};
    }

    public static Vec getVecBetween(Vec first, Vec second) {
        return new Vec(
                second.getX() - first.getX(),
                second.getY() - first.getY(),
                second.getZ() - first.getZ()
        );
    }

    public Vec cross(Vec other) {
        double newX, newY, newZ;
        newX = this.y * other.getZ() - this.z * other.getY();
        newY = this.z * other.getX() - this.x * other.getZ();
        newZ = this.x * other.getY() - this.y * other.getX();

        return new Vec(newX, newY, newZ);

    }

    public Vec normalize() {

        double mag = Math.sqrt(x*x + y*y + z*z);

        return new Vec(x/mag, y/mag, z/mag);
    }

    public static double DotProductOf(Vec v1, Vec v2) {
        return (
                v1.getX() * v2.getX() +
                        v1.getY() * v2.getY() +
                        v1.getZ() * v2.getZ()
                );
    }

    public Vec add(Vec other) {
        return new Vec(
                this.x + other.getX(),
                this.y + other.getY(),
                this.z + other.getZ()
        );
    }

    public Vec sub(Vec other) {
        return new Vec(
                x - other.getX(),
                y - other.getY(),
                z - other.getZ()
        );
    }

    public Vec mult(double other) {
        return new Vec(
                x * other,
                y * other,
                z * other
        );
    }

    public Vec div(double other) {
        return new Vec(
                x / other,
                y / other,
                z / other
        );
    }

    public static Vec intersectPlane(Vec plane_p, Vec plane_n, Vec lineStart, Vec lineEnd) {
        plane_n = plane_n.normalize();
        double plane_d = -Vec.DotProductOf(plane_n, plane_p);
        double ad = Vec.DotProductOf(lineStart, plane_n);
        double bd = Vec.DotProductOf(lineEnd, plane_n);
        double t = (-plane_d - ad) / (bd - ad);
        Vec lineStartToEnd = lineEnd.sub(lineStart);
        Vec lineToIntersect = lineStartToEnd.mult(t);
        Vec out = lineStart.add(lineToIntersect);
        return new Vec(out.getX(), out.getY(), out.getZ(), t);
    }

    public double getW() {return w;}
    public void setW(double i) {w = i;}

}
