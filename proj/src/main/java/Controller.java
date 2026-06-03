public class Controller {
    private City city;
    private TickEngine tickEngine;
    
    public Controller(int gridSize) {
        this.city = new City("MyCity", gridSize);
        this.tickEngine = new TickEngine();
    }
    
    // -------------------------- ENTITÀ ------------------------------
    
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
            return city.getGrid().place(x, y, building);
        }
        return false;
    }
    
    // Posizionamento Infrastructure e valutazione rispetto delle PlacementRules
    public boolean placeInfrastructure(int x, int y, Infrastructure infrastructure) {
        if (PlacementRules.canPlaceInfrastructure(infrastructure, city.getGrid(), x, y)) {
            return city.getGrid().place(x, y, infrastructure);
        }
        return false;
    }
    
    public void removeEntity(int x, int y) {
        city.getGrid().remove(x, y);
    }
    
    // ---------------------------POLICY ------------------------------
    
   public void removeEntity(int x, int y) {
        city.getGrid().remove(x, y);
    }
    
    public void activatePolicy(Policy policy) {
        city.setPolicy(policy);
    }
    
    public void deactivatePolicy() {
        city.setPolicy(null);
    }
    
    // --------------------------- SIMULAZIONE ------------------------------
    
    //per gestire evoluzione temporale della città
    public void nextTick() {
        TickEngine.advanceTick(city);
    }
    
    public int getCurrentTick() {
        return TickEngine.getCurrentTick();
    }
    
    //alcune opzioni per gestire l'interfaccia visiva
    public City getCity() {
        return city;
    }
    
    public CityState getCityState() {
        return city.getState();
    }
    
    public CityGrid getGrid() {
        return city.getGrid();
    }
}
