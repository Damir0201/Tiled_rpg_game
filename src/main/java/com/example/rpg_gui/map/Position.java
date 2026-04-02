package com.example.rpg_gui.map;

public class Position{
    private int myX;
    private int myY;

    public Position(int myX, int myY) {
        this.myX=myX;
        this.myY=myY;
    }
    public int getMyX() {
        return myX;
    }
    public int getMyY() {
        return myY;
    }

    public void setMyX(int myX) {
        this.myX = myX;
    }
    public void setMyY(int myY) {
        this.myY = myY;
    }
}
