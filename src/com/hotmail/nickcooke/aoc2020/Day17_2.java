package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day17_2 {
    public static void main( String[] args ) throws IOException {
        class Cube {
            boolean active;
            @Override
            public String toString() {
                return active ? "X" : ".";
            }
        }
        class PocketDimension {
            int dimensionSize = 30;
            int offset = 15;
            int currentSize = 8;
            Cube[][][][] positions = new Cube[dimensionSize][dimensionSize][dimensionSize][dimensionSize];
            
            PocketDimension( List<String> startPositions ) {
                initialiseAllPositions();
                for ( int x = 0; x < startPositions.size(); x++ ) {
                    for ( int y = 0; y < startPositions.get( 0 ).length(); y++ ) {
                        positions[offset + x - 4][offset + y - 4][offset][offset].active = startPositions.get( x ).toCharArray()[y] == '#';
                    }
                }
            }
    
            private void initialiseAllPositions() {
                for ( int w = 0; w < dimensionSize; w++ ) {
                    for ( int z = 0; z < dimensionSize; z++ ) {
                        for ( int x = 0; x < dimensionSize; x++ ) {
                            for ( int y = 0; y < dimensionSize; y++ ) {
                                positions[w][x][y][z] = new Cube();
                            }
                        }
                    }
                }
            }
    
            void playRound() {
                currentSize += 2;
                Cube[][][][] copyPositions = copy( positions );
                for ( int w = 0 - currentSize / 2; w <= currentSize / 2; w++ ) {
                    for ( int z = 0 - currentSize / 2; z <= currentSize / 2; z++ ) {
                        for ( int x = 0 - currentSize / 2; x <= currentSize / 2; x++ ) {
                            for ( int y = 0 - currentSize / 2; y <= currentSize / 2; y++ ) {
                                Cube currentCube = positions[offset + w][offset + x][offset + y][offset + z];
                                Cube copyCube = copyPositions[offset + w][offset + x][offset + y][offset + z];
                                int activeNeighbourCount = getActiveNeighbourCount( w, z, x, y );
                                if ( currentCube.active ) {
                                    if ( activeNeighbourCount < 2 || activeNeighbourCount > 3 ) {
                                        copyCube.active = false;
                                    }
                                }
                                else {
                                    if ( activeNeighbourCount == 3 ) {
                                        copyCube.active = true;
                                    }
                                }
                            }
                        }
                    }
                }
                positions = copy( copyPositions );
            }
    
            private int getActiveNeighbourCount( int w, int z, int x, int y ) {
                int activeNeighbourCount = 0;
                for ( int i = -1; i <= 1; i++ ) {
                    for ( int j = -1; j <= 1; j++ ) {
                        for ( int k = -1; k <= 1; k++ ) {
                            for ( int l = -1; l <= 1; l++ ) {
                                if ( i != 0 || j != 0 || k != 0 || l != 0 ) {
                                    if ( positions[offset + w + i][offset + x + j][offset + y + k][offset + z + l].active ) {
                                        activeNeighbourCount++;
                                    }
                                }
                            }
                        }
                    }
                }
                return activeNeighbourCount;
            }
    
            private Cube[][][][] copy( Cube[][][][] original ) {
                Cube[][][][] copy = new Cube[dimensionSize][dimensionSize][dimensionSize][dimensionSize];
                for ( int i = 0; i < copy.length; i++ ) {
                    for ( int j = 0; j < copy.length; j++ ) {
                        for ( int k = 0; k < copy.length; k++ ) {
                            for ( int l = 0; l < copy.length; l++ ) {
                                copy[i][j][k][l] = new Cube();
                                copy[i][j][k][l].active = original[i][j][k][l].active;
                            }
                        }
                    }
                }
                return copy;
            }
            
            void print() {
                int count = 0;
                for ( int w = 0 - currentSize / 2; w < currentSize - currentSize / 2; w++ ) {
                    System.out.println( "w=" + w );
                    for ( int z = 0 - currentSize / 2; z < currentSize - currentSize / 2; z++ ) {
                        System.out.println( "z=" + z );
                        for ( int x = 0 - currentSize / 2; x < currentSize - currentSize / 2; x++ ) {
                            for ( int y = 0 - currentSize / 2; y < currentSize - currentSize / 2; y++ ) {
                                if ( positions[offset + w][offset + x][offset + y][offset + z].active ) {
                                    count++;
                                }
                                System.out.print( positions[offset + w][offset + x][offset + y][offset + z] );
                            }
                            System.out.println( "\r" );
                        }
                    }
                }
                System.out.println();
                System.out.println( count );
            }
        }
    
        List<String> input = Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day17Input" ) );
        PocketDimension pocketDimension = new PocketDimension( input );
        for(int i = 0;i<6;i++){
            pocketDimension.playRound();
        }
        pocketDimension.print();
    }
}
