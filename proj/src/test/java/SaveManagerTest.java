import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class SaveManagerTest {

    @TempDir
    Path tempDir;

    //controllo se effettivamente il sistema è in grado di generare un file della citta' salvata
    @Test
    void saveShouldReturnTrue() {

        SaveManager saveManager = new SaveManager();

        City city = new City("SaveCity", 10);

        Path file = tempDir.resolve("save.json");

        boolean result = saveManager.save(city, file.toString());

        assertTrue(result);
    }

    // controllo se effettivamente il sistema e' in grado di caricare un file di una citta' salvata in precedenza, non ritornando null come valore.  
    @Test
    void loadShouldReturnCity() {

        SaveManager saveManager = new SaveManager();

        City city = new City("LoadCity", 10);

        Path file = tempDir.resolve("load.json");

        saveManager.save(city, file.toString());

        City loaded = saveManager.load(file.toString());

        assertNotNull(loaded);
    }
}
