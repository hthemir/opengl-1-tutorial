package com.example.hugo.opengl1tutorial.mesh;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

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
    //buffer de smooth color
    private FloatBuffer mColorBuffer = null;
    //buffer de textura
    private FloatBuffer mTextureBuffer = null;
    //id de textura
    private int mTextureId = -1;
    //bitmap a ser carregado como textura
    private Bitmap mBitmap;
    //se alguem subiu um bitmap
    private boolean mShouldLoadTexture;

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

        if(mShouldLoadTexture) {
            loadGLTexture(gl);
            mShouldLoadTexture = false;
        }
        if (mTextureId != -1 && mTextureBuffer != null) {
            gl.glEnable(GL10.GL_TEXTURE_2D);
            //habilita o estado de textura
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

            //aponta para os buffers
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
        }

        //translate and rotate
        gl.glTranslatef(x,y,z);
        gl.glRotatef(rx, 1,0,0);
        gl.glRotatef(ry, 1,0,0);
        gl.glRotatef(rz, 1,0,0);

        gl.glDrawElements(GL10.GL_TRIANGLES, mNumOfIndices, GL10.GL_UNSIGNED_SHORT, mIndicesBuffer);
        //desabilita o buffer de vertices
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        if (mTextureId != -1 && mTextureBuffer != null)
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        //desabilita face culling
        gl.glDisable(GL10.GL_CULL_FACE);
    }

    private void loadGLTexture(GL10 gl) {
        //gera um ponteiro pra textura
        int[] textures = new int[1];
        gl.glGenTextures(1, textures, 0);
        mTextureId = textures[0];

        //vincula o ponteiro ao vetor
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);

        //cria a textura filtrada, redimensiona a imagem
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);

        //parametros de textura
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

        //especifica uma textura de imagem 2d do bitmap
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
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
        mNumOfIndices = indices.length;
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

    protected void setTexture(float[] textureCoordinates) {
        //aloca 4 bytes por vertice
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(textureCoordinates.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        mTextureBuffer = byteBuffer.asFloatBuffer();
        mTextureBuffer.put(textureCoordinates);
        mTextureBuffer.position(0);
    }

    public void loadBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        mShouldLoadTexture = true;
    }
    //endregion setters
}
