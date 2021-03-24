import java.util.HashMap;

public class Ship {

    private int shipLength;
    private String shipClass;
    private final HashMap<String, Integer> shipClassHashMap = new HashMap<>();

    // Class constructor
    public Ship() {
        setShipClassHashMap();
        shipClass = "Aircraft Carrier";
        setShipLength();
    }

    // Setters
    public void setShipClassHashMap() {
        shipClassHashMap.put("Aircraft Carrier", 5);
        shipClassHashMap.put("Battleship", 4);
        shipClassHashMap.put("Submarine", 3);
        shipClassHashMap.put("Cruiser", 3);
        shipClassHashMap.put("Destroyer", 2);
    }

    public void setShipClass(String shipClass) {
        this.shipClass = shipClass;
        setShipLength();
    }

    public void setShipLength() {
        shipLength = shipClassHashMap.get(shipClass);
    }

    // Getters
    public int getShipLength() {
        return shipLength;
    }

    public String getShipClass() {
        return shipClass;
    }
}
