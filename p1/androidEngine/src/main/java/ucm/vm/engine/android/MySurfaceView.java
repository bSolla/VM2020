package ucm.vm.engine.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements Runnable {
    Graphics graphicsEngine;

    Thread _renderThread;
    SurfaceHolder _holder;

    volatile boolean _running = false;

    Paint _paint = new Paint();

    /**
     * Constructor.
     *
     * @param context Contexto en el que se integrará la vista
     *                (normalmente una actividad).
     */
    public MySurfaceView(Context context, Graphics engine) {
        super(context);
        _holder = getHolder();
        graphicsEngine = engine;
    } // MySurfaceView

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Método llamado para solicitar que se continue con el
     * active rendering. El "juego" se vuelve a poner en marcha
     * (o se pone en marcha por primera vez).
     */
    public void resume() {
        if (!_running) {
            // Solo hacemos algo si no nos estábamos ejecutando ya
            // (programación defensiva, nunca se sabe quién va a
            // usarnos...)
            _running = true;
            // Lanzamos la ejecución de nuestro método run()
            // en una hebra nueva.
            _renderThread = new Thread(this);
            _renderThread.start();
        } // if (!_running)

    } // resume

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Método llamado cuando el active rendering debe ser detenido.
     * Puede tardar un pequeño instante en volver, porque espera a que
     * se termine de generar el frame en curso.
     *
     * Se hace así intencionadamente, para bloquear la hebra de UI
     * temporalmente y evitar potenciales situaciones de carrera (como
     * por ejemplo que Android llame a resume() antes de que el último
     * frame haya terminado de generarse).
     */
    public void pause() {

        if (_running) {
            _running = false;
            while (true) {
                try {
                    _renderThread.join();
                    _renderThread = null;
                    break;
                } catch (InterruptedException ie) {
                    // Esto no debería ocurrir nunca...
                }
            } // while(true)
        } // if (_running)

    } // pause

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Método que implementa el bucle principal del "juego" y que será
     * ejecutado en otra hebra. Aunque sea público, NO debe ser llamado
     * desde el exterior.
     */
    @Override
    public void run() {

        if (_renderThread != Thread.currentThread()) {
            // ¿¿Quién es el tuercebotas que está llamando al
            // run() directamente?? Programación defensiva
            // otra vez, con excepción, por merluzo.
            throw new RuntimeException("run() should not be called directly");
        }

        // Antes de saltar a la simulación, confirmamos que tenemos
        // un tamaño mayor que 0. Si la hebra se pone en marcha
        // muy rápido, la vista podría todavía no estar inicializada.
        while(_running && getWidth() == 0)
            // Espera activa. Sería más elegante al menos dormir un poco.
            ;

        long lastFrameTime = System.nanoTime();

        long informePrevio = lastFrameTime; // Informes de FPS
        int frames = 0;


        // Bucle principal.
        while(_running) {
            graphicsEngine.run();
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
            update(elapsedTime);
            // Informe de FPS
            if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                System.out.println("" + fps + " fps");
                frames = 0;
                informePrevio = currentTime;
            }
            ++frames;

            // Pintamos el frame
            while (!_holder.getSurface().isValid())
                ;
            Canvas canvas = _holder.lockCanvas();
            render(canvas);
            _holder.unlockCanvasAndPost(canvas);
                /*
                // Posibilidad: cedemos algo de tiempo. es una medida conflictiva...
                try {
                    Thread.sleep(1);
                }
                catch(Exception e) {}
    			*/

        } // while

    } // run

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Realiza la actualización de "la lógica" de la aplicación. En particular,
     * desplaza el rótulo a su nueva posición en su deambular de izquierda
     * a derecha.
     *
     * @param deltaTime Tiempo transcurrido (en segundos) desde la invocación
     * anterior (frame anterior).
     */
    protected void update(double deltaTime) {


    } // update

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Dibuja en pantalla el estado actual de la aplicación.
     *
     * @param c Objeto usado para enviar los comandos de dibujado.
     */
    protected void render(Canvas c) {

        // Borramos el fondo.
        c.drawColor(0xFF0000FF); // ARGB

//        // Ponemos el rótulo (si conseguimos cargar la fuente)
//        if (_font != null) {
//            c.drawText("RENDERIZADO ACTIVO", (int) _x, 100, _paint);
//        } // if (_sprite != null)

    } // render

} // class MySurfaceView