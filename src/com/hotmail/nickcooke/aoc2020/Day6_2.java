package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day6_2 {
    public static void main( String... args ) throws IOException {
        String input = Files.readString( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day6Input" ) );
        String[] groups = input.split( "\r\n\\s*\r\n" );
        int sum = 0;
        for ( String group : groups ) {
            long groupSize = group.chars().filter( ch -> ch == '\n' ).count() + 1;
            sum += group.chars().distinct().filter( ch -> group.chars().filter( ch2 -> ch == ch2 ).count() == groupSize ).count();
        }
        System.out.println( sum );
    }
}
