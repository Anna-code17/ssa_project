import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/* Test per TickEngine.
   Verifica l'avanzamento dei tick, gli effetti delle entità e l'applicazione delle policy.*/

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
    
    // Metodo helper per testare solo entità singole 
    // Verifica: buildCost (applicato al posizionamento) e effetti ricorrenti (applicati ad ogni tick)
    private void testSingleEntity(PlaceableEntity entity, int x, int y) {
        
        // Setto i valori iniziali
        int beforeBudget = city.getState().getBudget();

        city.placeEntity(x, y, entity); //lo aggiungo dopo aver salvato il budget iniziale perché buildCost viene scalato 
        //nel momento in cui piazzo l'entità

        int beforePollution = city.getState().getPollution();
        int beforeHappiness = city.getState().getHappiness();
        int beforePopulation = city.getState().getPopulation();
        
        // Verifica effetto iniziale di buildCost 
        int budgetAfterCost = beforeBudget + entity.getEffects().getBuildCost();
        assertEquals(budgetAfterCost, city.getState().getBudget());  
    
        // Verifica effetti ricorrenti
        tickEngine.advanceTick(city);
        
        assertEquals(budgetAfterCost + entity.getEffects().getBudget(), city.getState().getBudget());
        assertEquals(beforePopulation + entity.getEffects().getPopulation(), city.getState().getPopulation());
        assertEquals(beforePollution + entity.getEffects().getPollution(), city.getState().getPollution());
        assertEquals(beforeHappiness + entity.getEffects().getHappiness(), city.getState().getHappiness());
    }
    
    @Test
    void testAdvanceTickWithPark() {
        Park park = new Park();
        
        testSingleEntity(park, 0, 0);
    }
    
    @Test
    void testAdvanceTickWithPowerPlant() {
        PowerPlant plant = new PowerPlant();
        
        testSingleEntity(plant, 0, 0);
    }
    
    
    @Test
    void testAdvanceTickWithCommercialBuilding() {
        CommercialBuilding commercial = new CommercialBuilding();
        
        testSingleEntity(commercial, 0, 0);
    }
    
    @Test
    void testAdvanceTickWithIndustrialBuilding() {
        IndustrialBuilding industrial = new IndustrialBuilding();
        
        testSingleEntity(industrial, 0, 0);
    }
    
    @Test
    void testAdvanceTickWithRoad() {
        Road road = new Road();
        
        testSingleEntity(road, 0, 0);
    }

    @Test
    void testAdvanceTickWithResidentialBuilding() {
        // Serve una centrale vicina per posizionare un residenziale
        PowerPlant plant = new PowerPlant();
        ResidentialBuilding house = new ResidentialBuilding();
        
        // Setto i valori iniziali
        int beforeBudget = city.getState().getBudget();

        // aggiungo le entità dopo aver salvato il budget iniziale perché buildCost viene scalato 
        //nel momento in cui le piazzo 
        city.placeEntity(1, 1, plant);  // Centrale in posizione centrale
        city.placeEntity(1, 2, house);  // Casa vicina alla centrale

        int beforePollution = city.getState().getPollution();
        int beforeHappiness = city.getState().getHappiness();
        int beforePopulation = city.getState().getPopulation();
        
        // Verifica effetti iniziali dei buildCost 
        int budgetAfterCost = beforeBudget + plant.getEffects().getBuildCost() + house.getEffects().getBuildCost();
        assertEquals(budgetAfterCost, city.getState().getBudget());  
    
        // Verifica effetti ricorrenti
        tickEngine.advanceTick(city);
        
        assertEquals(budgetAfterCost + plant.getEffects().getBudget() + house.getEffects().getBudget(), city.getState().getBudget());
        assertEquals(beforePopulation + plant.getEffects().getPopulation() + house.getEffects().getPopulation(), city.getState().getPopulation());
        assertEquals(beforePollution + plant.getEffects().getPollution() + house.getEffects().getPollution(), city.getState().getPollution());
        assertEquals(beforeHappiness + plant.getEffects().getHappiness() + house.getEffects().getHappiness(), city.getState().getHappiness());
    }
    
    @Test
    void testAdvanceTickWithMultipleEntities() {
        IndustrialBuilding industrial = new IndustrialBuilding();
        PowerPlant plant = new PowerPlant();
        
        // Setto i valori iniziali
        int beforeBudget = city.getState().getBudget();

        // aggiungo le entità dopo aver salvato il budget iniziale perché buildCost viene scalato 
        //nel momento in cui le piazzo 
        city.placeEntity(0, 0, industrial);
        city.placeEntity(0, 1, plant);

        int beforePollution = city.getState().getPollution();
        int beforeHappiness = city.getState().getHappiness();
        int beforePopulation = city.getState().getPopulation();
        
        // Verifica effetti iniziali dei buildCost 
        int budgetAfterCost = beforeBudget + plant.getEffects().getBuildCost() + industrial.getEffects().getBuildCost();
        assertEquals(budgetAfterCost, city.getState().getBudget());  
    
        // Verifica effetti ricorrenti
        tickEngine.advanceTick(city);
        
        assertEquals(budgetAfterCost + plant.getEffects().getBudget() + industrial.getEffects().getBudget(), city.getState().getBudget());
        assertEquals(beforePopulation + plant.getEffects().getPopulation() + industrial.getEffects().getPopulation(), city.getState().getPopulation());
        assertEquals(beforePollution + plant.getEffects().getPollution() + industrial.getEffects().getPollution(), city.getState().getPollution());
        assertEquals(beforeHappiness + plant.getEffects().getHappiness() + industrial.getEffects().getHappiness(), city.getState().getHappiness());
    }
    
    @Test
    void testAdvanceTickIncreasesTick() {
        int beforeTick = city.getCurrentTick();
        tickEngine.advanceTick(city);
        assertEquals(beforeTick + 1, city.getCurrentTick());
    }
        
    @Test
    void testBuildCostNotAppliedOnTick() {
    //Verifics che buildCost viene applicato solo al posizionamento, non nei tick successivi
        City city = new City("Test", 3);
        Park park = new Park(); // buildCost = -200, budget = -50
        
        city.placeEntity(0, 0, park);
        int afterBuild = city.getState().getBudget(); // 2250 - 200 = 2050
        
        tickEngine.advanceTick(city);
        
        // buildCost non deve essere applicato di nuovo
        // Solo effetto ricorrente del Park: budget=-50
        assertEquals(afterBuild - 50, city.getState().getBudget());
    }

    // Verifica che la policy ambientale modifichi correttamente gli effetti
    @Test
    void testAdvanceTickWithEnvironmentalTaxPolicy() {
        // PowerPlant: budget=-50, pollution=15, happiness=-5, buildCost = -400
        //Budget iniziale per città 3x3: 2250
        PowerPlant plant = new PowerPlant();
        city.placeEntity(0, 0, plant); //viene tolto dal Budget il builCost : Budget = 2250 - 400 = 1850
        city.setActivePolicy(new EnvironmentalTaxPolicy());
        
        tickEngine.advanceTick(city);
        
        /*Calcolo la modifica legata all'azione della policy: 
        EnvironmentalTaxPolicy: budget=-15%, pollution=-15%, happiness=+10%, population = 0%
        # Budget: 1850 + (-50 + [-50 * 0.15]) = 1793
        # Pollution: 0 + (15 + [-15 * 0.15]) = 13
        # Happiness: 0 + (-5 + [-5 * 0.1]) = -5 
        nel calcolo della percentuale appositamente indicato tra parentesi [] si applica il troncamento alla parte intera 
        del risultato, così come codificato nel metodo applyPolicyPercent() di TicketEngine*/
        assertEquals(1793, city.getState().getBudget());
        assertEquals(13, city.getState().getPollution());
        assertEquals(-5, city.getState().getHappiness());
    }

    // Verifica che la policy industriale modifichi correttamente gli effetti
    @Test
    void testAdvanceTickWithIndustrialExpansionPolicy() {
        // IndustrialBuilding: budget=110, pollution=20, happiness=-10, buildCost = -250                
        //Budget iniziale per città 3x3: 2250

        IndustrialBuilding industrial = new IndustrialBuilding();
        city.placeEntity(0, 0, industrial); //viene tolto dal Budget il builCost : Budget = 2250 - 250 = 2000
        city.setActivePolicy(new IndustrialExpansionPolicy());
        
        tickEngine.advanceTick(city);
        
        /*Calcolo la modifica legata all'azione della policy: 
        IndustrialExpansionPolicy: budget=+10%, pollution=+15%, happiness=-5%, population = 0%  
        # Budget: 2000 + (110 + [110 * 0.10]) = 2121
        # Pollution: 0 + (20 + [20 * 0.15]) = 23
        # Happiness: 0 + (-10 + [-10* 0.05]) = -10 
         nel calcolo della percentuale appositamente indicato tra parentesi [] si applica il troncamento alla parte intera 
        del risultato, così come codificato nel metodo applyPolicyPercent() di TicketEngine*/
        assertEquals(2121, city.getState().getBudget());
        assertEquals(23, city.getState().getPollution());
        assertEquals(-10, city.getState().getHappiness());
    }
}
   
