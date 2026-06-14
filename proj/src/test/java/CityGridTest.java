import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CityGridTest {
    
    private CityGrid grid;
    
    @BeforeEach
    void setUp() {
        grid = new CityGrid(5);         // Inizializza una città prima di ogni test
    }
    
    @Test
    void testGridCreation() {
        assertNotNull(grid);
        assertEquals(5, grid.getSize());
        assertEquals(0, grid.getOccupiedCount());
    }
    
    @Test
    void testPlaceEntity() {
        Park park = new Park();
        boolean result = grid.place(0, 0, park);
        
        assertTrue(result);
        assertEquals(1, grid.getOccupiedCount());
        assertFalse(grid.isEmpty(0, 0));
    }
    
    @Test
    void testPlaceEntityOnOccupiedCell() {
        Park park1 = new Park();
        Park park2 = new Park();
        
        grid.place(0, 0, park1);
        boolean result = grid.place(0, 0, park2);
        
        assertFalse(result);
        assertEquals(1, grid.getOccupiedCount());
    }
    
    @Test
    void testRemoveEntity() {
        Park park = new Park();
        grid.place(0, 0, park);
        grid.remove(0, 0);
        
        assertTrue(grid.isEmpty(0, 0));
        assertEquals(0, grid.getOccupiedCount());
    }
    
    @Test
    void testIsValidPosition() {
        assertTrue(grid.isValidPosition(0, 0));
        assertTrue(grid.isValidPosition(4, 4));
        assertFalse(grid.isValidPosition(-1, 0));
        assertFalse(grid.isValidPosition(5, 5));
    }
}
