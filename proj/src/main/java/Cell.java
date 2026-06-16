import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE
)

public class Cell {

    private int x;
    private int y;
    private PlaceableEntity entity;

// ------------------- COSTRUTTORE ---------------------

    public Cell(int x, int y) 
    {
        this.x = x;
        this.y = y;
        this.entity = null;
    }

    //costruttore vuoto necessario per poter utilizzare jackson. 
    public Cell() { }

// ------------------ METODI PER LA MODIFICAZIONE E CONTROLLO dello stato di una cella ------------

    public boolean isEmpty() 
    {
        return entity == null;
    }

    public boolean placeEntity(PlaceableEntity entity) 
    {
        //controllo se la cella non e' vuota. Se effettivamente e' così, ritorno false
        if (!isEmpty()) {
            return false;
        }
        
        this.entity = entity;
        return true;
    }

    public void clear() 
    { 
        this.entity = null;
    }

// ------------------------- METODI GETTER  ----------------------------- 


    public PlaceableEntity getEntity() 
    {
        return entity;
    }

    public int getX() 
    {
        return x;
    }

    public int getY() 
    {
        return y;
    }

}
