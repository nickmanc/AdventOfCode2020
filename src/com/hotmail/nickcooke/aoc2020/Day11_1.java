package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day11_1 {
    
    private static boolean changed;
    
    public static void main( String[] args ) throws IOException {
        char[][]floorplan=getStartingFloorplan();
        do {
            floorplan = runOneRound( floorplan );
            print( floorplan );
        }
        while ( changed );
        
        System.out.println( seatCount( floorplan ) );
    }
    
    static int seatCount( char[][] floorplan ) {
        int count = 0;
        for ( char[] chars : floorplan ) {
            for ( int column = 0; column < floorplan[0].length; column++ ) {
                if ( chars[column] == '#' ) {
                    count++;
                }
            }
        }
        return count;
    }
    
    private static char[][] runOneRound( char[][] floorplan ) {
        changed = false;
        char[][] newFloorplan = copyFloorplan( floorplan );
        for ( int row = 0; row < floorplan.length; row++ ) {
            for ( int column = 0; column < floorplan[0].length; column++ ) {
                if ( floorplan[row][column] == 'L' && getAdjacentSeatsCount( floorplan, row, column ) == 0 ) {
                    newFloorplan[row][column] = '#';
                    changed = true;
                }
                if ( floorplan[row][column] == '#' && getAdjacentSeatsCount( floorplan, row, column ) >= 4 ) {
                    newFloorplan[row][column] = 'L';
                    changed = true;
                }
            }
        }
        return newFloorplan;
    }
    
    static char[][] copyFloorplan( char[][] floorplan ) {
        char[][] newFloorplan = new char[floorplan.length][floorplan[0].length];
        for ( int row = 0; row < floorplan.length; row++ ) {
            for ( int column = 0; column < floorplan[row].length; column++ ) {
                newFloorplan[row][column] = floorplan[row][column];
            }
        }
        return newFloorplan;
    }
    
    static void print( char[][] floorplan ) {
        for ( char[] chars : floorplan ) {
            for ( int column = 0; column < floorplan[0].length; column++ ) {
                System.out.print( chars[column] );
            }
            System.out.println();
        }
        System.out.println( "==========" );
    }
    
    private static int getAdjacentSeatsCount( char[][] floorplan, int row, int column ) {
        int count = 0;
        for ( int searchRow = Math.max( 0, row - 1 ); searchRow <= Math.min( floorplan.length - 1, row + 1 ); searchRow++ ) {
            for ( int searchColumn = Math.max( 0, column - 1 ); searchColumn <= Math.min( floorplan[row].length - 1, column + 1 ); searchColumn++ ) {
    
                if ( ( searchRow != row || searchColumn != column ) ) {
                    if ( floorplan[searchRow][searchColumn] == '#' ) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
    
    static char[][] getStartingFloorplan() throws IOException {
        List<String> input = Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day11Input" ) );
        char[][] floorplan = new char[input.size()][input.get( 0 ).length()];
        int index = 0;
        for ( String line : input ) {
            floorplan[index++] = line.toCharArray();
        }
        return floorplan;
    }
}
