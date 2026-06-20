import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class EntityDetailsPanel extends JPanel {

    private JLabel selectedNameValue;
    private JLabel selectedTypeValue;
    private JTextArea selectedEffectsArea;

    public EntityDetailsPanel() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 225, 230), 1, true),
                new EmptyBorder(14, 16, 14, 16)
        ));
        setLayout(new BorderLayout(8, 8));

        JLabel title = new JLabel("Selected Entity");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

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

        add(info, BorderLayout.CENTER);
    }

    public void showEntity(PlaceableEntity entity) {
        if (entity == null) {
            clear();
            return;
        }

        selectedNameValue.setText("Name: " + safeString(entity.getName()));
        selectedTypeValue.setText("Type: " + safeString(entity.getType()));
        selectedEffectsArea.setText(readEffects(entity));
        selectedEffectsArea.setCaretPosition(0);
    }

    public void clear() {
        selectedNameValue.setText("Name: -");
        selectedTypeValue.setText("Type: -");
        selectedEffectsArea.setText("Click on a cell to see details here.");
    }

    private String readEffects(PlaceableEntity entity) {
        if (entity == null) {
            return "No entity selected.";
        }

        try {
            Object effects = entity.getClass().getMethod("getEffects").invoke(entity);
            if (effects != null) {
                return String.valueOf(effects);
            }
        } catch (Exception ignored) {
        }

        return "No effects information available.";
    }

    private String safeString(String value) {
        return (value == null || value.isBlank()) ? "N/A" : value;
    }
}