package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Day6_1
{
    public static void main( String ... args ) throws IOException
    {
        System.out.println(
            Arrays.stream( Files.readString( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day6Input" ) ).split( "\r\n\\s*\r\n" ) )
                .mapToLong( group ->   group.replace( "\r\n","").chars().distinct().count())
                .sum());
    }
}
