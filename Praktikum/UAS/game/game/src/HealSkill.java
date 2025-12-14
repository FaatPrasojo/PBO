public class HealSkill implements Skill {
    private final int healAmount;

    public HealSkill(int healAmount) {
        this.healAmount = healAmount;
    }

    @Override
    public String name() {
        return "Heal (+" + healAmount + ")";
    }

    @Override
    public void use(Character user, Character target) {
        if (target != null && target.isAlive()) {
            target.setCurrentHealth(target.getCurrentHealth() + healAmount);
        }
    }
}