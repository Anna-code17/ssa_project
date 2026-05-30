public class Road extends Infrastructure {

    public Road() {
        super("Road");
        this.effects = EffectLoader.load("road.json");
    }
}