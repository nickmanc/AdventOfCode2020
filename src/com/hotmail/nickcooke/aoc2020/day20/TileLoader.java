package com.hotmail.nickcooke.aoc2020.day20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TileLoader {
    List<Tile> tiles = null;
    
    List<Tile> load() {
        try {
            tiles = loadTiles();
            recordAllMatchingEdges( tiles );
            validateThatAllEdgesAreUnique( tiles );
        }
        catch ( IOException e ) {
            throw new RuntimeException( "Problem loading tiles" );
        }
        return tiles;
    }
    
    private List<Tile> loadTiles() throws IOException {
        String input = Files.readString( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day20Input" ) );
        String[] tileStrings = input.split( "\r\n\\s*\r\n" );
        List<Tile> tiles = new ArrayList<>();
        for ( String tileString : tileStrings ) {
            tiles.add( new Tile( tileString ) );
        }
        return tiles;
    }
    
    void recordAllMatchingEdges( List<Tile> tiles ) {
        for ( int i = 0; i < tiles.size(); i++ ) {
            Tile tile = tiles.get( i );
            for ( int j = i + 1; j < tiles.size(); j++ ) {
                Tile otherTile = tiles.get( j );
                for ( String pattern : tile.allEdgePatterns ) {
                    if ( otherTile.edgePatterns.contains( pattern ) ) {
                        tile.matchingPatterns.add( pattern );
                        otherTile.matchingPatterns.add( pattern );
                        tile.matchingPatterns.add( TileHelper.reverse( pattern ) );
                        otherTile.matchingPatterns.add( TileHelper.reverse( pattern ) );
                    }
                }
            }
        }
    }
    
    private void validateThatAllEdgesAreUnique( List<Tile> tiles ) {
        int gridSize = getGridSize();
        int adjoiningEdgesInGridCount = 2 * ( gridSize * gridSize ) - ( 2 * gridSize );
        System.out.println( "tileCount: " + ( gridSize * gridSize ) + ", gridSize: " + gridSize + ", adjoiningEdgesInGridCount=" + adjoiningEdgesInGridCount );
        
        int totalMatchingEdges = tiles.stream().mapToInt( tile -> tile.matchingPatterns.size() ).sum();
        assert ( 2 * 2 * adjoiningEdgesInGridCount == totalMatchingEdges );//storing each pattern forwards & backwards, and for each tile
        List<String> allMatchingPatterns = new ArrayList<>();
        for ( Tile tile : tiles ) {
            allMatchingPatterns.addAll( tile.matchingPatterns );
        }
        for ( String pattern : allMatchingPatterns ) {
            //each matching pattern appears twice (i.e. there for any given edge there is only one matching edge)
            assert 2 == allMatchingPatterns.stream().filter( p -> p.equals( pattern ) ).count();
        }
        System.out.println( allMatchingPatterns.size() );
    }
    
    int getGridSize() {
        int tileCount = tiles.size();
        return (int) Math.sqrt( tileCount );
    }
}
