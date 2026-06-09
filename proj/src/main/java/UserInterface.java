import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;

public class UserInterface extends JFrame {

    private final Controller controller;

    private JLabel cityValue;
    private JLabel tickValue;
    private JLabel budgetValue;
    private JLabel populationValue;
    private JLabel pollutionValue;
    private JLabel happinessValue;
    private JLabel occupiedValue;

    private JLabel activePolicyValue;
    private JButton activePolicyButton;

    private JLabel selectedNameValue;
    private JLabel selectedTypeValue;
    private JTextArea selectedEffectsArea;

    private JPanel gridPanel;

    public UserInterface(Controller controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }

        this.controller = controller;

        setTitle("City Simulator Dashboard - " + safeString(controller.getCityName()));
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
        root.add(buildCenter(), BorderLayout.CENTER);
        root.add(buildFooter(), BorderLayout.SOUTH);
    }

    private JComponent buildHeader() {
        JPanel header = new JPanel(new BorderLayout(10, 10));
        header.setOpaque(false);

        JLabel title = new JLabel("City Simulator");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(33, 37, 41));

        JLabel subtitle = new JLabel("MVC view: city state + grid + details");
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

    private JComponent buildCenter() {
        JPanel center = new JPanel(new BorderLayout(16, 16));
        center.setOpaque(false);

        center.add(buildStatePanel(), BorderLayout.WEST);
        center.add(buildGridAndDetailsPanel(), BorderLayout.CENTER);

        return center;
    }

    private JComponent buildStatePanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(320, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 225, 230), 1, true),
                new EmptyBorder(16, 16, 16, 16)
        ));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel sectionTitle = new JLabel("City State");
        sectionTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(sectionTitle);
        panel.add(Box.createVerticalStrut(16));

        cityValue = createStateLine(panel, "City:");
        tickValue = createStateLine(panel, "Tick:");
        budgetValue = createStateLine(panel, "Budget:");
        populationValue = createStateLine(panel, "Population:");
        pollutionValue = createStateLine(panel, "Pollution:");
        happinessValue = createStateLine(panel, "Happiness:");
        occupiedValue = createStateLine(panel, "Occupied cells:");

        panel.add(Box.createVerticalStrut(18));

        JLabel policyTitle = new JLabel("Active Policy");
        policyTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        policyTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(policyTitle);
        panel.add(Box.createVerticalStrut(8));

        activePolicyValue = new JLabel("No active policy");
        activePolicyValue.setFont(new Font("SansSerif", Font.PLAIN, 13));
        activePolicyValue.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(activePolicyValue);
        panel.add(Box.createVerticalStrut(8));

        activePolicyButton = new JButton("View policy effects");
        styleButton(activePolicyButton);
        activePolicyButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        activePolicyButton.setEnabled(false);
        activePolicyButton.addActionListener(e -> showActivePolicyDetails());
        panel.add(activePolicyButton);

        panel.add(Box.createVerticalStrut(18));
        panel.add(buildLegend());
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JLabel createStateLine(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText + " ");
        label.setFont(new Font("SansSerif", Font.PLAIN, 15));
        label.setBorder(new EmptyBorder(0, 0, 8, 0));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        return label;
    }

    private JComponent buildLegend() {
        JPanel legend = new JPanel();
        legend.setAlignmentX(Component.LEFT_ALIGNMENT);
        legend.setOpaque(false);
        legend.setLayout(new BoxLayout(legend, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Legend");
        title.setFont(new Font("SansSerif", Font.BOLD, 15));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        legend.add(title);
        legend.add(Box.createVerticalStrut(8));

        legend.add(legendRow(new Color(198, 239, 206), "Park"));
        legend.add(legendRow(new Color(222, 226, 230), "Road"));
        legend.add(legendRow(new Color(191, 219, 254), "Residential"));
        legend.add(legendRow(new Color(255, 229, 180), "Commercial"));
        legend.add(legendRow(new Color(255, 204, 204), "Industrial"));
        legend.add(legendRow(new Color(221, 214, 254), "PowerPlant"));

        return legend;
    }

    private JComponent legendRow(Color color, String text) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel box = new JPanel();
        box.setPreferredSize(new Dimension(14, 14));
        box.setBackground(color);
        box.setBorder(new LineBorder(new Color(170, 170, 170), 1, true));

        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));

        row.add(box);
        row.add(label);
        return row;
    }

    private JComponent buildGridAndDetailsPanel() {
        JPanel center = new JPanel(new BorderLayout(14, 14));
        center.setOpaque(false);

        center.add(buildGridPanel(), BorderLayout.CENTER);
        center.add(buildSelectedDetailsPanel(), BorderLayout.SOUTH);

        return center;
    }

    private JComponent buildGridPanel() {
        JPanel wrapper = new JPanel(new BorderLayout(10, 10));
        wrapper.setBackground(Color.WHITE);
        wrapper.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 225, 230), 1, true),
                new EmptyBorder(16, 16, 16, 16)
        ));

        JLabel sectionTitle = new JLabel("City Grid");
        sectionTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        wrapper.add(sectionTitle, BorderLayout.NORTH);

        gridPanel = new JPanel();
        gridPanel.setBackground(new Color(250, 251, 252));

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        wrapper.add(scrollPane, BorderLayout.CENTER);

        return wrapper;
    }

    private JComponent buildSelectedDetailsPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 225, 230), 1, true),
                new EmptyBorder(14, 16, 14, 16)
        ));
        panel.setLayout(new BorderLayout(8, 8));

        JLabel title = new JLabel("Selected Entity");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(title, BorderLayout.NORTH);

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        selectedNameValue = new JLabel("Name: -");
        selectedTypeValue = new JLabel("Type: -");
        selectedNameValue.setFont(new Font("SansSerif", Font.PLAIN, 14));
        selectedTypeValue.setFont(new Font("SansSerif", Font.PLAIN, 14));
        selectedNameValue.setAlignmentX(Component.LEFT_ALIGNMENT);
        selectedTypeValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        selectedEffectsArea = new JTextArea(5, 20);
        selectedEffectsArea.setEditable(false);
        selectedEffectsArea.setLineWrap(true);
        selectedEffectsArea.setWrapStyleWord(true);
        selectedEffectsArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        selectedEffectsArea.setText("Click on a cell to see details here.");

        info.add(selectedNameValue);
        info.add(Box.createVerticalStrut(4));
        info.add(selectedTypeValue);
        info.add(Box.createVerticalStrut(8));
        info.add(new JScrollPane(selectedEffectsArea));

        panel.add(info, BorderLayout.CENTER);
        return panel;
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

        JButton refreshButton = new JButton("Refresh");
        JButton nextTickButton = new JButton("Advance Tick");

        styleButton(refreshButton);
        styleButton(nextTickButton);

        refreshButton.addActionListener(e -> refresh());
        nextTickButton.addActionListener(e -> {
            controller.nextTick();
            refresh();
        });

        right.add(refreshButton);
        right.add(nextTickButton);

        footer.add(left, BorderLayout.WEST);
        footer.add(right, BorderLayout.EAST);
        return footer;
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBorder(new EmptyBorder(10, 16, 10, 16));
    }

    private void refresh() {
        refreshState();
        refreshGrid();
        refreshSelectedDetails(null);
    }

    private void refreshState() {
        City city = controller.getCity();
        CityState state = controller.getCityState();
        CityGrid grid = controller.getGrid();

        cityValue.setText("City: " + safeString(controller.getCityName()));
        tickValue.setText("Tick: " + controller.getCurrentTick());

        if (state != null) {
            budgetValue.setText("Budget: " + state.getBudget());
            populationValue.setText("Population: " + state.getPopulation());
            pollutionValue.setText("Pollution: " + state.getPollution());
            happinessValue.setText("Happiness: " + state.getHappiness());
        } else {
            budgetValue.setText("Budget: N/A");
            populationValue.setText("Population: N/A");
            pollutionValue.setText("Pollution: N/A");
            happinessValue.setText("Happiness: N/A");
        }

        occupiedValue.setText("Occupied cells: " + countOccupiedCells(grid));

        String policyText = getActivePolicyText();
        boolean hasPolicy = policyText != null && !policyText.isBlank();
        activePolicyValue.setText(hasPolicy ? policyText : "No active policy");
        activePolicyButton.setEnabled(hasPolicy);
        activePolicyButton.setText(hasPolicy ? "View policy effects" : "No active policy");

        if (city != null) {
            setTitle("City Simulator Dashboard - " + safeString(city.getName()));
        }
    }

    private void refreshGrid() {
        CityGrid grid = controller.getGrid();
        gridPanel.removeAll();

        if (grid == null) {
            gridPanel.setLayout(new BorderLayout());
            gridPanel.add(new JLabel("No grid available", SwingConstants.CENTER), BorderLayout.CENTER);
            gridPanel.revalidate();
            gridPanel.repaint();
            return;
        }

        int size = grid.getSize();
        gridPanel.setLayout(new GridLayout(size, size, 2, 2));

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Cell cell = grid.getCell(x, y);
                JPanel tile = createTile(cell, x, y);
                gridPanel.add(tile);
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
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
            tile.setBackground(colorForEntity(entity));
            tile.setToolTipText(entity.getName() + " (" + entity.getType() + ")");

            tile.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    refreshSelectedDetails(entity);
                }
            });
        } else {
            tile.setBackground(Color.WHITE);
            tile.setToolTipText("Empty cell");
            tile.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    refreshSelectedDetails(null);
                }
            });
        }

        tile.add(symbolLabel, BorderLayout.CENTER);
        return tile;
    }

    private void refreshSelectedDetails(PlaceableEntity entity) {
        if (entity == null) {
            selectedNameValue.setText("Name: -");
            selectedTypeValue.setText("Type: -");
            selectedEffectsArea.setText("Click on a cell to see details here.");
            return;
        }

        selectedNameValue.setText("Name: " + safeString(entity.getName()));
        selectedTypeValue.setText("Type: " + safeString(entity.getType()));
        selectedEffectsArea.setText(readEffects(entity));
        selectedEffectsArea.setCaretPosition(0);
    }

    private void showActivePolicyDetails() {
        String policyText = getActivePolicyDetails();
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

    private String readEffects(PlaceableEntity entity) {
        if (entity == null) {
            return "No entity selected.";
        }

        Object effects = invokeNoArgMethod(entity, "getEffects");
        if (effects != null) {
            return String.valueOf(effects);
        }

        return "No effects information available.";
    }

    private String getActivePolicyText() {
        Object policy = getActivePolicyObject();
        if (policy == null) {
            return null;
        }

        Object name = invokeNoArgMethod(policy, "getName");
        if (name != null) {
            return String.valueOf(name);
        }

        return String.valueOf(policy);
    }

    private String getActivePolicyDetails() {
        Object policy = getActivePolicyObject();
        if (policy == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        Object name = invokeNoArgMethod(policy, "getName");
        Object type = invokeNoArgMethod(policy, "getType");
        Object effects = invokeNoArgMethod(policy, "getEffects");

        if (name != null) {
            sb.append("Name: ").append(name).append("\n");
        }
        if (type != null) {
            sb.append("Type: ").append(type).append("\n");
        }
        if (effects != null) {
            sb.append("\nEffects:\n").append(effects);
        }

        if (sb.length() == 0) {
            sb.append(String.valueOf(policy));
        }

        return sb.toString();
    }

    private Object getActivePolicyObject() {
        City city = controller.getCity();
        if (city == null) {
            return null;
        }

        Object policy = invokeNoArgMethod(city, "getActivePolicy");
        if (policy != null) {
            return policy;
        }

        policy = invokeNoArgMethod(city, "getPolicy");
        if (policy != null) {
            return policy;
        }

        return null;
    }

    private Object invokeNoArgMethod(Object target, String methodName) {
        if (target == null || methodName == null || methodName.isBlank()) {
            return null;
        }

        try {
            Method method = target.getClass().getMethod(methodName);
            return method.invoke(target);
        } catch (Exception ignored) {
            return null;
        }
    }

    private Color colorForEntity(PlaceableEntity entity) {
        if (entity instanceof Park) {
            return new Color(198, 239, 206);
        }
        if (entity instanceof Road) {
            return new Color(222, 226, 230);
        }
        if (entity instanceof ResidentialBuilding) {
            return new Color(191, 219, 254);
        }
        if (entity instanceof CommercialBuilding) {
            return new Color(255, 229, 180);
        }
        if (entity instanceof IndustrialBuilding) {
            return new Color(255, 204, 204);
        }
        if (entity instanceof PowerPlant) {
            return new Color(221, 214, 254);
        }
        return new Color(233, 236, 239);
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

    private String safeString(String value) {
        return (value == null || value.isBlank()) ? "N/A" : value;
    }

    public void refreshFromModel() {
        refresh();
    }

}
