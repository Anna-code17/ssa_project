import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CityTest {
    
    private City city;
    
    @BeforeEach
    void setUp() {
        city = new City("TestCity", 6);  // Inizializza una città prima di ogni test

    }
    
