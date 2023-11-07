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

    public static Matrix getYRotationalMatrix(double θ) {
        return new Matrix(new double[][]{
                {Math.cos(Math.toRadians(θ)), 0, Math.sin(Math.toRadians(θ)), 0},
                {0, 1, 0, 0},
                {-Math.sin(Math.toRadians(θ)), 0, Math.cos(Math.toRadians(θ)), 0},
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

    public static Matrix getTranslationalMatrix(double dx, double dy, double dz) {
        return new Matrix(new double[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {dx, dy, dz, 1}
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

    public static Matrix getPointAtMatrix(Vec pos, Vec target, Vec up) {
        Vec newForward = target.sub(pos);
        newForward = newForward.normalize();

        Vec a = newForward.mult(Vec.DotProductOf(up, newForward));
        Vec newUp = up.sub(a);
        newUp = newUp.normalize();

        Vec newRight = newUp.cross(newForward);

        return new Matrix(new double[][]{
                {newRight.getX(), newRight.getY(), newRight.getZ(), 0},
                {newUp.getX(), newUp.getY(), newUp.getZ(), 0},
                {newForward.getX(), newForward.getY(), newForward.getZ(), 0},
                {pos.getX(), pos.getY(), pos.getZ(), 1}
        });
    }

    public static Matrix getQuickInverseOf(Matrix matrix) {
        double[][] m = matrix.getMatrix();

        return new Matrix(new double[][]{
                {m[0][0], m[1][0], m[2][0], 0},
                {m[0][1], m[1][1], m[2][1], 0},
                {m[0][2], m[1][2], m[2][2], 0},
                {
                        -(m[3][0] * m[0][0] + m[3][1] * m[0][1] + m[3][2] * m[0][2]),
                        -(m[3][0] * m[1][0] + m[3][1] * m[1][1] + m[3][2] * m[1][2]),
                        -(m[3][0] * m[2][0] + m[3][1] * m[2][1] + m[3][2] * m[2][2]),
                        1
                }
        });
    }

    public static Matrix MatrixMultiplication(Matrix matrix1, Matrix matrix2) {
        double[][] out = new double[4][4];
        double[][] m1 = matrix1.getMatrix();
        double[][] m2 = matrix2.getMatrix();

        for (int c = 0; c < 4; c ++) {
            for (int r = 0; r < 4; r ++) {
                out[r][c] = m1[r][0] * m2[0][c] + m1[r][1] * m2[1][c] + m1[r][2] * m2[2][c] + m1[r][3] * m2[3][c];
            }
        }

        return new Matrix(out);
    }
}
