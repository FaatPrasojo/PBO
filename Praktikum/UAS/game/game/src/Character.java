import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Character implements Cloneable {
    protected String name;
    protected int maxHealth;
    protected int currentHealth;
    protected int attackPower;
    protected AttackStrategy attackStrategy;
    protected List<StatusEffect> effects = new ArrayList<>();

    public Character(String name, int maxHealth, int attackPower, AttackStrategy attackStrategy) {
        this.name = name;
        if (maxHealth <= 0) throw new IllegalArgumentException("Max Health must be positive.");
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.attackPower = attackPower;
        this.attackStrategy = attackStrategy;
    }

    public abstract int attack(Character target);

    public int takeDamage(int rawDamage) {
        int finalDamage = rawDamage;
        
        // Apply all status effect modifiers
        for (StatusEffect effect : effects) {
            finalDamage = effect.modifyIncomingDamage(finalDamage);
        }
        
        this.currentHealth -= finalDamage;
        if (this.currentHealth < 0) this.currentHealth = 0;
        
        return finalDamage;
    }

    public boolean isAlive() {
        return this.currentHealth > 0;
    }
    
    public void addEffect(StatusEffect effect) {
        this.effects.add(effect);
    }

    public void applyStatusEffects() {
        // Apply onTurnEnd for all effects
        for (StatusEffect effect : effects) {
            effect.onTurnEnd(this);
        }
        
        // Remove expired effects
        effects = effects.stream()
                        .filter(e -> !e.isExpired())
                        .collect(Collectors.toList());
    }

    // Health management
    public int getCurrentHealth() { 
        return currentHealth; 
    }
    
    public void setCurrentHealth(int health) {
        this.currentHealth = Math.min(health, maxHealth);
        if (this.currentHealth < 0) this.currentHealth = 0;
    }
    
    // Standard getters
    public String getName() { return name; }
    public int getMaxHealth() { return maxHealth; }
    public int getAttackPower() { return attackPower; }
    public List<StatusEffect> getEffects() { return new ArrayList<>(effects); }

    @Override
    public String toString() { 
        return name + " (" + currentHealth + "/" + maxHealth + ")"; 
    }

    @Override
    public Character clone() throws CloneNotSupportedException {
        Character cloned = (Character) super.clone();
        cloned.effects = new ArrayList<>(this.effects); 
        return cloned;
    }
}