package com.example.hugo.opengl1tutorial.mesh;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Hugo on 15/10/2017.
 */

public class Mesh {
    //buffer de vertices
    private FloatBuffer mVerticesBuffer = null;
    //buffer de indices
    private ShortBuffer mIndicesBuffer = null;
    //number of indices
    private int mNumOfIndices = -1;
    //flat color
    private float[] mRGBA = new float[]{1f, 1f, 1f, 1f};
    //smooth color
    private FloatBuffer mColorBuffer = null;

    //translation params
    public float x = 0;
    public float y = 0;
    public float z = 0;

    //rotation params
    public float rx = 0;
    public float ry = 0;
    public float rz = 0;

    public void draw(GL10 gl) {
        //sentido anti horario
        gl.glFrontFace(GL10.GL_CCW);
        //habilita face culling
        gl.glEnable(GL10.GL_CULL_FACE);
        //qual face remover com culling
        gl.glCullFace(GL10.GL_BACK);
        //habilita o buffer de vertices para escrita e uso na renderizacao
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //especifica o vetor de coordenadas de vertices a ser usado
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer);
        //estabelece a cor plana
        gl.glColor4f(mRGBA[0], mRGBA[1], mRGBA[2], mRGBA[3]);
        //estabelece a cor suave, se houver
        if (mColorBuffer != null) {
            //habilita o buffer de cores para uso na renderizacao
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            //especifica o vetor de cores a ser usado
            gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
        }

        //translate and rotate
        gl.glTranslatef(x,y,z);
        gl.glRotatef(rx, 1,0,0);
        gl.glRotatef(ry, 1,0,0);
        gl.glRotatef(rz, 1,0,0);

        gl.glDrawElements(GL10.GL_TRIANGLES, mNumOfIndices, GL10.GL_UNSIGNED_SHORT, mIndicesBuffer);
        //desabilita o buffer de vertices
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        //desabilita face culling
        gl.glDisable(GL10.GL_CULL_FACE);
    }

    //region setters
    protected void setVertices(float[] vertices) {
        //aloca 4 bytes por vertice
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        mVerticesBuffer = byteBuffer.asFloatBuffer();
        mVerticesBuffer.put(vertices);
        mVerticesBuffer.position(0);
    }

    protected void setIndices(short[] indices) {
        //aloca 2 bytes por indice
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(indices.length * 2);
        byteBuffer.order(ByteOrder.nativeOrder());
        mIndicesBuffer = byteBuffer.asShortBuffer();
        mIndicesBuffer.put(indices);
        mIndicesBuffer.position(0);
    }

    protected void setFlatColor(float red, float green, float blue, float alpha) {
        mRGBA[0] = red;
        mRGBA[1] = green;
        mRGBA[2] = blue;
        mRGBA[3] = alpha;
    }

    protected void setSmoothColor(float[] colors) {
        //aloca 4 bytes por cor
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(colors.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        mColorBuffer = byteBuffer.asFloatBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);
    }
    //endregion setters
}
