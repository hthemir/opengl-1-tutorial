package com.example.hugo.opengl1tutorial;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.example.hugo.opengl1tutorial.mesh.Cube;
import com.example.hugo.opengl1tutorial.mesh.Group;
import com.example.hugo.opengl1tutorial.mesh.Mesh;
import com.example.hugo.opengl1tutorial.mesh.Plane;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Hugo on 15/10/2017.
 */

public class OpenGLRenderer implements GLSurfaceView.Renderer {
    private Mesh mRoot;
    private Group mGroup;
    private Cube mCube;

    public OpenGLRenderer() {
        mGroup = new Group();
        mCube = new Cube(1, 1, 1);
        mCube.rx = 45;
        mCube.ry = 45;
        mGroup.add(mCube);
        mRoot = mGroup;
    }

    //chamado quando a superficie eh criada ou recriada
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //torna o plano de fundo preto (rgba)
        gl.glClearColor(0f, 0f, 0f, 0.5f);
        //habilita sombreamento suave
        gl.glShadeModel(GL10.GL_SMOOTH);
        //configuracao do buffer de profundidade
        gl.glClearDepthf(1f);
        //habilita teste de profundidade
        gl.glEnable(GL10.GL_DEPTH_TEST);
        //tipo do teste de profundidade
        gl.glDepthFunc(GL10.GL_LEQUAL);
        //calculos de perspectiva
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }

    //chamado para desenhar o quadro atual
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //configura a janela de exibicao para o novo tamanho
        gl.glViewport(0, 0, width, height);
        //seleciona a matriz de projecao
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //reseta a matriz de projecao
        gl.glLoadIdentity();
        //calcula a proporcao da tela de exibicao
        GLU.gluPerspective(gl, 45f, (float) width / (float) height, 0.1f, 100f);
        //seleciona a matriz de modelo
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        //reseta a matriz de modelo
        gl.glLoadIdentity();
    }

    //chamado quando a superficie muda de tamanho
    @Override
    public void onDrawFrame(GL10 gl) {
        //limpa a tela e buffer de profundidade
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        //substitui a matriz atual pela matriz identidade
        gl.glLoadIdentity();
        //muda de posicao em 10 unidades pra dentro da tela
        gl.glTranslatef(0, 0, -10);

        mRoot.draw(gl);
    }
}
