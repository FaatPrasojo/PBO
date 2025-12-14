public interface AttackStrategy {
    int calculateDamage(int baseAttack, int level, int threatLevel);
    int computeDamage(Character attacker, Character target);
}