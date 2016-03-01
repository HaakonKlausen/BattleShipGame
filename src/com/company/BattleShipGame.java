package com.company;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.Random;


/**
 * Created by y00hkl on 13.01.2016.
 */
class BattleShipGame {
    static int cMaxRows = 9;
    static int cMaxColumns = 9;
    static int cMaxBattleshipLength = 5;
    static int cNumberOfBattleships = 3;
    static String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static int cDirectionHorizontal = 0;
    static int cDirectionVertical = 1;

    Scanner scanner;
    String coordinate;
    BattleShip[] battleShips;
    int roundsFired;
    int shipsSunken;

    // Asks for input and parses a move
    private boolean parseMove() {
        boolean errorInParse = false;   // Set to true if command is illegal
        String command;
        char row;
        int col=0;

        System.out.print("Enter move: ");
        command = scanner.next();

        // First check for exit command
        if (command.equals("exit")) {
            System.out.println("You lost the war after fireing " + roundsFired + " Missils");
            return true;
        }
        if (command.equals("print")) {
            this.printGame();
            return false;
        }

        // We did not exit, so carry on
        if (command.length() != 2) {
            System.out.println("Illegal coordinate, must be 2 character length");
            errorInParse = true;
        } else {
            char c1 = command.charAt(0);
            char c2 = command.charAt(1);
            row = 0;
            if (!Character.isDigit(c2)) {
                System.out.println("Illegal coordinate, the second character (row) must be a digit");
                errorInParse = true;
            } else {
                row = c2;
            }
            if (row == 0) {
                System.out.println("Illegal coordinate, the row can not be zero");
                errorInParse = true;
            }
            if (!Character.isLetter(c1)) {
                System.out.println("Illegal coordinate, the first character (column) must be a letter");
                errorInParse = true;
            } else {
                col = c1;
            }
        }
        if (!errorInParse) {
            // Now we know that we have a valid coordinate in command
            coordinate = new String(command);
            if (shootAtCoordinate(coordinate)) {
                shipsSunken++;
            };
            roundsFired++;
            if(shipsSunken == cNumberOfBattleships) {
                System.out.println("You won the war with " + roundsFired + " missiles!");
                return true;
            }
        }
        return false;
    }

    // Send a shot to all the battleships in the game, return true if one ship sunk
    private boolean shootAtCoordinate(String coordinate) {
        for (int i=0; i<battleShips.length; i++) {
            if (battleShips[i].shoot(coordinate)){
                System.out.println("Battleship " + i + ": You just killed 2000 marines.... :-(");
                return true;
            };
        }
        return false;
    }

    // Populate the game board with battleships
    private void populateBattleships(int numberOfBattleships) {
        // Helping array for populating battleships
        String[] squares;

        // Initiate class array
        battleShips = new BattleShip[numberOfBattleships];
        for (int i=0; i<numberOfBattleships; i++) {

            battleShips[i] = new BattleShip(positionBattleship(i));
        }
    }


    // Find a free position for a battleship
    private String[] positionBattleship(int battleshipNumber) {
        String[] position;
        boolean allFree;

        do {
            position = generateBattleshipPosition();
            allFree = true;
            for(int battleShip=0;battleShip<battleshipNumber;battleShip++) {
                for (int square=0;square<position.length;square++) {
                    if (battleShips[battleShip].isAtSquare(position[square])) {
                        allFree = false;
                    }
                }
            }
            // Check for free pos in all previous generated battleships
        } while (!allFree);

        return position;
    }

    private void printGame() {
        for(int row=0; row <= cMaxRows; row++) {
            System.out.print(String.valueOf(row) + " ");
            for (int col=0; col <= cMaxColumns; col++) {
                String coordinate = new String(letters.charAt(col) + String.valueOf(row));
                boolean shipAtCoordinate = false;
                for (int ship=0; ship < battleShips.length; ship++) {

                    if (battleShips[ship].isAtSquare(coordinate)) {
                        shipAtCoordinate = true;
                    }
                }
                if (shipAtCoordinate) {
                    System.out.print("| X ");
                }
                else {
                    System.out.print("|   ");
                }
            }
            System.out.println("|");
        }
        System.out.print("  ");
        for (int col=0; col <= cMaxColumns; col++) {
            System.out.print("| " + letters.charAt(col) + " ");
        }
        System.out.println("|");
    }

    // Generate a battleShip position that may not be free
    private String[] generateBattleshipPosition() {
        int battleshipLength;
        int direction;
        int startRow;
        int startCol;
        int maxCol;
        int maxRow;

        String[] position;
        Random randomGenerator = new Random();

        battleshipLength = randomGenerator.nextInt(cMaxBattleshipLength) + 1;  // 1 to 5
        position = new String[battleshipLength];

        // Find direction, 0=left to right, 1=up to down
        direction = randomGenerator.nextInt(2);

        // Find start row
        if(direction == cDirectionHorizontal) {
            maxRow = cMaxRows - 1;
        } else {
            maxRow = cMaxRows - battleshipLength;
        }
        startRow = randomGenerator.nextInt(cMaxRows - 1);

        // Find start col
        if(direction == cDirectionHorizontal) {
            maxCol = cMaxColumns - battleshipLength;
        } else {
            maxCol = cMaxColumns - 1;
        }
        startCol = randomGenerator.nextInt(cMaxColumns - 1);

        // Generate postions
        if(direction == cDirectionHorizontal) {
            int col = startCol;
            for (int i = 0; i < battleshipLength; i++) {
                position[i] = letters.charAt(col) + String.valueOf(startRow);
                col++;
            }
        } else {
            int row = startRow;
            for (int i = 0; i < battleshipLength; i++) {
                position[i] = letters.charAt(startCol) + String.valueOf(row);
                row++;
            }
        }
        return position;
    }

    public BattleShipGame(String[] args) {
        System.out.println("Hello battleship!");
        roundsFired=0;
        shipsSunken=0;

        populateBattleships(cNumberOfBattleships);

        scanner = new Scanner(System.in);
        while (!parseMove()) {
        }
    }
}
