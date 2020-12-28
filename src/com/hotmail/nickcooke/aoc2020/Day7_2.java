package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7_2 {
    private static Map<String, List<String>> bagRules;
    
    private static int bagCount;
    
    public static void main( String... args ) throws IOException {
        List<String> input = Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day7Input" ) );
        bagRules = new HashMap<>();
        for ( String bagRule : input ) {
            String bagName = bagRule.split( " bags contain " )[0];
            String[] bagContents = bagRule.split( " bags contain " )[1].split( " bag(s?)(,|.)(\\s?)" );
            List<String> contents = new ArrayList<>();
            for ( String subBag : bagContents ) {
                Pattern pattern = Pattern.compile( "(\\d)\\s(.*)" );
                Matcher matcher = pattern.matcher( subBag );
                if ( matcher.find() ) {
                    int bagCount = Integer.parseInt( matcher.group( 1 ) );
                    for ( int i = 0; i < bagCount; i++ ) {
                        contents.add( matcher.group( 2 ) );
                    }
                }
            }
            bagRules.put( bagName, contents );
            
        }
        countBags( "shiny gold" );
        System.out.println( bagCount - 1 );
    }
    
    private static void countBags( String bag ) {
        if ( bagRules.get( bag ).size() > 0 ) {
            for ( String subBag : bagRules.get( bag ) ) {
                countBags( subBag );
            }
            bagCount++;
        }
        else {
            bagCount++;
        }
    }
}

