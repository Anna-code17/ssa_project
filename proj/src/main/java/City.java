public class City {

    private String name;
    private CityGrid grid;
    private CityState state;
    private Policy activePolicy;
    private int currentTick;

    public City(String name, int size) {
        this.name = name;
        this.grid = new CityGrid(size);
        //Ho impostato il budget iniziale a 1000
        this.state = new CityState(1000);
        this.activePolicy = null;
        this.currentTick = 0;
        
    }
//--------------------------- METODI GESTIONE ENTITA' ------------------------------

     public boolean placeEntity(int x, int y, PlaceableEntity entity) {
    String type = entity.getName(); //la tipologia di edificio è salvata in name
    
    //distinguo Buildings e Infrastructures in base al tipo
        if (type.equals("ResidentialBuilding") || 
        type.equals("IndustrialBuilding") || 
        type.equals("CommercialBuilding")) {
            return placeBuilding(x, y, (Building)entity);
        } 
        else {
            return placeInfrastructure(x, y, (Infrastructure)entity);
        }
    }

    // Posizionamento Building e valutazione rispetto delle PlacementRules
    public boolean placeBuilding(int x, int y, Building building) {
        if (PlacementRules.canPlaceBuilding(building, city.getGrid(), x, y)) {
            return  this.grid.place(x, y, building);
        }
        return false;
    }
    
    // Posizionamento Infrastructure e valutazione rispetto delle PlacementRules
    public boolean placeInfrastructure(int x, int y, Infrastructure infrastructure) {
        if (PlacementRules.canPlaceInfrastructure(infrastructure, city.getGrid(), x, y)) {
            return this.grid.place(x, y, infrastructure);
        }
        return false;
    }

       public void removeEntity(int x, int y) {
        this.grid.remove(x, y);
    }
// --------------------------- METODI GETTER ------------------------------
    
    public String getName() {
        return name;
    }

    public CityGrid getGrid() {
        return grid;
    }

    public CityState getState() {
        return state;
    }

    public Policy getPolicy() {
        return activePolicy;
    }

//-----------------------------GESTIONE TICK-----------------------------
    
    public void incrementTick() {
        currentTick++;
    }
    
    public int getCurrentTick() {
        return currentTick;
    }

// --------------------------- METODI SETTER -----------------------------

    public void setPolicy(Policy policy) {
        this.activePolicy = policy;
    }

}
