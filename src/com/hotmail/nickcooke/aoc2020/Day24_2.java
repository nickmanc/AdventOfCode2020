package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day24_2 extends Day24_1{
    private static final int FLOOR_RADIUS = 70;
    
    public static void main( String[] args ) throws IOException {
        List<List<HexagonalDirection>> directionsToTilesToBeFlipped = getDirectionsToTilesToBeFlipped();
        Map<TilePosition, Boolean> flippedTiles = flipTiles( directionsToTilesToBeFlipped );
        Map<TilePosition, Boolean> floor = initialiseFloor(flippedTiles);
        for (int i=0;i<100;i++) {
            floor = runDay( floor );
        }
        System.out.println( floor.entrySet().stream().filter( Map.Entry::getValue ).count() );
    }
    
    private static Map<TilePosition, Boolean> runDay( Map<TilePosition, Boolean> floor ) {
        Map<TilePosition, Boolean> floorCopy= new HashMap<>();
        for ( TilePosition tile: floor.keySet())
        {
            int blackNeighbouringTiles = getNeighbouringBlackTileCount(tile, floor);
            TilePosition tileCopy=new TilePosition( tile );
            boolean thisTileIsBlack = (floor.get(tile));
            if ( thisTileIsBlack ) {
                if ( blackNeighbouringTiles == 0 || blackNeighbouringTiles > 2 ) {
                    thisTileIsBlack=false;
                }
            }
            else {
                if ( blackNeighbouringTiles == 2 ) {
                    thisTileIsBlack=true;
                }
            }
            floorCopy.put( tileCopy, thisTileIsBlack );
        }
        return floorCopy;
    }
    
    private static int getNeighbouringBlackTileCount( TilePosition tile, Map<TilePosition, Boolean> floor ) {
        int count = 0;
        count = floor.containsKey( tile.getNeighbour( HexagonalDirection.e )) && floor.get(tile.getNeighbour( HexagonalDirection.e )) ? count+1 : count;
        count = floor.containsKey( tile.getNeighbour( HexagonalDirection.se )) && floor.get(tile.getNeighbour( HexagonalDirection.se )) ? count+1 : count;
        count = floor.containsKey( tile.getNeighbour( HexagonalDirection.sw )) && floor.get(tile.getNeighbour( HexagonalDirection.sw )) ? count+1 : count;
        count = floor.containsKey( tile.getNeighbour( HexagonalDirection.w )) && floor.get(tile.getNeighbour( HexagonalDirection.w )) ? count+1 : count;
        count = floor.containsKey( tile.getNeighbour( HexagonalDirection.nw )) && floor.get(tile.getNeighbour( HexagonalDirection.nw )) ? count+1 : count;
        count = floor.containsKey( tile.getNeighbour( HexagonalDirection.ne )) && floor.get(tile.getNeighbour( HexagonalDirection.ne )) ? count+1 : count;
        return count;
    }
    
    private static Map<TilePosition, Boolean> initialiseFloor(Map<TilePosition, Boolean> flippedHexagons) {
        Map<TilePosition, Boolean> tiles = createAllWhiteFloor();
        tiles.putAll( flippedHexagons );
        return tiles;
    }
    
    private static Map<TilePosition, Boolean> createAllWhiteFloor() {
        TilePosition currentTile = new TilePosition(  );
        Map<TilePosition, Boolean> tiles=new HashMap<>(  );
        tiles.put( new TilePosition(), false );
        int loop=1;
        while (loop<FLOOR_RADIUS){
            //loops around in spiral from a central point, increase FLOOR_RADIUS if floor isn't big enough - should do this dynamically but can't be bothered...
            currentTile.move( HexagonalDirection.e );
            Map<TilePosition, Boolean>tilesInThisLoop = addSidesOfCurrentHexagon(currentTile,loop);
            tiles.putAll( tilesInThisLoop );
            loop++;
        }
        return tiles;
    }
    
    private static Map<TilePosition, Boolean> addSidesOfCurrentHexagon( TilePosition currentTile, int hexagonSideLength ) {
        Map<TilePosition, Boolean> tilesInThisLoop=new HashMap<>(  );
        tilesInThisLoop.putAll( getSideOfCurrentHexagon ( currentTile,HexagonalDirection.sw, hexagonSideLength  ) );
        tilesInThisLoop.putAll( getSideOfCurrentHexagon ( currentTile,HexagonalDirection.w, hexagonSideLength  ) );
        tilesInThisLoop.putAll( getSideOfCurrentHexagon ( currentTile,HexagonalDirection.nw, hexagonSideLength  ) );
        tilesInThisLoop.putAll( getSideOfCurrentHexagon ( currentTile,HexagonalDirection.ne, hexagonSideLength  ) );
        tilesInThisLoop.putAll( getSideOfCurrentHexagon ( currentTile,HexagonalDirection.e, hexagonSideLength  ) );
        tilesInThisLoop.putAll( getSideOfCurrentHexagon ( currentTile,HexagonalDirection.se, hexagonSideLength  ) );
        return tilesInThisLoop;
    }
    
    private static Map<TilePosition, Boolean> getSideOfCurrentHexagon( TilePosition currentTile, HexagonalDirection hexagonalDirection, int hexagonSideLength ) {
        Map<TilePosition, Boolean> tilesOnThisSide=new HashMap<>(  );
        for (int i=1;i<=hexagonSideLength;i++) {
            tilesOnThisSide.put( currentTile.move( hexagonalDirection ), false );
        }
        return tilesOnThisSide;
    }
}
