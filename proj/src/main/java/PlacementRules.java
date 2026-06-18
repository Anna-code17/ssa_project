/*Classe che gestisce le regole di posizionamento per le entità nella città.*/
     
     public class PlacementRules {
    
    //Verifica se un edificio può essere posizionato
    
   public static boolean canPlaceBuilding(Building entity, CityGrid grid, int x, int y) {
    if (!grid.isEmpty(x, y)) {         // La cella deve essere vuota
        return false;
    }
    
    // Regole specifiche per ResidentialBuilding: deve essere presente una centrale elettrica vicina
    if (entity.getName().equals("ResidentialBuilding")) {
        return hasPowerPlantNearby(grid, x, y);
    }
    
    return true;  // Tutti gli altri edifici possono essere posizionati ovunque
    }
    
    //Verifica se un'infrastruttura può essere posizionata (le infrastrutture non hanno restrizioni)
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
                     // Se la cella contiene una centrale elettrica, il ResidentialBuilding può essere posizionato
                    if (!(cell.isEmpty()) && ((cell.getEntity()).getName()).equals("PowerPlant")) {
                    return true; 
                    }
                }
            }
     }
    return false; // Nessuna centrale trovata
}
}
