import java.util.HashSet;
import java.util.Objects;

public class Territory {
    //properties
    private boolean hasAirport = false;
    private int troopCount;
    protected int id;
    protected String name;
    protected Player owner;
    protected HashSet<Territory> neighbors;

    private static int territoryCount = 0;

    //constructor
    public Territory(String name, int id) {
        this.name = name;
        owner = null;
        troopCount = 0;
        neighbors = new HashSet<>();
        this.id = id;
    }

    public Territory() {
    }

    //getter setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getOwnerId() {
        return owner == null ? -1 : owner.getId();
    }

    public Player getOwner() { return owner; }

    public void setOwner(Player owner) { this.owner = owner; }

    public int getTroopCount() {
        return troopCount;
    }

    public void setTroopCount(int troopCount) {
        this.troopCount = troopCount;
    }

    public boolean hasAirport() { return hasAirport; }

    public void setHasAirport(boolean hasAirport) {
        this.hasAirport = hasAirport;
    }

    public HashSet<Territory> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(HashSet<Territory> neighbors) {
        this.neighbors = neighbors;
    }
    //end getter setters

    public void addNeighbor(Territory neighbor) //adds given Territory parameter to the hashset using
    {
        neighbors.add(neighbor);
    }

    public void removeNeighbor(Territory ter) {
        neighbors.remove(ter);
    }

    public boolean isNeighbor(Territory territory) //checks if a given Territory object
    // is contained in the hashset: neighbors
    {
        return neighbors.contains(territory);
    }

    //Returns all the territories that can be attacked from this territory.
    public HashSet<Territory> searchForAttackable() {
        HashSet<Territory> attackableTerritories = new HashSet<>();

        for (Territory neighbor : neighbors) {
            if (owner.getId() == neighbor.owner.getId()) {
                continue;
            }
            if (owner.isAlly(neighbor.owner)) {
                attackableTerritories.addAll(getAttackableOfAlly(neighbor));
            } else {
                attackableTerritories.add(neighbor);
            }
        }

        return attackableTerritories;
    }

    private HashSet<Territory> getAttackableOfAlly(Territory allyTerritory) {
        HashSet<Territory> attackableTerritories = new HashSet<>();

        for (Territory allyNeighbor : allyTerritory.neighbors) {
            if (!(allyTerritory.getOwner().getId() == allyNeighbor.owner.getId()
                    || allyNeighbor.owner.getId() == owner.getId())) {
                attackableTerritories.add(allyNeighbor);
            }
        }

        return attackableTerritories;
    }



    public HashSet<Territory> searchForFortifyable( HashSet<Territory> alreadySearched) {
        HashSet<Territory> fortifyableTerritories = new HashSet<>();

        for( Territory neighbor: neighbors) {
            if(alreadySearched.contains(neighbor))
                continue;
            if( owner.getId() != neighbor.getOwnerId())
                continue;
            alreadySearched.add(this);
            fortifyableTerritories.add(neighbor);
            fortifyableTerritories.addAll(neighbor.searchForFortifyable(alreadySearched));
        }

        return fortifyableTerritories;
    }

    public void addTroop (int troopCount) {
        this.troopCount += troopCount;
    }

    @Override
    public String toString() {
        return "Territory{" +
                "name='" + name + '\'' +
                ", ownerId=" + getOwnerId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Territory territory = (Territory) o;
        return getOwnerId() == territory.getOwnerId() &&
                Objects.equals(name, territory.name);
    }

    public Territory getTerritory() {
        return this;
    }
}