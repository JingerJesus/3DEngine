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

    public static Matrix getXRotationalMatrix(double θ) {
        return new Matrix(new double[][]{
                {1, 0, 0, 0},
                {0, Math.cos(Math.toRadians(θ)), Math.sin(Math.toRadians(θ)), 0},
                {0, -Math.sin(Math.toRadians(θ)), Math.cos(Math.toRadians(θ)), 0},
                {0, 0, 0, 1}
        });
    }

    public static Matrix getZRotationalMatrix(double θ) {
        return new Matrix(new double[][]{
                {Math.cos(Math.toRadians(θ)), Math.sin(Math.toRadians(θ)), 0, 0},
                {-Math.sin(Math.toRadians(θ)), Math.cos(Math.toRadians(θ)), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
    }

    public static Vec MultiplyVector (Matrix matrix, Vec vec) {
        double[][] m = matrix.getMatrix();
        double[] v = vec.getVector();
        double[] out = new double[4];

        out[0] = v[0] * m[0][0] + v[1] * m[1][0] + v[2] * m[2][0] + m[3][0];
        out[1] = v[0] * m[0][1] + v[1] * m[1][1] + v[2] * m[2][1] + m[3][1];
        out[2] = v[0] * m[0][2] + v[1] * m[1][2] + v[2] * m[2][2] + m[3][2];
        out[3] = v[0] * m[0][3] + v[1] * m[1][3] + v[2] * m[2][3] + m[3][3];

        return new Vec(out);
    }

    public static Vec projectVector(Matrix matrix, Vec vec) {
        double[][] m = matrix.getMatrix();
        double[] v = vec.getVector();
        double[] out = new double[4];

        out[0] = v[0] * m[0][0] + v[1] * m[1][0] + v[2] * m[2][0] + m[3][0];
        out[1] = v[0] * m[0][1] + v[1] * m[1][1] + v[2] * m[2][1] + m[3][1];
        out[2] = v[0] * m[0][2] + v[1] * m[1][2] + v[2] * m[2][2] + m[3][2];
        out[3] = v[0] * m[0][3] + v[1] * m[1][3] + v[2] * m[2][3] + m[3][3];

//        for (int i = 0; i < out.length; i ++) {
//            System.out.print(out[i] + ", ");
//        } System.out.println();

        if (out[3] != 0.0) {
            out[0] /= out[3];
            out[1] /= out[3];
        }
        return new Vec(out);
    }
}
