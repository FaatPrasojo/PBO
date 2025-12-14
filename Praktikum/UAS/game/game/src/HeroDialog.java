import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

// Asumsi kelas Player, BattleGameGUI, Skill, HealSkill, PiercingStrike, 
// AttackStrategy, FixedStrategy, dan LevelScaledStrategy sudah didefinisikan di tempat lain
// Untuk kompilasi, import harus mencakup ListSelectionModel
import javax.swing.ListSelectionModel;

class HeroDialog extends JDialog {
    private BattleGameGUI mainGUI;
    private Player player;
    private int index;
    
    private JTextField nameField, hpField, apField, levelField;
    private JComboBox<String> strategyCombo;
    private JList<String> skillList;
    private DefaultListModel<String> skillListModel;
    
    public HeroDialog(BattleGameGUI mainGUI, Player player, int index) {
        super((Frame) SwingUtilities.getWindowAncestor(mainGUI), 
              player == null ? "Add Hero" : "Edit Hero", true);
        
        this.mainGUI = mainGUI;
        this.player = player;
        this.index = index;
        
        setSize(450, 500);
        setLocationRelativeTo(mainGUI);
        setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("HP:"), gbc);
        gbc.gridx = 1;
        hpField = new JTextField(20);
        formPanel.add(hpField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Attack Power:"), gbc);
        gbc.gridx = 1;
        apField = new JTextField(20);
        formPanel.add(apField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Level:"), gbc);
        gbc.gridx = 1;
        levelField = new JTextField(20);
        formPanel.add(levelField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Strategy:"), gbc);
        gbc.gridx = 1;
        strategyCombo = new JComboBox<>(new String[]{"FixedStrategy", "LevelScaledStrategy"});
        formPanel.add(strategyCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(new JLabel("Available Skills (select multiple):"), gbc);
        
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        skillListModel = new DefaultListModel<>();
        skillList = new JList<>(skillListModel);
        
        // --- KODE SOLUSI UNTUK MULTI-SELEKSI SKILL ---
        skillList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        // ---------------------------------------------
        
        JScrollPane skillScrollPane = new JScrollPane(skillList);
        skillScrollPane.setPreferredSize(new Dimension(300, 150));
        formPanel.add(skillScrollPane, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> saveHero());
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Memuat daftar skill yang tersedia
        for (Skill skill : mainGUI.getAvailableSkills()) {
            skillListModel.addElement(skill.name());
        }
        
        // Mode Edit: Memuat data Hero yang ada
        if (player != null) {
            nameField.setText(player.getName());
            hpField.setText(String.valueOf(player.getMaxHealth()));
            apField.setText(String.valueOf(player.getAttackPower()));
            levelField.setText(String.valueOf(player.getLevel()));
            
            // Menandai skill yang sudah dimiliki Hero
            List<Integer> selectedIndices = new ArrayList<>();
            for (int i = 0; i < mainGUI.getAvailableSkills().size(); i++) {
                Skill availableSkill = mainGUI.getAvailableSkills().get(i);
                for (Skill playerSkill : player.getSkills()) {
                    if (availableSkill.name().equals(playerSkill.name())) {
                        selectedIndices.add(i);
                        break;
                    }
                }
            }
            int[] indices = selectedIndices.stream().mapToInt(Integer::intValue).toArray();
            skillList.setSelectedIndices(indices);
        }
    }
    
    private void saveHero() {
        try {
            String name = nameField.getText().trim();
            int hp = Integer.parseInt(hpField.getText().trim());
            int ap = Integer.parseInt(apField.getText().trim());
            int level = Integer.parseInt(levelField.getText().trim());
            
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty!", 
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // --- VALIDASI NILAI POSITIF (SESUAI TEST CASE 5) ---
            if (hp <= 0 || ap <= 0 || level <= 0) {
                 throw new IllegalArgumentException("HP, Attack Power, and Level must be positive numbers.");
            }
            // ----------------------------------------------------
            
            AttackStrategy strategy;
            if (strategyCombo.getSelectedItem().equals("FixedStrategy")) {
                strategy = new FixedStrategy();
            } else {
                // Asumsi LevelScaledStrategy membutuhkan parameter, misal 2
                strategy = new LevelScaledStrategy(2); 
            }
            
            // Membuat objek Player baru
            Player newPlayer = new Player(name, hp, ap, level, strategy);
            
            // Menambahkan skill yang dipilih
            List<String> selectedSkillNames = skillList.getSelectedValuesList();
            for (String skillName : selectedSkillNames) {
                for (Skill skill : mainGUI.getAvailableSkills()) {
                    if (skill.name().equals(skillName)) {
                        // Kloning skill
                        try {
                            if (skill instanceof HealSkill) {
                                // Format: "Heal (+10)"
                                int start = skill.name().indexOf("(") + 1;
                                int end = skill.name().indexOf(")");
                                String numStr = skill.name().substring(start, end).replace("+", "").trim();
                                int amount = Integer.parseInt(numStr);
                                newPlayer.addSkill(new HealSkill(amount));
                            } else if (skill instanceof PiercingStrike) {
                                // Format: "PiercingStrike (x1,2)" atau "PiercingStrike (x1.2)"
                                int start = skill.name().indexOf("(") + 2; // +2 untuk skip "(x"
                                int end = skill.name().indexOf(")");
                                String numStr = skill.name().substring(start, end).replace(",", "."); // Replace koma dengan titik
                                double multiplier = Double.parseDouble(numStr);
                                newPlayer.addSkill(new PiercingStrike(multiplier));
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Error parsing skill: " + skillName, 
                                "Skill Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        break;
                    }
                }
            }
            
            // Menyimpan atau mengupdate Hero
            if (index == -1) {
                mainGUI.addPlayer(newPlayer);
            } else {
                mainGUI.updatePlayer(index, newPlayer);
            }
            
            dispose();
            
        } catch (NumberFormatException ex) {
            // Ditangkap jika input field bukan angka
            JOptionPane.showMessageDialog(this, "Please enter valid numbers!", 
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            // Ditangkap dari validasi positif yang baru ditambahkan
            JOptionPane.showMessageDialog(this, ex.getMessage(), 
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}