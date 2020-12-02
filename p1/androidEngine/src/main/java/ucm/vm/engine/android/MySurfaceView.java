package ucm.vm.engine.android;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;

public class MySurfaceView extends SurfaceView implements Runnable {
    Graphics graphicsEngine;

    Thread _renderThread;
    SurfaceHolder _holder;

    Bitmap _sprite;

    volatile boolean _running = false;

    Paint _paint = new Paint();

    public MySurfaceView(Context context, Graphics engine) {
        super(context);
        _holder = getHolder();
        graphicsEngine = engine;

        InputStream inputStream = null;
        AssetManager assetManager = context.getAssets();
        try{
            inputStream = assetManager.open("androidlogo.png");
            _sprite = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            android.util.Log.e("MainActivity", "Error leyendo el sprite");
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
            }
        }
    } // MySurfaceView

    public void resume() {
        if (!_running) {
            _running = true;
            _renderThread = new Thread(this);
            _renderThread.start();
        } // if (!_running)
    } // resume

    public void pause() {
        if (_running) {
            _running = false;
            while (true) {
                try {
                    _renderThread.join();
                    _renderThread = null;
                    break;
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            } // while(true)
        } // if (_running)
    } // pause

    @Override
    public void run() {

        if (_renderThread != Thread.currentThread()) {
            throw new RuntimeException("run() should not be called directly");
        }

        //while(_running && getWidth() == 0) ;

        long lastFrameTime = System.nanoTime();

        long informePrevio = lastFrameTime; // Informes de FPS
        int frames = 0;

        // Bucle principal.
        while(_running) {
            graphicsEngine.run();
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double delta = (double) nanoElapsedTime / 1.0E9;
            update(delta);
            // Informe de FPS
            if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                System.out.println("" + fps + " fps");
                frames = 0;
                informePrevio = currentTime;
            }
            ++frames;

            // Pintamos el frame
            while (!_holder.getSurface().isValid()) {
                Canvas canvas = _holder.lockCanvas();
                render(canvas);
                _holder.unlockCanvasAndPost(canvas);
            }
                /*
                // Posibilidad: cedemos algo de tiempo. es una medida conflictiva...
                try {
                    Thread.sleep(1);
                }
                catch(Exception e) {}
    			*/
        } // while
    } // run

    protected void update(double delta) {
        _x += _incX * delta;
        if (_x < 0) {
            _x = -_x;
            _incX *= -1;
        }
        else if (_x > getWidth() - _imageWidht) {
            _x = 2*(getWidth() - _imageWidht) - _x;
            _incX *= -1;
        }
    } // update

    protected void render(Canvas c) {
        c.drawColor(0xFF0000FF); // ARGB
        if (_sprite != null) {
            c.drawBitmap(_sprite, (int)_x, 100, null);
        } // if (_sprite != null)
    } // render

    double _x = 0;
    int _incX = 50;
    int _imageWidht;
} // class MySurfaceView