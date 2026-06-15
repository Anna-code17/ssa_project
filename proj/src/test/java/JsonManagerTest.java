import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class JsonManagerTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldSaveAndLoadCity() {

        City city = new City("TestCity", 10);

        Path file = tempDir.resolve("city.json");

        JsonManager.save(city, file.toString());

        City loaded = JsonManager.load(file.toString(), City.class);

        assertNotNull(loaded);
        assertEquals("TestCity", loaded.getName());
    }

    @Test
    void shouldThrowExceptionWhenFileDoesNotExist() {

        assertThrows(RuntimeException.class, () -> {
            JsonManager.load("fake_file.json", City.class);
        });
    }

    @Test
    void shouldLoadFromResources() {

        City city = JsonManager.loadFromResources(
            "city.json",
            City.class
        );

        assertNotNull(city);
        assertEquals("Roma", city.getName());
    }

    @Test
    void shouldPersistGridEntity() {

        City city = new City("GridCity", 10);

        city.placeBuilding(1, 1, new ResidentialBuilding());

        Path file = tempDir.resolve("grid.json");

        JsonManager.save(city, file.toString());

        City loaded = JsonManager.load(file.toString(), City.class);

        assertNotNull(
            loaded.getGrid().getCell(1, 1).getEntity()
        );
    }
}
