package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;

public class Day11_2 extends Day11_1 {
    
    private static boolean changedThisRound;
    
    public static void main( String[] args ) throws IOException {
        char[][]floorplan=getStartingFloorplan();
        
        do {
            floorplan = runOneRound( floorplan );
//            print( floorplan );
        }
        while ( changedThisRound );

        System.out.println( seatCount( floorplan ) );
        
    }
    
    private static char[][] runOneRound( char[][] floorplan ) {
        changedThisRound = false;
        char[][] newFloorplan = copyFloorplan( floorplan );
        for ( int row = 0; row < floorplan.length; row++ ) {
            for ( int column = 0; column < floorplan[0].length; column++ ) {
                if ( floorplan[row][column] == 'L' && getVisibleSeatsCount( floorplan, row, column ) == 0 ) {
                    newFloorplan[row][column] = '#';
                    changedThisRound = true;
                }
                if ( floorplan[row][column] == '#' && getVisibleSeatsCount( floorplan, row, column ) >= 5 ) {
                    newFloorplan[row][column] = 'L';
                    changedThisRound = true;
                }
                
            }
        }
        return newFloorplan;
    }
    
    private static int getVisibleSeatsCount( char[][] floorplan, int row, int column ) {
        int count = 0;
        for ( int searchRow = row - 1; searchRow >= 0; searchRow-- ) {
            if ( floorplan[searchRow][column] == 'L' )
                break;
            if ( floorplan[searchRow][column] == '#' ) {
                count++;
                break;
            }
        }
        for ( int searchRow = row + 1; searchRow < floorplan.length; searchRow++ ) {
            if ( floorplan[searchRow][column] == 'L' )
                break;
            if ( floorplan[searchRow][column] == '#' ) {
                count++;
                break;
            }
        }
        for ( int searchColumn = column - 1; searchColumn >= 0; searchColumn-- ) {
            if ( floorplan[row][searchColumn] == 'L' )
                break;
            if ( floorplan[row][searchColumn] == '#' ) {
                count++;
                break;
            }
        }
        for ( int searchColumn = column + 1; searchColumn < floorplan[row].length; searchColumn++ ) {
            if ( floorplan[row][searchColumn] == 'L' )
                break;
            if ( floorplan[row][searchColumn] == '#' ) {
                count++;
                break;
            }
        }
        for ( int i = 1; ; i++ ) {
            int searchRow = row + i;
            int searchColumn = column + i;
            if ( searchRow >= floorplan.length || searchColumn >= floorplan[searchRow].length )
                break;
            if ( floorplan[searchRow][searchColumn] == 'L' )
                break;
            if ( floorplan[searchRow][searchColumn] == '#' ) {
                count++;
                break;
            }
        }
        for ( int i = 1; ; i++ ) {
            int searchRow = row + i;
            int searchColumn = column - i;
            if ( searchRow >= floorplan.length || searchColumn < 0 )
                break;
            if ( floorplan[searchRow][searchColumn] == 'L' )
                break;
            if ( floorplan[searchRow][searchColumn] == '#' ) {
                count++;
                break;
            }
        }
        for ( int i = 1; ; i++ ) {
            int searchRow = row - i;
            int searchColumn = column - i;
            if ( searchRow < 0 || searchColumn < 0 )
                break;
            if ( floorplan[searchRow][searchColumn] == 'L' )
                break;
            if ( floorplan[searchRow][searchColumn] == '#' ) {
                count++;
                break;
            }
        }
        for ( int i = 1; ; i++ ) {
            int searchRow = row - i;
            int searchColumn = column + i;
            if ( searchRow < 0 || searchColumn >= floorplan[searchRow].length )
                break;
            if ( floorplan[searchRow][searchColumn] == 'L' )
                break;
            if ( floorplan[searchRow][searchColumn] == '#' ) {
                count++;
                break;
            }
        }
        return count;
    }
}
