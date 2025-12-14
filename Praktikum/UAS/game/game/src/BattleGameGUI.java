import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class BattleGameGUI extends JFrame {
    private List<Player> players = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private List<Skill> availableSkills = new ArrayList<>();
    
    private JTabbedPane tabbedPane;
    private HeroPanel heroPanel;
    private EnemyPanel enemyPanel;
    private SkillPanel skillPanel;
    private BattlePanel battlePanel;
    private HistoryPanel historyPanel;
    
    public BattleGameGUI() {
        setTitle("RPG Battle Simulator");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Setup database
        DatabaseManager.initializeDatabase();
        
        // Close database saat aplikasi ditutup
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                DatabaseManager.closeConnection();
            }
        });
        
        initializeSampleData();
        
        tabbedPane = new JTabbedPane();
        
        // Pastikan urutan inisialisasi ini benar sebelum digunakan di addTab
        heroPanel = new HeroPanel(this);
        enemyPanel = new EnemyPanel(this);
        skillPanel = new SkillPanel(this);
        battlePanel = new BattlePanel(this);
        historyPanel = new HistoryPanel(this);
        
        tabbedPane.addTab("Heroes", heroPanel);
        tabbedPane.addTab("Enemies", enemyPanel);
        tabbedPane.addTab("Skills", skillPanel);
        tabbedPane.addTab("Battle", battlePanel);
        tabbedPane.addTab("History", historyPanel);
        
        add(tabbedPane);
        setVisible(true);
    }
    
    private void initializeSampleData() {
        // Load dari database terlebih dahulu
        players = DatabaseManager.loadAllHeroes();
        enemies = DatabaseManager.loadAllEnemies();
        
        // Jika database kosong, gunakan sample data
        if (players.isEmpty()) {
            availableSkills.add(new HealSkill(15));
            availableSkills.add(new HealSkill(30));
            availableSkills.add(new PiercingStrike(1.2));
            availableSkills.add(new PiercingStrike(1.5));
            
            Player samplePlayer = new Player("HeroVipkas", 120, 25, 5, new LevelScaledStrategy(2));
            samplePlayer.addSkill(new HealSkill(15));
            samplePlayer.addSkill(new PiercingStrike(1.2));
            players.add(samplePlayer);
            DatabaseManager.saveHero(samplePlayer);
        } else {
            // Load available skills (bisa ditambah dari database jika diperlukan)
            availableSkills.add(new HealSkill(15));
            availableSkills.add(new HealSkill(30));
            availableSkills.add(new PiercingStrike(1.2));
            availableSkills.add(new PiercingStrike(1.5));
        }
        
        if (enemies.isEmpty()) {
            enemies.add(new BossMonster("Drake", 150, 28, 5, new FixedStrategy()));
            enemies.add(new Monster("Goblin", 80, 12, 2, new FixedStrategy()));
            for (Enemy e : enemies) {
                DatabaseManager.saveEnemy(e);
            }
        }
    }
    
    public List<Player> getPlayers() { return players; }
    public List<Enemy> getEnemies() { return enemies; }
    public List<Skill> getAvailableSkills() { return availableSkills; }
    
    public void addPlayer(Player player) {
        players.add(player);
        DatabaseManager.saveHero(player);
        heroPanel.refreshTable();
        battlePanel.refreshCombos();
    }
    
    public void updatePlayer(int index, Player player) {
        if (index >= 0 && index < players.size()) {
            players.set(index, player);
            DatabaseManager.saveHero(player);
            heroPanel.refreshTable();
            battlePanel.refreshCombos();
        }
    }
    
    public void removePlayer(int index) {
        if (index >= 0 && index < players.size()) {
            String name = players.get(index).getName();
            players.remove(index);
            DatabaseManager.deleteHero(name);
            heroPanel.refreshTable();
            battlePanel.refreshCombos();
        }
    }
    
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
        DatabaseManager.saveEnemy(enemy);
        enemyPanel.refreshTable();
        battlePanel.refreshCombos();
    }
    
    public void updateEnemy(int index, Enemy enemy) {
        if (index >= 0 && index < enemies.size()) {
            enemies.set(index, enemy);
            DatabaseManager.saveEnemy(enemy);
            enemyPanel.refreshTable();
            battlePanel.refreshCombos();
        }
    }
    
    public void removeEnemy(int index) {
        if (index >= 0 && index < enemies.size()) {
            String name = enemies.get(index).getName();
            enemies.remove(index);
            DatabaseManager.deleteEnemy(name);
            enemyPanel.refreshTable();
            battlePanel.refreshCombos();
        }
    }
    
    public void addSkill(Skill skill) {
        availableSkills.add(skill);
        skillPanel.refreshTable();
    }
    
    public void removeSkill(int index) {
        if (index >= 0 && index < availableSkills.size()) {
            availableSkills.remove(index);
            skillPanel.refreshTable();
        }
    }
    
    public void refreshHistory() {
        historyPanel.refreshTable();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BattleGameGUI());
    }
}