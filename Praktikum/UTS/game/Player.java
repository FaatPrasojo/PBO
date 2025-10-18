package game;

import java.util.*;

public class Player extends Character {
    private int level;
    private AttackStrategy strategy;
    private final List<Skill> skills = new ArrayList<>();
    private int turnCounter = 0;

    public Player(String name, int hp, int ap, int level, AttackStrategy strategy) {
        super(name, hp, ap);
        this.level = level;
        this.strategy = strategy;
    }

    public void addSkill(Skill s) { if (s != null) skills.add(s); }
    public List<Skill> getSkills() { return skills; }

    @Override
    public void attack(Character target) {
        turnCounter++;
        int base = strategy.computeDamage(this, target);

        if (turnCounter == 3) {
            for (Skill s : skills)
                if (s instanceof HealSkill heal) heal.apply(this, this);
        }

        boolean usePiercing = turnCounter % 2 == 1 &&
                skills.stream().anyMatch(s -> s instanceof PiercingStrike);

        if (usePiercing) {
            Skill pierce = skills.stream().filter(s -> s instanceof PiercingStrike).findFirst().get();
            pierce.apply(this, target);
        } else {
            System.out.printf("[Team A] %s -> %s (Normal %d)%n", getName(), target.getName(), base);
            target.takeDamage(base);
        }
    }

    public void heal(int amount) {
        int before = getHealth();
        setHealth(getHealth() + amount);
        System.out.printf("  %s HP: %d -> %d%n", getName(), before, getHealth());
    }
}
