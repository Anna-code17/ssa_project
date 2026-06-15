import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class PlacementRulesTest {

    private City city;

    @BeforeEach
    void setUp() {
        city = new City("Test", 5);
    }

    // Test su Residential Buildings

    @Test
    void testResidentialWithoutPowerPlant() {
        assertFalse(city.placeEntity(2, 2, new ResidentialBuilding()));
    }

    @Test
    void testResidentialNearPowerPlant() {
        city.placeEntity(2, 2, new PowerPlant());

        assertTrue(city.placeEntity(2, 3, new ResidentialBuilding()));
    }

    @Test
    void testResidentialTooFarFromPowerPlant() {
        city.placeEntity(0, 0, new PowerPlant());

        // Distanza 3 (oltre il limite)
        assertFalse(city.placeEntity(4, 4, new ResidentialBuilding()));
    }

    @Test
    void testResidentialAtMaxDistance() {
        city.placeEntity(2, 2, new PowerPlant());

        // distanza massima consentita
        assertTrue(city.placeEntity(2, 4, new ResidentialBuilding()));
    }

    @Test
    void testResidentialOnOccupiedCell() {
        city.placeEntity(2, 2, new PowerPlant());
        city.placeEntity(2, 3, new Park());  // Cella già occupata
        assertFalse(city.placeEntity(2, 3, new ResidentialBuilding()));
    }

    // Test su entities senza restrizioni di posizionamento

    @Test
    void testNonRestrictedEntities() {
        assertTrue(city.placeEntity(0, 0, new CommercialBuilding()));
        assertTrue(city.placeEntity(1, 1, new IndustrialBuilding()));
        assertTrue(city.placeEntity(2, 2, new Park()));
        assertTrue(city.placeEntity(3, 3, new PowerPlant()));
        assertTrue(city.placeEntity(4, 4, new Road()));
    }

    // Test casi limite

    @Test
    void testPlacementAtBoundary() {
        city.placeEntity(0, 0, new PowerPlant());

        assertTrue(city.placeEntity(0, 1, new ResidentialBuilding()));
        assertTrue(city.placeEntity(1, 0, new ResidentialBuilding()));
        assertTrue(city.placeEntity(1, 1, new ResidentialBuilding()));
    }

    @Test
    void testOutOfBoundsPlacement() {
        // Il metodo placeEntity dovrebbe gestire coordinate fuori range
        assertFalse(city.placeEntity(-1, 0, new Park()));
        assertFalse(city.placeEntity(0, -1, new Park()));
        assertFalse(city.placeEntity(5, 0, new Park()));
        assertFalse(city.placeEntity(0, 5, new Park()));
    }

}
