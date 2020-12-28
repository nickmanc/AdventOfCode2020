package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day9_2 {
    public static void main( String... args ) throws IOException {
        Long[] cipherTextArray = Files.readAllLines( Paths.get("resources\\com\\hotmail\\nickcooke\\aoc2020\\Day9Input" ))
                .stream()
                .map( Long::parseLong )
                .toArray(Long[]::new);
        
        long target = 167829540;
        
        for ( int checkingLength = 2; checkingLength < 25; checkingLength++ ) {
            for ( int cipherTextArrayIndex = 0; cipherTextArrayIndex < cipherTextArray.length; cipherTextArrayIndex++ ) {
                int counter = 0;
                for ( int checkingBlockIndex = cipherTextArrayIndex; checkingBlockIndex < Math.min( cipherTextArrayIndex + checkingLength, cipherTextArray.length ); checkingBlockIndex++ ) {
                    counter += cipherTextArray[checkingBlockIndex];
                }
                if ( counter == target ) {
                    long minValue = cipherTextArray[cipherTextArrayIndex];
                    long maxValue = cipherTextArray[cipherTextArrayIndex];
                    for ( int l = cipherTextArrayIndex + 1; l < cipherTextArrayIndex + checkingLength - 1; l++ ) {
                        minValue = Math.min( minValue, cipherTextArray[l] );
                        maxValue = Math.max( maxValue, cipherTextArray[l] );
                    }
                    System.out.println( minValue + maxValue );
                    return;
                }
            }
        }
    }
}
