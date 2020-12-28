package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day13_1 {
    public static void main( String[] args ) throws IOException {
        
        List<String> input = Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day13Input" )   );
        int earliestDepartureTime =  Integer.parseInt( input.get( 0 ));
        List<Integer> busNumbers = Arrays.stream( input.get( 1 ).split( "," ) )
                                    .filter(str -> str.matches( "\\d*" ) )
                                    .map(Integer::parseInt)
                                    .collect( Collectors.toList());
    
        for (int time=earliestDepartureTime;;time++){
            for(int bus:busNumbers){
                if (time%bus==0){
                    System.out.println((time - earliestDepartureTime )*bus);
                    return;
                }
            }
        }
    }
}
