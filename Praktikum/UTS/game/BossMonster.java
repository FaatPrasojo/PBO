package game;

public class BossMonster extends Enemy {
    private int turnCounter = 0;

    public BossMonster(String name, int hp, int ap, int threat, AttackStrategy strat) {
        super(name, hp, ap, threat, strat);
    }

    @Override
    public void attack(Character target) {
        turnCounter++;
        int base = strategy.computeDamage(this, target);
        boolean rage = (getHealth() < maxHealth / 2) || (turnCounter % 3 == 0);
        int dmg = rage ? base * 2 : base;

        if (rage)
            System.out.printf("[Team B] %s -> %s (RAGE x2: %d)%n", getName(), target.getName(), dmg);
        else
            System.out.printf("[Team B] %s -> %s (Normal hit %d)%n", getName(), target.getName(), dmg);

        target.takeDamage(dmg);
    }
}
