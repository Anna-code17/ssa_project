import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CityTest {
    
    private City city;
    
    @BeforeEach
    void setUp() {
        // Inizializza una città prima di ogni test
        city = new City("TestCity", 5);
    }
    
    @Test
    void testCityCreation() {
        assertNotNull(city);
        assertNotNull(city.getGrid());
        assertNotNull(city.getState());
        assertEquals("TestCity", city.getName());
        assertEquals(0, city.getCurrentTick());
    }
    
    @Test
    void testBudgetScalesWithGridSize() {
        // verifica formula budget iniziale
        // Griglia 3x3: 100000 * 9 / 400 = 2250
        City city3 = new City("Test", 3);
        assertEquals(2250, city3.getState().getBudget());
        
        // Griglia 5x5: 100000 * 25 / 400 = 6250
        City city5 = new City("Test", 5);
        assertEquals(6250, city5.getState().getBudget());
        
        // Griglia 17x17: 100000 * 289 / 400 = 72250
        City city17 = new City("Test", 17);
        assertEquals(72250, city17.getState().getBudget());
    }

    @Test
    void testReset() {
        City city = new City("Test", 5);
        TickEngine tickEngine = new TickEngine();

        // Posiziona alcune entità e attiva una policy
        city.placeEntity(0, 0, new PowerPlant());
        city.placeEntity(1, 2, new IndustrialBuilding());
        city.placeEntity(1, 1, new ResidentialBuilding()); // population=5
        city.setActivePolicy(new EnvironmentalTaxPolicy());

        
        // Avanza di 2 tick per avere effetti applicati
        tickEngine.advanceTick(city);
        tickEngine.advanceTick(city);

        // Verifica che la città sia cambiata
        assertNotEquals(0, city.getState().getPopulation());
        assertNotEquals(0, city.getState().getPollution());
        assertNotEquals(0, city.getState().getHappiness());
        assertNotEquals(6250, city.getState().getBudget()); // budget iniziale per città 5x5 = 6250

        // Esegue il reset della città
        city.reset();
        
           // Verifica reset
        assertEquals(0, city.getGrid().getOccupiedCount());
        assertNull(city.getActivePolicy());
        assertEquals(0, city.getCurrentTick());
        assertEquals(6250, city.getState().getBudget()); // budget iniziale per 5x5
        assertEquals(0, city.getState().getPopulation());
        assertEquals(0, city.getState().getPollution());
        assertEquals(0, city.getState().getHappiness());
    }

    @Test
    void testDeactivatePolicy() {
        City city = new City("Test", 5);
        
        city.setActivePolicy(new EnvironmentalTaxPolicy());
        assertNotNull(city.getActivePolicy());
        
        city.setActivePolicy(null);
        assertNull(city.getActivePolicy());
   }

    @Test
    void testPlacement() {
        // Testa il flusso: City → PlacementRules → CityGrid → Cell

        City city = new City("Test", 5);
        int initialBudget = city.getState().getBudget();

        // Posiziona delle entità
        PowerPlant plant = new PowerPlant();
        ResidentialBuilding house = new ResidentialBuilding();
        city.placeEntity(2, 2, plant);
        city.placeEntity(2, 3, house);

        // Verifica che l'entità sia effettivamente nella griglia
        assertFalse(city.getGrid().isEmpty(2, 2));
        assertFalse(city.getGrid().isEmpty(2, 3));
        
        // Verifica che il buildCost sia stato applicato
       int expectedBudget = initialBudget + plant.getEffects().getBuildCost()+ house.getEffects().getBuildCost();

        assertEquals(expectedBudget, city.getState().getBudget());
    }
}
