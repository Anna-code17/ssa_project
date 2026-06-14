
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;

public class UserInterface extends JFrame {

    private static final String VIEW_GRID = "VIEW_GRID";
    private static final String VIEW_BUILD = "VIEW_BUILD";

    private final Controller controller;





    private CityGridPanel cityGridPanel;
    private StatePanel statePanel;
    private EntityDetailsPanel selectedDetailsPanel;
    private BuildPanel buildPanel;
    private BuildSession buildSession;

    private CardLayout centerCardLayout;
    private JPanel centerCardPanel;

    private JButton buildButton;


    public UserInterface(Controller controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }

        this.controller = controller;
        buildSession = new BuildSession();

        setTitle("City Simulator Dashboard - " + UIUtils.safeString(controller.getCityName()));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1320, 820));
        setLocationRelativeTo(null);

        initComponents();
        refresh();

        setVisible(true);
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBorder(new EmptyBorder(16, 16, 16, 16));
        root.setBackground(new Color(245, 247, 250));
        setContentPane(root);

        root.add(buildHeader(), BorderLayout.NORTH);
        root.add(buildCenterContainer(), BorderLayout.CENTER);
        root.add(buildFooter(), BorderLayout.SOUTH);
    }

    private JComponent buildHeader() {
        JPanel header = new JPanel(new BorderLayout(10, 10));
        header.setOpaque(false);

        JLabel title = new JLabel("City Simulator");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(33, 37, 41));

        JLabel subtitle = new JLabel("MVC view: city state + grid + build mode");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(108, 117, 125));

        JPanel texts = new JPanel();
        texts.setOpaque(false);
        texts.setLayout(new BoxLayout(texts, BoxLayout.Y_AXIS));
        texts.add(title);
        texts.add(Box.createVerticalStrut(2));
        texts.add(subtitle);

        header.add(texts, BorderLayout.WEST);
        return header;
    }

    private JComponent buildCenterContainer() {

        JPanel center = new JPanel(new BorderLayout(16, 16));
        center.setOpaque(false);

        statePanel=new StatePanel();

        statePanel.getActivePolicyButton().addActionListener(e -> showPolicySelector());

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

    private JComponent buildGridView() {
        JPanel center = new JPanel(new BorderLayout(16, 16));
        center.setOpaque(false);

        center.add(buildGridAndDetailsPanel(), BorderLayout.CENTER);

        return center;
    }

    private JComponent buildBuildView() {

        JPanel center = new JPanel(new BorderLayout(16, 16));
        center.setOpaque(false);

        buildPanel = new BuildPanel();

        buildPanel.getConfirmButton().addActionListener(e -> confirmBuild());
        buildPanel.getCancelButton().addActionListener(e -> cancelBuild());

        center.add(buildPanel, BorderLayout.CENTER);

        buildPanel.getEntityList().addListSelectionListener(e -> {if (!e.getValueIsAdjusting()) {

            String selected = buildPanel.getEntityList().getSelectedValue();
            if (selected != null) {
                updateBuildPreview(selected);
                updateBuildConfirmState();
            }
        }
        });

        return center;
    }



    private JComponent buildGridAndDetailsPanel() {
        JPanel center = new JPanel(new BorderLayout(14, 14));
        center.setOpaque(false);

        cityGridPanel = new CityGridPanel();

        center.add(cityGridPanel, BorderLayout.CENTER);

        selectedDetailsPanel = new EntityDetailsPanel();
        center.add(selectedDetailsPanel, BorderLayout.SOUTH);

        return center;
    }





    private JComponent buildFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        left.setOpaque(false);

        JLabel note = new JLabel("Click a cell to inspect the entity effects.");
        note.setFont(new Font("SansSerif", Font.PLAIN, 13));
        note.setForeground(new Color(108, 117, 125));
        left.add(note);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        right.setOpaque(false);

        buildButton = new JButton("Build");
        JButton resetButton = new JButton("Reset");
        JButton nextTickButton = new JButton("Advance Tick");
        JButton removeButton = new JButton("Remove");


        styleButton(removeButton);
        styleButton(buildButton);
        styleButton(resetButton);
        styleButton(nextTickButton);

        buildButton.addActionListener(e -> enterBuildMode());
        resetButton.addActionListener(e -> {controller.resetCity();refresh();});
        nextTickButton.addActionListener(e -> {controller.nextTick();refresh();});
        removeButton.addActionListener(e -> enterRemoveMode());

        right.add(buildButton);
        right.add(resetButton);
        right.add(nextTickButton);
        right.add(removeButton);

        footer.add(left, BorderLayout.WEST);
        footer.add(right, BorderLayout.EAST);
        return footer;
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBorder(new EmptyBorder(10, 16, 10, 16));
    }

    private void enterBuildMode() {

        buildSession.startBuild();

        buildPanel.clearEntitySelection();

        JOptionPane.showMessageDialog(
                this,
                "Select an empty cell on the grid."
        );

        updateBuildConfirmState();
    }

    private void enterRemoveMode() {

        buildSession.startRemove();

        JOptionPane.showMessageDialog(
                this,
                "Select an entity to remove."
        );

        updateFooterButtonsForBuildMode();
    }

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

    private void refreshState() {

        City city = controller.getCity();
        CityState state = controller.getCityState();

        statePanel.updateState(UIUtils.safeString(controller.getCityName()), controller.getCurrentTick(), state, countOccupiedCells(controller.getGrid()),PolicyFormatter.getPolicyName(city));

        if (city != null) {
            setTitle("City Simulator Dashboard - " + UIUtils.safeString(city.getName()));
        }
    }

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
                JPanel tile = createTile(cell, x, y);
                cityGridPanel.addTile(tile);
            }
        }

        cityGridPanel.refreshView();
    }

    private JPanel createTile(Cell cell, int x, int y) {
        JPanel tile = new JPanel(new BorderLayout());
        tile.setPreferredSize(new Dimension(72, 72));
        tile.setBorder(new LineBorder(new Color(180, 185, 190), 1, true));
        tile.setOpaque(true);

        JLabel symbolLabel = new JLabel(".", SwingConstants.CENTER);
        symbolLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        symbolLabel.setForeground(new Color(33, 37, 41));

        if (cell != null && !cell.isEmpty()) {
            PlaceableEntity entity = cell.getEntity();
            String symbol = String.valueOf(entity.getSymbol());
            symbolLabel.setText(symbol);
            tile.setBackground(CityGridPanel.colorForEntity(entity));
            tile.setToolTipText(entity.getName() + " (" + entity.getType() + ")");

            tile.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (buildSession.isRemoveMode()) {
                        controller.removeEntity(x, y);
                        buildSession.reset();
                        refresh();
                        return;
                    }

                    if (buildSession.isBuildMode()) {
                        JOptionPane.showMessageDialog(
                                UserInterface.this,
                                "Select an empty cell to build.",
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
                    if (buildSession.isActive() && buildSession.isWaitingForCellSelection()) {

                        buildSession.selectCell(x, y);
                        buildSession.setSelectedType(null);

                        buildPanel.setSelectedCell(x, y);

                        buildPanel.clearPreview();

                        buildPanel.clearEntitySelection();
                        centerCardLayout.show(
                                centerCardPanel,
                                VIEW_BUILD
                        );

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








    private void updateBuildConfirmState() {

        if (buildPanel != null) {

            buildPanel.getConfirmButton().setEnabled(
                    buildSession.canConfirm()
            );

            buildPanel.getCancelButton().setEnabled(
                    buildSession.isActive()
            );
        }

        if (buildButton != null) {
            buildButton.setEnabled(!buildSession.isActive());
        }
    }

    private void showActivePolicyDetails() {
        String policyText = PolicyFormatter.getPolicyDetails(controller.getCity());
        if (policyText == null || policyText.isBlank()) {
            policyText = "No active policy";
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

    private void showPolicySelector() {

        java.util.List<Policy> policies =
                PolicyFactory.getAvailablePolicies();

        JDialog dialog =
                new JDialog(this, "Policy Selection", true);

        dialog.setSize(650, 400);
        dialog.setLocationRelativeTo(this);

        DefaultListModel<String> model =
                new DefaultListModel<>();

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
                detailsArea.setText("No policy will be active.\n\n" + "The city will evolve without modifiers.");
                return;
            }
            detailsArea.setText(PolicyDescriptionFormatter.format(policies.get(index - 1)));});

        JSplitPane splitPane =
                new JSplitPane(
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


    private int countOccupiedCells(CityGrid grid) {
        if (grid == null) {
            return 0;
        }

        int occupied = 0;
        int size = grid.getSize();

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Cell cell = grid.getCell(x, y);
                if (cell != null && !cell.isEmpty()) {
                    occupied++;
                }
            }
        }

        return occupied;
    }



    public void refreshFromModel() {
        refresh();
    }

    public void confirmBuild() {
        if (!buildSession.isActive()) {
            return;
        }

        if (buildSession.getSelectedX() < 0 || buildSession.getSelectedY() < 0)  {
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

        boolean placed = controller.placeEntity(buildSession.getSelectedX(), buildSession.getSelectedY(), entity);
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

    public void cancelBuild() {
        if (!buildSession.isActive()) {
            return;
        }
        exitBuildMode();
        refresh();
    }

    private void updateFooterButtonsForBuildMode() {

        if (buildButton != null) {
            buildButton.setEnabled(!buildSession.isActive());
        }

        if (buildPanel != null) {
            buildPanel.getConfirmButton()
                    .setEnabled(buildSession.isActive());

            buildPanel.getCancelButton()
                    .setEnabled(buildSession.isActive());
        }
    }


}
