package game;

import java.util.*;

public abstract class Character {
    private final String name;
    protected final int maxHealth;
    private int health;
    private final int attackPower;
    private final List<StatusEffect> effects = new ArrayList<>();

    protected Character(String name, int health, int attackPower) {
        if (health < 0 || attackPower < 0)
            throw new IllegalArgumentException("Health dan Attack Power tidak boleh negatif");
        this.name = name;
        this.maxHealth = health;
        this.health = health;
        this.attackPower = attackPower;
    }

    public final String getName() { return name; }
    public final int getAttackPower() { return attackPower; }
    public final int getHealth() { return health; }
    public final boolean isAlive() { return health > 0; }

    protected final void setHealth(int value) {
        if (value < 0) value = 0;
        if (value > maxHealth) value = maxHealth;
        this.health = value;
    }

    public void takeDamage(int dmg) {
        int actual = onIncomingDamage(dmg);
        setHealth(health - Math.max(0, actual));
    }

    protected int onIncomingDamage(int base) {
        int reduced = base;
        for (StatusEffect e : effects)
            if (e instanceof Shield s) reduced = s.reduceDamage(reduced);
        return Math.max(0, reduced);
    }

    public final void addEffect(StatusEffect e) {
        if (e != null) effects.add(e);
    }

    public final List<StatusEffect> getEffects() { return effects; }

    public final void performTurn(Character target) {
        for (StatusEffect e : effects) e.onTurnStart(this);
        if (isAlive()) attack(target);
        for (StatusEffect e : new ArrayList<>(effects)) {
            e.onTurnEnd(this);
            if (e.isExpired()) effects.remove(e);
        }
    }

    public abstract void attack(Character target);
}