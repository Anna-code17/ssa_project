import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Finestra principale: coordina pannelli, eventi e aggiornamento della vista.
public class UserInterface extends JFrame {

    private static final String VIEW_GRID = "VIEW_GRID";
    private static final String VIEW_BUILD = "VIEW_BUILD";

    private static final String TITLE = "City Simulator";
    private static final String SUBTITLE = "MVC view: city state + grid + build mode";
    private static final String FOOTER_NOTE = "Click a cell to inspect the entity effects.";
    private static final String BUILD_MODE_MESSAGE = "Select an empty cell on the grid.";
    private static final String REMOVE_MODE_MESSAGE = "Select an entity to remove.";
    private static final String INVALID_BUILD_CELL_MESSAGE = "Select an empty cell to build.";
    private static final String FIRST_BUILD_DIALOG_TITLE = "Build Mode";
    private static final String NO_POLICY_TEXT = "No active policy";

    private static final Color ROOT_BACKGROUND = new Color(245, 247, 250);
    private static final Color TEXT_COLOR = new Color(33, 37, 41);
    private static final Color SUBTEXT_COLOR = new Color(108, 117, 125);

    // Controller dell’applicazione secondo il pattern MVC.
    private final Controller controller;

    // Pannelli principali della UI.
    private CityGridPanel cityGridPanel;
    private StatePanel statePanel;
    private EntityDetailsPanel selectedDetailsPanel;
    private BuildPanel buildPanel;

    // Stato temporaneo della modalità build/remove.
    private BuildSession buildSession;

    // Gestione delle due viste centrali: griglia normale e build mode.
    private CardLayout centerCardLayout;
    private JPanel centerCardPanel;

    // Pulsante Build mostrato nel footer.
    private JButton buildButton;
    private JButton removeButton;


    // Utilizzato per mostrare il messaggio introduttivo una sola volta.
    private boolean isFirstBuild = true;

    /**
     * Costruisce la finestra principale dell’applicazione.
     */
    public UserInterface(Controller controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }

        this.controller = controller;
        this.buildSession = new BuildSession();

