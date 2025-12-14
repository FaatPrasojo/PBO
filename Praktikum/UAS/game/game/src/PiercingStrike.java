public class PiercingStrike implements Skill {
    private final double multiplier;

    public PiercingStrike(double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public String name() {
        return String.format("PiercingStrike (x%.1f)", multiplier);
    }

    @Override
    public void use(Character user, Character target) {
        if (target != null && target.isAlive()) {
            int damage = (int) (user.getAttackPower() * multiplier);
            target.takeDamage(damage);
        }
    }
}