package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14_2 {
    
    public static void main( String[] args ) throws IOException {
        Map<Long, Long> memory = new HashMap<>();
        char[] mask = new char[36];
        List<String> input = Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day14Input" ) );
        for ( String line : input ) {
            if ( line.startsWith( "mask = " ) ) {
                mask = line.substring( line.indexOf( '=' ) + 2 ).toCharArray();
            }
            else if ( line.startsWith( "mem" ) ) {
                long address = Long.parseLong( line.substring( line.indexOf( '[' ) + 1, line.indexOf( ']' ) ) );
                long value = Long.parseLong( line.substring( line.indexOf( '=' ) + 2 ) );
                for ( long index : getMemoryAddresses( mask, address ) ) {
                    memory.put( index, value );
                }
            }
        }
        long totalValuesInMemory=memory.values().stream().mapToLong( Long::valueOf ).reduce( 0, Long::sum );
        System.out.println( totalValuesInMemory );
    }
    
    private static List<String> calculateAddressPermutations( List<String> addresses ) {
        List<String> nextAddresses = new ArrayList<>();
        for ( String address : addresses ) {
            if ( address.matches( ".*X.*" ) ) {
                String address0 = address.replaceFirst( "X", "0" );
                String address1 = address.replaceFirst( "X", "1" );
                if ( address0.matches( ".*X.*" ) ) {
                    nextAddresses.addAll( calculateAddressPermutations( Arrays.asList( address0, address1 ) ) );
                }
                else {
                    nextAddresses.add( address0 );
                    nextAddresses.add( address1 );
                }
            }
        }
        return nextAddresses;
    }
    
    private static long[] getMemoryAddresses( char[] mask, long address ) {
        List<String> addresses = new ArrayList<>();
        addresses.add( applyMask (mask, address));
        addresses = calculateAddressPermutations(addresses);
        return addresses.stream().map( l ->Long.parseLong( l,2 ) ).mapToLong( l->l ).toArray();
    }
    
    private static String applyMask(char[]mask, long address) {
        char[] binaryValue = convertToBinaryString36( address );
        for ( int index = 0; index < 36; index++ ) {
            if ( mask[index] == '1' || mask[index] == 'X' ) {
                binaryValue[index] = mask[index] ;
            }
        }
        return String.valueOf( binaryValue);
    }
    
    private static char[] convertToBinaryString36( long value ) {
        String binaryString = Long.toBinaryString( value );
        return ( "000000000000000000000000000000000000".substring( binaryString.length() ) + binaryString ).toCharArray();
    }
}
