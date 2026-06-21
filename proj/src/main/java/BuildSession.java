public class BuildSession {

    /**
     * Modalità di interazione disponibili nella UI.
     */
    public enum InteractionMode {
        NORMAL,
        BUILD,
        REMOVE
    }

    // Valore che indica l'assenza di una cella selezionata.
    private static final int NO_SELECTION = -1;

    // Modalità corrente dell'interazione.
    private InteractionMode mode;

    // Indica se l'utente deve ancora scegliere una cella.
    private boolean waitingForCellSelection;

    // Coordinate della cella selezionata.
    private int selectedX;
    private int selectedY;

    // Tipo di entità selezionato per la costruzione.
    private String selectedType;

    /**
     * Inizializza una nuova sessione nello stato neutrale.
     */
    public BuildSession() {
        reset();
    }

    /**
     * Cancella la selezione corrente.
     */
    private void clearSelection() {
        selectedX = NO_SELECTION;
        selectedY = NO_SELECTION;
        selectedType = null;
    }

    /**
     * Avvia una sessione di costruzione.
     * L'utente dovrà selezionare una cella e un tipo di entità.
     */
    public void startBuild() {
        mode = InteractionMode.BUILD;
        waitingForCellSelection = true;
        clearSelection();
    }

    /**
     * Avvia una sessione di rimozione.
     */
    public void startRemove() {
        mode = InteractionMode.REMOVE;
        waitingForCellSelection = false;
        clearSelection();
    }

    /**
     * Riporta la sessione allo stato iniziale.
     */
    public void reset() {
        mode = InteractionMode.NORMAL;
        waitingForCellSelection = false;
        clearSelection();
    }

    /**
     * Verifica se è attiva una modalità diversa da NORMAL.
     */
    public boolean isActive() {
        return mode != InteractionMode.NORMAL;
    }

    /**
     * Verifica se la sessione è in modalità BUILD.
     */
    public boolean isBuildMode() {
        return mode == InteractionMode.BUILD;
    }

    /**
     * Verifica se la sessione è in modalità REMOVE.
     */
    public boolean isRemoveMode() {
        return mode == InteractionMode.REMOVE;
    }

    /**
     * Indica se l'utente deve ancora selezionare una cella.
     */
    public boolean isWaitingForCellSelection() {
        return waitingForCellSelection;
    }

    /**
     * Memorizza la cella scelta dall'utente.
     */
    public void selectCell(int x, int y) {
        selectedX = x;
        selectedY = y;
        waitingForCellSelection = false;
    }

    /**
     * Memorizza il tipo di entità selezionato.
     */
    public void setSelectedType(String type) {
        selectedType = type;
    }

    /**
     * Restituisce il tipo di entità selezionato.
     */
    public String getSelectedType() {
        return selectedType;
    }

    /**
     * Restituisce la coordinata X selezionata.
     */
    public int getSelectedX() {
        return selectedX;
    }

    /**
     * Restituisce la coordinata Y selezionata.
     */
    public int getSelectedY() {
        return selectedY;
    }

    /**
     * Verifica se sono presenti tutte le informazioni
     * necessarie per confermare una costruzione.
     */
    public boolean canConfirm() {
        return isBuildMode()
                && !waitingForCellSelection
                && selectedX != NO_SELECTION
                && selectedY != NO_SELECTION
                && selectedType != null;
    }

    /**
     * Annulla la sessione corrente.
     */
    public void cancel() {
        reset();
    }
}