package com.example.hugo.opengl1tutorial;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.hugo.opengl1tutorial.mesh.SimplePlane;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        GLSurfaceView view = new GLSurfaceView(this);
        OpenGLRenderer renderer = new OpenGLRenderer();
        view.setRenderer(renderer);
        setContentView(view);

        SimplePlane simplePlane = new SimplePlane(1,1);
        simplePlane.z = 0.3f;
        simplePlane.rx = -15;
        simplePlane.loadBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.jay));
        renderer.addMesh(simplePlane);
    }
}
