/* Classe che gestisce le regole di posizionamento per le entità nella città. */

public class PlacementRules {

    // Verifica se un edificio può essere posizionato
    public static boolean canPlaceBuilding(Building entity, CityGrid grid, int x, int y) {

        // La cella deve essere vuota
        if (!grid.isEmpty(x, y)) {
            return false;
        }

        // I ResidentialBuilding richiedono una centrale elettrica vicina
        if (entity instanceof ResidentialBuilding) {
            return hasNearby(grid, x, y, PowerPlant.class, 2);
        }

        // Tutti gli altri edifici possono essere piazzati ovunque
        return true;
    }

    // Verifica se un'infrastruttura può essere posizionata
    public static boolean canPlaceInfrastructure(Infrastructure infrastructure, CityGrid grid, int x, int y) {

        return grid.isEmpty(x, y);
    }

    /**
     * Verifica se esiste almeno un'entità del tipo richiesto
     * entro la distanza specificata dalla cella (x,y).
     */
    private static boolean hasNearby(CityGrid grid, int x, int y, Class<? extends PlaceableEntity> entityType, int distance) {

        for (int i = x - distance; i <= x + distance; i++) {

            for (int j = y - distance; j <= y + distance; j++) {

                if (!grid.isValidPosition(i, j)) {
                    continue;
                }

                Cell cell = grid.getCell(i, j);

                if (!cell.isEmpty() && entityType.isInstance(cell.getEntity())) {

                    return true;
                }
            }
        }

        return false;
    }
}
