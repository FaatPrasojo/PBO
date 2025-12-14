public class Regen implements StatusEffect {
    private final int perTurn;
    private int duration;

    public Regen(int perTurn, int duration) {
        if (perTurn < 0 || duration < 0) {
            throw new IllegalArgumentException("Regen amount and duration must be non-negative.");
        }
        this.perTurn = perTurn;
        this.duration = duration;
    }

    @Override
    public void onTurnStart(Character self) {
        // Do nothing at turn start
    }

    @Override
    public void onTurnEnd(Character self) {
        if (duration > 0 && self.isAlive()) {
            // Perbaikan: Menggunakan setCurrentHealth
            self.setCurrentHealth(self.getCurrentHealth() + perTurn);
            System.out.println("[End Effects] " + self.getName() + " Regen: +" + 
                                 perTurn + " HP => " + self.getCurrentHealth());
            duration--;
            
            if (duration > 0) {
                // System.out.println("Regen remaining: " + duration + " turn(s)");
            } else {
                // System.out.println("Regen EXPIRES");
            }
        }
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return rawDamage;
    }

    @Override
    public boolean isExpired() {
        return duration <= 0;
    }

    @Override
    public String toString() {
        return "Regen(+" + perTurn + " HP, " + duration + " turns)";
    }
}