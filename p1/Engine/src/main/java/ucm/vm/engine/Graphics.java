package ucm.vm.engine;

import ucm.vm.generallogic.Logic;

public interface Graphics {
    //region METODOS
    /**
     * Pinta ....
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    void drawLine(float x1, float y1, float x2, float y2);

    void fillRect(float x1, float y1, float x2, float y2);

    void clear(int r, int g, int b);

    void setColor(int r, int g, int b);

    void init(int width, int height, String windowName);

    void run();

    int getWidth();
    int getHeight();

    void setWidth(int w);
    void setHeight(int h);

    void setLogic(Logic logic);
    //endregion
}