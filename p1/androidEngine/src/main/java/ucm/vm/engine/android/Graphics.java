package ucm.vm.engine.android;
import android.graphics.Color;
import android.view.View;

import ucm.vm.engine.AbstractGraphics;

public class Graphics extends AbstractGraphics {
    int backgroundColor = Color.rgb(0, 0,0);
    int playerColor = Color.rgb(0,136, 255);

    @Override
    public void init(int width, int height, String windowName) {
        setWidth(width);
        setHeight(height);

        //view = new View(this);
    }

    @Override
    public void drawLine(float x1, float y1, float x2, float y2) {

    }

    @Override
    public void fillRect(float x1, float y1, float x2, float y2) {

    }

    @Override
    public void clear(int r, int g, int b) {

    }

    @Override
    public void setColor(int r, int g, int b) {

    }

    @Override
    public void run() {

    }
}
