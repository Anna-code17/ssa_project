import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/*Test per PlacementRules.
  Verifica le regole di posizionamento delle entità:
  - restrizioni per edifici residenziali (vicinanza a una centrale)
  - libertà di posizionamento per le altre entità */

public class PlacementRulesTest {

    private City city;

    @BeforeEach
    void setUp() {
        city = new City("Test", 5);
    }

    // Test su Residential Buildings: devono essere posizionati entro 2 celle da una PowerPlant

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

        // Caso limite: casa esattamente alla distanza massima consentita dalla centrale
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
        //Verifica il posizionamento ai bordi della griglia: deve funzionare
        city.placeEntity(0, 0, new PowerPlant());

        assertTrue(city.placeEntity(0, 1, new ResidentialBuilding()));
        assertTrue(city.placeEntity(1, 0, new ResidentialBuilding()));
        assertTrue(city.placeEntity(1, 1, new ResidentialBuilding()));
    }

    @Test
    void testOutOfBoundsPlacement() {
        // Coordinate fuori dalla griglia: il posizionamento deve essere rifiutato
        assertFalse(city.placeEntity(-1, 0, new Park()));
        assertFalse(city.placeEntity(0, -1, new Park()));
        assertFalse(city.placeEntity(5, 0, new Park()));
        assertFalse(city.placeEntity(0, 5, new Park()));
    }

}
