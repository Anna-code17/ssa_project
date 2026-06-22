import java.util.List;

public final class PolicyFactory {

    private PolicyFactory() {
    }

    public static List<Policy> getAvailablePolicies() {
        return List.of(
                new EnvironmentalTaxPolicy(),
                new IndustrialExpansionPolicy()
             //   new EducationReformPolicy()
        );
    }
}