public class PlacementRules {
    
    //Verifica se un edificio può essere posizionato
    
   public static boolean canPlaceBuilding(PlaceableEntity entity, CityGrid grid, int x, int y) {
    if (!grid.isEmpty(x, y)) {
        return false;
    }
    
    // Regole specifiche per ResidentialBuilding
    if (entity.getName().equals("ResidentialBuilding")) {
        return hasPowerPlantNearby(grid, x, y);
    }
    
    return true;
    }
    
    //Verifica se un'infrastruttura può essere posizionata
       public static boolean canPlaceInfrastructure(Infrastructure infrastructure, CityGrid grid, int x, int y) {
        return grid.isEmpty(x, y);
    }
    
    //Metodo per verificare se è presente un PowerPlant vicino 
    //alla cella in cui voglio piazzare il ResidentialBuilding
    
    private static boolean hasPowerPlantNearby(CityGrid grid, int x, int y) {
        // Distanza massima entro cui considero il PowerPlant vicino
        int distance = 2; 

    //Scansiono tutte le celle adiacenti per controllare se c'è una PowerPlant
        for (int i = x - distance; i <= x + distance; i++) {

            for (int j = y - distance; j <= y + distance; j++) {

            // Uso il metodo di CityGrid isValidPosition per controllare se la cella esiste
                if (grid.isValidPosition(i, j)) {
                Cell cell = grid.getCell(i, j);
                    if (!(cell.isEmpty()) && ((cell.getEntity()).getName()).equals("PowerPlant")) {
                    return true;
                    }
                }
            }
     }
    return false;
}
}
