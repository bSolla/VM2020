package ucm.vm.engine;

import ucm.vm.generallogic.Logic;

public abstract class AbstractGraphics implements ucm.vm.engine.Graphics {
    //region VARIABLES
    protected int width = 0;
    protected int height = 0;
    protected Logic gameLogic;
    //endregion

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setWidth(int w) {
        width = w;
    }

    @Override
    public void setHeight(int h) {
        height = h;
    }

    @Override
    public void setLogic(Logic logic) { gameLogic = logic;}

    public abstract void drawLine(float x1, float y1, float x2, float y2);

    public abstract void fillRect(float x1, float y1, float x2, float y2);

    public abstract void clear(int r, int g, int b);

    public abstract void setColor(int r, int g, int b);

    public abstract void init(int width, int height, String windowName);

    public abstract void run();
}
