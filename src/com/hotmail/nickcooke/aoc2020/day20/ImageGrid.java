package com.hotmail.nickcooke.aoc2020.day20;

import java.util.Iterator;

public class ImageGrid implements Iterator<GridPosition> {
    private Tile[][] grid;
    int gridSize;
    int nextX = 0;
    int nextY = 0;
    
    public ImageGrid( int gridSize ) {
        this.gridSize = gridSize;
        grid = new Tile[gridSize][gridSize];
    }
    
    public Tile getTileAt( GridPosition position ) {
        return grid[position.x][position.y];
    }
    
    public void setTileAt( GridPosition gridPosition, Tile tile ) {
        grid[gridPosition.x][gridPosition.y] = tile;
    }
    
    @Override
    public boolean hasNext() {
        return nextY < gridSize;
    }
    
    @Override
    public GridPosition next() {
        if ( !hasNext() ) {
            throw new RuntimeException( "Problem with the iterator" );
        }
        GridPosition result = new GridPosition( nextX, nextY );
        if ( nextX == gridSize - 1 ) {
            nextX = 0;
            nextY++;
        }
        else {
            nextX++;
        }
        return result;
    }
    
    public void resetIterator() {
        nextX = 0;
        nextY = 0;
    }
    
    public String edgeToJoinTo( GridPosition position ) {
        if ( position.x == 0 ) {
            return grid[position.x][position.y - 1].bottom;
        }
        return grid[position.x - 1][position.y].right;
    }
    
    public void print() {
        for ( int x = 0; x < 3; x++ ) {
            for ( int y = 0; y < 3; y++ ) {
                getTileAt( new GridPosition( x, y ) ).print();
                System.out.println( "=======================" );
            }
        }
    }
}
