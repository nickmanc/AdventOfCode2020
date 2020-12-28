package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day2_2
{
    public static void main( String[] args ) throws IOException
    {
        int count = 0;
        for ( String line :  Files.readAllLines( Paths.get("resources\\com\\hotmail\\nickcooke\\aoc2020\\Day2Input" )) )
        {
            if ( new Password( line ).isValid() )
            {
                count++;
            }
        }
        System.out.println( count );
    }
}

class Password
{
    char letter;
    int firstPosition;
    int secondPosition;
    String password;
    
    Password( String line )
    {
        letter = line.split( " " )[1].charAt( 0 );
        firstPosition = Integer.parseInt( line.split( " " )[0].split( "-" )[0] ) - 1;
        secondPosition = Integer.parseInt( line.split( " " )[0].split( "-" )[1] ) - 1;
        password = line.split( " " )[2];
    }
    
    boolean isValid()
    {
        boolean atPos1 = password.charAt( firstPosition ) == letter;
        boolean atPos2 = password.charAt( secondPosition ) == letter;
        return atPos1 ^ atPos2;
    }
}