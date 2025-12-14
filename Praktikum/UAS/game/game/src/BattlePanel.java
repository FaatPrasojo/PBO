import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Comparator; // Diperlukan untuk stream min/max

class BattlePanel extends JPanel {
    private BattleGameGUI mainGUI;
    private JComboBox<Player> player1Combo, player2Combo;
    private JComboBox<Enemy> enemy1Combo, enemy2Combo;
    private JTextArea logArea;
    
    public BattlePanel(BattleGameGUI mainGUI) {
        this.mainGUI = mainGUI;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Battle Simulator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);
        
        // --- Selection Panel ---
        JPanel selectionPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        
        selectionPanel.add(new JLabel("Hero 1:"));
        player1Combo = new JComboBox<>();
        selectionPanel.add(player1Combo);
        
        selectionPanel.add(new JLabel("Enemy 1:"));
        enemy1Combo = new JComboBox<>();
        selectionPanel.add(enemy1Combo);
        
        selectionPanel.add(new JLabel("Hero 2:"));
        player2Combo = new JComboBox<>();
        selectionPanel.add(player2Combo);
        
        selectionPanel.add(new JLabel("Enemy 2:"));
        enemy2Combo = new JComboBox<>();
        selectionPanel.add(enemy2Combo);

        selectionPanel.add(new JLabel("")); 
        
        JButton startButton = new JButton("Start Battle");
        startButton.addActionListener(e -> startBattle());
        selectionPanel.add(startButton);

        selectionPanel.add(new JLabel(""));
        
        add(selectionPanel, BorderLayout.NORTH);
        
