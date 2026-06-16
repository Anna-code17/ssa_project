import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class BuildSessionTest {
    
    private BuildSession session;
    
    @BeforeEach
    void setUp() {
        session = new BuildSession();
    }
    
    @Test
    void testInitialState() {
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
        session.startBuild();
        session.selectCell(2, 3);
        
        assertFalse(session.isWaitingForCellSelection());
        assertEquals(2, session.getSelectedX());
        assertEquals(3, session.getSelectedY());
    }
    
    @Test
    void testCanConfirmRequiresAllConditions() {
        session.startBuild();
        assertFalse(session.canConfirm()); // no cell, no type
        
        session.selectCell(2, 3);
        assertFalse(session.canConfirm()); // no type
        
        session.setSelectedType("Park");
        assertTrue(session.canConfirm()); // tutto OK
    }
    
    @Test
    void testCancelResetsState() {
        session.startBuild();
        session.selectCell(2, 3);
        session.setSelectedType("Park");
        
        assertTrue(session.canConfirm());
        
        session.cancel();
        
        assertFalse(session.isActive());
        assertEquals(-1, session.getSelectedX());
        assertEquals(-1, session.getSelectedY());
        assertNull(session.getSelectedType());
        assertFalse(session.canConfirm());
    }
}