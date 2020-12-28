package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day2_1 {
    public static void main( String[] args ) throws IOException {
        int count = 0;
        for ( String line : Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day2Input" ) ) ) {
            if ( new Password_Part1( line ).isValid() ) {
                count++;
            }
        }
        System.out.println( count );
    }
}


class Password_Part1
{
    char letter;
    int minOccurences;
    int maxOccurences;
    String password;
    
    Password_Part1( String line )
    {
        letter = line.split( " " )[1].charAt( 0 );
        minOccurences = Integer.parseInt( line.split( " " )[0].split( "-" )[0] );
        maxOccurences = Integer.parseInt( line.split( " " )[0].split( "-" )[1] );
        password = line.split( " " )[2];
    }
    
    boolean isValid()
    {
        int originalLength = password.length();
        int strippedLength = password.replace( letter + "", "" ).length();
        return originalLength - strippedLength >= minOccurences && originalLength - strippedLength <= maxOccurences;
    }
}