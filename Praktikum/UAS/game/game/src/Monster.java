public class Monster extends Enemy {
    public Monster(String name, int maxHealth, int attackPower, int threatLevel, AttackStrategy strategy) {
        super(name, maxHealth, attackPower, threatLevel, strategy);
    }
    
    @Override
    public String toString() { return name + " (T:" + threatLevel + ")"; }
}