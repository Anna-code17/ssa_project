import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ResidentialBuilding.class, name = "ResidentialBuilding"),
    @JsonSubTypes.Type(value = IndustrialBuilding.class, name = "IndustrialBuilding"),
    @JsonSubTypes.Type(value = CommercialBuilding.class, name = "CommercialBuilding"),
    @JsonSubTypes.Type(value = PowerPlant.class, name = "PowerPlant"),
    @JsonSubTypes.Type(value = Park.class, name = "Park"),
    @JsonSubTypes.Type(value = Road.class, name = "Road")
}) 

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
