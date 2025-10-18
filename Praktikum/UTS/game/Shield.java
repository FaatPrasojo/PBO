package game;

public class Shield implements StatusEffect {
    private int flatReduce;
    private int duration;

    public Shield(int flatReduce, int duration) {
        this.flatReduce = flatReduce;
        this.duration = duration;
    }

    public int reduceDamage(int dmg) {
        return Math.max(0, dmg - flatReduce);
    }

    @Override
    public void onTurnStart(Character self) { }

    @Override
    public void onTurnEnd(Character self) {
        duration--;
        if (duration > 0)
            System.out.printf("  Shield remaining: %d turns%n", duration);
        else
            System.out.println("  Shield EXPIRES");
    }

    @Override
    public boolean isExpired() { return duration <= 0; }

    @Override
    public String toString() {
        return "Shield(-" + flatReduce + " dmg, " + duration + " turns)";
    }
}