package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day9_1 {
    public static void main( String... args ) throws IOException {
        Long[] cipherTextArray = Files.readAllLines( Paths.get("resources\\com\\hotmail\\nickcooke\\aoc2020\\Day9Input" ))
                .stream()
                .map( Long::parseLong )
                .toArray(Long[]::new);
        
        int checkingBlockSize = 25;
        mainLoop:
        for ( int numberBeingChecked = checkingBlockSize; numberBeingChecked < cipherTextArray.length; numberBeingChecked++ ) {
            for ( int checkingBlockIndex1 = numberBeingChecked - checkingBlockSize; checkingBlockIndex1 < numberBeingChecked; checkingBlockIndex1++ ) {
                for ( int checkingBlockIndex2 = checkingBlockIndex1 + 1; checkingBlockIndex2 < numberBeingChecked; checkingBlockIndex2++ ) {
                    if ( cipherTextArray[checkingBlockIndex1].intValue() + cipherTextArray[checkingBlockIndex2].intValue() == cipherTextArray[numberBeingChecked].intValue() ) {
                        continue mainLoop;
                    }
                }
            }
            System.out.println( cipherTextArray[numberBeingChecked] );
            return;
        }
    }
}
