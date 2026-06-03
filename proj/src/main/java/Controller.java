public class Controller {
    private City city;
    private TickEngine tickEngine;
    
    public Controller(int gridSize) {
        this.city = new City("MyCity", gridSize);
        this.tickEngine = new TickEngine();
    }
    
    // -------------------------- ENTITÀ ------------------------------
    
    public boolean placeEntity(PlaceableEntity entity, int x, int y) {
        if (city.getGrid().isEmpty(x, y)) {
            return city.getGrid().place(x, y, entity);
        }
        return false;
    }
   
    public void removeEntity(int x, int y) {
        city.getGrid().remove(x, y);
    }
    
    // ---------------------------POLICY ------------------------------
    
    public void applyPolicy(Policy policy) {

        city.setPolicy(policy);
    }
    
    public void deactivatePolicy() { // disattiva la policy corrente
        city.setPolicy(null);
    }
    
    public Policy getActivePolicy() { 
        return city.getPolicy();
    }
    
    // --------------------------- SIMULAZIONE ------------------------------
    
    //per gestire evoluzione temporale della città
    public void nextTick() {
        tickEngine.advanceTick(city);
    }
    
    public int getCurrentTick() {
        return tickEngine.getCurrentTick();
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