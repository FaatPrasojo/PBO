package game;

public class HealSkill implements Skill {
    private int amount;
    public HealSkill(int amt) { this.amount = amt; }

    @Override
    public String name() { return "HealSkill(+" + amount + ")"; }

    @Override
    public void apply(Character self, Character target) {
        if (self instanceof Player p) {
            int before = p.getHealth();
            p.heal(amount);
            System.out.printf("[Team A] %s uses HealSkill(+%d): %d -> %d%n",
                    p.getName(), amount, before, p.getHealth());
        }
    }
}
