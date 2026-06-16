import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CityStateTest {
    
    @Test
    void testConstructor() {
        CityState state = new CityState(1000);
        
        assertEquals(1000, state.getBudget());
        assertEquals(0, state.getPopulation());
        assertEquals(0, state.getPollution());
        assertEquals(0, state.getHappiness());
    }
    
    @Test
    void testApplyEffects() {
        CityState state = new CityState(1000);
        Effect effect = new Effect(-50, 10, 15, -5);
        
        state.applyEffects(effect);
        
        assertEquals(950, state.getBudget());    // 1000 - 50
        assertEquals(10, state.getPopulation());
        assertEquals(15, state.getPollution());
        assertEquals(-5, state.getHappiness());
    }
    
    @Test
    void testApplyEffectsWithNull() {
        CityState state = new CityState(1000);
        
        state.applyEffects(null);
        
        assertEquals(1000, state.getBudget());
        assertEquals(0, state.getPopulation());
        assertEquals(0, state.getPollution());
        assertEquals(0, state.getHappiness());
    }

    @Test
    void testApplyEffectsMultipleTimes() {
        CityState state = new CityState(1000);

        state.applyEffects(new Effect(-50, 10, 15, -5));
        state.applyEffects(new Effect(-20, 5, 10, 2));

        assertEquals(930, state.getBudget());
        assertEquals(15, state.getPopulation());
        assertEquals(25, state.getPollution());
        assertEquals(-3, state.getHappiness());
    }
    
    @Test
    void testClear() {
        CityState state = new CityState(1000);
        state.applyEffects(new Effect(-50, 10, 15, -5));
        
        state.clear();
        
        assertEquals(1000, state.getBudget());
        assertEquals(0, state.getPopulation());
        assertEquals(0, state.getPollution());
        assertEquals(0, state.getHappiness());
    }
}