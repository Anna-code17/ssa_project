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
        assertEquals("TestCity", city.getName());
        assertEquals(0, city.getCurrentTick());
    }
    
    @Test
    void testPlaceResidentialBuilding() {
        ResidentialBuilding building = new ResidentialBuilding();
        PowerPlant plant = new PowerPlant();
        
        // Prima posiziona una centrale elettrica
        city.placeEntity(2, 2, plant);
        
        // Poi posiziona l'edificio residenziale vicino
        boolean result = city.placeEntity(2, 3, building);
        
        assertTrue(result);
    }
    
    @Test
    void testPlaceBuildingWithoutPowerPlant() {
        ResidentialBuilding building = new ResidentialBuilding();
        
        // Posiziona senza centrale elettrica vicina
        boolean result = city.placeEntity(0, 0, building);
        
        assertFalse(result);
    }
    
    @Test
    void testIncrementTick() {
        int initialTick = city.getCurrentTick();
        city.incrementTick();
        assertEquals(initialTick + 1, city.getCurrentTick());
    }
}
