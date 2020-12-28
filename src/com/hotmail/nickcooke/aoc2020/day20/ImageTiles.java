package com.hotmail.nickcooke.aoc2020.day20;

import java.util.List;
import java.util.stream.Collectors;

public class ImageTiles {
    private List<Tile> unusedCornerTiles;
    private List<Tile> unusedEdgeTiles;
    private List<Tile> unusedMiddleTiles;
    private int gridSize;
    
    public ImageTiles( TileLoader tileLoader ) {
        List<Tile> tiles = tileLoader.load();
        gridSize = tileLoader.getGridSize();
        unusedCornerTiles = tiles.stream().filter( Tile::isCornerTile ).collect( Collectors.toList() );
        unusedEdgeTiles = tiles.stream().filter( Tile::isEdgeTile ).collect( Collectors.toList() );
        unusedMiddleTiles = tiles.stream().filter( Tile::isMiddleTile ).collect( Collectors.toList() );
    }
    
    public List<Tile> getUnusedCornerTiles() {
        return unusedCornerTiles;
    }
    
    public List<Tile> getUnusedEdgeTiles() {
        return unusedEdgeTiles;
    }
    
    public List<Tile> getUnusedMiddleTiles() {
        return unusedMiddleTiles;
    }
    
    public List<Tile> getPossibleTiles( GridPosition gridPosition ) {
        if ( gridPositionIsCorner( gridPosition ) ) {
            return getUnusedCornerTiles();
        }
        else if ( gridPositionIsEdge( gridPosition ) ) {
            return getUnusedEdgeTiles();
        }
        else {
            return getUnusedMiddleTiles();
        }
    }
    
    private boolean gridPositionIsCorner( GridPosition gridPosition ) {
        return ( gridPosition.x == 0 && gridPosition.y == 0 ) || ( gridPosition.x == 0 && gridPosition.y == gridSize - 1 ) || ( gridPosition.x == gridSize - 1 && gridPosition.y == 0 ) || ( gridPosition.x == gridSize - 1 && gridPosition.y == gridSize - 1 );
    }
    
    private boolean gridPositionIsEdge( GridPosition gridPosition ) {
        return !gridPositionIsCorner( gridPosition ) && ( gridPosition.x == 0 || gridPosition.x == gridSize - 1 || gridPosition.y == 0 || gridPosition.y == gridSize - 1 );
    }
    
}
