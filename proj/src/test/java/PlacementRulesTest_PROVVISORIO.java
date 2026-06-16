/*import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class PlacementRulesTest {
    
    private City city;
    
    @BeforeEach
    void setUp() {
        city = new City("Test", 5);
    }
    
    // Terst su Residential Buildings
    
    @Test
    void testResidentialBuildingWithoutPowerPlantFails() {
        assertFalse(city.placeEntity(2, 2, new ResidentialBuilding()));
    }
    
    @Test
    void testResidentialBuildingWithPowerPlantNearbySucceeds() {
        city.placeEntity(2, 2, new PowerPlant());
        assertTrue(city.placeEntity(2, 3, new ResidentialBuilding()));
    }
    
    @Test
    void testResidentialBuildingWithPowerPlantTooFarFails() {
        city.placeEntity(0, 0, new PowerPlant());
        assertFalse(city.placeEntity(4, 4, new ResidentialBuilding()));
    }
    
    @Test
    void testResidentialBuildingAtMaxDistanceSucceeds() {
        city.placeEntity(2, 2, new PowerPlant());
        // Distanza 2 (esattamente al limite)
        assertTrue(city.placeEntity(2, 4, new ResidentialBuilding()));
    }
    
    @Test
    void testResidentialBuildingBeyondMaxDistanceFails() {
        city.placeEntity(2, 2, new PowerPlant());
        // Distanza 3 (oltre il limite)
        assertFalse(city.placeEntity(2, 5, new ResidentialBuilding()));
    }
    
    @Test
    void testResidentialBuildingInAllDirectionsFromPowerPlant() {
        city.placeEntity(2, 2, new PowerPlant());
        
        // Nord
        assertTrue(city.placeEntity(2, 0, new ResidentialBuilding()));
        // Sud
        assertTrue(city.placeEntity(2, 4, new ResidentialBuilding()));
        // Est
        assertTrue(city.placeEntity(4, 2, new ResidentialBuilding()));
        // Ovest
        assertTrue(city.placeEntity(0, 2, new ResidentialBuilding()));
        // Diagonale
        assertTrue(city.placeEntity(0, 0, new ResidentialBuilding()));
    }
    
    @Test
    void testResidentialBuildingOnOccupiedCellFails() {
        city.placeEntity(2, 2, new PowerPlant());
        city.placeEntity(2, 3, new Park());  // Cella già occupata
        assertFalse(city.placeEntity(2, 3, new ResidentialBuilding()));
    }

    // Test su Commercial e Industrial Buildings e Infrastructures
    
    @Test
    @Test
  void testNonRestrictedBuildingPlacement() {
        assertTrue(city.placeEntity(0, 0, new CommercialBuilding()));
        assertTrue(city.placeEntity(1, 1, new IndustrialBuilding()));
        assertTrue(city.placeEntity(2, 2, new Park()));
        assertTrue(city.placeEntity(3, 3, new PowerPlant()));
        assertTrue(city.placeEntity(4, 4, new Road()));
    }
  
    // Test casi limite
    
    @Test
    void testPlacementAtGridBoundaries() {
        city.placeEntity(0, 0, new PowerPlant());  // angolo
        assertTrue(city.placeEntity(0, 1, new ResidentialBuilding()));  // adiacente
        assertTrue(city.placeEntity(1, 0, new ResidentialBuilding()));  // adiacente
        assertTrue(city.placeEntity(1, 1, new ResidentialBuilding()));  // diagonale
    }
    
    @Test
    void testPlacementOutOfBounds() {
        // Il metodo placeEntity dovrebbe gestire coordinate fuori range
        assertFalse(city.placeEntity(-1, 0, new Park()));
        assertFalse(city.placeEntity(0, -1, new Park()));
        assertFalse(city.placeEntity(5, 0, new Park()));
        assertFalse(city.placeEntity(0, 5, new Park()));
    }
}
DA RIVEDERE!!!!!

*/ 
