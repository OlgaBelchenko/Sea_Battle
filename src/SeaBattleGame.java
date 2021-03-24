import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class SeaBattleGame {

    // Class fields
    private final char[][] grid = new char[10][10];
    private final HashMap<Character, Integer> rowHashMap = new HashMap<>();
    Ship ship = new Ship();
    Scanner scan = new Scanner(System.in);
    int lowRowIndex;
    int lowColIndex;
    int topRowIndex;
    int topColIndex;
    int shootingRowIndex;
    int shootingColIndex;

    // Class constructor
    public SeaBattleGame() {
        generateGrid();
        generateRowHashMap();
        printGrid();
    }

    // Initial field generation
    public void generateGrid() {
        for (char[] chars : grid) {
            Arrays.fill(chars, '~');
        }
    }

    public void calculatePlacingIndices(String start, String end) {
        int startR;
        int endR;
        // If the input is incorrect, set value -1 to row indexes
        try {
            startR = rowHashMap.get(start.charAt(0));
            endR = rowHashMap.get(end.charAt(0));
        } catch (NullPointerException e) {
            startR = -1;
            endR = -1;
        }

        int startC;
        int endC;

        if (start.length() == 2) {
            startC = Character.getNumericValue(start.charAt(1));
        } else {
            startC = 10;
        }

        if (end.length() == 2) {
            endC = Character.getNumericValue(end.charAt(1));
        } else if (end.length() == 3) {
            endC = Character.getNumericValue(end.charAt(1)) * 10 + Character.getNumericValue(end.charAt(2));
        } else {
            endC = -1;
        }

        lowRowIndex = Math.min(startR, endR);
        lowColIndex = Math.min(startC, endC) - 1;
        topRowIndex = Math.max(startR, endR);
        topColIndex = Math.max(startC, endC) - 1;
    }

    public void calculateShootingIndices(String shot) {
        try {
            shootingRowIndex = rowHashMap.get(shot.charAt(0));
        } catch (NullPointerException e) {
            shootingRowIndex = -1;
        }

        if (shot.length() == 2) {
            shootingColIndex = Character.getNumericValue(shot.charAt(1)) - 1;
        } else if (shot.length() == 3) {
            shootingColIndex = Character.getNumericValue(shot.charAt(1)) * 10 + Character.getNumericValue(shot.charAt(2)) - 1;
        } else {
            shootingColIndex = 100;
        }
    }

    // Generating HashMap for row names
    public void generateRowHashMap() {
        char key = 'A';
        for (int value = 0; value < 10; value++) {
            rowHashMap.put(key, value);
            key++;
        }
    }

    // Positioning the ship on the field if the position is OK
    public void positionShip() {

        for (int i = lowRowIndex; i <= topRowIndex; i++) {
            for (int j = lowColIndex; j <= topColIndex; j++) {
                grid[i][j] = 'O';
            }
        }
        printGrid();
    }

    // Checking if the indices for ship placement are wrong
    public boolean arePlacingIndicesInvalid() {
        if (lowRowIndex == -1 || topRowIndex == -1 || lowColIndex < 0 || lowColIndex > 9
                || topColIndex < 0 || topColIndex > 9) {
            System.out.println();
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            System.out.println();
            return true;
        }
        return false;
    }

    // Checking if the indices for shooting are wrong
    public boolean areShootingIndicesInvalid() {
        if (shootingRowIndex == -1 || shootingColIndex < 0 || shootingColIndex > 9) {
            System.out.println();
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            System.out.println();
            return true;
        }
        return false;
    }

    // Checking if the input position is OK
    public boolean isInvalidPosition() {

        boolean result = false;
        String errorText = "";
        if (lowRowIndex != topRowIndex && lowColIndex != topColIndex) {
            result = true;
            errorText = "Error! Wrong ship location! Try again:";
        } else {
            for (int i = lowRowIndex; i <= topRowIndex; i++) {
                for (int j = lowColIndex; j <= topColIndex; j++) {
                    if (grid[i][j] == 'O') {
                        result = true;
                        errorText = "Error! The cells are already taken by another ship! Try again:";
                        break;
                    }
                    if (i != 0) {
                        if (grid[i - 1][j] == 'O') {
                            result = true;
                            errorText = "Error! You placed it too close to another one. Try again:";
                            break;
                        }
                    }
                    if (i != 9) {
                        if (grid[i + 1][j] == 'O') {
                            result = true;
                            errorText = "Error! You placed it too close to another one. Try again:";
                            break;
                        }
                    }
                    if (j != 0) {
                        if (grid[i][j - 1] == 'O') {
                            result = true;
                            errorText = "Error! You placed it too close to another one. Try again:";
                            break;
                        }
                    }
                    if (j != 9) {
                        if (grid[i][j + 1] == 'O') {
                            result = true;
                            errorText = "Error! You placed it too close to another one. Try again:";
                            break;
                        }
                    }
                }
            }
        }

        if (result) {
            System.out.println();
            System.out.println(errorText);
            System.out.println();
        }
        return result;
    }

    // Checking is the input length is OK
    public boolean isWrongLength() {
        if (topRowIndex - lowRowIndex + 1 != ship.getShipLength() &&
                topColIndex - lowColIndex + 1 != ship.getShipLength()) {
            System.out.println();
            System.out.printf("Error! Wrong length of the %s! Try again:%n", ship.getShipClass());
            System.out.println();
            return true;
        }
        return false;
    }

    // Shooting
    public void takeShot() {
        char cell = grid[shootingRowIndex][shootingColIndex];
        String message = "";
        if (cell == 'O') {
            grid[shootingRowIndex][shootingColIndex] = 'X';
            message = "You hit a ship!";
        } else if (cell == '~') {
            grid[shootingRowIndex][shootingColIndex] = 'M';
            message = "You missed!";
        }
        printGrid();
        System.out.println(message);
    }

    public void printGrid() {
        System.out.println();
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char rowLabel = 'A';
        for (int i = 0; i < grid.length; i++) {
            for (int j = -1; j < grid[i].length; j++) {
                if (j == -1) {
                    System.out.print(rowLabel);
                } else {
                    System.out.print(" " + grid[i][j]);
                }
            }
            rowLabel++;
            System.out.println();
        }
        System.out.println();
    }

    public void playGame() {

        for (int i = 0; i < 5; i++) {

            // Positioning the ships
            System.out.printf("Enter the coordinates of the %s (%d cells):%n", ship.getShipClass(), ship.getShipLength());
            System.out.println();
            while (true) {
                String[] coords = scan.nextLine().split(" ");
                this.calculatePlacingIndices(coords[0], coords[1]);
                if (this.arePlacingIndicesInvalid() || this.isWrongLength() || this.isInvalidPosition()) {
                    continue;
                }
                this.positionShip();
                break;
            }

            if (i == 0) {
                ship.setShipClass("Battleship");
            } else if (i == 1) {
                ship.setShipClass("Submarine");
            } else if (i == 2) {
                ship.setShipClass("Cruiser");
            } else if (i == 3) {
                ship.setShipClass("Destroyer");
            }
        }

        // Shooting
        System.out.println();
        System.out.println("The game starts!");
        System.out.println();
        this.printGrid();
        System.out.println("Take a shot!");
        System.out.println();
        while (true) {
            String shot = scan.nextLine();
            this.calculateShootingIndices(shot);
            if (this.areShootingIndicesInvalid()) {
                continue;
            }
            this.takeShot();
            break;
        }

    }


    public static void main(String[] args) {
        SeaBattleGame game = new SeaBattleGame();
        game.playGame();
        game.scan.close();

    }
}
