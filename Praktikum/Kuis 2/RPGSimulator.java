import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class RPGSimulator extends JFrame {

    private JTextField txtHeroName, txtHeroLevel;
    private JTextField txtSkillName, txtSkillMultiplier;
    private JTextField txtEnemyName, txtEnemyHP, txtEnemyAP;
    private JTextArea txtOutput;

    public RPGSimulator() {
        setTitle("RPG Simulator - Battle Loop Edition");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Edit Hero", createEditHeroPanel());
        tabbedPane.addTab("Edit Enemy", createEditEnemyPanel());
        add(tabbedPane, BorderLayout.CENTER);

        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel createEditHeroPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel statusPanel = new JPanel(new GridBagLayout());
        statusPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Hero Status",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        statusPanel.add(new JLabel("Nama Hero"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtHeroName = new JTextField("Makima");
        statusPanel.add(txtHeroName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        statusPanel.add(new JLabel("Level"), gbc);
        gbc.gridx = 1;
        txtHeroLevel = new JTextField("99");
        statusPanel.add(txtHeroLevel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        JButton btnSave = new JButton("Save Hero");
        btnSave.addActionListener(e -> actionSaveHero());
        statusPanel.add(btnSave, gbc);

        JPanel skillPanel = new JPanel(new GridBagLayout());
        skillPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Edit Skill Hero",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12)));

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        skillPanel.add(new JLabel("Nama Skill"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtSkillName = new JTextField("Judgement Hammer");
        skillPanel.add(txtSkillName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        skillPanel.add(new JLabel("Multiplier"), gbc);
        gbc.gridx = 1;
        txtSkillMultiplier = new JTextField("100");
        skillPanel.add(txtSkillMultiplier, gbc);

        JPanel btnGroupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
        btnAdd.addActionListener(e -> actionAddSkill());
        btnDelete.addActionListener(e -> actionDeleteSkill());
        btnGroupPanel.add(btnAdd);
        btnGroupPanel.add(btnDelete);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        skillPanel.add(btnGroupPanel, gbc);

        mainPanel.add(statusPanel);
        mainPanel.add(skillPanel);
        return mainPanel;
    }

    private JPanel createEditEnemyPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel statusPanel = new JPanel(new GridBagLayout());
        statusPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Enemy Status (Boss)", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        statusPanel.add(new JLabel("Nama Enemy:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtEnemyName = new JTextField("Drake");
        statusPanel.add(txtEnemyName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        statusPanel.add(new JLabel("Max HP:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtEnemyHP = new JTextField("300");
        statusPanel.add(txtEnemyHP, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        statusPanel.add(new JLabel("Attack Power (AP):"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtEnemyAP = new JTextField("25");
        statusPanel.add(txtEnemyAP, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnSaveEnemy = new JButton("Save Enemy Stats");
        btnSaveEnemy.setPreferredSize(new Dimension(150, 35));
        btnSaveEnemy.addActionListener(e -> actionSaveEnemy());
        statusPanel.add(btnSaveEnemy, gbc);

        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.weightx = 1.0;
        mainGbc.weighty = 1.0;
        mainGbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(statusPanel, mainGbc);
        return mainPanel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel btnContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSimulasi = new JButton("Jalankan Simulasi (Loop)");
        btnSimulasi.setFont(new Font("Arial", Font.BOLD, 14));
        btnSimulasi.setPreferredSize(new Dimension(220, 40));
        btnSimulasi.addActionListener(e -> actionRunSimulation());
        btnContainer.add(btnSimulasi);

        txtOutput = new JTextArea(15, 50);
        txtOutput.setEditable(false);
        txtOutput.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtOutput);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Output Log"));

        panel.add(btnContainer, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void actionSaveHero() {
        logToOutput("> Hero saved: " + txtHeroName.getText() + " (Lv " + txtHeroLevel.getText() + ")");
    }

    private void actionSaveEnemy() {
        logToOutput("> Enemy saved: " + txtEnemyName.getText() + " | HP: " + txtEnemyHP.getText() + " | AP: "
                + txtEnemyAP.getText());
    }

    private void actionAddSkill() {
        logToOutput("> Skill '" + txtSkillName.getText() + "' added.");
    }

    private void actionDeleteSkill() {
        logToOutput("> Skill '" + txtSkillName.getText() + "' deleted.");
    }

    private void actionRunSimulation() {
        String heroName = txtHeroName.getText().isEmpty() ? "Hero" : txtHeroName.getText();
        String enemyName = txtEnemyName.getText().isEmpty() ? "Unknown Monster" : txtEnemyName.getText();

        int heroMaxHP = 200;
        int enemyMaxHP = 300;
        int enemyAP = 25;

        try {
            enemyMaxHP = Integer.parseInt(txtEnemyHP.getText());
            enemyAP = Integer.parseInt(txtEnemyAP.getText());
        } catch (NumberFormatException ex) {
            logToOutput("ERROR: HP/AP Enemy harus angka valid! Menggunakan default.");
        }

        int heroCurrentHP = heroMaxHP;
        int enemyCurrentHP = enemyMaxHP;

        int heroDmg = 45;

        StringBuilder simLog = new StringBuilder();
        simLog.append("\n=========================================================\n");
        simLog.append("BATTLE START: " + heroName + " VS " + enemyName + "\n");
        simLog.append("=========================================================\n");
        simLog.append("Stats Awal:\n");
        simLog.append(" > [" + heroName + "] HP: " + heroCurrentHP + " | Dmg: " + heroDmg + "\n");
        simLog.append(" > [" + enemyName + "] HP: " + enemyCurrentHP + " | AP: " + enemyAP + "\n\n");

        int turn = 1;
        boolean battleOngoing = true;

        while (battleOngoing) {
            simLog.append("--- TURN " + turn + " ---\n");

            simLog.append("1. [" + heroName + "] menyerang!\n");
            enemyCurrentHP -= heroDmg;
            simLog.append("   " + enemyName + " terkena " + heroDmg + " damage.\n");

            if (enemyCurrentHP <= 0) {
                enemyCurrentHP = 0;
                simLog.append("   " + enemyName + " HP menjadi 0.\n");
                battleOngoing = false;
                break;
            } else {
                simLog.append("   Sisa HP " + enemyName + ": " + enemyCurrentHP + "\n");
            }

            simLog.append("2. [" + enemyName + "] membalas serangan!\n");
            int shieldBlock = 10;
            int dmgToHero = Math.max(0, enemyAP - shieldBlock);

            heroCurrentHP -= dmgToHero;
            simLog.append(
                    "   (Raw: " + enemyAP + " - Shield: " + shieldBlock + ") = " + dmgToHero + " damage masuk.\n");

            if (heroCurrentHP <= 0) {
                heroCurrentHP = 0;
                simLog.append("   " + heroName + " HP menjadi 0.\n");
                battleOngoing = false;
            } else {
                simLog.append("   Sisa HP " + heroName + ": " + heroCurrentHP + "\n");
            }

            simLog.append("\n");
            turn++;
        }

        simLog.append("=========================================================\n");
        simLog.append("HASIL PERTARUNGAN:\n");

        if (heroCurrentHP > 0) {
            simLog.append("🏆 PEMENANG: " + heroName + " (Sisa HP: " + heroCurrentHP + ")\n");
            simLog.append("💀 KALAH   : " + enemyName + " (HP: 0)\n");
        } else {
            simLog.append("🏆 PEMENANG: " + enemyName + " (Sisa HP: " + enemyCurrentHP + ")\n");
            simLog.append("💀 KALAH   : " + heroName + " (HP: 0)\n");
        }
        simLog.append("=========================================================\n");

        logToOutput(simLog.toString());
    }

    private void logToOutput(String text) {
        txtOutput.append(text + "\n");
        txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
            }
            new RPGSimulator().setVisible(true);
        });
    }
}