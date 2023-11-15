package com.github.jingerjesus.gameenginethreedee.engine.peripherals;

import com.github.jingerjesus.gameenginethreedee.engine.Driver;
import com.github.jingerjesus.gameenginethreedee.engine.geometry.Tri;
import com.github.jingerjesus.gameenginethreedee.engine.geometry.Vec;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GraphicsScreen {
    private Canvas drawPlane;
    private GraphicsContext draw;
    private int WIDTH, HEIGHT;

    public GraphicsScreen() {
        drawPlane = new Canvas(100, 100);
        draw = drawPlane.getGraphicsContext2D();
        WIDTH = 100; HEIGHT = 100;
        Driver.getMainGroup().getChildren().add(drawPlane);
    }

    public GraphicsScreen(int w, int h) {
        drawPlane = new Canvas(w, h);
        draw = drawPlane.getGraphicsContext2D();
        WIDTH = w; HEIGHT = h;
        Driver.getMainGroup().getChildren().add(drawPlane);
    }

    public void cls() {
        draw.setStroke(Color.BLACK);
        draw.setFill(Color.BLACK);
        draw.strokeRect(0, 0, WIDTH, HEIGHT);
        draw.fillRect(0, 0, WIDTH, HEIGHT);
    }
    public void cls(Color c) {
        draw.setStroke(c);
        draw.setFill(c);
        draw.strokeRect(0, 0, WIDTH, HEIGHT);
        draw.fillRect(0, 0, WIDTH, HEIGHT);
    }

    public void drawLine(int x0, int y0, int x1, int y1) {
        draw.setStroke(Color.WHITE);
        draw.strokeLine(x0, y0, x1, y1);
    }
    public void drawLine(int x0, int y0, int x1, int y1, Color c) {
        draw.setStroke(c);
        draw.strokeLine(x0, y0, x1, y1);
    }

    public void drawTri(int x0, int y0, int x1, int y1, int x2, int y2) {
        drawLine(x0, y0, x1, y1);
        drawLine(x1, y1, x2, y2);
        drawLine(x0, y0, x2, y2);
    }
    public void drawTri(int x0, int y0, int x1, int y1, int x2, int y2, Color c) {
        drawLine(x0, y0, x1, y1, c);
        drawLine(x1, y1, x2, y2, c);
        drawLine(x0, y0, x2, y2, c);
    }

    public void fillTri(int x0, int y0, int x1, int y1, int x2, int y2) {
        draw.setFill(Color.WHITE);
        draw.fillPolygon(new double[]{x0, x1, x2}, new double[]{y0, y1, y2}, 3);
    }
    public void fillTri(int x0, int y0, int x1, int y1, int x2, int y2, Color c) {
        draw.setStroke(c);
        draw.setFill(c);
        draw.fillPolygon(new double[]{x0, x1, x2}, new double[]{y0, y1, y2}, 3);
    }

    public void fillTri(Tri in, Color color) {
        Vec[] pts = in.getVecs();
        fillTri(
                (int)pts[0].getX(), (int)pts[0].getY(),
                (int)pts[1].getX(), (int)pts[1].getY(),
                (int)pts[2].getX(), (int)pts[2].getY(),
                color
        );
    }

    public void drawTri(Tri in, Color color) {
        Vec[] pts = in.getVecs();
        drawTri(
                (int)pts[0].getX(), (int)pts[0].getY(),
                (int)pts[1].getX(), (int)pts[1].getY(),
                (int)pts[2].getX(), (int)pts[2].getY(),
                color
        );
    }
    public void plotPoint(int px, int py, Color c) {
        draw.getPixelWriter().setColor(px,py,c);
    }

    public void drawTexturedTriangle(Tri in) {
        int x1, y1, x2, y2, x3, y3;
        double u1, v1, u2, v2, u3, v3;

        x1 = (int)in.getVecs()[0].getX();
        x2 = (int)in.getVecs()[1].getX();
        x3 = (int)in.getVecs()[2].getX();

        y1 = (int)in.getVecs()[0].getY();
        y2 = (int)in.getVecs()[1].getY();
        y3 = (int)in.getVecs()[2].getY();

        u1 = in.getTexs()[0].getX();
        u2 = in.getTexs()[1].getX();
        u3 = in.getTexs()[2].getX();

        v1 = in.getTexs()[0].getY();
        v2 = in.getTexs()[1].getY();
        v3 = in.getTexs()[2].getY();

        double tempd;
        int tempi;

        if (y2 < y1) {
            tempi = y1; y1 = y2; y2 = tempi;
            tempi = x1; x1 = x2; x2 = tempi;

            tempd = u1; u1 = u2; u2 = tempd;
            tempd = v1; v1 = v2; v2 = tempd;
        }

        if (y3 < y1) {
            tempi = y1; y1 = y3; y3 = tempi;
            tempi = x1; x1 = x3; x3 = tempi;

            tempd = u1; u1 = u3; u3 = tempd;
            tempd = v1; v1 = v3; v3 = tempd;
        }

        if (y3 > y2) {
            tempi = y2; y2 = y3; y3 = tempi;
            tempi = x2; x2 = x3; x3 = tempi;

            tempd = u2; u2 = u3; u3 = tempd;
            tempd = v2; v2 = v3; v3 = tempd;
        }

        int dy1 = y2 - y1;
        int dx1 = x2 - x1;

        double dv1 = v2 - v1;
        double du1 = u2 - u1;



        int dy2 = y3 - y1;
        int dx2 = x3 - x1;

        double dv2 = v3 - v1;
        double du2 = u3 - u1;

        double tu, tv;

        double daxStep = 0, dbxStep = 0,
               du1Step = 0, dv1Step = 0,
               du2Step = 0, dv2Step = 0;

        if (dy1 != 0) {
            daxStep = 1.0 * dx1/(Math.abs(dy1));
            du1Step = 1.0 * du1/(Math.abs(dy1));
            dv1Step = 1.0 * dv1/(Math.abs(dy1));
        }
        if (dy2 != 0) {
            dbxStep = 1.0 * dx1/(Math.abs(dy2));
            du2Step = 1.0 * du2/(Math.abs(dy2));
            dv2Step = 1.0 * dv2/(Math.abs(dy2));
        }

        if (dy1 != 0) {
            for (int i = y1; i <= y2; i ++) {

                int ax = (int)(x1 + (double)((i-y1)*daxStep));
                int bx = (int)(x1 + (double)((i-y1)*dbxStep));

                double texSu = u1 + (double)(i-y1)*du1Step;
                double texSv = v1 + (double)(i-y1)*dv1Step;

                double texEu = u1 + (double)(i-y1)*du2Step;
                double texEv = v1 + (double)(i-y1)*dv2Step;

                if (ax > bx) {
                    tempi = ax; ax = bx; bx = tempi;
                    tempd = texSu; texSu = texEu; texEu = tempd;
                    tempd = texSv; texSv = texEv; texEv = tempd;
                }

                tu = texSu;
                tv = texSv;

                double tstep = 1.0 / ((double)(bx-ax));
                double t = 0.0;

                for (int j = ax; j < bx; j ++) {

                    tu = (1.0 - t) * texSu + t * texEu;
                    tv = (1.0 - t) * texSv + t * texEv;

                    plotPoint(j, i, in.getTexture().getNormalizedPixel(tu, tv));

                    t += tstep;

                }
            }

            dy1 = y3 - y2;
            dx1 = x3 - x2;
            dv1 = v3 - v2;
            du1 = u3 - u2;

            if (dy1 != 0) {
                daxStep = dx1/((double)Math.abs(dy1));
            }
            if (dy2 != 0) {
                dbxStep = dx2/(double)(1.0);
            }

        }
    }

    public Canvas get() {return drawPlane;}
}
