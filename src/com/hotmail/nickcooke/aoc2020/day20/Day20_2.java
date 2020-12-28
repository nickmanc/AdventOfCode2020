package com.hotmail.nickcooke.aoc2020.day20;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day20_2 {
    private static int gridSize;
    private static int combinedImageWidth = 96;
    static String seamonster2 = "(..................#.).*";
    static String seamonster1 = "(#....##....##....###)";
    static String seamonster0 = "(.#..#..#..#..#..#...).*";
    
    public static void main( String[] args ) {
        TileLoader tileLoader = new TileLoader();
        ImageTiles imageTiles = new ImageTiles( tileLoader );
        gridSize = tileLoader.getGridSize();
        ImageGrid imageGrid = alignTilesInGrid( imageTiles );
        char[][] combinedImage = combineTiles( imageGrid );
        printCombinedImage( combinedImage, false );
        searchForSeaMonsters( combinedImage );
    }
    
    private static void searchForSeaMonsters( char[][] combinedImage ) {
        for (int i=0; i<2; i++){
            for (int j=0;j<4;j++){
                if (checkForSeaMonsters( combinedImage )) return;
                combinedImage = TileHelper.rotate( combinedImage );
            }
            combinedImage = TileHelper.flip( combinedImage );
        }
    }
    
    private static boolean checkForSeaMonsters( char[][] combinedImage ) {
        List<String> imageStrings = new ArrayList<>();
        
        int hashCount = 0;
        for ( int i = combinedImageWidth - 1; i >= 0; i-- ) {
            String lineToAdd = new String( combinedImage[i] );
            hashCount += countHashes( lineToAdd );
            imageStrings.add( lineToAdd );
        }
    
        int seaMonsterCount = 0;
        for ( int i = 0; i <= combinedImageWidth - 1; i++ ) {
            String imageLine1 = imageStrings.get( i );
            
            Pattern pattern1 = Pattern.compile( seamonster1 );
            Matcher matcher1 = pattern1.matcher( imageLine1 );
            while ( matcher1.find() ) {
                String matchedString = matcher1.group( 1 );
                int startIndex = imageLine1.indexOf( matchedString );
                if ( imageStrings.get( i + 1 ).substring( startIndex ).matches( seamonster2 ) && imageStrings.get( i - 1 ).substring( startIndex ).matches( seamonster0 ) ) {
                    System.out.println( "FOUND A MONSTER: " + i + ":" + startIndex );
                    seaMonsterCount++;
                }
                else {
                    System.out.println( "not fully matched :-(" );
                }
            }
        }
        
        if ( seaMonsterCount > 1 ) {
            System.out.println( "seaMonsterCount: " + seaMonsterCount );
            System.out.println( "hashCount: " + hashCount );
            System.out.println( "Water Roughness: " + ( hashCount - ( seaMonsterCount * 15 ) ) );
            return true;
        }
        return false;
    }
    
    private static int countHashes( String lineToAdd ) {
        int hashCount = 0;
        for ( char c : lineToAdd.toCharArray() ) {
            if ( c == '#' ) {
                hashCount++;
            }
        }
        return hashCount;
    }
    
    private static void printCombinedImage( char[][] combinedImage, boolean showGaps ) {
        System.out.println( "********************" );
        for ( int i = combinedImageWidth - 1; i >= 0; i-- ) {
            for ( int j = 0; j < combinedImageWidth; j++ ) {
                System.out.print( combinedImage[i][j] );
                if ( showGaps && ( j + 1 ) % 8 == 0 ) {
                    System.out.print( "   " );
                }
            }
            System.out.println();
            if ( showGaps && ( i ) % 8 == 0 ) {
                System.out.println();
            }
        }
    }
    
    private static char[][] combineTiles( ImageGrid imageGrid ) {
        imageGrid.resetIterator();
        char[][] fullImage = new char[combinedImageWidth][combinedImageWidth];
        int currentStartI = combinedImageWidth - 1;
        int currentStartJ = 0;
        while ( imageGrid.hasNext() ) {
            Tile tileToInsert = imageGrid.getTileAt( imageGrid.next() );
            System.out.println( "inserting tile: " + tileToInsert.id + " at: " + currentStartI + "/" + currentStartJ );
            tileToInsert.print();
            char[][] trimmedImage = tileToInsert.getTrimmedImage();
            for ( int i = currentStartI; i > currentStartI - 8; i-- ) {
                for ( int j = currentStartJ; j < currentStartJ + 8; j++ ) {
                    fullImage[i][j] = trimmedImage[i % 8][j % 8];
                }
            }
            if ( currentStartJ == combinedImageWidth - 8 ) {
                currentStartJ = 0;
                currentStartI -= 8;
            }
            else {
                currentStartJ += 8;
            }
        }
        return fullImage;
    }
    
    private static ImageGrid alignTilesInGrid( ImageTiles imageTiles ) {
        ImageGrid imageGrid = new ImageGrid( gridSize );
        
        while ( imageGrid.hasNext() ) {
            GridPosition gridPosition = imageGrid.next();
            List<Tile> possibleTiles = imageTiles.getPossibleTiles( gridPosition );
            boolean matchToLeft = gridPosition.matchNextEdgeToLeft();
            Tile matchedTile = null;
            if ( gridPosition.isTopLeft() ) {
                matchedTile = possibleTiles.get( 0 );
                matchedTile.alignForTopLeft();
            }
            else {
                String nextPatternToSearchFor = imageGrid.edgeToJoinTo( gridPosition );
                for ( Tile tile : possibleTiles ) {
                    if ( tile.matchingPatterns.contains( nextPatternToSearchFor ) ) {
                        tile.align( nextPatternToSearchFor, matchToLeft );
                        matchedTile = tile;
                        break;
                    }
                }
            }
            imageGrid.setTileAt( gridPosition, matchedTile );
            possibleTiles.remove( matchedTile );
        }
        imageGrid.print();
        return imageGrid;
    }
}

