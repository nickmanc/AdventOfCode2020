package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7_1 {
    //DID NOT LIKE THIS, THERE IS SURELY A NICER SOLUTION
    public static void main( String... args ) throws IOException {
        List<String> input = Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day7Input" ) );
        Map<String, List<String>> bagRules = new HashMap<>();
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
        Set<String> bagsContainingShinyGold = new HashSet<>();
        bagsContainingShinyGold.add( "shiny gold" );
        int bagsContainingShinyGoldCount;
        do {
            bagsContainingShinyGoldCount = bagsContainingShinyGold.size();
            Set<String> newBagsContainingShinyGold = new HashSet<>();
            for ( String bag : bagRules.keySet() ) {
                List<String> bagContents = bagRules.get( bag );
                for ( String bagContainingShinyGold : bagsContainingShinyGold ) {
                    if ( bagContents.contains( bagContainingShinyGold ) ) {
                        newBagsContainingShinyGold.add( bag );
                    }
                }
            }
            bagsContainingShinyGold.addAll( newBagsContainingShinyGold );
        }
        while ( bagsContainingShinyGoldCount < bagsContainingShinyGold.size() );
        System.out.println( bagsContainingShinyGoldCount - 1 );
    }
}

