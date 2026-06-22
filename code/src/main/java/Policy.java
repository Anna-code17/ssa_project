import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EnvironmentalTaxPolicy.class, name = "EnvironmentalTaxPolicy"),
        @JsonSubTypes.Type(value = IndustrialExpansionPolicy.class, name = "IndustrialExpansionPolicy")
      //  @JsonSubTypes.Type(value = EducationReformPolicy.class, name = "EducationReformPolicy")
})
    
/*Interfaccia per le policy applicabili alla città.
  Le policy possono modificare le statistiche della città tramite variazioni percentuali.*/
    
public interface Policy {
    
    String getName();
    
    default int getPercentBudget() { 
        return 0; 
    }
    default int getPercentPopulation() { 
        return 0; 
    }
    default int getPercentPollution() { 
        return 0; 
    }
    default int getPercentHappiness() { 
        return 0; 
    }
}
