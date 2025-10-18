package game;

public class LevelScaledStrategy implements AttackStrategy {
    private int bonusPerLevel;

    public LevelScaledStrategy(int bonusPerLevel) {
        this.bonusPerLevel = bonusPerLevel;
    }

    @Override
    public int computeDamage(Character self, Character target) {
        int bonus = (self instanceof Player p) ? p.getAttackPower() + (p.getAttackPower() / 10 * bonusPerLevel) : self.getAttackPower();
        return bonus;
    }
}