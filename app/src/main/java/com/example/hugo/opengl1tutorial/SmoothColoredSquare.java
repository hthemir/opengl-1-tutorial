package com.example.hugo.opengl1tutorial;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Hugo on 15/10/2017.
 */

public class SmoothColoredSquare {//vertices
    private float[] vertices = {
            -1f, 1f, 0f,  //esquerda cima
            -1f, -1f, 0f, //esquerda baixo
            1f, -1f, 0f,  //direita baixo
            1f, 1f, 0f    //direita cima
    };

    private float[] colors = {
            1f, 0f, 0f, 1f, //vermelho
            0f, 1f, 0f, 1f, //verde
            0f, 0f, 1f, 1f, //azul
            1f, 0f, 1f, 1f  //magenta
    };

    //ordem das arestas
    private short[] indices = {0, 1, 2, 0, 2, 3};
    //buffer de vertice
    private FloatBuffer vertexBuffer;
    //buffer de indice
    private ShortBuffer indexBuffer;
    //buffer de cores
    private FloatBuffer colorBuffer;

    public SmoothColoredSquare() {
        //alocar 4 bytes por vertice
        ByteBuffer vByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        vByteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = vByteBuffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer iByteBuffer = ByteBuffer.allocateDirect(indices.length * 2);
        iByteBuffer.order(ByteOrder.nativeOrder());
        indexBuffer = iByteBuffer.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);

        ByteBuffer cByteBuffer = ByteBuffer.allocateDirect(colors.length * 4);
        cByteBuffer.order(ByteOrder.nativeOrder());
        colorBuffer = cByteBuffer.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);
    }

    public void draw(GL10 gl) {
        //ordem anti horaria
        gl.glFrontFace(GL10.GL_CCW);
        //habilita cull face
        //determina se um poligono de um objeto grafico esta visivel checando se sua face esta na ordem certa
        gl.glEnable(GL10.GL_CULL_FACE);
        //remove a face de tras
        gl.glCullFace(GL10.GL_BACK);
        //habilita o buffer de vertices para escrita e para ser usado na renderizacao
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //especifica a localizacao e o formato dos dados de um vetor de coordenadas de vertice para usar na renderizacao
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        //habilita o buffer de vetor de cores para usar na renderizacao
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        //aponta onde esta o buffer de cores
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);
        //desabilita o buffer de vertices
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        //desabilita o cull face
        gl.glDisable(GL10.GL_CULL_FACE);
        //desabilita o buffer de cores
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }
}