        setTitle("City Simulator Dashboard - " + UIUtils.safeString(controller.getCityName()));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1320, 820));
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        initComponents();
        refresh();
        setVisible(true);
    }

    /**
     * Inizializza la struttura generale della finestra.
     */
    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBorder(new EmptyBorder(16, 16, 16, 16));
        root.setBackground(ROOT_BACKGROUND);
        setContentPane(root);

        root.add(buildHeader(), BorderLayout.NORTH);
        root.add(buildCenterContainer(), BorderLayout.CENTER);
        root.add(buildFooter(), BorderLayout.SOUTH);
    }

    /**
     * Costruisce l’intestazione con titolo e sottotitolo.
     */
    private JComponent buildHeader() {
        JPanel header = new JPanel(new BorderLayout(10, 10));
        header.setOpaque(false);

        JLabel title = new JLabel(TITLE);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(TEXT_COLOR);

        JLabel subtitle = new JLabel(SUBTITLE);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(SUBTEXT_COLOR);

        JPanel texts = new JPanel();
        texts.setOpaque(false);
        texts.setLayout(new BoxLayout(texts, BoxLayout.Y_AXIS));
        texts.add(title);
        texts.add(Box.createVerticalStrut(2));
        texts.add(subtitle);

        header.add(texts, BorderLayout.WEST);
        return header;
    }

    /**
     * Costruisce l’area centrale con stato città e vista principale.
     */
    private JComponent buildCenterContainer() {
        JPanel center = new JPanel(new BorderLayout(16, 16));
        center.setOpaque(false);

        statePanel = new StatePanel();
        statePanel.getActivePolicyButton().addActionListener(e -> showPolicySelector());
        statePanel.getPolicyDetailsButton().addActionListener(e -> showActivePolicyDetails());
    

        centerCardLayout = new CardLayout();
        centerCardPanel = new JPanel(centerCardLayout);
        centerCardPanel.setOpaque(false);

        centerCardPanel.add(buildGridView(), VIEW_GRID);
        centerCardPanel.add(buildBuildView(), VIEW_BUILD);
        centerCardLayout.show(centerCardPanel, VIEW_GRID);

        center.add(statePanel, BorderLayout.WEST);
        center.add(centerCardPanel, BorderLayout.CENTER);
        return center;
    }

    /**
     * Costruisce la vista standard della città.
     */
    private JComponent buildGridView() {
        JPanel center = new JPanel(new BorderLayout(16, 16));
        center.setOpaque(false);
        center.add(buildGridAndDetailsPanel(), BorderLayout.CENTER);
        return center;
    }

    /**
     * Costruisce la vista dedicata alla modalità build.
     */
    private JComponent buildBuildView() {
        JPanel center = new JPanel(new BorderLayout(16, 16));
        center.setOpaque(false);

        buildPanel = new BuildPanel();
        buildPanel.getConfirmButton().addActionListener(e -> confirmBuild());
        buildPanel.getCancelButton().addActionListener(e -> cancelBuild());

        buildPanel.getEntityList().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }

            String selected = buildPanel.getEntityList().getSelectedValue();
            if (selected == null) {
                buildSession.setSelectedType(null);
                buildPanel.clearPreview();
            } else {
                updateBuildPreview(selected);
            }

            updateBuildConfirmState();
        });

        center.add(buildPanel, BorderLayout.CENTER);
        return center;
    }

    /**
     * Costruisce il pannello griglia + dettagli entità selezionata.
     */
    private JComponent buildGridAndDetailsPanel() {
        JPanel center = new JPanel(new BorderLayout(14, 14));
        center.setOpaque(false);

        cityGridPanel = new CityGridPanel();
        selectedDetailsPanel = new EntityDetailsPanel();

        center.add(cityGridPanel, BorderLayout.CENTER);
        center.add(selectedDetailsPanel, BorderLayout.SOUTH);
        return center;
    }

    /**
     * Costruisce il footer con i comandi principali.
     */
     private JComponent buildFooter() {
    JPanel footer = new JPanel(new BorderLayout());
    footer.setOpaque(false);

    // Save e Load a sinistra
    JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
    left.setOpaque(false);

    JButton saveButton = new JButton("Save");
    JButton loadButton = new JButton("Load");

    styleButton(saveButton);
    styleButton(loadButton);

    saveButton.addActionListener(e -> saveCity());
    loadButton.addActionListener(e -> loadCity());

    left.add(saveButton);
    left.add(loadButton);

    // nota al centro
    JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    center.setOpaque(false);

    JLabel note = new JLabel("Click a cell to inspect the entity effects.");
    note.setFont(new Font("SansSerif", Font.PLAIN, 13));
    note.setForeground(new Color(108, 117, 125));
    center.add(note);

    //a destra azioni
    JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
    right.setOpaque(false);

    JButton buildButton = new JButton("Build");
    JButton resetButton = new JButton("Reset");
    JButton nextTickButton = new JButton("Advance Tick");
    JButton removeButton = new JButton("Remove");

    styleButton(buildButton);
    styleButton(resetButton);
    styleButton(nextTickButton);
    styleButton(removeButton);

    buildButton.addActionListener(e -> enterBuildMode());
    resetButton.addActionListener(e -> { controller.resetCity(); refresh(); });
    nextTickButton.addActionListener(e -> { controller.nextTick(); refresh(); });
    removeButton.addActionListener(e -> enterRemoveMode());

    right.add(buildButton);
    right.add(resetButton);
    right.add(nextTickButton);
    right.add(removeButton);

    // footer
    footer.add(left, BorderLayout.WEST);
    footer.add(center, BorderLayout.CENTER);
    footer.add(right, BorderLayout.EAST);

    return footer;
}

    /**
     * Applica lo stile standard ai pulsanti.
     */
    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBorder(new EmptyBorder(10, 16, 10, 16));
    }

    /**
     * Attiva la build mode.
     */
    private void enterBuildMode() {
        buildSession.startBuild();
        buildPanel.clearEntitySelection();

        if (isFirstBuild) {
            JOptionPane.showMessageDialog(
                    this,
                    BUILD_MODE_MESSAGE,
                    FIRST_BUILD_DIALOG_TITLE,
                    JOptionPane.INFORMATION_MESSAGE
            );
            isFirstBuild = false;
        }

        updateBuildConfirmState();
    }

    /**
     * Attiva la remove mode.
     */
    private void enterRemoveMode() {
        buildSession.startRemove();
        JOptionPane.showMessageDialog(this, REMOVE_MODE_MESSAGE);
        updateFooterButtonsForBuildMode();
    }

    /**
     * Ripristina la vista normale e azzera la build session.
     */
    private void exitBuildMode() {
        buildSession.reset();
        buildPanel.clearEntitySelection();
        buildPanel.clearCellSelection();
        buildPanel.clearPreview();
        centerCardLayout.show(centerCardPanel, VIEW_GRID);
        updateFooterButtonsForBuildMode();
        updateBuildConfirmState();
        refreshGrid();
        refreshState();
    }

    /**
     * Aggiorna tutta l’interfaccia in base al modello.
     */
    private void refresh() {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(this::refresh);
            return;
        }

        refreshState();
        refreshGrid();

        if (!buildSession.isActive() && selectedDetailsPanel != null) {
            selectedDetailsPanel.clear();
        }

        updateFooterButtonsForBuildMode();
        updateBuildConfirmState();

        getContentPane().revalidate();
        getContentPane().repaint();
    }

    /**
     * Aggiorna i valori mostrati nel pannello stato.
     */
    private void refreshState() {
        City city = controller.getCity();
        CityState state = controller.getCityState();
        CityGrid grid = controller.getGrid();

        statePanel.updateState(
                UIUtils.safeString(controller.getCityName()),
                controller.getCurrentTick(),
                state,
                countOccupiedCells(grid),
                PolicyFormatter.getPolicyName(city)
        );

        if (city != null) {
            setTitle("City Simulator Dashboard - " + UIUtils.safeString(city.getName()));
        }

        statePanel.getPolicyDetailsButton().setEnabled(controller.getActivePolicy() != null);
    }

    /**
     * Ricostruisce la rappresentazione grafica della griglia dal modello.
     */
    private void refreshGrid() {
        CityGrid grid = controller.getGrid();
        cityGridPanel.clearGrid();

        if (grid == null) {
            cityGridPanel.showNoGrid();
            return;
        }

        int size = grid.getSize();
        cityGridPanel.prepareGrid(size);

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Cell cell = grid.getCell(x, y);
                cityGridPanel.addTile(createTile(cell, x, y));
            }
        }

        cityGridPanel.refreshView();
    }

    /**
     * Crea il componente grafico di una singola cella.
     */
    private JPanel createTile(Cell cell, int x, int y) {
        JPanel tile = new JPanel(new BorderLayout());
        tile.setPreferredSize(new Dimension(72, 72));
        tile.setBorder(new LineBorder(new Color(180, 185, 190), 1, true));
        tile.setOpaque(true);

        JLabel symbolLabel = new JLabel(".", SwingConstants.CENTER);
        symbolLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 19));
        symbolLabel.setForeground(TEXT_COLOR);

        if (cell != null && !cell.isEmpty()) {
            PlaceableEntity entity = cell.getEntity();
            symbolLabel.setText(String.valueOf(entity.getSymbol()));
            tile.setBackground(EntityFactory.getColor(entity.getName()));
            tile.setToolTipText(entity.getName() + " (" + entity.getType() + ")");

            tile.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Gestione del click su una cella occupata.
                    if (buildSession.isRemoveMode()) {
                        controller.removeEntity(x, y);
                        buildSession.reset();
                        refresh();
                        return;
                    }

                    if (buildSession.isBuildMode()) {
                        JOptionPane.showMessageDialog(
                                UserInterface.this,
                                INVALID_BUILD_CELL_MESSAGE,
                                "Invalid cell",
                                JOptionPane.WARNING_MESSAGE
                        );
                        return;
                    }

                    selectedDetailsPanel.showEntity(entity);
                }
            });
        } else {
            tile.setBackground(Color.WHITE);
            tile.setToolTipText("Empty cell");

            tile.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Gestione del click su una cella vuota.
                    if (buildSession.isActive() && buildSession.isWaitingForCellSelection()) {
                        buildSession.selectCell(x, y);
                        buildSession.setSelectedType(null);
                        buildPanel.setSelectedCell(x, y);
                        buildPanel.clearPreview();
                        buildPanel.clearEntitySelection();
                        centerCardLayout.show(centerCardPanel, VIEW_BUILD);
                        updateBuildConfirmState();
                        return;
                    }

                    selectedDetailsPanel.clear();
                }
            });
        }

        tile.add(symbolLabel, BorderLayout.CENTER);
        return tile;
    }

    /**
     * Aggiorna l’anteprima dell’entità selezionata.
     */
    private void updateBuildPreview(String selectedType) {
        buildSession.setSelectedType(selectedType);
        PlaceableEntity preview = EntityFactory.create(selectedType);

        if (preview == null) {
            buildPanel.showNoPreview();
            updateBuildConfirmState();
            return;
        }

        buildPanel.showPreview(preview);
        updateBuildConfirmState();
    }

    /**
     * Aggiorna lo stato dei pulsanti della build mode.
     */
    private void updateBuildConfirmState() {
        if (buildPanel != null) {
            buildPanel.getConfirmButton().setEnabled(buildSession.canConfirm());
            buildPanel.getCancelButton().setEnabled(buildSession.isActive());
        }

        if (buildButton != null) {
            buildButton.setEnabled(!buildSession.isActive());
        }
    }

    /**
     * Mostra una finestra con i dettagli della policy attualmente attiva.
     */
    private void showActivePolicyDetails() {
        String policyText = PolicyFormatter.getPolicyDetails(controller.getCity());
        if (policyText == null || policyText.isBlank()) {
            policyText = NO_POLICY_TEXT;
        }

        JTextArea area = new JTextArea(policyText, 10, 32);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setCaretPosition(0);

        JOptionPane.showMessageDialog(
                this,
                new JScrollPane(area),
                "Active Policy",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Mostra la finestra di selezione delle policy.
     */
    private void showPolicySelector() {
        java.util.List<Policy> policies = PolicyFactory.getAvailablePolicies();
        JDialog dialog = new JDialog(this, "Policy Selection", true);
        dialog.setSize(650, 400);
        dialog.setLocationRelativeTo(this);

        DefaultListModel<String> model = new DefaultListModel<>();
        model.addElement("No active policy");
        for (Policy policy : policies) {
            model.addElement(policy.getName());
        }

        JList<String> policyList = new JList<>(model);
        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);

        policyList.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }

            int index = policyList.getSelectedIndex();
            if (index < 0) {
                return;
            }

            if (index == 0) {
                detailsArea.setText(NO_POLICY_TEXT);
                return;
            }

            detailsArea.setText(PolicyFormatter.format(policies.get(index - 1)));
        });

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(policyList),
                new JScrollPane(detailsArea)
        );
        splitPane.setDividerLocation(220);

        JButton applyButton = new JButton("Apply");
        JButton cancelButton = new JButton("Cancel");

        JPanel footer = new JPanel();
        footer.add(applyButton);
        footer.add(cancelButton);

        applyButton.addActionListener(e -> {
            int index = policyList.getSelectedIndex();
            if (index < 0) {
                return;
            }

            if (index == 0) {
                controller.deactivatePolicy();
            } else {
                controller.applyPolicy(policies.get(index - 1));
            }

            refresh();
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setLayout(new BorderLayout());
        dialog.add(splitPane, BorderLayout.CENTER);
        dialog.add(footer, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    /**
     * Restituisce il numero di celle occupate nella griglia.
     */
    private int countOccupiedCells(CityGrid grid) {
        return grid == null ? 0 : grid.getOccupiedCount();
    }

    /**
     * Punto di ingresso usato dal controller per aggiornare la vista.
     */
    public void refreshFromModel() {
        refresh();
    }

    /**
     * Convalida e completa l’operazione di costruzione.
     */
    public void confirmBuild() {
        if (!buildSession.isActive()) {
            return;
        }

        if (buildSession.getSelectedX() < 0 || buildSession.getSelectedY() < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Select an empty cell first.",
                    "Build mode",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (buildSession.getSelectedType() == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Select an entity first.",
                    "Build mode",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        PlaceableEntity entity = EntityFactory.create(buildSession.getSelectedType());
        if (entity == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unknown entity selected.",
                    "Build mode",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        boolean placed = controller.placeEntity(
                buildSession.getSelectedX(),
                buildSession.getSelectedY(),
                entity
        );

        if (!placed) {
            JOptionPane.showMessageDialog(
                    this,
                    "Cannot place the selected entity in that cell.",
                    "Build mode",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        exitBuildMode();
        refresh();
    }

    /**
     * Annulla l’operazione di costruzione corrente.
     */
    public void cancelBuild() {
        if (!buildSession.isActive()) {
            return;
        }

        exitBuildMode();
        refresh();
    }

    /**
     * Aggiorna i pulsanti in base alla modalità corrente.
     */
    private void updateFooterButtonsForBuildMode() {
        if (buildButton != null) {
            buildButton.setEnabled(!buildSession.isActive());
        }

        if (removeButton != null) {
            removeButton.setEnabled(!buildSession.isActive() && countOccupiedCells(controller.getGrid()) > 0);
        }

        if (buildPanel != null) {
            buildPanel.getConfirmButton().setEnabled(buildSession.isActive());
            buildPanel.getCancelButton().setEnabled(buildSession.isActive());
        }

    }

    /**
     * Salva la città corrente su file.
     */
    private void saveCity() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        boolean saved = controller.saveCity(chooser.getSelectedFile().getAbsolutePath());
        JOptionPane.showMessageDialog(this, saved ? "City saved successfully." : "Error while saving.");
    }

    /**
     * Carica una città da file.
     */
    private void loadCity() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        boolean loaded = controller.loadCity(chooser.getSelectedFile().getAbsolutePath());
        if (!loaded) {
            JOptionPane.showMessageDialog(this, "Error while loading.");
            return;
        }

        refresh();
    }
}
