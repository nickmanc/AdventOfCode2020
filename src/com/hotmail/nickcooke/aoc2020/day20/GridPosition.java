package com.hotmail.nickcooke.aoc2020.day20;

public class GridPosition {
    int x,y;

    public GridPosition( int x, int y ) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String toString() {
        return "["+ x + "," + y + ']';
    }

    public boolean isTopLeft() {
        return x==0  && y==0;
    }

    public boolean matchNextEdgeToLeft() {
        return x != 0;
    }
}
