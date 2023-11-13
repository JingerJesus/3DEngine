package com.github.jingerjesus.gameenginethreedee.engine.peripherals;

import com.github.jingerjesus.gameenginethreedee.engine.geometry.Mesh;
import com.github.jingerjesus.gameenginethreedee.engine.geometry.Tri;
import com.github.jingerjesus.gameenginethreedee.engine.geometry.UnitCube;
import com.github.jingerjesus.gameenginethreedee.engine.geometry.Vec;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ObjFileLoader {

    private static Scanner scan;

    public static Mesh getObj(String path) {

        Mesh temp = new UnitCube().getMesh();
        ArrayList<Vec> vecPool = new ArrayList<Vec>();
        ArrayList<Tri> triPool = new ArrayList<Tri>();

        File file = new File("src/main/java/com/github/jingerjesus/gameenginethreedee/game/models/objects/" + path);
        try {
            scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String currentLine = scan.nextLine();
                char type = currentLine.charAt(0);
                if (type == 'v') {
                    //vec here
                    currentLine = currentLine.substring(2);

                    String xString = currentLine.substring(0, currentLine.indexOf(" "));
                    double x = Double.parseDouble(xString);
                    currentLine = currentLine.substring(xString.length()+1);

                    String yString = currentLine.substring(0, currentLine.indexOf(" "));
                    double y = Double.parseDouble(yString);
                    currentLine = currentLine.substring(yString.length()+1);

                    String zString = currentLine;
                    double z = Double.parseDouble(zString);

                    vecPool.add(new Vec(x, y, z));

                } else if (type == 'f') {
                    //tri here
                    currentLine = currentLine.substring(2);

                    String p1 = currentLine.substring(0, currentLine.indexOf(" "));
                    int i1 = Integer.parseInt(p1) - 1;
                    currentLine = currentLine.substring(p1.length() + 1);

                    String p2 = currentLine.substring(0, currentLine.indexOf(" "));
                    int i2 = Integer.parseInt(p2) - 1;
                    currentLine = currentLine.substring(p2.length() + 1);

                    String p3 = currentLine;
                    int i3 = Integer.parseInt(p3) - 1;

                    triPool.add(new Tri(
                            vecPool.get(i1),
                            vecPool.get(i2),
                            vecPool.get(i3)
                    ));
                }
            }

            Tri[] trisFinal = new Tri[triPool.size()];

            for (int i = 0; i < triPool.size(); i ++) {
                trisFinal[i] = triPool.get(i);
            }

            return new Mesh(trisFinal);

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        return temp;
    }
}
