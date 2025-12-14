public class BossMonster extends Enemy {
    public BossMonster(String name, int maxHealth, int attackPower, int threatLevel, AttackStrategy strategy) {
        super(name, maxHealth, attackPower, threatLevel, strategy);
    }
    
    // Implementasi khusus Rage Strike yang diperlukan GameTest
    @Override
    public int attack(Character target) {
        int baseDamage = attackPower;
        
        // Rage Strike: Attack power x 1.5 jika HP < 50%
        if (getCurrentHealth() <= (maxHealth / 2)) {
            System.out.println("BOSS RAGE STRIKE!");
            baseDamage = (int) (baseDamage * 1.5);
        }
        
        int damage = attackStrategy.calculateDamage(baseDamage, 0, threatLevel);
        target.takeDamage(damage);
        applyStatusEffects();
        return damage;
    }
    
    @Override
    public String toString() { return name + " (BOSS T:" + threatLevel + ")"; }
}