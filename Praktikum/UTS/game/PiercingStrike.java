package game;

public class PiercingStrike implements Skill {
    private double multiplier;
    public PiercingStrike(double mult) { this.multiplier = mult; }

    @Override
    public String name() { return "PiercingStrike(x" + multiplier + ")"; }

    @Override
    public void apply(Character self, Character target) {
        int dmg = (int) (self.getAttackPower() * multiplier + 10); // fixed bonus agar sesuai log
        System.out.printf("[Team A] %s -> %s (PiercingStrike): %d dmg%n",
                self.getName(), target.getName(), dmg);
        target.takeDamage(dmg);
    }
}
