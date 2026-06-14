public class BuildSession {

    public enum InteractionMode {
        NORMAL,
        BUILD,
        REMOVE
    }



    private InteractionMode mode;

    private boolean waitingForCellSelection;

    private int selectedX;
    private int selectedY;

    private String selectedType;

    public BuildSession() {
        reset();
    }

    public void startBuild() {
        mode = InteractionMode.BUILD;

        waitingForCellSelection = true;

        selectedX = -1;
        selectedY = -1;

        selectedType = null;
    }

    public void startRemove() {
        mode = InteractionMode.REMOVE;

        waitingForCellSelection = false;

        selectedX = -1;
        selectedY = -1;

        selectedType = null;
    }

    public void reset() {
        mode = InteractionMode.NORMAL;

        waitingForCellSelection = false;

        selectedX = -1;
        selectedY = -1;

        selectedType = null;
    }

    public boolean isActive() {
        return mode != InteractionMode.NORMAL;
    }

    public boolean isBuildMode() {
        return mode == InteractionMode.BUILD;
    }

    public boolean isRemoveMode() {
        return mode == InteractionMode.REMOVE;
    }

    public boolean isWaitingForCellSelection() {
        return waitingForCellSelection;
    }

    public void selectCell(int x, int y) {
        selectedX = x;
        selectedY = y;
        waitingForCellSelection = false;
    }

    public void setSelectedType(String type) {
        selectedType = type;
    }

    public String getSelectedType() {
        return selectedType;
    }

    public int getSelectedX() {
        return selectedX;
    }

    public int getSelectedY() {
        return selectedY;
    }

    public boolean canConfirm() {
        return isBuildMode()
                && !waitingForCellSelection
                && selectedX >= 0
                && selectedY >= 0
                && selectedType != null;
    }

    public void cancel() {
        reset();
    }
}