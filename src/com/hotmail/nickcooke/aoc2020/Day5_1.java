package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day5_1
{
    public static void main( String[] args ) throws IOException
    {
        List<String> input = Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day5Input" ) );
        int maxId = 0;
        for ( String seat : input ) {
            int row = Integer.parseInt( seat.substring( 0, 7 ).replace( 'B', '1' ).replace( 'F', '0' ), 2 );
            int column = Integer.parseInt( seat.substring( 7, 10 ).replace( 'R', '1' ).replace( 'L', '0' ), 2 );
            maxId = Math.max( maxId, 8 * row + column );
        }
        System.out.println( "max seat id is: " + maxId );
    }
}
