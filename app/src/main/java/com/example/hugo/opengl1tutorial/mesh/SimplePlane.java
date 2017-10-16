package com.example.hugo.opengl1tutorial.mesh;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.example.hugo.opengl1tutorial.R;

/**
 * Created by Hugo on 16/10/2017.
 */

public class SimplePlane extends Mesh {
    //cria um plano com altura e largura de 1 unidade
    public SimplePlane() {
        this(1,1);
    }
    //cria um plano com altura height e largura width
    public SimplePlane(float width, float height) {
        float textureCoordinates[] = {
                0f, 2f,
                2f, 2f,
                0f, 0f,
                2f, 0f
        };

        short[] indices = new short[] {0, 1, 2, 1, 3, 2};
        float[] vertices = new float[] {
                -.5f, -.5f, 0f,
                .5f, .5f, 0f,
                -.5f, .5f, 0f,
                .5f, .5f, 0f
        };

        setIndices(indices);
        setVertices(vertices);
        setTexture(textureCoordinates);
    }
}
