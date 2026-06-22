
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class StatePanel extends JPanel {

    private JLabel cityValue;
    private JLabel tickValue;
    private JLabel budgetValue;
    private JLabel populationValue;
    private JLabel pollutionValue;
    private JLabel happinessValue;
    private JLabel occupiedValue;
    private JLabel activePolicyValue;
    private JButton activePolicyButton;

    public StatePanel() {

        setPreferredSize(new Dimension(320, 0));
        setBackground(Color.WHITE);

        setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 225, 230), 1, true),
                new EmptyBorder(16, 16, 16, 16)
        ));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel sectionTitle = new JLabel("City State");
        sectionTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(sectionTitle);
        add(Box.createVerticalStrut(16));

        cityValue = createStateLine("City:");
        tickValue = createStateLine("Tick:");
        budgetValue = createStateLine("Budget:");
        populationValue = createStateLine("Population:");
        pollutionValue = createStateLine("Pollution:");
        happinessValue = createStateLine("Happiness:");
        occupiedValue = createStateLine("Occupied cells:");

        add(Box.createVerticalStrut(18));

        JLabel policyTitle = new JLabel("Active Policy");
        policyTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        policyTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(policyTitle);
        add(Box.createVerticalStrut(8));

        activePolicyValue = new JLabel("No active policy");
        activePolicyValue.setFont(new Font("SansSerif", Font.PLAIN, 13));
        activePolicyValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(activePolicyValue);
        add(Box.createVerticalStrut(8));

        activePolicyButton = new JButton("Set policy");
        activePolicyButton.setFocusPainted(false);
        activePolicyButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        activePolicyButton.setBorder(new EmptyBorder(10, 16, 10, 16));
        activePolicyButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        activePolicyButton.setMaximumSize(new Dimension(140, 40));

        add(activePolicyButton);
        add(Box.createVerticalStrut(18));

        add(buildLegend());

        add(Box.createVerticalStrut(16));
    }

    private JLabel createStateLine(String text) {

        JLabel label = new JLabel(text + " ");
        label.setFont(new Font("SansSerif", Font.PLAIN, 15));
        label.setBorder(new EmptyBorder(0, 0, 8, 0));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(label);

        return label;
    }

    private JComponent buildLegend() {

        JPanel legend = new JPanel();
        legend.setOpaque(false);
        legend.setAlignmentX(Component.LEFT_ALIGNMENT);
        legend.setLayout(new BoxLayout(legend, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Legend");
        title.setFont(new Font("SansSerif", Font.BOLD, 13));

        legend.add(title);
        legend.add(Box.createVerticalStrut(4));

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

    public void updateState(
            String cityName,
            int tick,
            CityState state,
            int occupiedCells,
            String activePolicy
    ) {

        cityValue.setText("City: " + cityName);
        tickValue.setText("Tick: " + tick);

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

        occupiedValue.setText("Occupied cells: " + occupiedCells);

        boolean hasPolicy =
                activePolicy != null &&
                        !activePolicy.isBlank();

        activePolicyValue.setText(
                hasPolicy
                        ? activePolicy
                        : "No active policy"
        );


    }

    public JButton getActivePolicyButton() {
        return activePolicyButton;
    }
    
}