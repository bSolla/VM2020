package ucm.vm.engine.android;

import android.graphics.Color;
import android.view.View;

import ucm.vm.engine.AbstractGraphics;

public class Graphics extends AbstractGraphics {
    int backgroundColor = Color.rgb(0, 0,0);
    int playerColor = Color.rgb(0,136, 255);
    int currentColor;

    MySurfaceView surfaceView;

    public void setSurfaceView(MySurfaceView sv) {
        surfaceView = sv;
    }

    @Override
    public void init(int width, int height, String windowName) {
        setWidth(width);
        setHeight(height);

        //view = new View(this);
    }

    @Override
    public void drawLine(float x1, float y1, float x2, float y2) {
        surfaceView.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void fillRect(float x1, float y1, float x2, float y2) {
        surfaceView.fillRect(x1, y1, x2, y2);;
    }

    @Override
    public void clear(int r, int g, int b) {
        int c = Color.rgb(r, g, b);
        surfaceView.clear(c);
    }

    @Override
    public void setColor(int r, int g, int b) {
        currentColor = Color.rgb(r, g, b);
        surfaceView.setColor(currentColor);
    }

    @Override
    public void run() {

    }
}
