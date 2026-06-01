/*
Aggiunti rispetto al design model:
- getSize (Potrebbe essere utile per fare il debugging)
*/ 

public class CityGrid {

    private int size;
    private Cell[][] cells;

// --------------------------------- METODO COSTRUTTORE -------------------------------

    public CityGrid(int size) {
        //Non immetto il controllo se size < 0, perche' il valore lo decidiamo da dentro il sistema. 
        this.size = size;
        this.cells = new Cell[size][size];

        // Creazione di tutte le celle della griglia
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }
    }

//---------------------- METODI PER MODIFICARE LA GRIGLIA -----------------------------

    public boolean place(int x, int y, PlaceableEntity entity) {
        
        // effettua un controllo se la posizione presa in considerazione va al di fuori della griglia  
        if (!isValidPosition(x, y)) 
        {
            return false;
        }
        
        //il metodo place entity gestisce il controllo se la cella e' valida o meno. Rerstituisce un boolean.
        //puo' essere quindi evitato un secondo controllo qui. 
        return cells[x][y].placeEntity(entity);
    }


    // da considerare se bisogna aggiungere dei constraints dati dalle diverse policies 
    public void remove(int x, int y) {
        //controllo se effettivamente si sta cercando di rimuovere una cella che non esiste
        if (!isValidPosition(x, y)) {
            return;
        }

        cells[x][y].clear();
    }


// ---------------------------- METODI GETTER -------------------------------

    public Cell getCell(int x, int y) {

        if (!isValidPosition(x, y)) {
            return null;
        }

        return cells[x][y];
    }

    public int getSize()
    {
        return size;
    }

// --------------------------- METODO PRIVATO -------------------------------

//Effettua il controllo se le coordinate sono negative oppure vanno oltre allo spazio disponibile
    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < size &&
               y >= 0 && y < size;
    }

}