package com.hotmail.nickcooke.aoc2020;

public class Day25_1 {
    public static void main( String[] args ) {
        int subjectNumber = 7;
        long cardPublicKeyGuess = 1;
        long doorPublicKeyGuess = 1;
        final int CARD_PUBLIC_KEY = 3469259;
        final int DOOR_PUBLIC_KEY = 13170438;
        int cardLoopSize = -1;
        int doorLoopSize = -1;
        int loopSizeGuess = 1;
        while ( doorLoopSize == -1 || cardLoopSize == -1 ) {
            if ( cardLoopSize == -1 ) {
                cardPublicKeyGuess = transform( cardPublicKeyGuess, subjectNumber );
                if ( cardPublicKeyGuess == CARD_PUBLIC_KEY ) {
                    cardLoopSize = loopSizeGuess;
                }
            }
            if ( doorLoopSize == -1 ) {
                doorPublicKeyGuess = transform( doorPublicKeyGuess, subjectNumber );
                if ( doorPublicKeyGuess == DOOR_PUBLIC_KEY ) {
                    doorLoopSize = loopSizeGuess;
                }
            }
            loopSizeGuess++;
        }
        System.out.println( "Card loop size is: " + cardLoopSize + ", Door loop size is: " + doorLoopSize );
        
        long cardEncryptionKey = 1;
        for ( int i = 0; i < doorLoopSize; i++ ) {
            cardEncryptionKey = transform( cardEncryptionKey, CARD_PUBLIC_KEY );
        }
        
        long doorEncryptionKey = 1;
        for ( int i = 0; i < cardLoopSize; i++ ) {
            doorEncryptionKey = transform( doorEncryptionKey, DOOR_PUBLIC_KEY );
        }
        if ( doorEncryptionKey != cardEncryptionKey ) {
            throw new RuntimeException( "Encryption Keys should be the same..." );
        }
        else {
            System.out.println( "Encryption key is: " + doorEncryptionKey );
        }
    }
    
    private static long transform( long value, int subjectNumber ) {
        return ( value * subjectNumber ) % 20201227;
    }
}
