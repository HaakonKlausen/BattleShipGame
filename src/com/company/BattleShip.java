package com.company;

/**
 * Created by y00hkl on 13.01.2016.
 */
public class BattleShip {
    private String[] onSquares; // Array with coordinates on form A3, B6 etc showing squares occupied by the ship
    private Boolean[] damage;   // Array with elements set to true when damage is taken on the corresponding square

    public BattleShip(String[] squares) {
        onSquares = new String[squares.length];
        damage = new Boolean[squares.length];
        for(int i=0;i<squares.length;i++) {
            onSquares[i] = squares[i];
            damage[i] = false;
        }
        System.out.print("New Battleship at: ");
        // Debug code start
        for(int i=0;i<squares.length;i++) {
            if (i > 0) {
                System.out.print("-");
            }
            System.out.print(onSquares[i]);
        }
        System.out.println("");
        // Debug code end

    }

    // returns true if all squares has damage
    public boolean hasSunken() {
        boolean sunken=true;    // We start with the assumption that we are sunken, and set it to false if discovering a non-hit square
        for (int i=0;i<onSquares.length;i++) {
            if (damage[i] == false) {
                sunken = false;
            }
        }
        return sunken;
    }

    // Returns true if a battleship is at a coordinate
    public boolean isAtSquare(String square) {
        for (int i=0;i<onSquares.length;i++) {
            if (square.equals(onSquares[i])) {
                return true;
            }
        }
        return false;
    }

    // Called whenever a shot is fired, return true if this shot sunk the ship.
    public boolean shoot(String square) {
        if (hasSunken()) {
            // We are already down, so dont report the hit double.
            return false;
        }
        for (int i=0;i<onSquares.length;i++) {
            if (square.equals(onSquares[i])) {
                if (damage[i] == true) {
                    System.out.println("Hit Again");
                }
                else {
                    System.out.println("Hit!");
                    damage[i] = true;
                }
            }
        }
        return hasSunken();
    }
}
