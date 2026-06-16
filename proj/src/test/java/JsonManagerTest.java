import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.*;

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
        assertEquals("MyCity", city.getName());
    }

    @Test
    void shouldPersistGridEntity() throws IOException {

        City city = new City("GridCity", 4);

        city.placeEntity(1, 1, new CommercialBuilding());

        /* 
	System.out.println("stampo entita'"+
    	city.getGrid()
        .getCell(1,1)
        .getEntity()
	+ "\n");
	
	System.out.println("provo a prendere la cella " + city.getGrid().getCell(1,1));
	System.out.println(city.getGrid().getCell(1,1).getEntity());
	System.out.println(city.getGrid().getCell(1,1).getX());
	System.out.println(city.getGrid().getCell(1,1).getY());
        */

        Path file = tempDir.resolve("grid.json");

        JsonManager.save(city, file.toString());

	    //String json = Files.readString(file);
	    //System.out.println(json);


        City loaded = JsonManager.load(file.toString(), City.class);

        assertNotNull(
            loaded.getGrid().getCell(1, 1).getEntity()
        );
    }
}

