package com.example.hugo.opengl1tutorial.mesh;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Hugo on 15/10/2017.
 */

public class Group extends Mesh {
    private Vector<Mesh> children = new Vector<Mesh>();

    @Override
    public void draw(GL10 gl) {
        for (int i = 0; i< children.size(); i ++) {
            children.get(i).draw(gl);
        }
    }

    public void add(int location, Mesh object) {
        children.add(location, object);
    }

    public boolean add(Mesh object) {
        return children.add(object);
    }

    public void clear() {
        children.clear();
    }

    public Mesh get(int location) {
        return children.get(location);
    }

    public Mesh remove(int location) {
        return children.remove(location);
    }

    public boolean remove(Mesh object) {
        return children.remove(object);
    }

    public int size() {
        return children.size();
    }
}
