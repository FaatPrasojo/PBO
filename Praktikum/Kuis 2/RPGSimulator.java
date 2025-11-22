import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class RPGSimulator extends JFrame {

    // Komponen UI Global agar bisa diakses oleh method event
    // --- HERO COMPONENTS ---
    private JTextField txtHeroName, txtHeroLevel;
    private JTextField txtSkillName, txtSkillMultiplier;

    // --- ENEMY COMPONENTS (BARU) ---
    private JTextField txtEnemyName, txtEnemyHP, txtEnemyAP;

    private JTextArea txtOutput;

    public RPGSimulator() {
        // 1. Konfigurasi Frame Utama
        setTitle("RPG Simulator - Hero vs Enemy Edition");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 2. Membuat Tab Pane (Tab Atas)
        JTabbedPane tabbedPane = new JTabbedPane();

        // Panel "Edit Hero"
        JPanel panelEditHero = createEditHeroPanel();
        tabbedPane.addTab("Edit Hero", panelEditHero);

        // Panel "Edit Enemy" (SUDAH DI-UPDATE)
        JPanel panelEditEnemy = createEditEnemyPanel();
        tabbedPane.addTab("Edit Enemy", panelEditEnemy);

        // Menambahkan Tab ke Frame
        add(tabbedPane, BorderLayout.CENTER);

        // 3. Membuat Bagian Bawah (Tombol Simulasi & Output)
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // -------------------------------------------------------
    // PANEL HERO (Sama seperti sebelumnya)
    // -------------------------------------------------------
    private JPanel createEditHeroPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // === SUB-PANEL KIRI: HeroStatus ===
        JPanel statusPanel = new JPanel(new GridBagLayout());
        statusPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Hero Status",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nama Hero
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        statusPanel.add(new JLabel("Nama Hero"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtHeroName = new JTextField("Makima");
        statusPanel.add(txtHeroName, gbc);

        // Level Hero
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        statusPanel.add(new JLabel("Level"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtHeroLevel = new JTextField("99");
        statusPanel.add(txtHeroLevel, gbc);

        // Button Save
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        JButton btnSave = new JButton("Save Hero");
        btnSave.setPreferredSize(new Dimension(120, 30));
        btnSave.addActionListener(e -> actionSaveHero());
        statusPanel.add(btnSave, gbc);

        // === SUB-PANEL KANAN: Edit Skill ===
        JPanel skillPanel = new JPanel(new GridBagLayout());
        skillPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Edit Skill Hero",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12)));

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nama Skill
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        skillPanel.add(new JLabel("Nama Skill"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        txtSkillName = new JTextField("Judgement Hammer");
        skillPanel.add(txtSkillName, gbc);

        // Multiplier
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        skillPanel.add(new JLabel("Multiplier"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        txtSkillMultiplier = new JTextField("100");
        skillPanel.add(txtSkillMultiplier, gbc);

        // Tombol Add/Delete
        JPanel btnGroupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
        btnAdd.setPreferredSize(new Dimension(80, 30));
        btnDelete.setPreferredSize(new Dimension(80, 30));

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

    // -------------------------------------------------------
    // PANEL ENEMY (BARU DITAMBAHKAN)
    // -------------------------------------------------------
    private JPanel createEditEnemyPanel() {
        // Panel utama untuk Enemy, kita buat Layout Grid sederhana agar rapi
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Bungkus dalam Panel Status Enemy
        JPanel statusPanel = new JPanel(new GridBagLayout());
        statusPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Enemy Status (Boss)", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. Nama Enemy
        gbc.gridx = 0;
        gbc.gridy = 0;
        statusPanel.add(new JLabel("Nama Enemy:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        txtEnemyName = new JTextField("Drake"); // Default value
        statusPanel.add(txtEnemyName, gbc);

        // 2. HP Enemy
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        statusPanel.add(new JLabel("Max HP:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        txtEnemyHP = new JTextField("150"); // Default value
        statusPanel.add(txtEnemyHP, gbc);

        // 3. Attack Power (AP)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        statusPanel.add(new JLabel("Attack Power (AP):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        txtEnemyAP = new JTextField("28"); // Default value
        statusPanel.add(txtEnemyAP, gbc);

        // 4. Tombol Save Enemy
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnSaveEnemy = new JButton("Save Enemy Stats");
        btnSaveEnemy.setPreferredSize(new Dimension(150, 35));
        btnSaveEnemy.addActionListener(e -> actionSaveEnemy());
        statusPanel.add(btnSaveEnemy, gbc);

        // Tambahkan statusPanel ke mainPanel (biar ada di tengah atas)
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.weightx = 1.0;
        mainGbc.weighty = 1.0;
        mainGbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(statusPanel, mainGbc);

        return mainPanel;
    }

    // -------------------------------------------------------
    // PANEL BAWAH (OUTPUT)
    // -------------------------------------------------------
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel btnContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSimulasi = new JButton("Jalankan Simulasi!");
        btnSimulasi.setFont(new Font("Arial", Font.BOLD, 14));
        btnSimulasi.setPreferredSize(new Dimension(200, 40));
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

    // ================= LOGIKA PROGRAM (ACTIONS) =================

    private void actionSaveHero() {
        String name = txtHeroName.getText();
        String level = txtHeroLevel.getText();
        logToOutput("> Hero saved: " + name + " (Lv " + level + ")");
    }

    // Aksi Baru: Save Enemy
    private void actionSaveEnemy() {
        String name = txtEnemyName.getText();
        String hp = txtEnemyHP.getText();
        String ap = txtEnemyAP.getText();
        logToOutput("> Enemy saved: " + name + " | HP: " + hp + " | AP: " + ap);
    }

    private void actionAddSkill() {
        logToOutput("> Skill '" + txtSkillName.getText() + "' added.");
    }

    private void actionDeleteSkill() {
        logToOutput("> Skill '" + txtSkillName.getText() + "' deleted.");
    }

    // --- UPDATE LOGIKA SIMULASI ---
    private void actionRunSimulation() {
        // 1. Ambil Data Hero
        String heroName = txtHeroName.getText();
        if (heroName.isEmpty())
            heroName = "Hero";

        // 2. Ambil Data Enemy (BARU)
        String enemyName = txtEnemyName.getText();
        if (enemyName.isEmpty())
            enemyName = "Unknown Monster";

        int enemyMaxHP = 150;
        int enemyAP = 28;

        // Parsing angka dengan try-catch agar tidak error jika user input huruf
        try {
            enemyMaxHP = Integer.parseInt(txtEnemyHP.getText());
            enemyAP = Integer.parseInt(txtEnemyAP.getText());
        } catch (NumberFormatException ex) {
            logToOutput("ERROR: HP atau AP Enemy harus berupa angka! Menggunakan default.");
        }

        // 3. Simulasi Perhitungan Sederhana
        // Misal: Hero damage fix 42 (seperti contoh sebelumnya)
        int heroDmg = 42;
        int enemyCurrentHP = enemyMaxHP - heroDmg;

        // Misal: Enemy damage terkena shield (-10)
        int enemyRealDmg = Math.max(0, enemyAP - 10); // Minimal 0, tidak boleh minus
        int heroHP = 120; // Anggap HP Hero statis dulu untuk contoh
        int heroCurrentHP = heroHP - enemyRealDmg;

        // 4. Buat Log String
        StringBuilder simLog = new StringBuilder();
        simLog.append("\n=========================================================\n");
        simLog.append("SIMULASI DIMULAI: " + heroName + " VS " + enemyName + "\n");
        simLog.append("=========================================================\n");

        simLog.append("=== STATUS AWAL ===\n");
        simLog.append("  [" + heroName + "] HP: " + heroHP + " | Shield Active\n");
        simLog.append("  [" + enemyName + "] HP: " + enemyMaxHP + " | Attack Power: " + enemyAP + "\n\n");

        simLog.append("=== TURN 1 ===\n");
        simLog.append("1. [" + heroName + "] menyerang " + enemyName + "!\n");
        simLog.append("   Skill: PiercingStrike -> Damage: " + heroDmg + "\n");
        simLog.append("   " + enemyName + " HP: " + enemyMaxHP + " -> " + enemyCurrentHP + "\n\n");

        simLog.append("2. [" + enemyName + "] membalas serangan!\n");
        simLog.append("   Raw Damage: " + enemyAP + " | Shield Block: -10\n");
        simLog.append("   Total Damage masuk ke Hero: " + enemyRealDmg + "\n");
        simLog.append("   " + heroName + " HP: " + heroHP + " -> " + heroCurrentHP + "\n");

        simLog.append("=========================================================\n");
        simLog.append("Simulasi Selesai.\n");

        logToOutput(simLog.toString());
    }

    private void logToOutput(String text) {
        txtOutput.append(text + "\n");
        txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Gunakan Nimbus LookAndFeel agar tampilan lebih modern (Opsional)
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                // If Nimbus is not available, fall back to default
            }
            new RPGSimulator().setVisible(true);
        });
    }
}