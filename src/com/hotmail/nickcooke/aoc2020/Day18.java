package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18
{
    public static void main( String[] args ) throws IOException {
        Calculator calculator = new Calculator();
        workOutAnswer( calculator );
        calculator.setAdvanced( true );
        workOutAnswer( calculator );
    }
    
    private static void workOutAnswer (Calculator calculator) throws IOException {
        List<String> input = Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day18Input" ) );
        long total=0;
        for(String line:input ){
            total += calculator.calculate( line );
        }
        System.out.println(total);
    }
}

class Calculator {
    public static final String OPERATION_REGEX = "(\\d+)\\s*([+\\-*/])\\s*(\\d+)";
    public static final String SIMPLE_OPERATION_REGEX = "^" + OPERATION_REGEX + "$";
    public static final String COMPOUND_OPERATION_REGEX = "^(" + OPERATION_REGEX + ")(\\s*([+\\-*/])\\s*(\\d+))*";
    public static final String SINGLE_BRACKETED_OPERATION_REGEX = "(.*)\\(([^)]*)\\)(.*)";
    public static final String ADDITION_OPERATION_REGEX = "(.*?)(\\d+\\s*\\+\\s*\\d+)(.*)";
    boolean isAdvanced;
    
    public void setAdvanced(boolean advanced){
        isAdvanced=advanced;
    }
    
    protected long calculate(String input) {
        input = input.trim();
        if ( input.matches( "\\d+" ) ) {
            return Long.parseLong( input );
        }
        if ( isSimpleOperation( input ) ) {
            return calculateSimpleOperation( input );
        }
        else if ( containsBrackets( input ) ) {
            Matcher matcher = Pattern.compile( SINGLE_BRACKETED_OPERATION_REGEX ).matcher( input );
            matcher.matches();
            String preBracketedStatement = matcher.group( 1 );
            String bracketedStatement= matcher.group( 2 );
            String postBracketedStatement= matcher.group( 3 );
            return calculate( preBracketedStatement + calculate( bracketedStatement ) + postBracketedStatement );
        }
        else if ( isAdvanced && containsAddition( input ) ) {
            Matcher matcher = Pattern.compile( ADDITION_OPERATION_REGEX ).matcher( input );
            matcher.matches();
            String preAdditionStatement = matcher.group( 1 );
            String additionStatement = matcher.group( 2 );
            String postAdditionStatement = matcher.group( 3 );
            return calculate( preAdditionStatement + calculate( additionStatement ) + postAdditionStatement );
        }
        else {
            Matcher matcher = Pattern.compile( COMPOUND_OPERATION_REGEX ).matcher( input );
            matcher.matches();
            String firstOperation = matcher.group( 1 );
            return calculate( calculate( firstOperation ) + input.substring( firstOperation.length() ) );
        }
    }
    
    protected boolean containsBrackets( String input ) {
        return input.matches( ".*\\(.*");
    }
    
    private boolean containsAddition( String input ) {
        return input.matches( ADDITION_OPERATION_REGEX );
    }
    
    protected boolean isSimpleOperation( String input ) {
        return input.matches( SIMPLE_OPERATION_REGEX );
    }
    
    private long calculateSimpleOperation( String input ) {
        Matcher matcher = Pattern.compile(SIMPLE_OPERATION_REGEX).matcher( input );
        matcher.matches();
        long operand1 = Long.parseLong( matcher.group(1));
        long operand2 = Long.parseLong( matcher.group(3));
        char operation = matcher.group(2).charAt( 0 );
        
        switch (operation) {
            case '+': return operand1 + operand2;
            case '*': return operand1 * operand2;
        }
        throw new RuntimeException(  );
    }
}