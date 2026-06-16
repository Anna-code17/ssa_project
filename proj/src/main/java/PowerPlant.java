public class PowerPlant extends Infrastructure {

    public PowerPlant() {
        super();
        this.effects = JsonManager.loadFromResources(
        "effects/powerPlant.json",
        Effect.class
        );
    }

    @Override
    public String getSymbol() {
        return "W";
    }
}

