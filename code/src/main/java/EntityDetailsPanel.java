import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class EntityDetailsPanel extends JPanel {

    // Informazioni dell'entità attualmente selezionata.
    private JLabel selectedNameValue;
    private JLabel selectedTypeValue;
    private JTextArea selectedEffectsArea;

    /**
     * Costruisce il pannello che mostra i dettagli
     * dell'entità selezionata nella griglia.
     */
    public EntityDetailsPanel() {

        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 225, 230), 1, true),
                new EmptyBorder(14, 16, 14, 16)
        ));
        setLayout(new BorderLayout(8, 8));

        // Titolo della sezione.
        JLabel title = new JLabel("Selected Entity");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        // Campi che mostrano nome e tipo dell'entità.
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        selectedNameValue = new JLabel("Name: -");
        selectedTypeValue = new JLabel("Type: -");

        selectedNameValue.setFont(new Font("SansSerif", Font.PLAIN, 14));
        selectedTypeValue.setFont(new Font("SansSerif", Font.PLAIN, 14));
        selectedNameValue.setAlignmentX(Component.LEFT_ALIGNMENT);
        selectedTypeValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Area testuale utilizzata per mostrare gli effetti.
        selectedEffectsArea = new JTextArea(5, 20);
        selectedEffectsArea.setEditable(false);
        selectedEffectsArea.setLineWrap(true);
        selectedEffectsArea.setWrapStyleWord(true);
        selectedEffectsArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        selectedEffectsArea.setText("Click on a cell to see details here.");

        JScrollPane scrollPane = new JScrollPane(selectedEffectsArea);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        info.add(selectedNameValue);
        info.add(Box.createVerticalStrut(4));
        info.add(selectedTypeValue);
        info.add(Box.createVerticalStrut(8));
        info.add(scrollPane);

        add(info, BorderLayout.CENTER);
    }

    /**
     * Aggiorna il pannello con i dati
     * dell'entità selezionata.
     */
    public void showEntity(PlaceableEntity entity) {
        if (entity == null) {
            clear();
            return;
        }

        selectedNameValue.setText("Name: " + UIUtils.safeString(entity.getName()));
        selectedTypeValue.setText("Type: " + UIUtils.safeString(entity.getType()));
        selectedEffectsArea.setText(UIUtils.readEffects(entity));
        selectedEffectsArea.setCaretPosition(0);
    }

    /**
     * Ripristina il pannello allo stato iniziale.
     */
    public void clear() {
        selectedNameValue.setText("Name: -");
        selectedTypeValue.setText("Type: -");
        selectedEffectsArea.setText("Click on a cell to see details here.");
        selectedEffectsArea.setCaretPosition(0);
    }
}