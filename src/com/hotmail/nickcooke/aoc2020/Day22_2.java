package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Day22_2 {
    public static void main( String[] args ) throws IOException {
        Queue<Integer> player1Hand = new LinkedList<>();
        Queue<Integer> player2Hand = new LinkedList<>();
        initialiseHands( player1Hand, player2Hand );
        boolean player1Winner = playGame( player1Hand, player2Hand );
        System.out.println( player1Winner ? getResult( player1Hand ): getResult( player2Hand )  );
    }
    
    private static boolean playGame( Queue<Integer> player1Hand, Queue<Integer> player2Hand ) {
        Set<String> currentGameHands = new HashSet<>();
        boolean player1Winner = false;
        while ( player1Hand.size() > 0 && player2Hand.size() > 0 ) {
            if ( !currentGameHands.add( hashHands( player1Hand, player2Hand ) ) ) {
                return true;//these hands have already been played so player 1 wins
            }
            
            int player1Score = player1Hand.poll();
            int player2Score = player2Hand.poll();
            
            player1Winner = player1Score > player2Score;
            if ( player1Score <= player1Hand.size() && player2Score <= player2Hand.size() ) {
                Queue<Integer> player1HandCopy = copyHand(player1Hand, player1Score );
                Queue<Integer> player2HandCopy = copyHand(player2Hand, player2Score );
                player1Winner = playGame( player1HandCopy, player2HandCopy );
            }
            if ( player1Winner ) {
                player1Hand.add( player1Score );
                player1Hand.add( player2Score );
            }
            else {
                player2Hand.add( player2Score );
                player2Hand.add( player1Score );
            }
        }
        return player1Winner;
    }
    
    private static Queue<Integer> copyHand(Queue<Integer>hand,int cardsToCopy){
        Queue<Integer> handCopy = new LinkedList<>();
        for ( int i = 0; i < cardsToCopy; i++ ) {
            handCopy.add( (int) hand.toArray()[i] );
        }
        return handCopy;
    }
    
    private static void initialiseHands( Queue<Integer> player1Hand, Queue<Integer> player2Hand ) throws IOException {
        List<String> input = Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day22Input" ) );
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
    }
    
    private static String hashHands( Queue<Integer> player1Hand, Queue<Integer> player2Hand ) {
        return player1Hand.toString() + ":" + player2Hand.toString();
    }
    
    private static int getResult( Queue<Integer> winningHand ) {
        int result = 0;
        for ( int i = 0; i < winningHand.size(); i++ ) {
            result += ( winningHand.size() - i ) * (int) winningHand.toArray()[i];
        }
        return result;
    }
}
