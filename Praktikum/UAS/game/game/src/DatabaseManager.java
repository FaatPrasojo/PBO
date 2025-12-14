import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:battle_history.db";
    private static Connection connection;
    
    // Static initializer - setup database saat class di-load
    static {
        try {
            Class.forName("org.sqlite.JDBC");
            initializeDatabase();
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found!");
            System.err.println("Download dari: https://github.com/xerial/sqlite-jdbc/releases");
        }
    }
    
    /**
     * Inisialisasi database - buat table jika belum ada
     */
    public static void initializeDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            
            // Battle history table
            String battleTableSQL = """
                CREATE TABLE IF NOT EXISTS battle_history (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    hero1 TEXT NOT NULL,
                    hero2 TEXT,
                    enemy1 TEXT NOT NULL,
                    enemy2 TEXT,
                    winner TEXT NOT NULL,
                    total_turns INTEGER NOT NULL,
                    battle_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    battle_log TEXT
                )
                """;
            
            // Heroes table
            String heroesTableSQL = """
                CREATE TABLE IF NOT EXISTS heroes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT UNIQUE NOT NULL,
                    max_hp INTEGER NOT NULL,
                    attack_power INTEGER NOT NULL,
                    level INTEGER NOT NULL,
                    strategy TEXT NOT NULL
                )
                """;
            
            // Hero skills table
            String heroSkillsTableSQL = """
                CREATE TABLE IF NOT EXISTS hero_skills (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    hero_id INTEGER NOT NULL,
                    skill_type TEXT NOT NULL,
                    skill_value REAL NOT NULL,
                    FOREIGN KEY(hero_id) REFERENCES heroes(id) ON DELETE CASCADE
                )
                """;
            
            // Enemies table
            String enemiesTableSQL = """
                CREATE TABLE IF NOT EXISTS enemies (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT UNIQUE NOT NULL,
                    max_hp INTEGER NOT NULL,
                    attack_power INTEGER NOT NULL,
                    level INTEGER NOT NULL,
                    enemy_type TEXT NOT NULL,
                    strategy TEXT NOT NULL
                )
                """;
            
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(battleTableSQL);
                stmt.execute(heroesTableSQL);
                stmt.execute(heroSkillsTableSQL);
                stmt.execute(enemiesTableSQL);
            }
            
            System.out.println("✓ Database initialized: battle_history.db");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
    
    /**
     * Simpan battle history ke database
     */
    public static void saveBattleHistory(BattleHistory history) {
        String insertSQL = """
            INSERT INTO battle_history (hero1, hero2, enemy1, enemy2, winner, total_turns, battle_log)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
        
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, history.getHero1());
            pstmt.setString(2, history.getHero2());
            pstmt.setString(3, history.getEnemy1());
            pstmt.setString(4, history.getEnemy2());
            pstmt.setString(5, history.getWinner());
            pstmt.setInt(6, history.getTotalTurns());
            pstmt.setString(7, history.getBattleLog());
            
            pstmt.executeUpdate();
            System.out.println("✓ Battle history saved to database");
        } catch (SQLException e) {
            System.err.println("Error saving battle history: " + e.getMessage());
        }
    }
    
    /**
     * Ambil semua battle history
     */
    public static List<BattleHistory> getAllBattles() {
        List<BattleHistory> battles = new ArrayList<>();
        String query = "SELECT * FROM battle_history ORDER BY battle_date DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                BattleHistory history = new BattleHistory(
                    rs.getString("hero1"),
                    rs.getString("hero2"),
                    rs.getString("enemy1"),
                    rs.getString("enemy2"),
                    rs.getString("winner"),
                    rs.getInt("total_turns"),
                    rs.getString("battle_log")
                );
                history.setId(rs.getInt("id"));
                battles.add(history);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving battles: " + e.getMessage());
        }
        
        return battles;
    }
    
    /**
     * Filter battle berdasarkan winner
     */
    public static List<BattleHistory> getBattlesByWinner(String winner) {
        List<BattleHistory> battles = new ArrayList<>();
        String query = "SELECT * FROM battle_history WHERE winner = ? ORDER BY battle_date DESC";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, winner);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    BattleHistory history = new BattleHistory(
                        rs.getString("hero1"),
                        rs.getString("hero2"),
                        rs.getString("enemy1"),
                        rs.getString("enemy2"),
                        rs.getString("winner"),
                        rs.getInt("total_turns"),
                        rs.getString("battle_log")
                    );
                    history.setId(rs.getInt("id"));
                    battles.add(history);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error filtering battles: " + e.getMessage());
        }
        
        return battles;
    }
    
    /**
     * Get battle detail berdasarkan ID
     */
    public static BattleHistory getBattleById(int id) {
        String query = "SELECT * FROM battle_history WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    BattleHistory history = new BattleHistory(
                        rs.getString("hero1"),
                        rs.getString("hero2"),
                        rs.getString("enemy1"),
                        rs.getString("enemy2"),
                        rs.getString("winner"),
                        rs.getInt("total_turns"),
                        rs.getString("battle_log")
                    );
                    history.setId(rs.getInt("id"));
                    return history;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving battle: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Hitung total battles
     */
    public static int getTotalBattleCount() {
        String query = "SELECT COUNT(*) FROM battle_history";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error counting battles: " + e.getMessage());
        }
        
        return 0;
    }
    
    /**
     * Hapus battle history berdasarkan ID
     */
    public static void deleteBattle(int id) {
        String deleteSQL = "DELETE FROM battle_history WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("✓ Battle history deleted");
        } catch (SQLException e) {
            System.err.println("Error deleting battle: " + e.getMessage());
        }
    }
    
    /**
     * Clear semua history (untuk reset)
     */
    public static void clearAllHistory() {
        String deleteSQL = "DELETE FROM battle_history";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(deleteSQL);
            System.out.println("✓ All battle history cleared");
        } catch (SQLException e) {
            System.err.println("Error clearing history: " + e.getMessage());
        }
    }
    
    /**
     * Close database connection (call saat aplikasi ditutup)
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database: " + e.getMessage());
        }
    }
    
    // ==================== HEROES DATA ====================
    
    /**
     * Save hero ke database
     */
    public static void saveHero(Player player) {
        String insertSQL = "INSERT OR REPLACE INTO heroes (name, max_hp, attack_power, level, strategy) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, player.getName());
            pstmt.setInt(2, player.getMaxHealth());
            pstmt.setInt(3, player.getAttackPower());
            pstmt.setInt(4, player.getLevel());
            pstmt.setString(5, player.attackStrategy.getClass().getSimpleName());
            
            pstmt.executeUpdate();
            
            // Get hero ID
            int heroId = getHeroId(player.getName());
            
            // Clear existing skills
            String deleteSkillsSQL = "DELETE FROM hero_skills WHERE hero_id = ?";
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSkillsSQL)) {
                deleteStmt.setInt(1, heroId);
                deleteStmt.executeUpdate();
            }
            
            // Save new skills
            for (Skill skill : player.getSkills()) {
                String insertSkillSQL = "INSERT INTO hero_skills (hero_id, skill_type, skill_value) VALUES (?, ?, ?)";
                try (PreparedStatement skillStmt = connection.prepareStatement(insertSkillSQL)) {
                    skillStmt.setInt(1, heroId);
                    skillStmt.setString(2, skill.getClass().getSimpleName());
                    
                    // Extract value dari skill
                    double value = 0;
                    if (skill instanceof HealSkill) {
                        String[] parts = skill.name().split("[()]");
                        if (parts.length > 1) {
                            value = Integer.parseInt(parts[1].replace("+", "").trim());
                        }
                    } else if (skill instanceof PiercingStrike) {
                        String[] parts = skill.name().split("[()]");
                        if (parts.length > 1) {
                            String valStr = parts[1].replace("x", "").trim().replace(",", ".");
                            value = Double.parseDouble(valStr);
                        }
                    }
                    
                    skillStmt.setDouble(3, value);
                    skillStmt.executeUpdate();
                }
            }
            
            System.out.println("✓ Hero saved: " + player.getName());
        } catch (SQLException e) {
            System.err.println("Error saving hero: " + e.getMessage());
        }
    }
    
    /**
     * Get hero ID by name
     */
    private static int getHeroId(String name) {
        String query = "SELECT id FROM heroes WHERE name = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting hero ID: " + e.getMessage());
        }
        
        return -1;
    }
    
    /**
     * Load all heroes dari database
     */
    public static List<Player> loadAllHeroes() {
        List<Player> heroes = new ArrayList<>();
        String query = "SELECT * FROM heroes ORDER BY name";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                int heroId = rs.getInt("id");
                String name = rs.getString("name");
                int hp = rs.getInt("max_hp");
                int ap = rs.getInt("attack_power");
                int level = rs.getInt("level");
                String strategyType = rs.getString("strategy");
                
                // Create strategy
                AttackStrategy strategy;
                if (strategyType.equals("FixedStrategy")) {
                    strategy = new FixedStrategy();
                } else {
                    strategy = new LevelScaledStrategy(2);
                }
                
                // Create player
                Player player = new Player(name, hp, ap, level, strategy);
                
                // Load skills untuk hero ini
                String skillQuery = "SELECT * FROM hero_skills WHERE hero_id = ?";
                try (PreparedStatement skillStmt = connection.prepareStatement(skillQuery)) {
                    skillStmt.setInt(1, heroId);
                    try (ResultSet skillRs = skillStmt.executeQuery()) {
                        while (skillRs.next()) {
                            String skillType = skillRs.getString("skill_type");
                            double value = skillRs.getDouble("skill_value");
                            
                            if (skillType.equals("HealSkill")) {
                                player.addSkill(new HealSkill((int) value));
                            } else if (skillType.equals("PiercingStrike")) {
                                player.addSkill(new PiercingStrike(value));
                            }
                        }
                    }
                }
                
                heroes.add(player);
            }
        } catch (SQLException e) {
            System.err.println("Error loading heroes: " + e.getMessage());
        }
        
        return heroes;
    }
    
    /**
     * Delete hero dari database
     */
    public static void deleteHero(String heroName) {
        String deleteSQL = "DELETE FROM heroes WHERE name = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setString(1, heroName);
            pstmt.executeUpdate();
            System.out.println("✓ Hero deleted: " + heroName);
        } catch (SQLException e) {
            System.err.println("Error deleting hero: " + e.getMessage());
        }
    }
    
    // ==================== ENEMIES DATA ====================
    
    /**
     * Save enemy ke database
     */
    public static void saveEnemy(Enemy enemy) {
        String insertSQL = "INSERT OR REPLACE INTO enemies (name, max_hp, attack_power, level, enemy_type, strategy) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, enemy.getName());
            pstmt.setInt(2, enemy.getMaxHealth());
            pstmt.setInt(3, enemy.getAttackPower());
            pstmt.setInt(4, enemy.getThreatLevel());
            pstmt.setString(5, enemy.getClass().getSimpleName());
            pstmt.setString(6, enemy.attackStrategy.getClass().getSimpleName());
            
            pstmt.executeUpdate();
            System.out.println("✓ Enemy saved: " + enemy.getName());
        } catch (SQLException e) {
            System.err.println("Error saving enemy: " + e.getMessage());
        }
    }
    
    /**
     * Load all enemies dari database
     */
    public static List<Enemy> loadAllEnemies() {
        List<Enemy> enemies = new ArrayList<>();
        String query = "SELECT * FROM enemies ORDER BY name";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                String name = rs.getString("name");
                int hp = rs.getInt("max_hp");
                int ap = rs.getInt("attack_power");
                int threatLevel = rs.getInt("level"); // Ini adalah threatLevel
                String enemyType = rs.getString("enemy_type");
                String strategyType = rs.getString("strategy");
                
                // Create strategy
                AttackStrategy strategy;
                if (strategyType.equals("FixedStrategy")) {
                    strategy = new FixedStrategy();
                } else {
                    strategy = new LevelScaledStrategy(2);
                }
                
                // Create enemy based on type
                Enemy enemy;
                if (enemyType.equals("BossMonster")) {
                    enemy = new BossMonster(name, hp, ap, threatLevel, strategy);
                } else {
                    enemy = new Monster(name, hp, ap, threatLevel, strategy);
                }
                
                enemies.add(enemy);
            }
        } catch (SQLException e) {
            System.err.println("Error loading enemies: " + e.getMessage());
        }
        
        return enemies;
    }
    
    /**
     * Delete enemy dari database
     */
    public static void deleteEnemy(String enemyName) {
        String deleteSQL = "DELETE FROM enemies WHERE name = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setString(1, enemyName);
            pstmt.executeUpdate();
            System.out.println("✓ Enemy deleted: " + enemyName);
        } catch (SQLException e) {
            System.err.println("Error deleting enemy: " + e.getMessage());
        }
    }
}
