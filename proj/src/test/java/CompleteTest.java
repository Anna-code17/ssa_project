import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class CompleteTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldSaveAndLoadFullCityState() {

        // =========================
        // 1. CREAZIONE CITY
        // =========================
        City city = new City("MegaCity", 20);

        // =========================
        // 2. ENTITÀ (tutti i tipi)
        // =========================

        city.placeBuilding(1, 1, new ResidentialBuilding());
        city.placeBuilding(2, 2, new CommercialBuilding());
        city.placeBuilding(3, 3, new IndustrialBuilding());

        city.placeInfrastructure(4, 4, new Road());
        city.placeInfrastructure(5, 5, new Park());
        city.placeInfrastructure(6, 6, new PowerPlant());

        // =========================
        // 3. POLICY
        // =========================

        Policy policy = new EnvironmentalTaxPolicy();
        city.setActivePolicy(); // oppure city.applyPolicy(policy) se esiste nel tuo controller

        // =========================
        // 4. TICK (per verificare stato dinamico)
        // =========================

        city.incrementTick();
        city.incrementTick();

        // =========================
        // 5. SAVE
        // =========================

        Path file = tempDir.resolve("full_city.json");

        SaveManager saveManager = new SaveManager();

        boolean saved = saveManager.save(city, file.toString());

        assertTrue(saved);

        // =========================
        // 6. LOAD
        // =========================

        City loaded = saveManager.load(file.toString());

        // =========================
        // 7. CHECK BASE STATE
        // =========================

        assertNotNull(loaded);
        assertEquals("MegaCity", loaded.getName());
        assertEquals(city.getCurrentTick(), loaded.getCurrentTick());

        // =========================
        // 8. CHECK BUILDINGS
        // =========================

        assertInstanceOf(
            ResidentialBuilding.class,
            loaded.getGrid().getCell(1, 1).getEntity()
        );

        assertInstanceOf(
            CommercialBuilding.class,
            loaded.getGrid().getCell(2, 2).getEntity()
        );

        assertInstanceOf(
            IndustrialBuilding.class,
            loaded.getGrid().getCell(3, 3).getEntity()
        );

        // =========================
        // 9. CHECK INFRASTRUCTURE
        // =========================

        assertInstanceOf(
            Road.class,
            loaded.getGrid().getCell(4, 4).getEntity()
        );

        assertInstanceOf(
            Park.class,
            loaded.getGrid().getCell(5, 5).getEntity()
        );

        assertInstanceOf(
            PowerPlant.class,
            loaded.getGrid().getCell(6, 6).getEntity()
        );

        // =========================
        // 10. CHECK POLICY (SE SERIALIZZATA)
        // =========================

        assertNotNull(loaded.getActivePolicy());

        // opzionale se vuoi essere più preciso:
        assertTrue(
            loaded.getActivePolicy() instanceof EnvironmentalTaxPolicy
        );
    }
}
