import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class IndustrialExpansionPolicy implements Policy {

    /* IndustrialExpansionPolicy aumenta del 10% il budget, del 15% l'inquinamento e diminuisce la 
   felicità del 5%*/
    @Override
    public String getName() {
        return "Industrial Expansion Policy";
    }
    
    @Override
    public int getPercentBudget() {
        return 10;
    }

    @Override
    public int getPercentPollution() {
        return 15;
    }
    
    @Override
    public int getPercentHappiness() {
        return -5;
    }
}
