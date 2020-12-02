package ucm.vm.androidgame;

import ucm.vm.engine.android.Graphics;
import ucm.vm.engine.android.

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        InputStream inputStream = null;
        AssetManager assetManager = this.getAssets();

        try{
            inputStream = assetManager.open("androidlogo.png");
            _sprite = BitmapFactory.decodeStream(inputStream);
            Activity a = this;
        } catch (IOException e) {
            android.util.Log.e("MainActivity", "Error leyendo el sprite");
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
            }
        }
        _myView = new MyView(this);
        setContentView(_myView);
    }
    MySurfaceView _myView;

    /*protected void onResume() {
        super.onResume();
        _myView.resume();
    }

    protected void onPause() {
        super.onPause();
        _myView.pause();
    }*/

    Bitmap _sprite;

}
