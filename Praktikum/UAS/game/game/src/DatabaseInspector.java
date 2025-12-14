import java.sql.*;

public class DatabaseInspector {
    public static void main(String[] args) {
        String dbUrl = "jdbc:sqlite:battle_history.db";
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(dbUrl);
            
            System.out.println("=== Database Inspection ===\n");
            
            // Count battles
            String countQuery = "SELECT COUNT(*) as count FROM battle_history";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(countQuery)) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    System.out.println("📊 Total Battles: " + count);
                }
            }
            
            // Count heroes
            countQuery = "SELECT COUNT(*) as count FROM heroes";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(countQuery)) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    System.out.println("🧑 Total Heroes: " + count);
                }
            }
            
            // Count enemies
            countQuery = "SELECT COUNT(*) as count FROM enemies";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(countQuery)) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    System.out.println("👹 Total Enemies: " + count);
                }
            }
            
            System.out.println("\n--- Battle History ---");
            String battleQuery = "SELECT id, hero1, hero2, enemy1, enemy2, winner, total_turns, battle_date FROM battle_history ORDER BY id DESC LIMIT 10";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(battleQuery)) {
                
                if (!rs.isBeforeFirst()) {
                    System.out.println("(No battles yet)");
                } else {
                    while (rs.next()) {
                        System.out.println("Battle #" + rs.getInt("id") + 
                            " | " + rs.getString("hero1") + " vs " + rs.getString("enemy1") + 
                            " | Winner: " + rs.getString("winner") +
                            " | Turns: " + rs.getInt("total_turns") +
                            " | Date: " + rs.getString("battle_date"));
                    }
                }
            }
            
            System.out.println("\n--- Heroes ---");
            String heroQuery = "SELECT name, max_hp, attack_power, level FROM heroes ORDER BY name";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(heroQuery)) {
                
                if (!rs.isBeforeFirst()) {
                    System.out.println("(No heroes)");
                } else {
                    while (rs.next()) {
                        System.out.println("  • " + rs.getString("name") + 
                            " (HP: " + rs.getInt("max_hp") + 
                            ", AP: " + rs.getInt("attack_power") + 
                            ", Lvl: " + rs.getInt("level") + ")");
                    }
                }
            }
            
            System.out.println("\n--- Enemies ---");
            String enemyQuery = "SELECT name, max_hp, attack_power, level FROM enemies ORDER BY name";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(enemyQuery)) {
                
                if (!rs.isBeforeFirst()) {
                    System.out.println("(No enemies)");
                } else {
                    while (rs.next()) {
                        System.out.println("  • " + rs.getString("name") + 
                            " (HP: " + rs.getInt("max_hp") + 
                            ", AP: " + rs.getInt("attack_power") + 
                            ", Level: " + rs.getInt("level") + ")");
                    }
                }
            }
            
            conn.close();
            System.out.println("\n✓ Database inspection complete!");
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
