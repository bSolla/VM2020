package ucm.vm.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ucm.vm.engine.android.MySurfaceView;

public class MainActivity extends AppCompatActivity {
    MySurfaceView _renderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Preparamos el contenido de la actividad.
        _renderView = new MySurfaceView(this);
        setContentView(_renderView);
    }

    @Override
    protected void onResume() {
        // Avisamos a la vista (que es la encargada del active render)
        // de lo que está pasando.
        super.onResume();
        _renderView.resume();

    } // onResume

    @Override
    protected void onPause() {
        // Avisamos a la vista (que es la encargada del active render)
        // de lo que está pasando.
        super.onPause();
        _renderView.pause();

    } // onPause
}
