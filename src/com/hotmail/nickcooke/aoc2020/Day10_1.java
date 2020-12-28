package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day10_1 {
    public static void main( String[] args ) throws IOException {
        List<Integer> adapterJoltages = Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day10Input" ) ).stream()
                .map( Integer::parseInt )
                .sorted()
                .collect( Collectors.toList() );
        Map<Integer, Integer> joltageDifferences = new HashMap<>();
        int previousJoltage = 0;
        for ( int adapterJoltage : adapterJoltages ) {
            int joltageDifference = adapterJoltage - previousJoltage;
            joltageDifferences.merge( joltageDifference, 1, Integer::sum );
            previousJoltage = adapterJoltage;
        }
        System.out.println( ( joltageDifferences.get( 3 ) + 1 ) * joltageDifferences.get( 1 ) );
    }
}
