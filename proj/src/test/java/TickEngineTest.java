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
        
        // Setto i valori iniziali
        int beforeBudget = city.getState().getBudget();
        int beforePollution = city.getState().getPollution();
        int beforeHappiness = city.getState().getHappiness();
        
        tickEngine.advanceTick(city);
        
        // budget=-150, population=0, pollution=-15, happiness=20
        assertEquals(beforeBudget - 150, city.getState().getBudget());
        assertEquals(0, city.getState().getPopulation());
        assertEquals(beforePollution - 15, city.getState().getPollution());
        assertEquals(beforeHappiness + 20, city.getState().getHappiness());
    }
    
    @Test
    void testAdvanceTickWithPowerPlant() {
        PowerPlant plant = new PowerPlant();
        city.placeEntity(0, 0, plant);
        
       // Setto i valori iniziali
        int beforeBudget = city.getState().getBudget();
        int beforePollution = city.getState().getPollution();
        int beforeHappiness = city.getState().getHappiness();
        
        tickEngine.advanceTick(city);

        // budget=-400, population=0, pollution=15, happiness=-5
        assertEquals(beforeBudget - 400, city.getState().getBudget());
        assertEquals(0, city.getState().getPopulation());
        assertEquals(beforePollution + 15, city.getState().getPollution());
        assertEquals(beforeHappiness - 5, city.getState().getHappiness());
    }
    
    @Test
    void testAdvanceTickWithResidentialBuilding() {
        // Serve una centrale vicina per posizionare un residenziale
        PowerPlant plant = new PowerPlant();
        ResidentialBuilding house = new ResidentialBuilding();
        
        city.placeEntity(1, 1, plant);  // Centrale in posizione centrale
        city.placeEntity(1, 2, house);  // Casa vicina alla centrale
        
       // Setto i valori iniziali
        int beforeBudget = city.getState().getBudget();
        int beforePollution = city.getState().getPollution();
        int beforeHappiness = city.getState().getHappiness();
        
        tickEngine.advanceTick(city);
        
        // ResidentialBuilding: budget=-100, population=0, pollution=5, happiness=8
        // PowerPlant : budget=-400, population=0, pollution=15, happiness=-5
        // Somma degli effetti:  budget= -500, pollution= +20, happiness= +3
        assertEquals(beforeBudget - 500, city.getState().getBudget());
        assertEquals(beforePollution + 20, city.getState().getPollution());
        assertEquals(beforeHappiness + 3, city.getState().getHappiness());
    }
    
    @Test
    void testAdvanceTickWithCommercialBuilding() {
        CommercialBuilding commercial = new CommercialBuilding();
        city.placeEntity(0, 0, commercial);
        
        // Setto i valori iniziali
        int beforeBudget = city.getState().getBudget();
        int beforePollution = city.getState().getPollution();
        int beforeHappiness = city.getState().getHappiness();
        
        tickEngine.advanceTick(city);
        
        // budget=-180, population=0, pollution=9, happiness=5
        assertEquals(beforeBudget - 180, city.getState().getBudget());
        assertEquals(0, city.getState().getPopulation());
        assertEquals(beforePollution + 9, city.getState().getPollution());
        assertEquals(beforeHappiness + 5, city.getState().getHappiness());
    }
    
    @Test
    void testAdvanceTickWithIndustrialBuilding() {
        IndustrialBuilding industrial = new IndustrialBuilding();
        city.placeEntity(0, 0, industrial);
        
        // Setto i valori iniziali
        int beforeBudget = city.getState().getBudget();
        int beforePollution = city.getState().getPollution();
        int beforeHappiness = city.getState().getHappiness();
        
        tickEngine.advanceTick(city);
        
        // budget=-250, population=0, pollution=20, happiness=-10
        assertEquals(beforeBudget - 250, city.getState().getBudget());
        assertEquals(0, city.getState().getPopulation());
        assertEquals(beforePollution + 20, city.getState().getPollution());
        assertEquals(beforeHappiness - 10, city.getState().getHappiness());
    }
    
    @Test
    void testAdvanceTickWithRoad() {
        Road road = new Road();
        city.placeEntity(0, 0, road);
        
       // Setto i valori iniziali
        int beforeBudget = city.getState().getBudget();
        int beforePollution = city.getState().getPollution();
        int beforeHappiness = city.getState().getHappiness();
                
        tickEngine.advanceTick(city);
        
        // road.json: budget=-20, population=0, pollution=0, happiness=2
  
        assertEquals(beforeBudget - 20, city.getState().getBudget());
        assertEquals(0, city.getState().getPopulation());
        assertEquals(0, city.getState().getPollution());
        assertEquals(beforeHappiness + 2, city.getState().getHappiness());    }
    
    @Test
    void testAdvanceTickWithMultipleEntities() {
        Park park = new Park();
        PowerPlant plant = new PowerPlant();
        
        city.placeEntity(0, 0, park);
        city.placeEntity(0, 1, plant);
        
        // Setto i valori iniziali
        int beforeBudget = city.getState().getBudget();
        int beforePollution = city.getState().getPollution();
        int beforeHappiness = city.getState().getHappiness();     
        
        tickEngine.advanceTick(city);

        // Park: budget=-150, population=0, pollution=-15, happiness=20
        // PowerPlant: budget=-400, population=0, pollution=15, happiness=-5
        // Somma degli effetti: budget=-550, pollution=0, happiness=+15
        assertEquals(beforeBudget - 550, city.getState().getBudget());
        assertEquals(0, city.getState().getPopulation());
        assertEquals(beforePollution + 0, city.getState().getPollution());
        assertEquals(beforeHappiness + 15, city.getState().getHappiness());
    }
    
    @Test
    void testAdvanceTickIncreasesTickCounter() {
        int beforeTick = city.getCurrentTick();
        tickEngine.advanceTick(city);
        assertEquals(beforeTick + 1, city.getCurrentTick());
    }
    
    @Test
    void testAdvanceTickWithEnvironmentalTaxPolicy() {
        // Posiziona un edificio per avere degli effetti attivi
        PowerPlant plant = new PowerPlant();
        city.placeEntity(0, 0, plant);
        
        // Attiva policy ambientale
        city.setActivePolicy(new EnvironmentalTaxPolicy());
        
        // Setto i valori iniziali
        int beforeBudget = city.getState().getBudget();
        int beforePollution = city.getState().getPollution();
        int beforeHappiness = city.getState().getHappiness();
        
        tickEngine.advanceTick(city);
        
        //Calcolo l'effetto di powerPlant da solo
        // PowerPlant: budget=-400, pollution=15, happiness=-5
        int basePollution = beforePollution + 15;
        int baseHappiness = beforeHappiness - 5;
        int baseBudget = beforeBudget - 400;

        //calcolo la modifica legata all'azione della policy
        // EnvironmentalTaxPolicy: budget=-15%, pollution=-15%, happiness=+10%
        int expectedBudget = (int)(baseBudget * 0.85);
        int expectedPollution = (int)(basePollution * 0.85);
        int expectedHappiness = (int)(baseHappiness * 1.1);
        
        assertEquals(expectedBudget, city.getState().getBudget());
        assertEquals(0, city.getState().getPopulation());  
        assertEquals(expectedPollution, city.getState().getPollution());
        assertEquals(expectedHappiness, city.getState().getHappiness());    }
    
    @Test
    void testAdvanceTickWithIndustrialExpansionPolicy() {
        // Posiziona un edificio industriale
        IndustrialBuilding industrial = new IndustrialBuilding();
        city.placeEntity(0, 0, industrial);
        
        // Attiva policy industriale
        city.setActivePolicy(new IndustrialExpansionPolicy());
         
        // Setto i valori iniziali
        int beforeBudget = city.getState().getBudget();
        int beforePollution = city.getState().getPollution();
        int beforeHappiness = city.getState().getHappiness();   
        
        tickEngine.advanceTick(city);

        //calcolo la modifica legata all'azione della policy
        // industrialBuilding: budget=-250, pollution=20, happiness=-10
        int baseBudget = beforeBudget - 250;
        int basePollution = beforePollution + 20;
        int baseHappiness = beforeHappiness - 10;
        
        // IndustrialExpansionPolicy: budget=+10%, pollution=+15%, happiness=-5%
        //calcolo la modifica legata all'azione della policy
        int expectedBudget = (int)(baseBudget * 1.10);
        int expectedPollution = (int)(basePollution * 1.15);
        int expectedHappiness = (int)(baseHappiness * 0.95);
        
        assertEquals(expectedBudget, city.getState().getBudget());
        assertEquals(0, city.getState().getPopulation());  
        assertEquals(expectedPollution, city.getState().getPollution());
        assertEquals(expectedHappiness, city.getState().getHappiness());
    }
}
