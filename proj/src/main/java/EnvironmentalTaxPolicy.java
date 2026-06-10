import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class EnvironmentalTaxPolicy implements Policy {

/* EnvironmentalTaxPolicy aumenta del 10% la felicità, diminuisce del 15% l'inquinamento
 e il budget*/
    
    @Override
    public String getName() {
        return "Environmental Tax Policy";
    }
    
    @Override
    public int getPercentBudget() {
        return -15;
    }
   
    @Override
    public int getPercentPollution() {
        return -15;
    }
    
    @Override
    public int getPercentHappiness() {
        return 10;
    }
}
