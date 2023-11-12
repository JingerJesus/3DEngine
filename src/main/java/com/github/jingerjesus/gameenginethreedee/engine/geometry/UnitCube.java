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

    private Vec texOne = new Vec(0, 1);
    private Vec texTwo = new Vec(0, 0);
    private Vec texThree = new Vec(1, 0);
    private Vec texFour = new Vec(1, 1);


    public UnitCube() {

        mesh = new Mesh(new Tri[] {
                //SOUTH
                new Tri(one, two, three, texOne, texTwo, texThree),
                new Tri(one, three, four, texOne, texThree, texFour),

                //EAST
                new Tri(four, three, seven, texOne, texTwo, texThree),
                new Tri(four, seven, eight, texOne, texThree, texFour),

                //NORTH
                new Tri(eight, seven, six, texOne, texTwo, texThree),
                new Tri(eight, six, five, texOne, texThree, texFour),

                //WEST
                new Tri(five, six, two, texOne, texTwo, texThree),
                new Tri(five, two, one, texOne, texThree, texFour),

                //TOP
                new Tri(two, six, seven, texOne, texTwo, texThree),
                new Tri(two, seven, three, texOne, texThree, texFour),

                //BOTTOM
                new Tri(eight, five, one, texOne, texTwo, texThree),
                new Tri(eight, one, four, texOne, texThree, texFour)
        });
    }

    public Mesh getMesh() {return mesh;}
}
