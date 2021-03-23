import java.util.Arrays;
import java.util.HashMap;

public class SeaBattleGame {

    // Class fields
    private char[][] grid = new char[10][10];
    private final HashMap<Character, Integer> rowHashMap = new HashMap<>();

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

    // Generating HashMap for row names
    public void generateRowHashMap() {
        char key = 'A';
        for (int value = 0; value < 10; value++) {
            rowHashMap.put(key, value);
            key++;
        }
    }

    // Positioning the ship on the field
    public void positionShip(int shipLength, String start, String end) {

        if (!isLengthCorrect(shipLength, start, end)) {

            System.out.println();
            System.out.println("Error! Wrong length of the Submarine! Try again:");
            System.out.println();

        } else if (!isValidPosition(start, end)) {

            System.out.println();
            System.out.println("Error! Wrong ship location! Try again:");
            System.out.println();

        } else {

        }
    }

    // Helper methods
    public boolean isValidPosition(String start, String end) {
        return true;
    }

    public boolean isLengthCorrect(int shipLength, String start, String end) {
        return Math.abs((int) start.charAt(1) - (int) end.charAt(1)) == shipLength
                || Math.abs(rowHashMap.get(start.charAt(0)) - rowHashMap.get(start.charAt(0))) == shipLength;
    }


    public static void main(String[] args) {
        SeaBattleGame game = new SeaBattleGame();

    }
}
