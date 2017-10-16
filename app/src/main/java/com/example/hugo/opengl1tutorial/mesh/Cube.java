package com.example.hugo.opengl1tutorial.mesh;

/**
 * Created by Hugo on 15/10/2017.
 */

public class Cube extends Mesh {
    public Cube(float width, float height, float depth) {
        width /= 2;
        height /= 2;
        depth /= 2;

        float vertices[] = {
                -width, -height, -depth,    //esquerda, baixo, fundo
                 width, -height, -depth,    //direita,  baixo, fundo
                 width,  height, -depth,    //direita,  cima,  fundo
                -width,  height, -depth,    //esquerda, cima,  fundo
                -width, -height,  depth,    //esquerda, baixo, frente
                 width, -height,  depth,    //direita,  baixo, frente
                 width,  height,  depth,    //direita,  cima,  frente
                -width,  height,  depth,    //esquerda, cima,  frente
        };

        short indices[] = {
                0, 4, 5,
                0, 5, 1,
                1, 5, 6,
                1, 6, 2,
                2, 6, 7,
                2, 7, 3,
                3, 7, 4,
                3, 4, 0,
                4, 7, 6,
                4, 6, 5,
                3, 0, 1,
                3, 1, 2
        };

        setIndices(indices);
        setVertices(vertices);
    }

    //construtor com segmentos
    //public Cube(float width, float height, float depth, int widthSegments, int heightSegments, int depthSegments) {}
}
