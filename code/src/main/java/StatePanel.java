import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class StatePanel extends JPanel {

    // Costanti grafiche condivise dal pannello.
    private static final String FONT_NAME = "SansSerif";
    private static final Color PANEL_BACKGROUND = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(220, 225, 230);
    private static final Color LEGEND_BORDER_COLOR = new Color(170, 170, 170);

    private static final int PANEL_WIDTH = 320;
    private static final int BUTTON_WIDTH = 140;
    private static final int BUTTON_HEIGHT = 40;

    // Etichette che mostrano lo stato corrente della città.
    private final JLabel cityValue;
    private final JLabel tickValue;
    private final JLabel budgetValue;
    private final JLabel populationValue;
    private final JLabel pollutionValue;
    private final JLabel happinessValue;
    private final JLabel occupiedValue;

    // Componenti dedicati alla gestione delle policy.
    private final JLabel activePolicyValue;
    private final JButton activePolicyButton;
    private JButton policyDetailsButton;

    /**
     * Costruisce il pannello laterale che mostra
     * lo stato della città e i principali controlli utente.
     */
    public StatePanel() {

        // Configurazione grafica generale del pannello.
        setPreferredSize(new Dimension(PANEL_WIDTH, 0));
        setBackground(PANEL_BACKGROUND);

        setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(16, 16, 16, 16)
        ));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel sectionTitle = new JLabel("City State");
        sectionTitle.setFont(new Font(FONT_NAME, Font.BOLD, 18));
        sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(sectionTitle);
        add(Box.createVerticalStrut(16));

        // Sezione che mostra le statistiche della città.
        cityValue = addStateLine("City:");
        tickValue = addStateLine("Tick:");
        budgetValue = addStateLine("Budget:");
        populationValue = addStateLine("Population:");
        pollutionValue = addStateLine("Pollution:");
        happinessValue = addStateLine("Happiness:");
        occupiedValue = addStateLine("Occupied cells:");

        add(Box.createVerticalStrut(18));

        // Sezione dedicata alla policy attualmente attiva.
        JLabel policyTitle = new JLabel("Active Policy");
        policyTitle.setFont(new Font(FONT_NAME, Font.BOLD, 15));
        policyTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(policyTitle);
        add(Box.createVerticalStrut(8));

        activePolicyValue = new JLabel("No active policy");
        activePolicyValue.setFont(new Font(FONT_NAME, Font.PLAIN, 13));
        activePolicyValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(activePolicyValue);
        add(Box.createVerticalStrut(8));

        activePolicyButton = createActionButton("Set policy");
        policyDetailsButton = createActionButton("View details");

        JPanel policyButtons = new JPanel();
        policyButtons.setOpaque(false);
        policyButtons.setAlignmentX(Component.LEFT_ALIGNMENT);

        policyButtons.setLayout(new BoxLayout(policyButtons, BoxLayout.X_AXIS));

        policyButtons.add(activePolicyButton);
        policyButtons.add(Box.createHorizontalStrut(8));
        policyButtons.add(policyDetailsButton);

        add(policyButtons);

        add(Box.createVerticalStrut(18));

        // Legenda dei colori utilizzati nella griglia.
        add(buildLegend());

        add(Box.createVerticalStrut(16));

        // Mantiene il contenuto allineato verso l'alto.
        add(Box.createVerticalGlue());
    }

    /**
     * Crea una riga dello stato e la aggiunge al pannello.
     * La label verrà aggiornata durante la simulazione.
     */
    private JLabel addStateLine(String text) {

        JLabel label = new JLabel(text + " ");
        label.setFont(new Font(FONT_NAME, Font.PLAIN, 15));
        label.setBorder(new EmptyBorder(0, 0, 8, 0));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(label);

        return label;
    }

    /**
     * Crea un pulsante con lo stile standard dell'interfaccia.
     */
    private JButton createActionButton(String text) {

        JButton button = new JButton(text);

        button.setFocusPainted(false);
        button.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        button.setBorder(new EmptyBorder(10, 16, 10, 16));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        return button;
    }

    /**
     * Costruisce la legenda che associa ogni colore
     * al relativo tipo di entità presente nella città.
     */
    private JComponent buildLegend() {

        JPanel legend = new JPanel();
        legend.setOpaque(false);
        legend.setAlignmentX(Component.LEFT_ALIGNMENT);
        legend.setLayout(new BoxLayout(legend, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Legend");
        title.setFont(new Font(FONT_NAME, Font.BOLD, 15));

        legend.add(title);
        legend.add(Box.createVerticalStrut(8));

        for (String type : EntityFactory.getAvailableEntityTypes()) {
            legend.add(legendRow(EntityFactory.getColor(type), type));
        }

        return legend;
    }

    /**
     * Crea una singola riga della legenda composta
     * da un quadrato colorato e dal relativo nome.
     */
    private JComponent legendRow(Color color, String text) {

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        row.setOpaque(false);

        JPanel box = new JPanel();
        box.setPreferredSize(new Dimension(14, 14));
        box.setBackground(color);
        box.setBorder(new LineBorder(LEGEND_BORDER_COLOR, 1, true));

        JLabel label = new JLabel(text);
        label.setFont(new Font(FONT_NAME, Font.PLAIN, 13));

        row.add(box);
        row.add(label);

        return row;
    }

    /**
     * Aggiorna tutte le informazioni mostrate nel pannello
     * in base allo stato corrente della simulazione.
     */
    public void updateState(
            String cityName,
            int tick,
            CityState state,
            int occupiedCells,
            String activePolicy
    ) {

        cityValue.setText("City: " + safeText(cityName));
        tickValue.setText("Tick: " + tick);

        // Aggiorna le statistiche principali della città.
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

        // Aggiorna il nome della policy attiva.
        boolean hasPolicy =
                activePolicy != null &&
                        !activePolicy.isBlank();

        activePolicyValue.setText(
                hasPolicy
                        ? activePolicy
                        : "No active policy"
        );
    }

    /**
     * Evita di mostrare stringhe nulle o vuote nella UI.
     */
    private String safeText(String value) {
        return (value == null || value.isBlank())
                ? "N/A"
                : value;
    }

    /**
     * Espone il pulsante per consentire al controller
     * di registrare gli ActionListener.
     */
    public JButton getActivePolicyButton() {
        return activePolicyButton;
    }

    public JButton getPolicyDetailsButton() {
        return policyDetailsButton;
    }
}