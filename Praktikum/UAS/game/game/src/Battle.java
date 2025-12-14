import java.util.*;
import java.util.stream.Collectors; // Diperlukan untuk stream

public class Battle {
    private final List<Character> teamA;
    private final List<Character> teamB;

    public Battle(List<Character> teamA, List<Character> teamB) {
        this.teamA = teamA;
        this.teamB = teamB;
    }

    public void run() {
        int turnCounter = 0;
        
        System.out.println("\n=== SETUP ===");
        printTeam("Team A", teamA);
        printTeam("Team B", teamB);
        
        while (hasAliveCharacters(teamA) && hasAliveCharacters(teamB)) {
            turnCounter++;
            System.out.println("\n=== TURN " + turnCounter + " ===");
            
            // Team A attacks
            for (Character attacker : teamA) {
                if (!attacker.isAlive()) continue;
                
                Character target = selectTarget(attacker, teamB);
                if (target != null) {
                    attacker.attack(target);
                }
                
                if (!hasAliveCharacters(teamB)) break;
            }
            
            if (!hasAliveCharacters(teamB)) break;
            
            // Team B attacks
            for (Character attacker : teamB) {
                if (!attacker.isAlive()) continue;
                
                Character target = selectTarget(attacker, teamA);
                if (target != null) {
                    attacker.attack(target);
                }
                
                if (!hasAliveCharacters(teamA)) break;
            }
        }
        
        System.out.println("\n=== RESULT ===");
        if (hasAliveCharacters(teamA)) {
            System.out.println("Team A menang!");
        } else {
            System.out.println("Team B menang!");
        }
        
        System.out.println("\nSisa HP:");
        printFinalStats(teamA);
        printFinalStats(teamB);
    }

    private boolean hasAliveCharacters(List<Character> team) {
        for (Character c : team) {
            if (c.isAlive()) return true;
        }
        return false;
    }

    private Character selectTarget(Character attacker, List<Character> enemies) {
        List<Character> aliveEnemies = enemies.stream()
            .filter(Character::isAlive)
            .collect(Collectors.toList());
        
        if (aliveEnemies.isEmpty()) return null;
        
        // Enemy targets Player with highest HP (dibiarkan sesuai logika asli Anda)
        if (attacker instanceof Enemy) {
            return aliveEnemies.stream()
                .max(Comparator.comparingInt(Character::getCurrentHealth))
                .orElse(aliveEnemies.get(0));
        }
        
        // Player targets Enemy with highest threat level, then lowest HP
        if (attacker instanceof Player) {
            return aliveEnemies.stream()
                .filter(c -> c instanceof Enemy)
                .map(c -> (Enemy) c)
                .min(Comparator
                    .comparing(Enemy::getThreatLevel).reversed() // Threat Level DESC
                    .thenComparing(Enemy::getCurrentHealth))      // HP ASC
                .map(c -> (Character) c)
                .orElse(aliveEnemies.get(0));
        }
        
        return aliveEnemies.get(0);
    }

    private void printTeam(String teamName, List<Character> team) {
        System.out.println(teamName + ":");
        for (Character c : team) {
            System.out.print("- ");
            if (c instanceof Player) {
                Player p = (Player) c;
                System.out.println("Player(name=" + p.getName() + ", HP=" + 
                                     p.getCurrentHealth() + "/" + p.getMaxHealth() + 
                                     ", AP=" + p.getAttackPower() + ", Lv=" + p.getLevel() + ")");
            } else if (c instanceof BossMonster) {
                BossMonster b = (BossMonster) c;
                System.out.println("BossMonster(name=" + b.getName() + ", HP=" + 
                                     b.getCurrentHealth() + "/" + b.getMaxHealth() + 
                                     ", AP=" + b.getAttackPower() + 
                                     ", Threat=" + b.getThreatLevel() + ")");
            } else if (c instanceof Monster) {
                Monster m = (Monster) c;
                System.out.println("Monster(name=" + m.getName() + ", HP=" + 
                                     m.getCurrentHealth() + "/" + m.getMaxHealth() + 
                                     ", AP=" + m.getAttackPower() + 
                                     ", Threat=" + m.getThreatLevel() + ")");
            }
        }
    }

    private void printFinalStats(List<Character> team) {
        for (Character c : team) {
            System.out.println("- " + c.getName() + ": " + 
                                 c.getCurrentHealth() + "/" + c.getMaxHealth());
        }
    }
}