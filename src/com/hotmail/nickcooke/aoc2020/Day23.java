package com.hotmail.nickcooke.aoc2020;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Day23 {
    public static void main( String[] args ) {
        run( 9, 100, CupCircle::printAnswer1 );
        run( 1_000_000, 10_000_000, CupCircle::printAnswer2 );
    }
    
    private static void run( int numberOfCups, int numberOfMoves, Consumer<CupCircle> printFunction ) {
        CupCircle circle = new CupCircle( "685974213", numberOfCups );
        for ( int i = 0; i < numberOfMoves; i++ ) {
            circle.move();
        }
        printFunction.accept( circle );
    }
}

class CupPosition {
    long label;
    CupPosition nextCupPosition;
    
    public CupPosition( long value ) {
        this.label = value;
    }
    
}

class CupCircle {
    CupPosition head, tail, currentCup;
    Map<Long, CupPosition> valuesToPositions = new HashMap<>();
    long maxValue = 0L;
    
    CupCircle( String input, int numberOfCups ) {
        for ( char number : input.toCharArray() ) {
            int label = Integer.parseInt( number + "" );
            addCupPosition( label );
        }
        for ( int i = input.length()+1; i <= numberOfCups; i++ ) {
            addCupPosition( i );
        }
        currentCup = head;
    }
    
    public void addCupPosition( long cupLabel ) {
        maxValue = Math.max( maxValue, cupLabel );
        CupPosition cupPosition = new CupPosition( cupLabel );
        valuesToPositions.put( cupLabel, cupPosition );//cache for quick lookup otherwise will take hours
        if ( null == head ) {
            head = cupPosition;
            tail = cupPosition;
        }
        tail.nextCupPosition = cupPosition;
        tail = cupPosition;
        tail.nextCupPosition = head;
    }
    
    public void move() {
        CupPosition cupToMove1 = currentCup.nextCupPosition;
        CupPosition cupToMove2 = cupToMove1.nextCupPosition;
        CupPosition cupToMove3 = cupToMove2.nextCupPosition;
        long destinationCupLabel = currentCup.label - 1;
        while ( destinationCupLabel <= 0 || destinationCupLabel == cupToMove1.label || destinationCupLabel == cupToMove2.label || destinationCupLabel == cupToMove3.label ) {
            destinationCupLabel--;
            if ( destinationCupLabel <= 0 ) {
                destinationCupLabel = maxValue;
            }
        }
        CupPosition insertionPosition = valuesToPositions.get( destinationCupLabel );
        currentCup.nextCupPosition = cupToMove3.nextCupPosition;
        
        if ( cupToMove1 == head || cupToMove2 == head || cupToMove3 == head ) {
            head = cupToMove3.nextCupPosition;
            tail = currentCup;
        }
        else if ( insertionPosition == tail ) {
            tail = cupToMove3;
        }
        cupToMove3.nextCupPosition = insertionPosition.nextCupPosition;
        insertionPosition.nextCupPosition = cupToMove1;
        currentCup = currentCup.nextCupPosition;
    }
    
    public void printAnswer2() {
        CupPosition searchPosition = valuesToPositions.get( 1L );
        System.out.println( searchPosition.nextCupPosition.label * searchPosition.nextCupPosition.nextCupPosition.label );
    }
    
    public void printAnswer1() {
        CupPosition cp = head;
        while ( cp != tail ) {
            System.out.print( cp.label );
            cp = cp.nextCupPosition;
        }
        System.out.println();
    }
}