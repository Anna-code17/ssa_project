public class Road extends Infrastructure {

    public Road() {
        super("Road");
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