        // --- Log Area ---
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);
        
        refreshCombos();
    }
    
    public void refreshCombos() {
        player1Combo.removeAllItems();
        player2Combo.removeAllItems();
        enemy1Combo.removeAllItems();
        enemy2Combo.removeAllItems();
        
        for (Player p : mainGUI.getPlayers()) {
            player1Combo.addItem(p);
            player2Combo.addItem(p);
        }
        
        for (Enemy e : mainGUI.getEnemies()) {
            enemy1Combo.addItem(e);
            enemy2Combo.addItem(e);
        }
    }
    
    private void startBattle() {
        logArea.setText(""); 
        
        Player p1 = (Player) player1Combo.getSelectedItem();
        Player p2 = (Player) player2Combo.getSelectedItem();
        Enemy e1 = (Enemy) enemy1Combo.getSelectedItem();
        Enemy e2 = (Enemy) enemy2Combo.getSelectedItem();

        if (p1 == null || e1 == null) {
            JOptionPane.showMessageDialog(this, "Please select at least Hero 1 and Enemy 1.",
                "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Character> heroes = new ArrayList<>();
        List<Character> enemies = new ArrayList<>();

        try {
            // Cloning is essential for running multiple simulations without affecting original stats
            heroes.add(p1.clone());
            enemies.add(e1.clone());

            if (p2 != null && p1.getName() != p2.getName()) heroes.add(p2.clone());
            if (e2 != null && e1.getName() != e2.getName()) enemies.add(e2.clone());
        } catch (CloneNotSupportedException e) {
             log("Error: Character cloning failed. " + e.getMessage());
             return;
        }

        if (heroes.size() == 0 || enemies.size() == 0) {
            log("Battle cannot start: Need at least one Hero and one Enemy.");
            return;
        }

        log("--- Battle Started ---");
        int turn = 1;
        Random random = new Random();
        String winner = null; // Track battle result

        while (heroes.stream().anyMatch(Character::isAlive) && enemies.stream().anyMatch(Character::isAlive)) {
            log("\n--- Turn " + turn + " ---");
            
            // 1. Heroes' Turn
            for (Character hero : heroes) {
                if (hero.isAlive()) {
                    Character target = selectTarget(enemies, random);
                    if (target != null) {
                        performAction((Player)hero, target, enemies, heroes, random);
                    }
                }
            }
            
            // Apply all ongoing effects (Regen, Shield duration down, etc.)
            heroes.forEach(Character::applyStatusEffects);
            enemies.forEach(Character::applyStatusEffects);

            // Check win condition 1
            if (enemies.stream().noneMatch(Character::isAlive)) {
                log("\nHeroes win!");
                winner = "Heroes";
                break;
            }
            
            // 2. Enemies' Turn
            for (Character enemy : enemies) {
                if (enemy.isAlive()) {
                    Character target = selectTarget(heroes, random);
                    if (target != null) {
                        int damage = ((Enemy)enemy).attack(target);
                        log(enemy.getName() + " attacks " + target.getName() + " for " + damage + " damage. " + 
                            target.getName() + " HP: " + target.getCurrentHealth());
                    }
                }
            }
            
            // Apply all ongoing effects (Regen, Shield duration down, etc.)
            heroes.forEach(Character::applyStatusEffects);
            enemies.forEach(Character::applyStatusEffects);
            
            // Check win condition 2
            if (heroes.stream().noneMatch(Character::isAlive)) {
                log("\nEnemies win!");
                winner = "Enemies";
                break;
            }
            
            turn++;
            if (turn > 50) { 
                log("\nBattle ended due to turn limit.");
                winner = "Draw";
                break;
            }
        }
        log("\n--- Battle Ended ---");
        
        // Save battle history to database
        if (winner != null) {
            String hero2Name = (p2 != null) ? p2.getName() : "";
            String enemy2Name = (e2 != null) ? e2.getName() : "";
            BattleHistory history = new BattleHistory(
                p1.getName(),
                hero2Name,
                e1.getName(),
                enemy2Name,
                winner,
                turn - 1,
                logArea.getText()
            );
            DatabaseManager.saveBattleHistory(history);
            mainGUI.refreshHistory(); // Refresh history panel
        }
    }

    private Character selectTarget(List<Character> targets, Random random) {
        List<Character> livingTargets = new ArrayList<>();
        for (Character c : targets) {
            if (c.isAlive()) {
                livingTargets.add(c);
            }
        }
        if (livingTargets.isEmpty()) return null;

        // Simple random targeting
        return livingTargets.get(random.nextInt(livingTargets.size()));
    }

    private void performAction(Player hero, Character enemyTarget, List<Character> enemies, List<Character> heroes, Random random) {
        // Simple logic: 20% chance to use skill (if available), otherwise normal attack
        if (!hero.getSkills().isEmpty() && random.nextDouble() < 0.2) {
            Skill skill = hero.getSkills().get(random.nextInt(hero.getSkills().size()));
            
            if (skill instanceof HealSkill) {
                Character lowestHP = heroes.stream()
                                         .filter(Character::isAlive)
                                         .min(Comparator.comparingInt(Character::getCurrentHealth))
                                         .orElse(hero); 
                
                ((HealSkill) skill).use(hero, lowestHP);
                log(hero.getName() + " uses " + skill.name() + " on " + lowestHP.getName() + ". " + 
                    lowestHP.getName() + " HP: " + lowestHP.getCurrentHealth());
            } else if (skill instanceof PiercingStrike) {
                ((PiercingStrike) skill).use(hero, enemyTarget);
                log(hero.getName() + " uses " + skill.name() + " on " + enemyTarget.getName() + ". " + 
                    enemyTarget.getName() + " HP: " + enemyTarget.getCurrentHealth());
            } else {
                int damage = hero.attack(enemyTarget);
                log(hero.getName() + " attacks " + enemyTarget.getName() + " for " + damage + " damage. " + 
                    enemyTarget.getName() + " HP: " + enemyTarget.getCurrentHealth());
            }
        } else {
            // Normal attack
            int damage = hero.attack(enemyTarget);
            log(hero.getName() + " attacks " + enemyTarget.getName() + " for " + damage + " damage. " + 
                enemyTarget.getName() + " HP: " + enemyTarget.getCurrentHealth());
        }
    }
    
    private void log(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
}