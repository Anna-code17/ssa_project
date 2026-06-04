public class Controller {
    private City city;
    private TickEngine tickEngine;
    
    public Controller(int gridSize) {
        this.city = new City("MyCity", gridSize);
        this.tickEngine = new TickEngine();
    }
    
    // -------------------------- ENTITÀ ------------------------------
    
   public boolean placeEntity(int x, int y, PlaceableEntity entity) {
        return city.placeEntity(x, y, entity);  
    }
    
    public void removeEntity(int x, int y) {
        city.removeEntity(x, y); 
    }
    
    // ---------------------------POLICY ------------------------------
    
    public void applyPolicy(Policy policy) {
        city.setPolicy(policy);
    }
    
    public void deactivatePolicy() {
        city.setPolicy(null);
    }
    
    // --------------------------- SIMULAZIONE ------------------------------
    
    //per gestire evoluzione temporale della città
    public void nextTick() {
        tickEngine.advanceTick(city);
    }
    
    public int getCurrentTick() {
        return  city.getCurrentTick();
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
