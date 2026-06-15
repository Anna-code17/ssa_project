import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class SaveManagerTest {

    @TempDir
    Path tempDir;

    @Test
    void saveShouldReturnTrue() {

        SaveManager saveManager = new SaveManager();

        City city = new City("SaveCity", 10);

        Path file = tempDir.resolve("save.json");

        boolean result = saveManager.save(city, file.toString());

        assertTrue(result);
    }

    @Test
    void loadShouldReturnCity() {

        SaveManager saveManager = new SaveManager();

        City city = new City("LoadCity", 10);

        Path file = tempDir.resolve("load.json");

        saveManager.save(city, file.toString());

        City loaded = saveManager.load(file.toString());

        assertNotNull(loaded);
        assertEquals("LoadCity", loaded.getName());
    }
}
