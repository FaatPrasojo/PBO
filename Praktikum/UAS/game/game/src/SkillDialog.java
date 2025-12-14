import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class SkillDialog extends JDialog {
    private BattleGameGUI mainGUI;
    private JComboBox<String> typeCombo;
    private JTextField valueField;
    
    public SkillDialog(BattleGameGUI mainGUI) {
        super((Frame) SwingUtilities.getWindowAncestor(mainGUI), "Add Skill", true);
        
        this.mainGUI = mainGUI;
        
        setSize(350, 250);
        setLocationRelativeTo(mainGUI);
        setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Skill Type:"), gbc);
        gbc.gridx = 1;
        typeCombo = new JComboBox<>(new String[]{"HealSkill", "PiercingStrike"});
        formPanel.add(typeCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Value:"), gbc);
        gbc.gridx = 1;
        valueField = new JTextField(15);
        formPanel.add(valueField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        JLabel helpLabel = new JLabel("<html><i>For HealSkill: amount (e.g., 15)<br>" +
                                          "For PiercingStrike: multiplier (e.g., 1.2)</i></html>");
        helpLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        formPanel.add(helpLabel, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> saveSkill());
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void saveSkill() {
        try {
            String type = (String) typeCombo.getSelectedItem();
            String valueStr = valueField.getText().trim();
            
            if (valueStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Value cannot be empty!",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Skill newSkill = null;
            if (type.equals("HealSkill")) {
                int amount = Integer.parseInt(valueStr);
                if (amount <= 0) throw new IllegalArgumentException("Heal amount must be positive.");
                newSkill = new HealSkill(amount);
            } else if (type.equals("PiercingStrike")) {
                double multiplier = Double.parseDouble(valueStr);
                if (multiplier <= 1.0) throw new IllegalArgumentException("Multiplier must be > 1.0.");
                newSkill = new PiercingStrike(multiplier);
            }
            
            if (newSkill != null) {
                for (Skill existingSkill : mainGUI.getAvailableSkills()) {
                    if (existingSkill.name().equals(newSkill.name())) {
                        JOptionPane.showMessageDialog(this, "Skill with the same value already exists: " + newSkill.name(),
                            "Duplicate Skill", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                mainGUI.addSkill(newSkill);
                dispose();
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for value!",
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}