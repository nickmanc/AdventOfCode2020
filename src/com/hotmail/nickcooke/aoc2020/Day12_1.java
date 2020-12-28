package com.hotmail.nickcooke.aoc2020;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day12_1 {
    public static void main( String[] args ) throws Exception {
        List<String> input = Files.readAllLines( Paths.get( "resources\\com\\hotmail\\nickcooke\\aoc2020\\Day12Input" ) );
        Ship ship = new Ship();
        for(String instruction:input){
            ship.applyInstruction( new Instruction( instruction ) );
        }
        System.out.println(ship.getManhattanDistance());
    }
}

class Ship {
    int x = 0;
    int y = 0;
    int facing = 90;
    
    void applyInstruction(Instruction instruction) throws Exception {
        if (instruction.action=='F'){
            switch ( facing ){
                case 0: instruction.action = 'N';break;
                case 90: instruction.action = 'E';break;
                case 180: instruction.action = 'S';break;
                case 270: instruction.action = 'W';break;
                default: throw new Exception( "unexpected facing value [" + facing + "]" );
            }
        }
        switch (instruction.action){
            case 'N': y +=instruction.units;break;
            case 'S': y -=instruction.units;break;
            case 'E': x +=instruction.units;break;
            case 'W': x -=instruction.units;break;
            case 'R': facing = Math.abs(facing + instruction.units)%360;break;
            case 'L': facing = Math.abs(facing + 360 - instruction.units)%360;break;
            default: throw new Exception( "unexpected instruction value [" + instruction.action + "]" );
        }
    }
    
    int getManhattanDistance(){
        return Math.abs(x) + Math.abs(y);
    }
}

class Instruction {
    char action;
    int units;
    Instruction(String instruction){
        this.action=instruction.charAt( 0 );
        this.units=Integer.parseInt(instruction.substring( 1 ));
    }
}
