
public final class EntityFactory {

    private EntityFactory() {
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

            default ->
                    null;
        };
    }
}