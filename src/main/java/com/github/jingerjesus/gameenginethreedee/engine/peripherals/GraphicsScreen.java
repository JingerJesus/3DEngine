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

    public Canvas get() {return drawPlane;}
}
