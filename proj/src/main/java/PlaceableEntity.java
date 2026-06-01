/*

In questa classe sono stati aggiunti rispetto al design model:
1)Dal momento che la variabile dell'oggetto si chiama nome, ho cambiato da getType a getName
2)Inserito un getEffect() e getType()
3)inserito setEffect()
4)  Inserito un to string per far vedere tutte le informazioni che contiene un oggetto

*/ 

public abstract class PlaceableEntity {
    
    protected String name;
    protected Effect effects;
    protected String type;

// il nome da inserire corrisponde con il tipo di edificio che si puo' costruire
    public PlaceableEntity(String name) {
        this.name = name;
    }

    public PlaceableEntity(String name, String type) {
        this.name = name;
        this.type = type;
    }

//------------------------------- Metodi getter --------------------------------
    
    public String getName() {
        return name;
    }

    public Effect getEffects() {
        return effects;
    }

    public String getType()
    {
        return type;
    }

//------------------------------- Metodi setter --------------------------------

    public void setEffects(Effect effects) {
        this.effects = effects;
    }

//------------------------------- Metodo per avere tutte le metriche su una entita'------------------------

    @Override
    public String toString() 
    {
        return  effects.toString() + "Entity:\nName: " + name + " \nType: " + type;
    }
//------------------------------- METODO ASTRATTO PER RICAVARE IL SIMBOLO DELL'ENTITA' --------------------
    
    public abstract String getSymbol();

}
