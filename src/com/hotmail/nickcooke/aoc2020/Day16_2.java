package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day16_2 {
    public static void main( String[] args ) throws IOException {
        class TicketField {
            String name;
            long range1Low, range1High, range2Low, range2High;
    
            TicketField( String ticketField ) {
                name = ticketField.substring( 0, ticketField.indexOf( ':' ) );
                range1Low = Long.parseLong( ticketField.substring( ticketField.indexOf( ':' ) + 2 ).split( " or " )[0].split( "-" )[0] );
                range1High = Long.parseLong( ticketField.substring( ticketField.indexOf( ':' ) + 2 ).split( " or " )[0].split( "-" )[1] );
                range2Low = Long.parseLong( ticketField.substring( ticketField.indexOf( ':' ) + 2 ).split( " or " )[1].split( "-" )[0] );
                range2High = Long.parseLong( ticketField.substring( ticketField.indexOf( ':' ) + 2 ).split( " or " )[1].split( "-" )[1] );
            }
    
            boolean isValidValue( long number ) {
                return ( number >= range1Low && number <= range1High ) || ( number >= range2Low && number <= range2High );
            }
        }
        class TicketFormat {
            List<TicketField> fields = new ArrayList<>();
    
            TicketFormat( String formatString ) {
                fields.addAll( Arrays.stream( formatString.split( "\r\n" ) ).map( TicketField::new ).collect( Collectors.toList() ) );
            }
    
            boolean isValidValue( long number ) {
                return fields.stream().anyMatch( f -> f.isValidValue( number ) );
            }
        }
        class Ticket {
            List<Long> values;
            TicketFormat ticketFormat;
    
            Ticket( String ticketString, TicketFormat ticketFormat ) {
                this.values = Stream.of( ticketString.split( "," ) ).map( Long::parseLong ).collect( Collectors.toList() );
                this.ticketFormat = ticketFormat;
            }
    
            boolean hasInvalidValues() {
                return getTotalInvalidValues() > 1 || values.contains( 0L );
            }
    
            long getTotalInvalidValues() {
                return values.stream().filter( v -> !ticketFormat.isValidValue( v ) ).reduce( 0L, Long::sum );
            }
        }
    
        String input = Files.readString( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day16Input" ) );
        List<String> inputSections = Arrays.asList( input.split( "\r\n\\s*\r\n" ) );
    
        TicketFormat ticketFormat = new TicketFormat( inputSections.get( 0 ) );
        List<Ticket> nearbyTickets = Arrays.stream( inputSections.get( 2 ).split( "\r\n" ) )
                .filter( s -> !s.matches( ".*:" ) )
                .map( s -> new Ticket( s, ticketFormat ) )
                .filter( ticket -> !ticket.hasInvalidValues() )
                .collect( Collectors.toList() );
    
        Map<String, List<Integer>> fieldNamesToPossiblePositions = new HashMap<>();
        for ( TicketField field : ticketFormat.fields ) {
            ticketValueLoop:
            for ( int i = 0; i < ticketFormat.fields.size(); i++ ) {
                for ( Ticket nearbyTicket : nearbyTickets ) {
                    if ( !field.isValidValue( nearbyTicket.values.get( i ) ) ) {
                        continue ticketValueLoop;
                    }
                }
                List<Integer> possiblePosition=new ArrayList<>(  );
                possiblePosition.add( i );
                fieldNamesToPossiblePositions.merge( field.name,possiblePosition,(oldVal,newVal)->{oldVal.addAll( newVal );return oldVal;});
            }
        }
    
        Map<String, Integer> confirmedPositions = new HashMap<>();
        boolean moreToProcess=true;
        while ( moreToProcess ) {
            moreToProcess=false;
            for ( Map.Entry<String, List<Integer>> possiblePosition : fieldNamesToPossiblePositions.entrySet() ) {
                if ( possiblePosition.getValue().size() == 1 ) {
                    int valueToRemove = possiblePosition.getValue().get( 0 );
                    confirmedPositions.put( possiblePosition.getKey(), possiblePosition.getValue().get( 0 ) );
                    for ( List<Integer> possiblePositions : fieldNamesToPossiblePositions.values() ) {
                        possiblePositions.remove( Integer.valueOf( valueToRemove ) );
                    }
                    moreToProcess=true;
                }
            }
        }
    
        Ticket myTicket = new Ticket( inputSections.get( 1 ).split( "\r\n" )[1], ticketFormat );
        long answer = confirmedPositions.entrySet().stream().filter( e -> e.getKey().matches( "departure.*" ) ).mapToLong( e -> myTicket.values.get( e.getValue() ) ).reduce( 1, ( v1, v2 ) -> v1 * v2 );
        System.out.println( answer );
    }
}