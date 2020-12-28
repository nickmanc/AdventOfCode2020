package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day13_2 {
    public static void main( String[] args ) throws IOException {
        class Bus implements Comparable<Bus> {
            long busNumber;
            long timeOffSet;
            
            public Bus( long busNumber, long timeOffSet ) {
                this.busNumber = busNumber;
                this.timeOffSet = timeOffSet;
            }
            
            @Override
            public int compareTo( Bus bus ) {//sort high to low
                return ( (int) bus.busNumber - (int) this.busNumber );
            }
        }
    
        String[] input = Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day13Input" ) ).get( 1 ).split( "," );
        List<Bus> buses = IntStream.range( 0, input.length ).boxed()
                .filter( index -> input[index].matches( "\\d*" ) )
                .map( index -> new Bus( Integer.parseInt( input[index] ), index ) )
                .sorted()
                .collect( Collectors.toList() );
        
        int busCounter = 0;
        long timestamp = 0;
        long increment = 1;
        while ( busCounter < buses.size() ) {
            Bus bus = buses.get( busCounter );
            if ( ( timestamp + bus.timeOffSet ) % bus.busNumber == 0 ) {
                System.out.println( "found match for bus: " + bus.busNumber + " at " + ( timestamp ) );
                increment *= bus.busNumber;//as all bus numbers are prime we don't need to work out lowest common multiple - it is just their product
                //increment = caculateLowestCommonMultiple(  Arrays.asList( increment, bus.busNumber ));
                busCounter++;
                continue;
            }
            timestamp += increment;
        }
        System.out.println( "Answer is: " + timestamp );
    }
}