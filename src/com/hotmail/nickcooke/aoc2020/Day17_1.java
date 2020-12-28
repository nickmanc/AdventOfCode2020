package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day17_1 {
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
            Cube[][][] positions = new Cube[dimensionSize][dimensionSize][dimensionSize];
            
            PocketDimension( List<String> startPositions ) {
                initialiseAllPositions();
                for ( int x = 0; x < startPositions.size(); x++ ) {
                    for ( int y = 0; y < startPositions.get( 0 ).length(); y++ ) {
                        positions[offset + x-4][offset + y-4][offset].active = startPositions.get( x ).toCharArray()[y] == '#';
                    }
                }
            }
    
            private void initialiseAllPositions() {
                for ( int z = 0; z < dimensionSize; z++ ) {
                    for ( int x = 0; x < dimensionSize; x++ ) {
                        for ( int y = 0; y < dimensionSize; y++ ) {
                            positions[x][y][z] = new Cube();
                        }
                    }
                }
            }
    
            void playRound() {
                currentSize += 2;
                Cube[][][]copyPositions = copy(positions);
                for ( int z = 0 - currentSize / 2; z <= currentSize / 2; z++ ) {
                    for ( int x = 0 - currentSize / 2; x <= currentSize / 2; x++ ) {
                        for ( int y = 0 - currentSize / 2; y <= currentSize / 2; y++ ) {
                            Cube currentCube = positions[offset + x][offset + y][offset + z];
                            Cube copyCube = copyPositions[offset + x][offset + y][offset + z];
                            int activeNeighbourCount = getActiveNeighbourCount( z, x, y );
                            if (currentCube.active){
                                if (activeNeighbourCount < 2 || activeNeighbourCount > 3){
                                    copyCube.active = false;
                                }
                            }
                            else {
                                if (activeNeighbourCount == 3) {
                                    copyCube.active = true;
                                }
                            }
                        }
                    }
                }
                positions = copy(copyPositions);
            }
    
            private int getActiveNeighbourCount( int z, int x, int y ) {
                int activeNeighbourCount=0;
                for (int i=-1;i<=1;i++){
                    for (int j=-1;j<=1;j++){
                        for (int k=-1;k<=1;k++) {
                            if (i!=0 || j!=0 || k!=0){
                                if (positions[offset + x + i][offset + y + j][offset + z + k].active){
                                    activeNeighbourCount++;
                                }
                            }
                        }
                    }
                }
                return activeNeighbourCount;
            }
    
            private Cube[][][] copy( Cube[][][] original ) {
                Cube[][][] copy = new Cube[dimensionSize][dimensionSize][dimensionSize];
                for (int i=0;i < copy.length;i++){
                    for (int j=0;j < copy.length;j++) {
                        for ( int k = 0; k < copy.length; k++ ) {
                            copy[i][j][k]=new Cube();
                            copy[i][j][k].active=original[i][j][k].active;
                        }
                    }
                }
                return copy;
            }
    
            void print() {
                int count=0;
                for ( int z = 0 - currentSize / 2; z < currentSize - currentSize / 2; z++ ) {
                    System.out.println( "z=" + z );
                    for ( int x = 0 - currentSize / 2; x < currentSize - currentSize / 2; x++ ) {
                        for ( int y = 0 - currentSize / 2; y < currentSize - currentSize / 2; y++ ) {
                            if (positions[offset + x][offset + y][offset + z].active ){
                                count++;
                            }
                            System.out.print( positions[offset + x][offset + y][offset + z] );
                        }
                        System.out.println( "\r" );
                    }
                }
                System.out.println(count);
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
