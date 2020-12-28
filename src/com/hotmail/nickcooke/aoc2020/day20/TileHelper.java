package com.hotmail.nickcooke.aoc2020.day20;

public class TileHelper {
    public static String reverse( String string ) {
        return new StringBuilder( string ).reverse().toString();
    }
    
    public static char[][] rotate( char[][] input ) {
        char[][] result = new char[input.length][input.length];
        for ( int i = 0; i < input.length; i++ ) {
            for ( int j = 0; j < input.length; j++ ) {
                result[i][j] = input[input.length - j - 1][i];
            }
        }
        return result;
    }
    
    public static char[][] flip( char[][] input ) {
        char[][] result = new char[input.length][input.length];
        for ( int i = 0; i < input.length; i++ ) {
            for ( int j = 0; j < input.length; j++ ) {
                result[i][j] = input[i][input.length - j - 1];
            }
        }
        return result;
    }
}
