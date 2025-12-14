import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

class EnemyDialog extends JDialog {
    private BattleGameGUI mainGUI;
    private Enemy enemy;
    private int index;
    
    private JTextField nameField, hpField, apField, threatField;
    private JComboBox<String> typeCombo, strategyCombo;
    
    public EnemyDialog(BattleGameGUI mainGUI, Enemy enemy, int index) {
        super((Frame) SwingUtilities.getWindowAncestor(mainGUI), 
              enemy == null ? "Add Enemy" : "Edit Enemy", true);
        
        this.mainGUI = mainGUI;
        this.enemy = enemy;
        this.index = index;
        
        setSize(400, 350);
        setLocationRelativeTo(mainGUI);
        setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        typeCombo = new JComboBox<>(new String[]{"Monster", "Boss"});
        formPanel.add(typeCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("HP:"), gbc);
        gbc.gridx = 1;
        hpField = new JTextField(20);
        formPanel.add(hpField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Attack Power:"), gbc);
        gbc.gridx = 1;
        apField = new JTextField(20);
        formPanel.add(apField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Threat Level (1-5):"), gbc);
        gbc.gridx = 1;
        threatField = new JTextField(20);
        formPanel.add(threatField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Strategy:"), gbc);
        gbc.gridx = 1;
        strategyCombo = new JComboBox<>(new String[]{"FixedStrategy", "LevelScaledStrategy"});
        formPanel.add(strategyCombo, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> saveEnemy());
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        if (enemy != null) {
            typeCombo.setSelectedItem(enemy instanceof BossMonster ? "Boss" : "Monster");
            nameField.setText(enemy.getName());
            hpField.setText(String.valueOf(enemy.getMaxHealth()));
            apField.setText(String.valueOf(enemy.getAttackPower()));
            threatField.setText(String.valueOf(enemy.getThreatLevel()));
        }
    }
    
    private void saveEnemy() {
        try {
            String name = nameField.getText().trim();
            int hp = Integer.parseInt(hpField.getText().trim());
            int ap = Integer.parseInt(apField.getText().trim());
            int threat = Integer.parseInt(threatField.getText().trim());
            
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty!", 
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            AttackStrategy strategy;
            if (strategyCombo.getSelectedItem().equals("FixedStrategy")) {
                strategy = new FixedStrategy();
            } else {
                strategy = new LevelScaledStrategy(2);
            }
            
            Enemy newEnemy;
            if (typeCombo.getSelectedItem().equals("Boss")) {
                newEnemy = new BossMonster(name, hp, ap, threat, strategy);
            } else {
                newEnemy = new Monster(name, hp, ap, threat, strategy);
            }
            
            if (index == -1) {
                mainGUI.addEnemy(newEnemy);
            } else {
                mainGUI.updateEnemy(index, newEnemy);
            }
            
            dispose();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers!", 
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), 
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}