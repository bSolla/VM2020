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
        System.out.println("Hola");
        androidEngine = new Graphics();
        gameLogic = new OffTheLineLogic();

        gameLogic.init(androidEngine);
        androidEngine.setLogic(gameLogic);

        _renderView = new MySurfaceView(this, androidEngine);
        setContentView(_renderView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        _renderView.resume();

    } // onResume

    @Override
    protected void onPause() {
        super.onPause();
        _renderView.pause();

    } // onPause
}