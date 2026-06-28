package com.example.rpg_gui.map;

public class Position{
    private int myX;
    private int myY;

    public Position(int myX, int myY) {
        this.myX=myX;
        this.myY=myY;
    }

    public int getManhattanDistance(Position other) {
        return Math.abs(this.myX - other.getMyX()) + Math.abs(this.myY - other.getMyY());
    }

    public int getChebyshevDistance(Position other) {
        return Math.max(Math.abs(this.myX - other.getMyX()), Math.abs(this.myY - other.getMyY()));
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
