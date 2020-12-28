package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14_1 {
    static Map<Integer,Long> memory = new HashMap<>(  );
    public static void main( String[] args ) throws IOException {
        char[] mask = new char[36];
        List<String> input = Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day14Input" ) );
        for ( String line : input ) {
            if ( line.startsWith( "mask = " ) ) {
                mask = line.substring( line.indexOf( '=' ) + 2 ).toCharArray();
            }
            else if ( line.startsWith( "mem" ) ) {
                int index = Integer.parseInt( line.substring( line.indexOf( '[' ) + 1, line.indexOf( ']' ) ) );
                long value = Long.parseLong( line.substring( line.indexOf( '=' ) + 2 ) );
                memory.put( index, applyMask( mask, value ));
            }
        }
        System.out.println(totalMemory());
        
    }
    
    private static long applyMask( char[] mask, long value ) {
        char[] binaryValue = convertToBinaryString36(value);
        for ( int index = 0; index < 36 ; index++ ) {
            if ( mask[index] != 'X' ) {
                binaryValue[index] = mask[index];
            }
        }
        return Long.parseLong( String.valueOf( binaryValue ), 2 );
    }
    
    private static char[] convertToBinaryString36 (long value){
        String binaryString = Long.toBinaryString(value);
        return ("000000000000000000000000000000000000".substring(binaryString.length()) + binaryString).toCharArray();
        
    }
    
    private static long totalMemory() {
        return memory.values().stream().mapToLong( Long::valueOf ).reduce( 0, Long::sum );
    }
}
