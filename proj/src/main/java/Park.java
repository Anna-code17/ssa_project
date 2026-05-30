public class Park extends Infrastructure {

    public Park() {
        super("Park");
        this.effects = EffectLoader.load("park.json");
    }
}