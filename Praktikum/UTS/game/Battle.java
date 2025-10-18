package game;

import java.util.*;

public class Battle {
    private final List<Character> teamA;
    private final List<Character> teamB;

    public Battle(List<Character> teamA, List<Character> teamB) {
        this.teamA = teamA;
        this.teamB = teamB;
    }

    public void run() {
        int turn = 1;
        while (teamAlive(teamA) && teamAlive(teamB)) {
            System.out.println("\n=== TURN " + turn + " ===");
            takeTurn(teamA, teamB);
            takeTurn(teamB, teamA);
            turn++;
        }
        System.out.println("\n=== RESULT ===");
        System.out.println(teamAlive(teamA) ? "Team A menang!" : "Team B menang!");
    }

    private void takeTurn(List<Character> attackers, List<Character> defenders) {
        for (Character c : attackers) {
            if (!c.isAlive()) continue;
            Character target = autoTarget(defenders, c instanceof Player);
            if (target != null) c.performTurn(target);
        }
    }

    private Character autoTarget(List<Character> defenders, boolean playerSide) {
        return defenders.stream()
                .filter(Character::isAlive)
                .min(Comparator.comparingInt(d -> playerSide ? 
                        ((d instanceof Enemy e) ? -e.getThreatLevel() * 1000 + e.getHealth() : d.getHealth()) :
                        -d.getHealth()))
                .orElse(null);
    }

    private boolean teamAlive(List<Character> team) {
        return team.stream().anyMatch(Character::isAlive);
    }
}
