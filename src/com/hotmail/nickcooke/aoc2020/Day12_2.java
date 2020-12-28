package com.hotmail.nickcooke.aoc2020;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day12_2 {
    public static void main( String[] args ) throws Exception {
        List<String> input = Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day12Input" ) );
        ShipWithWaypoint ship = new ShipWithWaypoint();
        for(String instruction:input){
            ship.applyInstruction( new Instruction( instruction ) );
        }
        System.out.println(ship.getManhattanDistance());
        
    }
}
class ShipWithWaypoint extends Ship{
    int waypointX=10;
    int waypointY=1;
    
    @Override
    void applyInstruction(Instruction instruction) {
        if (instruction.action=='F'){
            x += instruction.units * waypointX;
            y += instruction.units * waypointY;
        }
        else {
            int tmpX;
            switch (instruction.action){
                case 'N': waypointY +=instruction.units;break;
                case 'S': waypointY -=instruction.units;break;
                case 'E': waypointX +=instruction.units;break;
                case 'W': waypointX -=instruction.units;break;
                case 'L': switch (instruction.units) {
                    case 270: tmpX = waypointX; waypointX =  waypointY; waypointY  = -1 * tmpX;break;
                    case 180: waypointX *= -1; waypointY *= -1;break;
                    case 90: tmpX = waypointX; waypointX = -1 * waypointY; waypointY = tmpX;break;}break;
                case 'R': switch (instruction.units) {
                    case 90: tmpX = waypointX; waypointX =  waypointY; waypointY  = -1 * tmpX;break;
                    case 180: waypointX *= -1; waypointY *= -1;break;
                    case 270: tmpX = waypointX; waypointX = -1 * waypointY; waypointY = tmpX;break;}
                break;
            }
        }
    }
}

