package ucm.vm.offthelinelogic;

import ucm.vm.engine.Graphics;
import ucm.vm.generallogic.Logic;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class OffTheLineLogic implements Logic {
    class Vertice {
        public int x, y;

        public Vertice(int i, int j) {
            x = i;
            y = j;
        }
    }
    class Segmento {
        public Vertice vertice1;
        public Vertice vertice2;

        public Segmento(Vertice v1, Vertice v2) {
            vertice1 = v1;
            vertice2 = v2;
        }
    }
    Graphics engine;
    final String jsonFileLocation = "assets/levels.json";

    String lvlName;
    Vector<Vertice> vectorVertices;
    Vector<Segmento> vectorSegmentos;

    public void init(Graphics graphics) {
        engine = graphics;
        vectorVertices = new Vector<Vertice>();
        vectorSegmentos = new Vector<Segmento>();

        readJSON();
    }

    @Override
    public void render() {
        engine.clear(0, 0,0);
        engine.setColor(255, 50, 0);

        int offsetX = 320;
        int offsetY = 240;
        for (Segmento segmento : vectorSegmentos) {
            engine.drawLine(
                    segmento.vertice1.x + offsetX,
                    segmento.vertice1.y + offsetY,
                    segmento.vertice2.x + offsetX,
                    segmento.vertice2.y + offsetY
            );
        }
        //engine.drawLine(0, 0, 100, 100);
        //engine.drawLine(100, 100, 0, 200);
    }

    @Override
    public void update(double deltaTime) {
        // movimientos etc
    }

    void readJSON() {
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(new FileReader(jsonFileLocation));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = (JSONArray) obj;
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);

        String levelName = (String) jsonObject.get("name");

        vectorVertices.clear();
        JSONArray paths = (JSONArray) jsonObject.get("paths");
        JSONArray vertices = (JSONArray) paths.get(0);
        for (Object vertice : vertices) {
            JSONObject points = (JSONObject) vertice;
            int xVal = (int) points.get("x");
            int yVal = (int) points.get("y");
            Vertice v = new Vertice(xVal, yVal);
            vectorVertices.add(v);
        }

        int nVertices = vectorVertices.size();
        for (int i = 0; i < nVertices; ++i) {
            Vertice v1 = vectorVertices.elementAt(i);
            int j = i + 1;
            if (j == nVertices) { j = 0; }
            Vertice v2 = vectorVertices.elementAt(j);

            Segmento s = new Segmento(v1, v2);
            vectorSegmentos.add(s);
        }
    }
}