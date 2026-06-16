public class IndustrialBuilding extends Building {

    public IndustrialBuilding() {
        super();
        this.effects = JsonManager.loadFromResources(
        "effects/industrialBuilding.json",
        Effect.class
        );
    }

    @Override
    public String getSymbol() {
        return "I";
    }
}
