import java.awt.Color;
import java.util.List;

public final class EntityFactory {

    private EntityFactory() {

    }

    public static List<String> getAvailableEntityTypes() {

        return List.of(
                "Park",
                "Road",
                "PowerPlant",
                "ResidentialBuilding",
                "CommercialBuilding",
                "IndustrialBuilding"
              //  "School"
        );
    }

    public static Color getColor(String type) {

        return switch (type) {

            case "Park" ->
                    new Color(198, 239, 206);

            case "Road" ->
                    new Color(222, 226, 230);

            case "ResidentialBuilding" ->
                    new Color(191, 219, 254);

            case "CommercialBuilding" ->
                    new Color(255, 229, 180);

            case "IndustrialBuilding" ->
                    new Color(255, 204, 204);

            case "PowerPlant" ->
                    new Color(221, 214, 254);

           // case "School" ->
              //      new Color(255, 242, 204);

            default ->
                    new Color(233, 236, 239);
        };
    }

    public static PlaceableEntity create(String type) {

        if (type == null) {
            return null;
        }

        return switch (type) {

            case "Park" ->
                    new Park();

            case "Road" ->
                    new Road();

            case "PowerPlant" ->
                    new PowerPlant();

            case "ResidentialBuilding" ->
                    new ResidentialBuilding();

            case "CommercialBuilding" ->
                    new CommercialBuilding();

            case "IndustrialBuilding" ->
                    new IndustrialBuilding();

          //  case "School" ->
                //    new School();

            default ->
                    null;
        };
    }
}