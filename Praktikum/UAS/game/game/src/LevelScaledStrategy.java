public class LevelScaledStrategy implements AttackStrategy {
    private final int scalingFactor;


    // Tambahkan constructor yang menerima argumen:
    public LevelScaledStrategy(int scalingFactor) {
        this.scalingFactor = scalingFactor;
    }

    @Override
    public int calculateDamage(int baseAttack, int level, int threatLevel) {
        return baseAttack + (level * scalingFactor); 
    }
    
    @Override
    public int computeDamage(Character attacker, Character target) {
        if (attacker instanceof Player) {
            Player p = (Player) attacker;
            return p.getAttackPower() + (p.getLevel() * scalingFactor);
        }
        return attacker.getAttackPower();
    }
    
    @Override
    public String toString() { return "LevelScaledStrategy(x" + scalingFactor + ")"; }
}