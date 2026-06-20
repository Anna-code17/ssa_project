public class City {

    private static final int MAX_BUDGET_20X20 = 100000;
    private String name;
    private CityGrid grid;
    private CityState state;
    private Policy activePolicy;
    private int currentTick;

    public City(String name, int size) {
        this.name = name;
        this.grid = new CityGrid(size);
        this.state = new CityState(calculateMaxBudget());
        this.activePolicy = null;
        this.currentTick = 0;
    }

    public City () { }
    
//--------------------------- METODI GESTIONE ENTITA' ------------------------------

    public boolean placeEntity(int x, int y, PlaceableEntity entity) {

    if (!grid.isValidPosition(x, y)) return false;

    boolean result;

    if (entity instanceof Building building) {
        result = placeBuilding(x, y, building);
    } else if (entity instanceof Infrastructure infrastructure) {
        result = placeInfrastructure(x, y, infrastructure);
    } else {
        return false;
    }

    if (result) {
        Effect effects = entity.getEffects();
        if (effects != null && effects.getBuildCost() != 0) {
            state.setBudget(state.getBudget() + effects.getBuildCost());
        }
    }

    return result;
    }
    

    // Posizionamento Building e valutazione rispetto delle PlacementRules
    public boolean placeBuilding(int x, int y, Building building) {
        
        if (PlacementRules.canPlaceBuilding(building, this.grid , x, y)) {
            return this.grid.place(x, y, building);
        }

        return false;
    }
    
    // Posizionamento Infrastructure e valutazione rispetto delle PlacementRules
    public boolean placeInfrastructure(int x, int y, Infrastructure infrastructure) {
        if (PlacementRules.canPlaceInfrastructure(infrastructure,this.grid, x, y)) {
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

    public Policy getActivePolicy() {
        return activePolicy;
    }
    
    public int getMaxBudget()
    {
    	return calculateMaxBudget();
    }

//-----------------------------GESTIONE TICK-----------------------------
    
    public void incrementTick() {
        currentTick++;
    }
    
    public int getCurrentTick() {
        return currentTick;
    }

// --------------------------- METODI SETTER -----------------------------

    public void setActivePolicy(Policy policy) {
        this.activePolicy = policy;
    }

    //Reset completo della città 
    public void reset() {
    this.grid.clearGrid(); //griglia
    this.state.clear();    //stato
    this.activePolicy = null; //policy
    this.currentTick = 0; //tick
    }

// --------------------------- METODO PER MANIPOLARE IL BUDGET -----------------
    
    private int calculateMaxBudget() 
    {
        //20 utilizzato come numero perche' e' la massima grandezza della griglia  
        int size = grid.getSize();
        return (MAX_BUDGET_20X20 * size * size) / (20 * 20);
    }

}
