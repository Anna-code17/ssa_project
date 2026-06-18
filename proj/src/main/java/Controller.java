 /*  Gestisce le operazioni di: entità, policy, simulazione, salvataggio.
     Fa da ponte tra l'interfaccia utente e il modello (City). */

public class Controller {
    private City city;
    private TickEngine tickEngine;
    private SaveManager SaveLoad;
    
    public Controller(String cityName, int gridSize) {
        this.city = new City(cityName, gridSize);
        this.tickEngine = new TickEngine();
        this.SaveLoad = new SaveManager();
    }
    
    // -------------------------- ENTITÀ ------------------------------
    
    // Delega il posizionamento di un'entità alla città
    public boolean placeEntity(int x, int y, PlaceableEntity entity) {
        return city.placeEntity(x, y, entity);  
    }
    
    // Delega la rimozione di un'entità alla città
    public void removeEntity(int x, int y) {
        city.removeEntity(x, y); 
    }
    
    // --------------------------- POLICY ------------------------------
    
    // Attiva una policy
    public void applyPolicy(Policy policy) {
        city.setActivePolicy(policy);
    }
    
    // Disattiva la policy attiva
    public void deactivatePolicy() {
        city.setActivePolicy(null);
    }
    
    // --------------------------- SIMULAZIONE ------------------------------
    
    // Avanza la simulazione di un tick
    public void nextTick() {
        tickEngine.advanceTick(city);
    }
    
    // Restituisce il tick corrente
    public int getCurrentTick() {
        return city.getCurrentTick();
    }

    // Resetta la simulazione allo stato iniziale
    public void resetCity() {
        city.reset();
    }
    
    // --------------------------- GETTER ------------------------------
    
    public City getCity() {
        return city;
    }
    
    public CityState getCityState() {
        return city.getState();
    }
    
    public CityGrid getGrid() {
        return city.getGrid();
    }

    public Policy getActivePolicy() {
        return city.getActivePolicy();
    }
    
    public String getCityName() {
        return city.getName();
    }

    // --------------------------- SALVATAGGI ------------------------------
    
    // Salva lo stato della città su file
    public boolean saveCity(String filepath) {
        return SaveLoad.save(city, filepath);
    }

    // Carica lo stato della città da file
    public boolean loadCity(String filepath) {
        City loadedCity = SaveLoad.load(filepath);
        if (loadedCity == null) {
            return false;
        }
        this.city = loadedCity;
        return true; 
    }
}
