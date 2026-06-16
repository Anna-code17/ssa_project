public class Road extends Infrastructure {

    public Road() {
        super();
        this.effects = JsonManager.loadFromResources(
        "effects/road.json",
        Effect.class
        );
    }

    @Override
    public String getSymbol() {
        return "R";
    }
}
