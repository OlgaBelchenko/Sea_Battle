import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class SeaBattleGame {

    // Class fields
    private final char[][] playerOnePlacingGrid = new char[10][10];
    private final char[][] playerTwoPlacingGrid = new char[10][10];
    private final char[][] playerOneShootingGrid = new char[10][10];
    private final char[][] playerTwoShootingGrid = new char[10][10];
    private String currentPlayer;

    private char[][] currentPlacingGrid;
    private char[][] currentShootingGrid;
    private char[][] otherPlacingGrid;

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
        currentPlacingGrid = playerTwoPlacingGrid;
        currentShootingGrid = playerTwoShootingGrid;
        generatePlacingGrid();
        generateShootingGrid();

        currentPlacingGrid = playerOnePlacingGrid;
        currentShootingGrid = playerOneShootingGrid;
        generatePlacingGrid();
        generateShootingGrid();

        generateRowHashMap();
        changeCurrentPlayer();
    }

    // Initial field generation
    public void generatePlacingGrid() {
        for (char[] chars : currentPlacingGrid) {
            Arrays.fill(chars, '~');
        }
    }

    public void generateShootingGrid() {
        for (char[] chars : currentShootingGrid) {
            Arrays.fill(chars, '~');
        }
    }

    public void changeCurrentPlayer() {
        if ("Player 1".equals(currentPlayer)) {
            currentPlayer = "Player 2";
            currentPlacingGrid = playerTwoPlacingGrid;
            currentShootingGrid = playerTwoShootingGrid;
            otherPlacingGrid = playerOnePlacingGrid;
        } else {
            currentPlayer = "Player 1";
            currentPlacingGrid = playerOnePlacingGrid;
            currentShootingGrid = playerOneShootingGrid;
            otherPlacingGrid = playerTwoPlacingGrid;
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

    // Positioning the ship on the field if the position is OK
    public void positionShip() {

        for (int i = lowRowIndex; i <= topRowIndex; i++) {
            for (int j = lowColIndex; j <= topColIndex; j++) {
                currentPlacingGrid[i][j] = 'O';
            }
        }
        printPlacingGrid();
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
                    if (currentPlacingGrid[i][j] == 'O') {
                        result = true;
                        errorText = "Error! The cells are already taken by another ship! Try again:";
                        break;
                    }
                    if (i != 0) {
                        if (currentPlacingGrid[i - 1][j] == 'O') {
                            result = true;
                            errorText = "Error! You placed it too close to another one. Try again:";
                            break;
                        }
                    }
                    if (i != 9) {
                        if (currentPlacingGrid[i + 1][j] == 'O') {
                            result = true;
                            errorText = "Error! You placed it too close to another one. Try again:";
                            break;
                        }
                    }
                    if (j != 0) {
                        if (currentPlacingGrid[i][j - 1] == 'O') {
                            result = true;
                            errorText = "Error! You placed it too close to another one. Try again:";
                            break;
                        }
                    }
                    if (j != 9) {
                        if (currentPlacingGrid[i][j + 1] == 'O') {
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

    public void printPlacingGrid() {

        System.out.println();
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char rowLabel = 'A';
        for (char[] chars : currentPlacingGrid) {
            for (int j = -1; j < chars.length; j++) {
                if (j == -1) {
                    System.out.print(rowLabel);
                } else {
                    System.out.print(" " + chars[j]);
                }
            }
            rowLabel++;
            System.out.println();
        }
        System.out.println();
    }

    public void printShootingGrid() {

        System.out.println();
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char rowLabel = 'A';
        for (char[] chars : currentShootingGrid) {
            for (int j = -1; j < chars.length; j++) {
                if (j == -1) {
                    System.out.print(rowLabel);
                } else {
                    System.out.print(" " + chars[j]);
                }
            }
            rowLabel++;
            System.out.println();
        }
        System.out.println();
    }

    public void printBothGrids() {
        printShootingGrid();
        System.out.println("---------------------");
        printPlacingGrid();
    }

    // Placing ships
    public void placeShips() {

        System.out.println(currentPlayer + ", place your ships on the game field");
        printPlacingGrid();

        this.ship.setShipClass("Aircraft Carrier");

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
    }

    // Shooting
    public void shoot() {

        changeCurrentPlayer();
        printBothGrids();
        System.out.println(currentPlayer + ", it's your turn:");
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

    public void takeShot() {
        char cell = otherPlacingGrid[shootingRowIndex][shootingColIndex];
        String message = "";
        if (cell == 'O') {
            otherPlacingGrid[shootingRowIndex][shootingColIndex] = 'X';
            currentShootingGrid[shootingRowIndex][shootingColIndex] = 'X';
            if (isNotLastShip()) {
                if (isShipNotComplete()) {
                    message = "You hit a ship!";
                } else {
                    message = "You sank a ship!";
                }
            } else {
                message = "Congratulations, " + currentPlayer + "! You sank the last ship! You won!";
            }

        } else if (cell == '~') {
            otherPlacingGrid[shootingRowIndex][shootingColIndex] = 'M';
            currentShootingGrid[shootingRowIndex][shootingColIndex] = 'M';
            message = "You missed!";
        }
        System.out.println(message);
        System.out.println();
    }

    public boolean isNotLastShip() {
        for (char[] charArr : otherPlacingGrid) {
            for (char ch : charArr) {
                if (ch == 'O') {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isShipNotComplete() {
        return isOAbove() || isOBelow() || isOLeft() || isORight();
    }

    public boolean isOBelow() {
        if (shootingRowIndex < 9) {
            for (int i = shootingRowIndex; i < otherPlacingGrid.length; i++) {
                if (otherPlacingGrid[i][shootingColIndex] == 'O') {
                    return true;
                } else if (otherPlacingGrid[i][shootingColIndex] == '~' || otherPlacingGrid[i][shootingColIndex] == 'M') {
                    break;
                }
            }
        }
        return false;
    }

    public boolean isOAbove() {
        if (shootingRowIndex > 0) {
            for (int i = shootingRowIndex; i >= 0; i--) {
                if (otherPlacingGrid[i][shootingColIndex] == 'O') {
                    return true;
                } else if (otherPlacingGrid[i][shootingColIndex] == '~' || otherPlacingGrid[i][shootingColIndex] == 'M') {
                    break;
                }
            }
        }
        return false;
    }

    public boolean isOLeft() {
        if (shootingColIndex > 0) {
            for (int i = shootingColIndex; i >= 0; i--) {
                if (otherPlacingGrid[shootingRowIndex][i] == 'O') {
                    return true;
                } else if (otherPlacingGrid[shootingRowIndex][i] == '~' || otherPlacingGrid[shootingRowIndex][i] == 'M') {
                    break;
                }
            }
        }
        return false;
    }

    public boolean isORight() {
        if (shootingColIndex < 9) {
            for (int i = shootingColIndex; i < otherPlacingGrid.length; i++) {
                if (otherPlacingGrid[shootingRowIndex][i] == 'O') {
                    return true;
                } else if (otherPlacingGrid[shootingRowIndex][i] == '~' || otherPlacingGrid[shootingRowIndex][i] == 'M') {
                    break;
                }
            }
        }
        return false;
    }

    public static void promptEnterKey() {
        System.out.println();
        System.out.println("Press Enter and pass the move to another player");
        System.out.println();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SeaBattleGame game = new SeaBattleGame();

        game.placeShips();
        promptEnterKey();
        game.changeCurrentPlayer();
        game.placeShips();

        while (game.isNotLastShip()) {
            promptEnterKey();
            game.shoot();
        }
        game.scan.close();

    }
}
