package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

public class Day4_2
{
    public static void main( String[] args ) throws IOException
    {
        Map<String, String> regexForRequiredFields = Map.of(
                "byr", "19[2-9]\\d|200[0-2]",
                "iyr", "201\\d|2020",
                "eyr", "202\\d|2030",
                "hgt", "1[5-8]\\dcm||19[0-3]cm|59in|6\\din|7[0-6]in",
                "hcl", "#[\\d|a-f]{6}",
                "ecl", "amb|blu|brn|gry|grn|hzl|oth",
                "pid", "\\d{9}"
        );
        
        String input = Files.readString( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day4Input" ) );
        String[] passports = input.split( "\r\n\\s*\r\n" );
        int validPassports = 0;
        passportLoop:
        for ( String passport : passports )
        {
            Properties passportFields = new Properties();
            passportFields.load( new StringReader( passport.replaceAll( "\\s+", "\r\n" ) ) );
            for ( String requiredField : regexForRequiredFields.keySet() )
            {
                if ( passportFields.containsKey( requiredField ) )
                {
                    String value = (String) passportFields.get( requiredField );
                    if ( !value.matches( regexForRequiredFields.get( requiredField ) ) )
                    {
                        continue passportLoop;
                    }
                }
                else
                {
                    continue passportLoop;
                }
            }
            validPassports++;
        }
        System.out.println( validPassports );
    }
}
