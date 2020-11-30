package ucm.vm.engine.desktop;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import ucm.vm.engine.AbstractGraphics;

public class Graphics extends AbstractGraphics {
    Color backgroundColor = new Color(0, 0, 0);
    Color playerColor =  new Color(0, 136, 255);
    Color currentColor;

    JFrame jFrame;
    Graphics2D g2D;

    @Override
    public void init(int width, int height, String windowName) {
        setWidth(width);
        setHeight(height);

        jFrame = new JFrame(windowName);
        jFrame.setSize(width, height);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Vamos a usar renderizado activo. No queremos que Swing llame al
        // método repaint() porque el repintado es continuo en cualquier caso.
        jFrame.setIgnoreRepaint(true);
        jFrame.setVisible(true);

        // Intentamos crear el buffer strategy con 2 buffers.
        int intentos = 100;
        while(intentos-- > 0) {
            try {
                jFrame.createBufferStrategy(2);
                break;
            }
            catch(Exception e) {
            }
        } // while pidiendo la creación de la buffeStrategy
        if (intentos == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        }

    }

    /**
     * Pinta ....
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    @Override
    public void drawLine(float x1, float y1, float x2, float y2) {
        g2D.setColor(currentColor);
        g2D.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }

    @Override
    public void fillRect(float x1, float y1, float x2, float y2) {
        g2D.setColor(currentColor);
        g2D.fillRect((int) x1, (int) y1, (int) x2, (int) y2);
    }

    @Override
    public void clear(int r, int g, int b) {
        currentColor = new Color(r, g, b);
        fillRect(0,0, getWidth(), getHeight());
    }

    @Override
    public void setColor(int r, int g, int b) {
        currentColor = new Color(r, g, b);
    }

    @Override
    public void run() {
        java.awt.image.BufferStrategy strategy = jFrame.getBufferStrategy();
        long lastFrameTime = System.nanoTime();

        while (true) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;

            gameLogic.update(elapsedTime);

            do {
                do {
                    g2D = (Graphics2D) strategy.getDrawGraphics();
                    try {
                        gameLogic.render();
                    } finally {
                        g2D.dispose();
                    }
                } while (strategy.contentsRestored());
                strategy.show();
            } while (strategy.contentsLost());
        }
    }
}