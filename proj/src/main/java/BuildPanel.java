
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class BuildPanel extends JPanel {

    private final JLabel buildCellValue;

    private final JList<String> buildEntityList;

    private final JLabel buildPreviewNameValue;
    private final JLabel buildPreviewTypeValue;
    private final JTextArea buildPreviewEffectsArea;

    private final JButton confirmButton;
    private final JButton cancelButton;

    public BuildPanel() {

        setLayout(new BorderLayout(12, 12));
        setBackground(Color.WHITE);

        setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 225, 230), 1, true),
                new EmptyBorder(16, 16, 16, 16)
        ));

        JLabel title = new JLabel("Build Mode");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        JPanel topInfo = new JPanel();
        topInfo.setOpaque(false);
        topInfo.setLayout(new BoxLayout(topInfo, BoxLayout.Y_AXIS));

        buildCellValue =
                new JLabel("Selected cell: click an empty cell on the grid");

        buildCellValue.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );

        topInfo.add(buildCellValue);
        topInfo.add(Box.createVerticalStrut(6));

        JLabel chooseLabel = new JLabel("Choose an entity");
        chooseLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        topInfo.add(chooseLabel);

        add(topInfo, BorderLayout.WEST);

        JPanel selectionPanel = new JPanel(new BorderLayout(10, 10));
        selectionPanel.setOpaque(false);
        selectionPanel.setPreferredSize(new Dimension(250, 0));

        DefaultListModel<String> model =
                new DefaultListModel<>();

        model.addElement("Park");
        model.addElement("Road");
        model.addElement("PowerPlant");
        model.addElement("ResidentialBuilding");
        model.addElement("CommercialBuilding");
        model.addElement("IndustrialBuilding");

        buildEntityList = new JList<>(model);
        buildEntityList.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        buildEntityList.setVisibleRowCount(6);

        selectionPanel.add(
                new JScrollPane(buildEntityList),
                BorderLayout.CENTER
        );

        add(selectionPanel, BorderLayout.CENTER);

        JPanel previewPanel = new JPanel();
        previewPanel.setOpaque(false);
        previewPanel.setLayout(new BorderLayout(8, 8));
        previewPanel.setPreferredSize(new Dimension(340, 0));

        JLabel previewTitle =
                new JLabel("Entity Preview");

        previewTitle.setFont(
                new Font("SansSerif", Font.BOLD, 16)
        );

        previewPanel.add(
                previewTitle,
                BorderLayout.NORTH
        );

        JPanel previewInfo = new JPanel();
        previewInfo.setOpaque(false);
        previewInfo.setLayout(
                new BoxLayout(previewInfo, BoxLayout.Y_AXIS)
        );

        buildPreviewNameValue = new JLabel("Name: -");
        buildPreviewTypeValue = new JLabel("Type: -");

        buildPreviewNameValue.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );

        buildPreviewTypeValue.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );

        buildPreviewEffectsArea =
                new JTextArea(8, 18);

        buildPreviewEffectsArea.setEditable(false);
        buildPreviewEffectsArea.setLineWrap(true);
        buildPreviewEffectsArea.setWrapStyleWord(true);

        buildPreviewEffectsArea.setFont(
                new Font("Monospaced", Font.PLAIN, 13)
        );

        buildPreviewEffectsArea.setText(
                "Select an entity to see its effects."
        );

        previewInfo.add(buildPreviewNameValue);
        previewInfo.add(Box.createVerticalStrut(4));
        previewInfo.add(buildPreviewTypeValue);
        previewInfo.add(Box.createVerticalStrut(8));

        previewInfo.add(
                new JScrollPane(buildPreviewEffectsArea)
        );

        previewPanel.add(
                previewInfo,
                BorderLayout.CENTER
        );

        add(previewPanel, BorderLayout.EAST);

        JPanel bottomButtons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                12,
                                0
                        )
                );

        bottomButtons.setOpaque(false);

        confirmButton = new JButton("Confirm");
        cancelButton = new JButton("Cancel");

        bottomButtons.add(confirmButton);
        bottomButtons.add(cancelButton);

        add(bottomButtons, BorderLayout.SOUTH);
    }

    public JList<String> getEntityList() {
        return buildEntityList;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public void clearEntitySelection() {
        buildEntityList.clearSelection();
    }

    public String getSelectedEntityType() {
        return buildEntityList.getSelectedValue();
    }

    public void setSelectedCell(int x, int y) {
        buildCellValue.setText(
                "Selected cell: (" + x + ", " + y + ")"
        );
    }

    public void clearCellSelection() {
        buildCellValue.setText("Selected cell: -");
    }

    public void clearPreview() {

        buildPreviewNameValue.setText("Name: -");
        buildPreviewTypeValue.setText("Type: -");

        buildPreviewEffectsArea.setText(
                "Select an entity to see its effects."
        );
    }

    public void showNoPreview() {

        buildPreviewNameValue.setText("Name: -");
        buildPreviewTypeValue.setText("Type: -");

        buildPreviewEffectsArea.setText(
                "No preview available."
        );
    }



    public void showPreview(PlaceableEntity entity) {

            buildPreviewNameValue.setText(
                    "Name: " + UIUtils.safeString(entity.getName())
            );

            buildPreviewTypeValue.setText(
                    "Type: " + UIUtils.safeString(entity.getType())
            );

            buildPreviewEffectsArea.setText(
                    UIUtils.readEffects(entity)
            );

            buildPreviewEffectsArea.setCaretPosition(0);
        }

}

