import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/*Test per CityGrid - gestione della griglia e delle celle.
 Verifica creazione, posizionamento, rimozione e validazione coordinate.*/

public class CityGridTest {
    
    private CityGrid grid;
    
    @BeforeEach
    void setUp() {
        grid = new CityGrid(5);         // Inizializza una città prima di ogni test
    }
    
    @Test
    void testGridCreation() {
        // Verifica lo stato iniziale della griglia appena creata
        assertNotNull(grid);
        assertEquals(5, grid.getSize());
        assertEquals(0, grid.getOccupiedCount());
    }
    
    @Test
    void testPlaceEntity() {
        // Posizionamento su cella libera: operazione consentita
        Park park = new Park();
        boolean result = grid.place(0, 0, park);
        
        assertTrue(result);
        assertEquals(1, grid.getOccupiedCount());
        assertFalse(grid.isEmpty(0, 0));
    }
    
    @Test
    void testPlaceEntityOnOccupiedCell() {
        // Una cella occupata non può contenere una seconda entità
        Park park1 = new Park();
        Park park2 = new Park();
        
        grid.place(0, 0, park1);
        boolean result = grid.place(0, 0, park2);
        
        assertFalse(result);
        assertEquals(1, grid.getOccupiedCount());
    }
    
    @Test
    void testRemoveEntity() {
        // La rimozione libera la cella e aggiorna il numero di celle occupate    
        Park park = new Park();
        grid.place(0, 0, park);
        grid.remove(0, 0);
        
        assertTrue(grid.isEmpty(0, 0));
        assertEquals(0, grid.getOccupiedCount());
    }

    @Test
    void testIsValidPosition(){
        // Accetta coordinate interne alla griglia e rifiuta quelle fuori dai limiti
        assertTrue(grid.isValidPosition(0, 0));
        assertTrue(grid.isValidPosition(4, 4));
        assertFalse(grid.isValidPosition(-1, 0));
        assertFalse(grid.isValidPosition(5, 5));
    }
}
