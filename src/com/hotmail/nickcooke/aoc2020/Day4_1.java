package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Day4_1
{
    public static void main( String[] args ) throws IOException
    {
        List<String> requiredFields = Arrays.asList( "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"/*,"cid"*/ );
        String input = Files.readString( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day4Input" ) );
        String[] passports = input.split( "\r\n\\s*\r\n" );
        int validPassports = 0;
        passportLoop:
        for ( String passport : passports )
        {
            Properties passportFields = new Properties();
            passportFields.load( new StringReader( passport.replaceAll( "\\s+", "\r\n" ) ) );
            for ( String requiredField : requiredFields )
            {
                if ( !passportFields.containsKey( requiredField ) )
                {
                    continue passportLoop;
                }
            }
            validPassports++;
        }
        System.out.println( validPassports );
    }
}
