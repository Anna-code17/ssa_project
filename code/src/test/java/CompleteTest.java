import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;


import static org.junit.jupiter.api.Assertions.*;

public class CompleteTest {

//serve per creare una cartella temporanea isolata che viene automaticamente gestita dal framework
    @TempDir
    Path tempDir;

    @Test
    void shouldSaveAndLoadFullCityState() {

        
        // ---------------------------------------- 1. CREAZIONE CITY-------------------------------------
       
        City city = new City("MegaCity", 4);

        // ---------------------------------------- 2. ENTITÀ (tutti i tipi)------------------------------
	
	    city.placeEntity(0, 0, new PowerPlant());
        city.placeEntity(0, 1, new ResidentialBuilding());
        city.placeEntity(0, 2, new CommercialBuilding());
        city.placeEntity(0, 3, new IndustrialBuilding());

        city.placeEntity(1, 1, new Road());
        city.placeEntity(1, 2, new Park());
        

        // ----------------------------------------- 3. POLICY -------------------------------------------

        Policy policy = new EnvironmentalTaxPolicy();
        city.setActivePolicy(policy); 

        // ----------------------------------------- 4. TICK (per verificare stato dinamico)--------------

        city.incrementTick();
        city.incrementTick();

        // ----------------------------------------- 5. SAVE ---------------------------------------------
	
	    //creo un path themporaneo ma NON CREA FILE DA SOLO. il file viene creato con save. 
        Path file = tempDir.resolve("full_city.json");

        SaveManager saveManager = new SaveManager();

        boolean saved = saveManager.save(city, file.toString());

        assertTrue(saved);
    	

        // ----------------------------------------- 6. LOAD ---------------------------------------------

        City loaded = saveManager.load(file.toString());

        // ----------------------------------------- 7. CHECK BASE STATE ---------------------------------
        //si controlla se la serializzazione di jackson e' stata almeno in grado di salvare il nome ed il tick della city iniziale 
        assertNotNull(loaded);
        assertEquals("MegaCity", loaded.getName());
        assertEquals(city.getCurrentTick(), loaded.getCurrentTick());

        // ----------------------------------------- 8. CHECK BUILDINGS ---------------------------------
 	    //punto e 8 e 9 sono focalizzati se è stato mantenuto correttamente il tipo di oggetti       
        assertInstanceOf(
            ResidentialBuilding.class,
            loaded.getGrid().getCell(0, 1).getEntity()
        );

        assertInstanceOf(
            CommercialBuilding.class,
            loaded.getGrid().getCell(0, 2).getEntity()
        );

        assertInstanceOf(
            IndustrialBuilding.class,
            loaded.getGrid().getCell(0, 3).getEntity()
        );

        // ----------------------------------------- 9. CHECK INFRASTRUCTURE -------------------------------

        assertInstanceOf(
            Road.class,
            loaded.getGrid().getCell(1, 1).getEntity()
        );

        assertInstanceOf(
            Park.class,
            loaded.getGrid().getCell(1, 2).getEntity()
        );

        assertInstanceOf(
            PowerPlant.class,
            loaded.getGrid().getCell(0, 0).getEntity()
        );

        // -------------------------------------------- 10. CHECK POLICY ------------------------------------- 
      
        assertNotNull(loaded.getActivePolicy());

        //controllo se e' stato salvato correttamente il tipo della policy
        assertTrue(
            loaded.getActivePolicy() instanceof EnvironmentalTaxPolicy
        );
    }
}
