public abstract class Enemy extends Character {
    protected int threatLevel;

    public Enemy(String name, int maxHealth, int attackPower, int threatLevel, AttackStrategy strategy) {
        super(name, maxHealth, attackPower, strategy);
        this.threatLevel = threatLevel;
    }

    @Override
    public int attack(Character target) {
        int damage = attackStrategy.calculateDamage(attackPower, 0, threatLevel);
        target.takeDamage(damage);
        applyStatusEffects(); // Panggil efek setelah turn selesai
        return damage;
    }

    public int getThreatLevel() { return threatLevel; }

    @Override
    public Enemy clone() throws CloneNotSupportedException {
        return (Enemy) super.clone();
    }
}