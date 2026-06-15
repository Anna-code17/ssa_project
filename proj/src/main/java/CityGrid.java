import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class CityGrid {

    private int size;
    private Cell[][] cells;
    private int occupiedCount = 0;

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

	public CityGrid () { }

//---------------------- METODI PER MODIFICARE LA GRIGLIA -----------------------------

    public boolean place(int x, int y, PlaceableEntity entity) {
        
        // effettua un controllo se la posizione presa in considerazione va al di fuori della griglia  
        if (!isValidPosition(x, y)) 
        {
            return false;
        }

        boolean result = cells[x][y].placeEntity(entity);

        if(result)
            occupiedCount++;

        return result;
    }


    // da considerare se bisogna aggiungere dei constraints dati dalle diverse policies 
    public void remove(int x, int y) {
        //controllo se effettivamente si sta cercando di rimuovere una cella che non esiste
        if (!isValidPosition(x, y)) {
            return;
        }

        if (!cells[x][y].isEmpty()) {
        cells[x][y].clear();
        occupiedCount--;
        }

        cells[x][y].clear();
        
    }
	
	public void clearGrid() {
    	for (int x = 0; x < size; x++) {
        	for (int y = 0; y < size; y++) {
            	cells[x][y].clear();
        	}		
    	}

    	occupiedCount = 0;
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

    public int getOccupiedCount()
    {
        return occupiedCount;
    }


// --------------------------- ALTRI METODI --------------------------

//Effettua il controllo se le coordinate sono negative oppure vanno oltre allo spazio disponibile
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < size &&
               y >= 0 && y < size;
    }

//permette di visualizzare la griglia in modo grafico
@Override
public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("CityGrid ")
      .append(occupiedCount)
      .append("/")
      .append(size * size)
      .append("\n");

    for (int y = 0; y < size; y++) {
        for (int x = 0; x < size; x++) {
            Cell cell = cells[x][y];

            if (cell.isEmpty()) {
                sb.append(". ");
            } else {
                sb.append(cell.getEntity().getSymbol()).append(" ");
            }
        }
        sb.append("\n");
    }

    return sb.toString();
}

// Mostra se la griglia non e' occupata in una certa posizione 
public boolean isEmpty(int x, int y) {
    if (!isValidPosition(x, y)) {
       throw new IllegalArgumentException("Invalid position");
    }
    return cells[x][y].isEmpty();
}

}
