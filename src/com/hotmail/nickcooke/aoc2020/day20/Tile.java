package com.hotmail.nickcooke.aoc2020.day20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Tile {
    String top,left,right,bottom;
    long id;
    Set<String> matchingPatterns =new HashSet<>(  );
    List<String> edgePatterns =new ArrayList<>(  );
    List<String> allEdgePatterns =new ArrayList<>(  );
    char[][] data = new char[10][10];
    
    Tile(String input){
        List<String> lines = Arrays.asList( input.split( "\r\n" ) );
        String idLine = lines.get( 0 );
        id = Integer.parseInt( idLine.substring( 5,idLine.indexOf( ":" ) ));
        for (int lineNumber=1;lineNumber<lines.size();lineNumber++){
            data[10-lineNumber]=lines.get( lineNumber ).toCharArray();
        }
//        top=new String(data[0]);
//        bottom=new String(data[9]);
//        left="";
//        right="";
//        for (int i=0;i<10;i++){
//            left=left + data[i][0];
//            right=right + data[i][9];
//        }
        resetEdgePatterns();
        edgePatterns.addAll( Arrays.asList( top, bottom, left,right) );
        allEdgePatterns.addAll( edgePatterns );
        allEdgePatterns.addAll( Arrays.asList( TileHelper.reverse(top), TileHelper.reverse(bottom), TileHelper.reverse(left), TileHelper.reverse(right)) );
    }
    
    void print(){
        System.out.println("id: " + id);
        for (int x=9;x>=0;x--){
        for ( int y = 0; y < 10; y++ ) {
                    System.out.print(data[x][y]);
                }
            System.out.println();
        }
    }
    
    @Override
    public String toString() {
        return "Tile{" + "top='" + top + '\'' + '\'' + ", right='" + right + '\'' + ", bottom='" + bottom + '\'' + ", left='" + left  + ", id=" + id + ", matchingPatterns=" + matchingPatterns.size() + '}';
    }
    
    public boolean isCornerTile(){
        return matchingPatterns.size()==4;//storing each pattern forwards & backwards so 4 permutations for corners
    }
    public boolean isEdgeTile(){
        return matchingPatterns.size()==6;//storing each pattern forwards & backwards so 6 permutations for edges
    }
    public boolean isMiddleTile(){
        return matchingPatterns.size()==8;//storing each pattern forwards & backwards so 8 permutations for middle pieces
    }
    
    private void resetEdgePatterns() {
        top = String.valueOf( data[9]);
        bottom = String.valueOf( data[0]);
        left="";
        right="";
        for (int i=0;i<10;i++){
            left=left + data[i][0];
            right=right + data[i][9];
        }
        edgePatterns.clear();
        edgePatterns.addAll( Arrays.asList( top, bottom, left, right ) );
    }
    
    public void rotate() {
        data = TileHelper.rotate( data );
        resetEdgePatterns();
    }
    
    public void flip () {
        data = TileHelper.flip( data );
        resetEdgePatterns();
    }
    
    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        Tile tile = (Tile) o;
        return id == tile.id;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash( id );
    }
    
    public boolean alignForTopLeft() {
        for (int i=0; i<2; i++) {
            for ( int j = 0; j < 4; j++ ) {
                if ( matchingPatterns.contains( right ) && matchingPatterns.contains( bottom ) ) {
                    return true;
                }
                rotate();
            }
            flip();
        }
        return false;
    }
    
    public boolean align( String pattern, boolean matchToLeft ) {
        for (int i=0; i<2; i++) {
            for ( int j = 0; j < 4; j++ ) {
                String tileEdgePatternToMatch = matchToLeft?left:top;
                if ( tileEdgePatternToMatch.equals( pattern ) ) {
                    System.out.println( "Matched: " + id );
                    return true;
                }
                rotate();
            }
            flip();
        }
        return false;
    }
    
    public char[][] getTrimmedImage(){
        if (data.length != 10 || data[0].length != 10) {
            throw new RuntimeException( "Tile is not correct size!" );
        }
        char[][] trimmedImage=new char[8][8];
        for (int i=0;i<8;i++){
             for (int j=0;j<8;j++) {
                trimmedImage[i][j]=data[i+1][j+1];
            }
        }
        return trimmedImage;
    }
}
