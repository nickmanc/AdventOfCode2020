package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day15_1
{
    public static void main( String[] args ) throws IOException {
    
        List<String> input = Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day15Input" ) );
        List<Integer> inputValues= Arrays.stream( input.get( 0 ).split( "," ) ).map( Integer::parseInt ).collect( Collectors.toList());
        Map<Integer,Integer> valuesToLastOccurence = new HashMap<>(  );
        for (int index=0;index < inputValues.size();index++){
            valuesToLastOccurence.put( inputValues.get( index ),index+1 );
        }
        int lastValue=0;
        int nextValue;
        for(int i=inputValues.size()+1;i<=2020;i++){
            if (valuesToLastOccurence.containsKey( lastValue ))
            {
                nextValue=i-valuesToLastOccurence.get( lastValue );
            }
            else {
                nextValue=0;
            }
            System.out.println(i+": " + lastValue + "|" + nextValue);
            valuesToLastOccurence.put( lastValue,i);
            lastValue=nextValue;
        }
    }
}
