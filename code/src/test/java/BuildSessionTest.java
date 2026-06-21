import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

 /*Test per BuildSession.
   Verifica la gestione dello stato delle modalità costruzione e rimozione.*/

public class BuildSessionTest {
    
    private BuildSession session;
    
    @BeforeEach
    void setUp() {
        session = new BuildSession();
    }
    
    @Test
    void testInitialState() {
        //Verifica stato iniziale: inattivo, nessuna selezione
        assertFalse(session.isActive());
        assertFalse(session.isBuildMode());
        assertFalse(session.isRemoveMode());
        assertFalse(session.canConfirm());
        assertEquals(-1, session.getSelectedX());
        assertEquals(-1, session.getSelectedY());
        assertNull(session.getSelectedType());
    }
    
    @Test
    void testStartBuildSetsCorrectState() {
        //Verifica l'avvio della modalità Build: attiva, in attesa di selezione cella
        session.startBuild();
        
        assertTrue(session.isActive());
        assertTrue(session.isBuildMode());
        assertFalse(session.isRemoveMode());
        assertTrue(session.isWaitingForCellSelection());
        assertFalse(session.canConfirm());
        assertEquals(-1, session.getSelectedX());
        assertEquals(-1, session.getSelectedY());
        assertNull(session.getSelectedType());
    }
    
    @Test
    void testStartRemoveSetsCorrectState() {
        //Verifica modalità Remove: attiva, non in attesa di selezione cella
        session.startRemove();
        
        assertTrue(session.isActive());
        assertFalse(session.isBuildMode());
        assertTrue(session.isRemoveMode());
        assertFalse(session.isWaitingForCellSelection());
        assertEquals(-1, session.getSelectedX());
        assertEquals(-1, session.getSelectedY());
        assertNull(session.getSelectedType());
    }
    
    @Test
    void testSelectCell() {
        //Verifica che selezione cella memorizza coordinate e aggiorna stato
        session.startBuild();
        session.selectCell(2, 3);
        
        assertFalse(session.isWaitingForCellSelection());
        assertEquals(2, session.getSelectedX());
        assertEquals(3, session.getSelectedY());
    }
    
    @Test
    void testCanConfirmRequiresAllConditions() {
        //Verifica che canConfirm() è vero solo con build attivo + cella selezionata + tipo selezionato
        session.startBuild();
        assertFalse(session.canConfirm()); //  manca cella e tipo
        
        session.selectCell(2, 3);
        assertFalse(session.canConfirm()); // manca tipo
        
        session.setSelectedType("Park");
        assertTrue(session.canConfirm()); // tutto OK
    }
    
    @Test
    void testCancelResetsState() {
        // Setup sessione 
        session.startBuild();
        session.selectCell(2, 3);
        session.setSelectedType("Park");
        assertTrue(session.canConfirm());

        //Esegue il reset
        session.cancel();
        
        // Verifica reset
        assertFalse(session.isActive());
        assertEquals(-1, session.getSelectedX());
        assertEquals(-1, session.getSelectedY());
        assertNull(session.getSelectedType());
        assertFalse(session.canConfirm());
    }
}
