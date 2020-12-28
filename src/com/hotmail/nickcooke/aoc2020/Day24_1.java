package com.hotmail.nickcooke.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day24_1 {
    public static void main( String[] args ) throws IOException {
        List<List<HexagonalDirection>> directionsToTilesToFlip = getDirectionsToTilesToBeFlipped();
        Map<TilePosition, Boolean> flippedTiles = flipTiles( directionsToTilesToFlip );
        System.out.println( flippedTiles.entrySet().stream().filter( Map.Entry::getValue ).count() );
    }
    
    protected static Map<TilePosition, Boolean> flipTiles( List<List<HexagonalDirection>> directionsToTilesToFlip ) {
        Map<TilePosition, Boolean> flippedHexagons = new HashMap<>();
        for ( List<HexagonalDirection> directions : directionsToTilesToFlip ) {
            TilePosition hexagonToFlip = new TilePosition().applyDirections( directions );
            flippedHexagons.merge( hexagonToFlip,true, (oldValue, newValue)-> !oldValue );
        }
        return flippedHexagons;
    }
    
    protected static List<List<HexagonalDirection>> getDirectionsToTilesToBeFlipped() throws IOException {
        List<String> input = Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day24Input" ) );
        List<List<HexagonalDirection>> directionsToTilesToBeFlipped = new ArrayList<>();
        for ( String line : input ) {
            List<HexagonalDirection> directions = new ArrayList<>();
            char[] directionCharacters = line.toCharArray();
            for ( int i = 0; i < directionCharacters.length; i++ ) {
                String directionString = directionCharacters[i] + "";
                if ( directionString.equals( "s" ) || directionString.equals( "n" ) ) {
                    directionString += directionCharacters[++i];
                }
                directions.add( HexagonalDirection.valueOf( directionString ) );
            }
            directionsToTilesToBeFlipped.add( directions );
        }
        return directionsToTilesToBeFlipped;
    }
}

class TilePosition {
    int x;
    int y;
    int z;
    
    TilePosition() {
        this( 0, 0, 0 );
    }
    
    TilePosition( TilePosition tilePositionToCopy ) {
        this( tilePositionToCopy.x, tilePositionToCopy.y, tilePositionToCopy.z );
    }
    
    TilePosition( int x, int y, int z ) {
        this.x = x;
        this.y = y;
        this.z = z;
        if ( !( x + y + z == 0 ) ) {
            throw new RuntimeException( "Invalid TilePosition" );
        }
    }

    TilePosition getNeighbour( HexagonalDirection direction ) {
        return new TilePosition( x + direction.x, y + direction.y, z + direction.z );
    }
    
    TilePosition move( HexagonalDirection direction ) {
        this.x = this.x + direction.x;
        this.y = this.y + direction.y;
        this.z = this.z + direction.z;
        return new TilePosition( this );
    }
    
    public TilePosition applyDirections( List<HexagonalDirection> directions ) {
        TilePosition resultingHexagon = new TilePosition( this );
        for ( HexagonalDirection direction : directions ) {
            resultingHexagon = resultingHexagon.getNeighbour( direction );
        }
        return resultingHexagon;
    }
    
    @Override
    public String toString() {
        return "TilePosition{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }
    
    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        TilePosition hexagon = (TilePosition) o;
        return x == hexagon.x && y == hexagon.y && z == hexagon.z;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash( x, y, z );
    }
}

enum HexagonalDirection {
    e( 1, -1, 0 ),
    se( 0, -1, 1 ),
    sw( -1, 0, 1 ),
    w( -1, 1, 0 ),
    nw( 0, 1, -1 ),
    ne( 1, 0, -1 );
    
    public int x, y, z;
    
    HexagonalDirection( int x, int y, int z ) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}