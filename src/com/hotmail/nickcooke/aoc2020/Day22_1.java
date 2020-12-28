package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Day22_1 {
    public static void main( String[] args ) throws IOException {
        List<String> input = Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day22Input" ) );
        Queue<Integer> player1Hand = new LinkedList<>();
        Queue<Integer> player2Hand = new LinkedList<>();
        Queue<Integer> lastWinningHand  = player1Hand;
        Queue<Integer> currentHand = player1Hand;
        for ( String line : input ) {
                if ( line.equals( "" ) || line.startsWith( "Player" ) ) {
                if ( line.startsWith( "Player 2" )) {
                    currentHand = player2Hand;
                }
            }
            else{
                currentHand.add( Integer.parseInt( line ) );
            }
        }
        
        while ( player1Hand.size() > 0 && player2Hand.size() > 0 ) {
            int player1Score = player1Hand.poll();
            int player2Score = player2Hand.poll();
            
            if ( player1Score > player2Score ) {
                player1Hand.add( player1Score );
                player1Hand.add( player2Score );
                lastWinningHand = player1Hand;
            }
            else {
                player2Hand.add( player2Score );
                player2Hand.add( player1Score );
                lastWinningHand = player2Hand;//use lastWinningHand to track winner
            }
        }
        
        int result = 0;
        for ( int i =0;i<lastWinningHand.size() ; i++ ) {
            result += ( lastWinningHand.size() - i ) * (int) lastWinningHand.toArray()[i];
        }
        System.out.println( result );
    }
}
