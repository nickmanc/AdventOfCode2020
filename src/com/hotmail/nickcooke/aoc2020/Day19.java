package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day19 {
    public static void main( String[] args ) throws IOException {
        String input = Files.readString( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day19Input" ) );
        List<String> inputSections = Arrays.asList( input.split( "\r\n\\s*\r\n" ) );
        String[] messages = inputSections.get( 1 ).split( "\r\n" );
        
        MessageChecker messageChecker1 = new MessageChecker( inputSections.get( 0 ), false );
        MessageChecker messageChecker2 = new MessageChecker( inputSections.get( 0 ), true );
        int part1Count = 0;
        int part2Count = 0;
        for ( String message : messages ) {
            part1Count = messageChecker1.checkMessage( message ) ? ++part1Count : part1Count;
            part2Count = messageChecker2.checkMessage( message ) ? ++part2Count : part2Count;
        }
        System.out.println( "Part 1: " + part1Count );
        System.out.println( "Part 2: " + part2Count );
    }
}

class MessageChecker {
    private Map<Integer, String> rules;
    String ruleRegex;
    boolean isPart2;
    
    public MessageChecker( String rulesString, boolean isPart2 ) {
        this.isPart2 = isPart2;
        rules = new HashMap<>();
        rules = Arrays.stream( rulesString.split( "\r\n" ) )
                .map( string -> string.split( ":" ) )
                .collect( Collectors.toMap( strings -> Integer.parseInt( strings[0] ), strings -> strings[1] ) );
        ruleRegex = "^" + expandRule( rules.get( 0 ) ) + "$";
    }
    
    protected boolean checkMessage( String message ) {
        return message.matches( ruleRegex );
    }
    
    protected String expandRule( String rule ) {
        StringBuilder result = new StringBuilder();
        rule = rule.trim();
        if ( isStringLiteral( rule ) ) {
            result.append( rule, 1, rule.length() - 1 );
        }
        else if ( rule.contains( "|" ) ) {
            result = new StringBuilder( "(" + expandRule( rule.substring( 0, rule.indexOf( "|" ) ) ) + "|" + expandRule( rule.substring( rule.indexOf( "|" ) + 1 ) ) + ")" );
        }
        else {
            for ( String subRule : rule.split( "\\s" ) ) {
                if ( isPart2 && Integer.parseInt( subRule.trim() ) == 8 ) {
                    result.append( "(" ).append( expandRule( "42" ) ).append( "+)" );
                }
                else if ( isPart2 && Integer.parseInt( subRule.trim() ) == 11 ) {
                    String rule42 = expandRule( "42" );
                    String rule31 = expandRule( "31" );
                    String rule11 = rule42 + rule31;
                    for ( int i = 2; i < 100; i++ ) {
                        //this only works up to i repetitions - this was easier than finding a way to have matching groups of same size with regex...
                        rule11 = rule11 + "|(" + rule42 + "{" + i + "}" + rule31 + "{" + i + "})";
                    }
                    result.append( "(" ).append( rule11 ).append( ")" );
                }
                else {
                    result.append( expandRule( rules.get( Integer.parseInt( subRule.trim() ) ) ) );
                }
            }
        }
        return result.toString();
    }
    
    private boolean isStringLiteral( String rule ) {
        return rule.matches( "^\"(.*)\"$" );
    }
}