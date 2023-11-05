package com.github.jingerjesus.gameenginethreedee.engine.geometry;

import com.github.jingerjesus.gameenginethreedee.engine.interactables.Room;


public class Matrix {
    private double[][] matrix;

    public Matrix(double[][] in) {
        matrix = in;
    }

    public double[][] getMatrix() {return matrix;}

    public static Matrix getIdentityMatrix() {
        return new Matrix(new double[][]{
                {1,0,0,0},
                {0,1,0,0},
                {0,0,1,0},
                {0,0,0,1}
        });
    }

    public static Matrix getProjectionMatrix(Room r) {
        double a = r.aspectRatio;
        double f = r.fov;
        double q = r.zNorm;
        double zn = r.zNear;
        return new Matrix(new double[][]{
                {a*f, 0, 0,     0},
                {0,   f, 0,     0},
                {0,   0, q,     1},
                {0,   0, -zn*q, 0}
        });
    }
    public static Vec MultiplyVector (Matrix matrix, Vec vec) {
        double[][] m = matrix.getMatrix();
        double[] v = vec.getVector();
        double[] out = new double[4];

        for (int r = 0; r < m.length; r ++) {
            for (int c = 0; c < m[r].length; c ++) {
                out[r] += v[r] * m[r][c];
            }
        }
        return new Vec(out);
    }
}
