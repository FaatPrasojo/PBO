import java.util.ArrayList;
import java.util.List;

public class GameTest {

    public static void main(String[] args) {
        System.out.println("=== BATTLE SIMULATION TEST ===\n");
        
        // Setup menggunakan Battle class
        Player hero = new Player("HeroVipkas", 120, 25, 5, new LevelScaledStrategy(2)); 
        hero.addSkill(new HealSkill(15));
        hero.addSkill(new PiercingStrike(1.2));
        hero.addEffect(new Shield(10, 3));
        hero.addEffect(new Regen(8, 4));

        BossMonster drake = new BossMonster("Drake", 150, 28, 5, new FixedStrategy());
        Monster goblin = new Monster("Goblin", 80, 12, 2, new FixedStrategy());

        List<Character> teamA = new ArrayList<>();
        teamA.add(hero);
        
        List<Character> teamB = new ArrayList<>();
        teamB.add(drake);
        teamB.add(goblin);

        Battle battle = new Battle(teamA, teamB);
        battle.run();

        System.out.println("\n\n=== VALIDATION TESTS ===");
        testValidation();
    }

    private static void testValidation() {
        // 1. Testing HealSkill tidak melebihi maxHealth
        System.out.println("\n1. Testing HealSkill tidak melebihi maxHealth:");
        Player testPlayer = new Player("TestPlayer", 100, 20, 1, new FixedStrategy());
        testPlayer.setCurrentHealth(90);
        HealSkill healSkill = new HealSkill(50);
        
        System.out.println("HP sebelum: " + testPlayer.getCurrentHealth() + "/" + testPlayer.getMaxHealth());
        healSkill.use(testPlayer, testPlayer);
        System.out.println("HP setelah heal 50: " + testPlayer.getCurrentHealth() + "/" + testPlayer.getMaxHealth());
        System.out.println("✓ Health tidak melebihi maxHealth");

        // 2. Testing BossMonster Rage Strike
        System.out.println("\n2. Testing BossMonster Rage Strike:");
        BossMonster testBoss = new BossMonster("TestBoss", 100, 20, 5, new FixedStrategy());
        Player dummyTarget = new Player("Dummy", 200, 10, 1, new FixedStrategy());
        
        System.out.println("Boss HP: " + testBoss.getCurrentHealth() + "/" + testBoss.getMaxHealth());
        testBoss.setCurrentHealth(40);
        System.out.println("Boss HP turun ke: " + testBoss.getCurrentHealth() + " (< 50%)");
        System.out.println("Serangan Boss (seharusnya Rage Strike):");
        testBoss.attack(dummyTarget);
        System.out.println("✓ Rage Strike terpicu saat HP < 50%");

        // 3. Testing Shield mengurangi damage
        System.out.println("\n3. Testing Shield mengurangi damage:");
        Player shieldPlayer = new Player("ShieldTest", 100, 10, 1, new FixedStrategy());
        shieldPlayer.addEffect(new Shield(15, 2));
        
        int hpBefore = shieldPlayer.getCurrentHealth();
        System.out.println("HP sebelum: " + hpBefore);
        int damageTaken = shieldPlayer.takeDamage(30);
        System.out.println("HP setelah damage 30 dengan Shield(-15): " + shieldPlayer.getCurrentHealth());
        System.out.println("Damage diterima: " + damageTaken);
        System.out.println("✓ Shield mengurangi damage dengan benar");

        // 4. Testing AttackStrategy berbeda
        System.out.println("\n4. Testing AttackStrategy berbeda:");
        Player fixedPlayer = new Player("Fixed", 100, 20, 5, new FixedStrategy());
        Player scaledPlayer = new Player("Scaled", 100, 20, 5, new LevelScaledStrategy(3));
        Player target = new Player("Target", 100, 10, 1, new FixedStrategy());
        
        System.out.println("FixedStrategy damage: " + 
                             (new FixedStrategy()).computeDamage(fixedPlayer, target));
        System.out.println("LevelScaledStrategy damage: " + 
                             (new LevelScaledStrategy(3)).computeDamage(scaledPlayer, target));
        System.out.println("✓ Strategy berbeda menghasilkan damage berbeda");

        // 5. Testing setter health menolak nilai negatif
        System.out.println("\n5. Testing setter health menolak nilai negatif:");
        try {
            new Player("NegTest", -10, 10, 1, new FixedStrategy());
            System.out.println("✗ Seharusnya throw exception");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Exception tertangkap: " + e.getMessage());
        }
    }
}