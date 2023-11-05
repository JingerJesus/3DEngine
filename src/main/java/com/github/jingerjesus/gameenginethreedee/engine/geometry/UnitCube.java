package com.github.jingerjesus.gameenginethreedee.engine.geometry;

public class UnitCube {
    private Mesh mesh;
    //VERTICES
    private Vec one = new Vec(0.0, 0.0, 0.0);
    private Vec two = new Vec(0.0, 1.0, 0.0);
    private Vec three = new Vec(1.0, 1.0, 0.0);
    private Vec four = new Vec(1.0, 0.0, 0.0);

    private Vec five = new Vec(0.0, 0.0, 1.0);
    private Vec six = new Vec(0.0, 1.0, 1.0);
    private Vec seven = new Vec(1.0, 1.0, 1.0);
    private Vec eight = new Vec(1.0, 0.0, 1.0);
    public UnitCube() {

        mesh = new Mesh(new Tri[] {
                //SOUTH
                new Tri(one, two, three),
                new Tri(one, three, four),

                //EAST
                new Tri(four, three, seven),
                new Tri(four, seven, eight),

                //NORTH
                new Tri(eight, seven, six),
                new Tri(eight, six, five),

                //WEST
                new Tri(five, six, two),
                new Tri(five, two, one),

                //TOP
                new Tri(two, six, seven),
                new Tri(two, seven, three),

                //BOTTOM
                new Tri(eight, five, one),
                new Tri(eight, one, four)
        });
    }

    public Mesh getMesh() {return mesh;}
}
