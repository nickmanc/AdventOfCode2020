package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Day21_2 {
    
    public static void main( String[] args ) throws IOException {
        List<String> input = Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day21Input" ) );
        Map<List<String>,List<String>> ingredientsAndAllergens = new HashMap<>(  );
        Set<String> allAllergens=new HashSet<>(  );
        for(String label : input){
            List<String> ingredients = Arrays.asList(label.substring( 0, label.indexOf( "(" ) ).split( "\\s" ));
            List<String> allergens = Arrays.asList( label.substring( label.indexOf( "("  ) +"(contains ".length(),label.trim().length()-1 ).split(",\\s"));
            allAllergens.addAll( allergens );
            ingredientsAndAllergens.put( ingredients,allergens );
        }
    
        Map<String,Set<String>> possibleAllergenTranslations=new HashMap<>(  );
        for (String allergen: allAllergens){
            Set<String> possibleIngredients=new HashSet<>(  );
            for (Map.Entry<List<String>,List<String>> entry: ingredientsAndAllergens.entrySet()){
                if (entry.getValue().contains( allergen )){
                    if (possibleIngredients.size()==0)
                    {
                        possibleIngredients.addAll( entry.getKey() );
                    }
                    else {
                        possibleIngredients.retainAll( entry.getKey() );
                    }
                }
            }
            System.out.println("Possible ingredients for " + allergen + " are: " + possibleIngredients);
            possibleAllergenTranslations.put( allergen, possibleIngredients );
        }
        Map<String, String> confirmedAllergens = new TreeMap<>();
        boolean moreToProcess=true;
        while ( moreToProcess ) {
            moreToProcess=false;
            for ( Map.Entry<String, Set<String>> possibleAllergen : possibleAllergenTranslations.entrySet() ) {
                if ( possibleAllergen.getValue().size() == 1 ) {
                    String valueToRemove = (String)possibleAllergen.getValue().toArray()[0];
                    confirmedAllergens.put( possibleAllergen.getKey(), (String)possibleAllergen.getValue().toArray()[0] );
                    for ( Set<String> possiblePositions : possibleAllergenTranslations.values() ) {
                        possiblePositions.remove( valueToRemove  );
                    }
                    moreToProcess=true;
                }
            }
        }
        System.out.println( String.join( ",", confirmedAllergens.values() ) );
    }
}
