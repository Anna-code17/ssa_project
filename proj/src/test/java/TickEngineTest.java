import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TickEngineTest {
    
    private TickEngine tickEngine;
    private City city;
    
    @BeforeEach
    void setUp() {
        tickEngine = new TickEngine();
        city = new City("TestCity", 3);
    }
    
    @Test
    void testAdvanceTickWithNoEntities() {
        // Salva i valori iniziali
        int beforeBudget = city.getState().getBudget();
        int beforePopulation = city.getState().getPopulation();
        int beforePollution = city.getState().getPollution();
        int beforeHappiness = city.getState().getHappiness();
        
        tickEngine.advanceTick(city);
        
        // Senza entità, i valori non devono cambiare
        assertEquals(beforeBudget, city.getState().getBudget());
        assertEquals(beforePopulation, city.getState().getPopulation());
        assertEquals(beforePollution, city.getState().getPollution());
        assertEquals(beforeHappiness, city.getState().getHappiness());
    }
    
    @Test
    void testAdvanceTickWithPark() {
        Park park = new Park();
        city.placeEntity(0, 0, park);
        
        // Salva i valori iniziali
        int beforeHappiness = city.getState().getHappiness();
        int beforePollution = city.getState().getPollution();
        int beforeBudget = city.getState().getBudget();
        
        tickEngine.advanceTick(city);
        
        // Park dovrebbe aumentare felicità e ridurre inquinamento
        assertNotEquals(beforeHappiness, city.getState().getHappiness());
        assertNotEquals(beforePollution, city.getState().getPollution());
        
        // Verifica che felicità sia aumentata e inquinamento diminuito
        assertTrue(city.getState().getHappiness() > beforeHappiness);
        assertTrue(city.getState().getPollution() < beforePollution);
        
        // Budget potrebbe non cambiare con Park
        assertEquals(beforeBudget, city.getState().getBudget());
    }
    
    @Test
    void testAdvanceTickWithPowerPlant() {
        PowerPlant plant = new PowerPlant();
        city.placeEntity(0, 0, plant);
        
        int beforeBudget = city.getState().getBudget();
        
        tickEngine.advanceTick(city);
        
        // PowerPlant dovrebbe aumentare il budget
        assertTrue(city.getState().getBudget() > beforeBudget);
    }
    
    @Test
    void testAdvanceTickWithResidentialBuilding() {
        // Serve una centrale vicina per posizionare un residenziale
        PowerPlant plant = new PowerPlant();
        ResidentialBuilding house = new ResidentialBuilding();
        
        city.placeEntity(1, 1, plant);  // Centrale in posizione centrale
        city.placeEntity(1, 2, house);  // Casa vicina alla centrale
        
        int beforePopulation = city.getState().getPopulation();
        
        tickEngine.advanceTick(city);
        
        // ResidentialBuilding dovrebbe aumentare la popolazione
        assertTrue(city.getState().getPopulation() > beforePopulation);
    }
    
    @Test
    void testAdvanceTickWithCommercialBuilding() {
        CommercialBuilding commercial = new CommercialBuilding();
        city.placeEntity(0, 0, commercial);
        
        int beforeBudget = city.getState().getBudget();
        int beforeHappiness = city.getState().getHappiness();
        
        tickEngine.advanceTick(city);
        
        // CommercialBuilding dovrebbe aumentare budget e felicità
        assertTrue(city.getState().getBudget() > beforeBudget);
        assertTrue(city.getState().getHappiness() > beforeHappiness);
    }
    
    @Test
    void testAdvanceTickWithIndustrialBuilding() {
        IndustrialBuilding industrial = new IndustrialBuilding();
        city.placeEntity(0, 0, industrial);
        
        int beforeBudget = city.getState().getBudget();
        int beforePollution = city.getState().getPollution();
        
        tickEngine.advanceTick(city);
        
        // IndustrialBuilding aumenta budget ma anche inquinamento
        assertTrue(city.getState().getBudget() > beforeBudget);
        assertTrue(city.getState().getPollution() > beforePollution);
    }
    
    @Test
    void testAdvanceTickWithRoad() {
        Road road = new Road();
        city.placeEntity(0, 0, road);
        
        int beforeHappiness = city.getState().getHappiness();
        
        tickEngine.advanceTick(city);
        
        // Road dovrebbe aumentare la felicità
        assertTrue(city.getState().getHappiness() > beforeHappiness);
    }
    
    @Test
    void testAdvanceTickWithMultipleEntities() {
        Park park = new Park();
        PowerPlant plant = new PowerPlant();
        
        city.placeEntity(0, 0, park);
        city.placeEntity(0, 1, plant);
        
        int beforeBudget = city.getState().getBudget();
        int beforePollution = city.getState().getPollution();
        int beforeHappiness = city.getState().getHappiness();
        
        tickEngine.advanceTick(city);
        
        // Devono esserci cambiamenti in più metriche
        assertNotEquals(beforeBudget, city.getState().getBudget());
        assertNotEquals(beforePollution, city.getState().getPollution());
        assertNotEquals(beforeHappiness, city.getState().getHappiness());
    }
    
    @Test
    void testAdvanceTickIncreasesTickCounter() {
        int beforeTick = city.getCurrentTick();
        tickEngine.advanceTick(city);
        int afterTick = city.getCurrentTick();
        
        assertEquals(beforeTick + 1, afterTick);
    }
    
    @Test
    void testAdvanceTickWithEnvironmentalTaxPolicy() {
        // Posiziona un edificio che produce budget
        PowerPlant plant = new PowerPlant();
        city.placeEntity(0, 0, plant);
        
        // Attiva policy ambientale
        city.setActivePolicy(new EnvironmentalTaxPolicy());
        
        int beforeBudget = city.getState().getBudget();
        int beforePollution = city.getState().getPollution();
        
        tickEngine.advanceTick(city);
        
        // Policy ambientale riduce budget e inquinamento
        // Il budget potrebbe comunque aumentare, ma meno del normale
        // L'inquinamento dovrebbe essere ridotto
        assertTrue(city.getState().getPollution() < beforePollution + plant.getEffects().getPollution());
    }
    
    @Test
    void testAdvanceTickWithIndustrialExpansionPolicy() {
        // Posiziona un edificio industriale
        IndustrialBuilding industrial = new IndustrialBuilding();
        city.placeEntity(0, 0, industrial);
        
        // Attiva policy industriale
        city.setActivePolicy(new IndustrialExpansionPolicy());
        
        int beforeBudget = city.getState().getBudget();
        int beforePollution = city.getState().getPollution();
        
        tickEngine.advanceTick(city);
        
        // Policy industriale aumenta budget e inquinamento più del normale
        assertTrue(city.getState().getBudget() > beforeBudget);
        assertTrue(city.getState().getPollution() > beforePollution);
    }
}
