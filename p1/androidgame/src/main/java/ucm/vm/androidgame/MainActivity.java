package ucm.vm.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ucm.vm.engine.android.Graphics;
import ucm.vm.engine.android.MySurfaceView;
import ucm.vm.offthelinelogic.OffTheLineLogic;

public class MainActivity extends AppCompatActivity {
    MySurfaceView _renderView;
    Graphics androidEngine;
    OffTheLineLogic gameLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        androidEngine = new Graphics();
        gameLogic = new OffTheLineLogic();

        gameLogic.init(androidEngine);
        androidEngine.setLogic(gameLogic);

        _renderView = new MySurfaceView(this, androidEngine);
        _renderView.resume();
        _renderView.run();
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