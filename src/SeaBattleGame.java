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

    public void calculateIndices(String start, String end) {
        int startR = rowHashMap.get(start.charAt(0));
        int endR = rowHashMap.get(end.charAt(0));
        int startC;
        int endC;
        if (start.length() == 2) {
            startC = Character.getNumericValue(start.charAt(1));
        } else {
            startC = 10;
        }

        if (end.length() == 2) {
            endC = Character.getNumericValue(end.charAt(1));
        } else {
            endC = 10;
        }
        lowRowIndex = Math.min(startR, endR);
        lowColIndex = Math.min(startC, endC) - 1;
        topRowIndex = Math.max(startR, endR);
        topColIndex = Math.max(startC, endC) - 1;
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

    // Checking is the input position is OK
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
            System.out.println("Error! Wrong length of the Submarine! Try again:");
            System.out.println();
            return true;
        }
        return false;
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
            System.out.printf("Enter the coordinates of the %s (%d cells):%n", ship.getShipClass(), ship.getShipLength());
            System.out.println();
            while (true) {
                String[] coords = scan.nextLine().split(" ");
                this.calculateIndices(coords[0], coords[1]);
                if (this.isWrongLength()) {
                    continue;
                }
                if (this.isInvalidPosition()) {
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


    public static void main(String[] args) {
        SeaBattleGame game = new SeaBattleGame();
        game.playGame();
        game.scan.close();

    }
}
