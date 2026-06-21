import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;


import static org.junit.jupiter.api.Assertions.*;

public class JsonManagerTest {

    @TempDir
    Path tempDir;

    //si controlla se il sistema e' in grado di salvare almeno le metriche generali di city, come il nome e la grandezza
    @Test
    void shouldSaveAndLoadCity() {

        City city = new City("TestCity", 10);

        Path file = tempDir.resolve("city.json");

        JsonManager.save(city, file.toString());

        City loaded = JsonManager.load(file.toString(), City.class);

        assertNotNull(loaded);
        assertEquals("TestCity", loaded.getName());
        assertEquals(10, loaded.getGrid().getSize());
    }

    //Se si dovesse cercare di caricare un file che non esiste, il sistema deve essere in grado di laanciare un'eccezzione 
    @Test
    void shouldThrowExceptionWhenFileDoesNotExist() {

        assertThrows(RuntimeException.class, () -> {
            JsonManager.load("fake_file.json", City.class);
        });
    }

    //controlla se e' in grado di prelevare dati da un file precedentemente salvato. E' presente su test/resources 
    @Test
    void shouldLoadFromResources() {

        City city = JsonManager.loadFromResources(
            "city.json",
            City.class
        );

        assertNotNull(city);
        assertEquals("MyCity", city.getName());
    }

    //
    @Test
    void shouldPersistGridEntity() {

        City city = new City("GridCity", 4);

        city.placeEntity(1, 1, new CommercialBuilding());

        Path file = tempDir.resolve("grid.json");

        JsonManager.save(city, file.toString());

        City loaded = JsonManager.load(file.toString(), City.class);
        
        //controllo se effettivamente la griglia presenta l'entita' scelta sulla cella in posizione (1,1)
        assertNotNull(
            loaded.getGrid().getCell(1, 1).getEntity()
        );
        //controllo se Jackson e' in grado di gestire correttamente il polimorfismo
        assertInstanceOf(
            CommercialBuilding.class,
            loaded.getGrid().getCell(1, 1).getEntity()
        );
    }
}

