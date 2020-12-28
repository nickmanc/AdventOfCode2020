package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day10_2 {
    public static void main( String[] args ) throws IOException {
        List<Integer> adapterJoltages = Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day10Input" ) ).stream()
                .map( Integer::parseInt )
                .collect( Collectors.toList() );
        adapterJoltages.add( 0 );
        Collections.sort( adapterJoltages );
        adapterJoltages.add( adapterJoltages.get( adapterJoltages.size() - 1 ) + 3 );
        int previousJoltage = 0;
        long total = 1;
        List<Integer> currentSet = new ArrayList<>();
        for ( int adapterJoltage : adapterJoltages ) {
            if ( adapterJoltage - previousJoltage == 3 ) {
                //this works on my data as there is no groups with more than 5 voltages, and no differences of 2 between voltages
                //am sure there will a more mathematical way of doing this,  buti worked them out manually
                if ( currentSet.size() > 2 ) {
                    if ( currentSet.size() == 3 ) {
                        total *= 2;
                    }
                    else if ( currentSet.size() == 4 ) {
                        total *= 4;
                    }
                    else if ( currentSet.size() == 5 ) {
                        total *= 7;
                    }
                }
                currentSet = new ArrayList<>();
            }
            currentSet.add( adapterJoltage );
            previousJoltage = adapterJoltage;
        }
        System.out.println( total );
    }
}
