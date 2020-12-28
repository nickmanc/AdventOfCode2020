package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day20_1 {
    
    public static void main( String[] args ) throws IOException {
        List<Tile> tiles = loadTiles();
        System.out.println( tiles.stream().filter( Tile::isCornerTile ).map( t -> t.id ).reduce( 1L, ( total, element ) -> total * element ) );
    }
    
    static List<Tile> loadTiles() throws IOException {
        String input = Files.readString( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day20Input" ) );
        String[] tileStrings = input.split( "\r\n\\s*\r\n" );
        List<Tile> tiles = new ArrayList<>();
        for ( String tileString : tileStrings ) {
            tiles.add( new Tile( tileString ) );
        }
        recordAllMatchingEdges( tiles );
        return tiles;
    }
    
    static void recordAllMatchingEdges( List<Tile> tiles ) {
        for ( int i = 0; i < tiles.size(); i++ ) {
            Tile tile = tiles.get( i );
            for ( int j = i + 1; j < tiles.size(); j++ ) {
                Tile otherTile = tiles.get( j );
                for ( String pattern : tile.allEdgePatterns ) {
                    if ( otherTile.edgePatterns.contains( pattern ) ) {
                        tile.matchingPatterns.add( pattern );
                        otherTile.matchingPatterns.add( pattern );
                    }
                }
            }
        }
    }
}

class Tile {
    String top, left, right, bottom;
    
    long id;
    
    Set<String> matchingPatterns = new HashSet<>();
    
    List<String> edgePatterns = new ArrayList<>();
    
    List<String> reversedEdgePatterns = new ArrayList<>();
    
    List<String> allEdgePatterns = new ArrayList<>();
    
    Tile( String input ) {
        List<String> lines = Arrays.asList( input.split( "\r\n" ) );
        String idLine = lines.get( 0 );
        id = Integer.parseInt( idLine.substring( 5, idLine.indexOf( ":" ) ) );
        top = lines.get( 1 );
        bottom = reverse( lines.get( 10 ) );
        left = "";
        right = "";
        for ( int i = 1; i <= 10; i++ ) {
            right = right + lines.get( i ).charAt( 9 );
        }
        for ( int i = 10; i >= 1; i-- ) {
            left = left + lines.get( i ).charAt( 0 );
        }
        edgePatterns.addAll( Arrays.asList( top, bottom, left, right ) );
        reversedEdgePatterns.addAll( Arrays.asList( reverse( top ), reverse( bottom ), reverse( left ), reverse( right ) ) );
        allEdgePatterns.addAll( edgePatterns );
        allEdgePatterns.addAll( reversedEdgePatterns );
    }
    
    @Override
    public String toString() {
        return "Tile{" + "top='" + top + '\'' + '\'' + ", right='" + right + '\'' + ", bottom='" + bottom + '\'' + ", left='" + left + ", id=" + id + ", matchingPatterns=" + matchingPatterns.size() + '}';
    }
    
    public boolean isCornerTile() {
        return matchingPatterns.size() == 2;
    }
    
    private String reverse( String string ) {
        return new StringBuilder( string ).reverse().toString();
    }
}