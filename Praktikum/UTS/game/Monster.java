package game;

import java.util.Random;

public class Monster extends Enemy {
    private final Random rand = new Random();

    public Monster(String name, int hp, int ap, int threat, AttackStrategy strat) {
        super(name, hp, ap, threat, strat);
    }

    @Override
    public void attack(Character target) {
        int base = strategy.computeDamage(this, target);
        int dmg = base / 2 + rand.nextInt(base / 2 + 1);
        System.out.printf("[Team B] %s -> %s (Normal %d)%n", getName(), target.getName(), dmg);
        target.takeDamage(dmg);
    }
}