package com.example.hugo.opengl1tutorial;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Hugo on 15/10/2017.
 */

public class OpenGLRenderer implements GLSurfaceView.Renderer {

    private Square mSquare;
    private FlatColoredSquare mFlatColoredSquare;
    private SmoothColoredSquare mSmoothColoredSquare;
    private float mAngle;

    public OpenGLRenderer() {
        mSquare = new Square();
        mFlatColoredSquare = new FlatColoredSquare();
        mSmoothColoredSquare = new SmoothColoredSquare();
        mAngle = 0f;
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
        GLU.gluPerspective(gl, 45f, (float)width / (float)height, 0.1f, 100f);
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
        //muda de posicao em 4 unidades pra dentro da tela
        gl.glTranslatef(0,0,-10);

        //quadrado A, rotacionado sentido anti horario
        //salva a matriz atual
        gl.glPushMatrix();
        //rotaciona A em sentido anti horario
        gl.glRotatef(mAngle, 0, 0, 1);
        //desenha A
        mSquare.draw(gl);
        //restaura a ultima matriz
        gl.glPopMatrix();

        //quadrado B, 50% menor que A e rotacionado sentido horario
        //salva a matriz atual
        gl.glPushMatrix();
        //rotaciona antes de mover, fazendo rotacionar em volta de A
        gl.glRotatef(-mAngle,0, 0, 1);
        //move B
        gl.glTranslatef(2,0,0);
        //escala pra 50% de A
        gl.glScalef(.5f, .5f, .5f);
        mFlatColoredSquare.draw(gl);

        //quadrado C, 50% menor que B, rotaciona em sentido horario em volta de B e em sentido anti horario em relacao ao seu centro
        //salva a matriz atual
        gl.glPushMatrix();
        //faz a rotacao em volta de B
        gl.glRotatef(-mAngle, 0,0,1);
        gl.glTranslatef(2,0,0);
        //escala pra 50% de B
        gl.glScalef(.5f, .5f, .5f);
        //rotaciona ao redor de seu centro
        gl.glRotatef(mAngle*10, 0, 0, 1);
        mSmoothColoredSquare.draw(gl);

        //restaura para a matriz antes de C
        gl.glPopMatrix();
        //restaura para a matriz antes de B
        gl.glPopMatrix();

        //aumenta o angulo
        mAngle++;
    }
}
