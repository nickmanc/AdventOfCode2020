package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Day1
{
    public static void main( String[] args ) throws IOException
    {
        part1();
        part2();
    }
    protected static  void part1() throws IOException
    {
        List<Integer> input = Files.readAllLines( Paths.get("resources\\com\\hotmail\\nickcooke\\aoc2020\\Day1Input" ))
                .stream()
                .map( Integer::parseInt )
                .collect( Collectors.toList());
        for ( int number : input )
        {
            if ( input.contains( 2020 - number ) )
            {
                System.out.println( "Part 1: " + number * ( 2020 - number ) );
                return;
            }
        }
    }
    
    protected static void part2() throws IOException
    {
        List<Integer> input = Files.readAllLines( Paths.get("resources\\com\\hotmail\\nickcooke\\aoc2020\\Day1Input" ))
                .stream()
                .map( Integer::parseInt )
                .collect( Collectors.toList());
        for ( int number : input )
        {
            for ( int anotherNumber : input )
            {
                if ( input.contains( 2020 - number - anotherNumber) )
                {
                    System.out.println( "Part 2: " +  number * anotherNumber * ( 2020 - number - anotherNumber ) );
                    return;
                }
            }
        }
    }
    
}
