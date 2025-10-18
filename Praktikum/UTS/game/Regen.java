package game;

public class Regen implements StatusEffect {
    private int perTurn;
    private int duration;

    public Regen(int perTurn, int duration) {
        this.perTurn = perTurn;
        this.duration = duration;
    }

    @Override
    public void onTurnStart(Character self) { }

    @Override
    public void onTurnEnd(Character self) {
        if (duration > 0) {
            int before = self.getHealth();
            ((Player) self).heal(perTurn);
            System.out.printf("  Regen: +%d HP => %d%n", perTurn, self.getHealth());
            duration--;
        }
    }

    @Override
    public boolean isExpired() { return duration <= 0; }

    @Override
    public String toString() {
        return "Regen(+" + perTurn + " HP, " + duration + " turns)";
    }
}