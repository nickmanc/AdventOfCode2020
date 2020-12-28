package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day16_1 {
    public static void main( String[] args ) throws IOException {
        class TicketField{
            String name;
            long range1Low, range1High, range2Low, range2High;
            
            TicketField(String ticketField){
                name = ticketField.substring( 0,ticketField.indexOf( ':' ) );
                range1Low =  Long.parseLong( ticketField.substring(ticketField.indexOf( ':' )+2 ).split(" or ")[0].split( "-" )[0]);
                range1High = Long.parseLong(ticketField.substring( ticketField.indexOf( ':' )+2 ).split(" or ")[0].split( "-" )[1]);
                range2Low =  Long.parseLong(ticketField.substring( ticketField.indexOf( ':' )+2 ).split(" or ")[1].split( "-" )[0]);
                range2High = Long.parseLong(ticketField.substring( ticketField.indexOf( ':' )+2 ).split(" or ")[1].split( "-" )[1]);
            }
            
            boolean isValidValue(long number){
                return (number>= range1Low && number<= range1High )||(number>= range2Low && number<= range2High );
            }
        }
        class TicketFormat {
            List<TicketField> fields = new ArrayList<>(  );
            
            TicketFormat(String formatString){
                fields.addAll(Arrays.stream( formatString.split( "\r\n" ) ).map( TicketField::new ).collect( Collectors.toList() ));
            }
        
            boolean isValidValue( long number ) {
                return fields.stream().anyMatch( f -> f.isValidValue( number ) );
            }
        }
        class Ticket {
            List<Long> values;
            TicketFormat ticketFormat;
            Ticket(String ticketString, TicketFormat ticketFormat){
                this.values = Stream.of(ticketString.split(",")).map(Long::parseLong).collect( Collectors.toList());
                this.ticketFormat=ticketFormat;
            }
       
            long getTotalInvalidValues() {
                return values.stream().filter(v -> !ticketFormat.isValidValue( v )).reduce(0L,Long::sum);
            }
        }
        
        String input = Files.readString( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day16Input" ) );
        List<String> inputSections = Arrays.asList( input.split( "\r\n\\s*\r\n" ) );
    
        TicketFormat ticketFormat = new TicketFormat(inputSections.get(0));
        
        long totalInvalidValues = Arrays.stream( inputSections.get( 2 ).split( "\r\n" ) )
                .filter(s -> !s.matches( ".*:" ))
                .map( s -> new Ticket(s,ticketFormat).getTotalInvalidValues() )
                .reduce( 0L, Long::sum );
       
        System.out.println(totalInvalidValues);
    }
}