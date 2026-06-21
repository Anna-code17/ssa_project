public final class UIUtils {

    // Classe di utilità: non deve essere istanziata.
    private UIUtils() {
    }

    // Ritorna una stringa sicura da mostrare nella UI.
    public static String safeString(String value) {
        return (value == null || value.isBlank()) ? "N/A" : value;
    }

    // Restituisce una descrizione testuale degli effetti dell'entità.
    public static String readEffects(PlaceableEntity entity) {
        if (entity == null) {
            return "No entity selected.";
        }

        Object effects = entity.getEffects();

        if (effects == null) {
            return "No effects information available.";
        }

        return String.valueOf(effects);
    }
}