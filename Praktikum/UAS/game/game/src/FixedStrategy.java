public class FixedStrategy implements AttackStrategy {
    @Override
    public int calculateDamage(int baseAttack, int level, int threatLevel) {
        return baseAttack; // Fixed damage
    }

    @Override
    public int computeDamage(Character attacker, Character target) {
        // Logika sederhana: AP - 0
        return attacker.getAttackPower();
    }
    
    @Override
    public String toString() { return "FixedStrategy"; }
}