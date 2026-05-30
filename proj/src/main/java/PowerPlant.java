public class PowerPlant extends Infrastructure {

    public PowerPlant() {
        super("PowerPlant");
        this.effects = EffectLoader.load("powerPlant.json");
    }
}