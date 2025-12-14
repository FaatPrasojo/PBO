import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BattleHistory {
    private int id;
    private String hero1;
    private String hero2;
    private String enemy1;
    private String enemy2;
    private String winner; // "Heroes" atau "Enemies"
    private int totalTurns;
    private LocalDateTime battleDate;
    private String battleLog;
    
    public BattleHistory(String hero1, String hero2, String enemy1, String enemy2, 
                        String winner, int totalTurns, String battleLog) {
        this.hero1 = hero1;
        this.hero2 = hero2;
        this.enemy1 = enemy1;
        this.enemy2 = enemy2;
        this.winner = winner;
        this.totalTurns = totalTurns;
        this.battleDate = LocalDateTime.now();
        this.battleLog = battleLog;
    }
    
    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getHero1() { return hero1; }
    public String getHero2() { return hero2; }
    public String getEnemy1() { return enemy1; }
    public String getEnemy2() { return enemy2; }
    public String getWinner() { return winner; }
    public int getTotalTurns() { return totalTurns; }
    public LocalDateTime getBattleDate() { return battleDate; }
    public String getBattleLog() { return battleLog; }
    
    public String getFormattedDate() {
        return battleDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s vs %s - Winner: %s (Turns: %d)", 
            getFormattedDate(), hero1, enemy1, winner, totalTurns);
    }
}
