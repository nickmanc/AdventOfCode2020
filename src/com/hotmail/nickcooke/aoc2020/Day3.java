package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day3
{
    public static void main( String[] args ) throws IOException
    {
        List<String> input  = Files.readAllLines( Paths.get("resources\\com\\hotmail\\nickcooke\\aoc2020\\Day3Input"));
        int treeCount=0;
        for ( int i = 0; i < input.size(); i ++)
        {
            if ( input.get( i ).charAt( ( i ) * 3 % input.get( i ).length() ) == '#' )
            {
                treeCount++;
            }
        }
        System.out.println( "Part 1: " +  treeCount + " trees were hit");
        System.out.println( "Part 2: " + countTrees( 1, 1 , input )
                * countTrees( 1, 3, input )
                * countTrees( 1, 5, input )
                * countTrees( 1, 7, input )
                * countTrees( 2, 1, input ) );
    }

    private static long countTrees( int stepsDown, int stepsRight, List<String> input )
    {
        long count = 0;
        for ( int i = stepsDown; i < input.size(); i += stepsDown )
        {
            if ( input.get( i ).charAt( ( i / stepsDown ) * stepsRight % input.get( i ).length() ) == '#' )
            {
                count++;
            }
        }
        return count;
    }
}