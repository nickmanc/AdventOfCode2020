package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day5_2
{
    public static void main( String[] args ) throws IOException
    {
        List<String> input  = Files.readAllLines( Paths.get("resources\\com\\hotmail\\nickcooke\\aoc2020\\Day5Input"));
        Map<Integer,Integer> seats = new HashMap<>();
        for (String seat:input) {
            int row = Integer.parseInt(seat.substring( 0,7 ).replace( 'B','1' ).replace( 'F','0' ),2);
            int column = Integer.parseInt(seat.substring( 7,10 ).replace( 'R','1' ).replace( 'L','0' ),2);
            seats.put( 8 * row + column, row);
        }
        int minRow = Collections.min(seats.values());
        int maxRow = Collections.min(seats.values());
        seats.values().remove(minRow );
        seats.values().remove(maxRow );
        for (int seat:seats.keySet()) {
            if (!seats.containsKey( seat + 1 ) && seats.containsKey( seat + 2 )) {
                System.out.println(seat + 1);
            }
        }
    }
}
