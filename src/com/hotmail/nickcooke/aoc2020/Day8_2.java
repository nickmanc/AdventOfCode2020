package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day8_2
{
    public static void main( String... args ) throws IOException
    {
        String[] instructions  = Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day8Input" ) ).toArray( new String[0] );
        List<Integer> instructionsExecuted = new ArrayList<>();
        int accValue;
        int currentInstructionIndex;
        
        changeOperations:
        for ( int instructionToChange = 0; instructionToChange < instructions.length; instructionToChange++ )
        {
            instructionsExecuted.clear();
            accValue = 0;
            currentInstructionIndex = 0;
            while ( currentInstructionIndex < instructions.length )
            {
                if ( instructionsExecuted.contains( currentInstructionIndex ) )
                {
                    continue changeOperations;
                }
                instructionsExecuted.add( currentInstructionIndex );
                String operation = instructions[currentInstructionIndex].split( "\\s" )[0];
                String argument = instructions[currentInstructionIndex].split( "\\s" )[1];
                if ( currentInstructionIndex == instructionToChange )
                {
                    if ( operation.equals( "jmp" ) )
                    {
                        operation = "nop";
                    }
                    else if ( operation.equals( "nop" ) )
                    {
                        operation = "jmp";
                    }
                }
                if ( "acc".equals( operation ) )
                {
                    accValue += Integer.parseInt( argument );
                }
                else if ( "jmp".equals( operation ) )
                {
                    currentInstructionIndex += Integer.parseInt( argument ) - 1;
                }
                currentInstructionIndex++;
            }
            System.out.println( "acc is: " + accValue );//if we get here then have executed a full program
            break;
        }
    }
}
