import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CityGridPanel extends JPanel {

    // Pannello interno che contiene le celle della griglia.
    private final JPanel gridPanel;

    /**
     * Costruisce il pannello che ospita la griglia della città.
     */
    public CityGridPanel() {

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // Bordo esterno e spazio interno del pannello.
        setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 225, 230), 1, true),
                new EmptyBorder(16, 16, 16, 16)
        ));

        // Titolo della sezione.
        JLabel sectionTitle = new JLabel("City Grid");
        sectionTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(sectionTitle, BorderLayout.NORTH);

        // Contenitore vero e proprio della griglia.
        gridPanel = new JPanel();
        gridPanel.setBackground(new Color(250, 251, 252));

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Restituisce il pannello interno che contiene le celle.
     */
    public JPanel getGridPanel() {
        return gridPanel;
    }

    /**
     * Rimuove tutte le celle dalla griglia.
     */
    public void clearGrid() {
        gridPanel.removeAll();
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    /**
     * Mostra un messaggio quando non è disponibile alcuna griglia.
     */
    public void showNoGrid() {
        gridPanel.removeAll();
        gridPanel.setLayout(new BorderLayout());

        gridPanel.add(
                new JLabel("No grid available", SwingConstants.CENTER),
                BorderLayout.CENTER
        );

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    /**
     * Prepara il contenitore per una griglia quadrata della dimensione data.
     */
    public void prepareGrid(int size) {
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(size, size, 2, 2));
    }

    /**
     * Aggiunge una singola cella alla griglia.
     */
    public void addTile(JPanel tile) {
        gridPanel.add(tile);
    }

    /**
     * Aggiorna la visualizzazione della griglia.
     */
    public void refreshView() {
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    /**
     * Restituisce il colore associato a una determinata entità.
     */
    public static Color colorForEntity(PlaceableEntity entity) {

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
}