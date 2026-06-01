/*
modifiche sul domain model:
Aggiunti i metodi per fare il get delle coordinate x e y
*/ 

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