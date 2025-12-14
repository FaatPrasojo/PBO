public class Shield implements StatusEffect {
    private final int damageReduction;
    private int duration;

    public Shield(int damageReduction, int duration) {
        this.damageReduction = damageReduction;
        this.duration = duration;
    }

    @Override
    public void onTurnStart(Character self) {}

    @Override
    public void onTurnEnd(Character self) {
        if (duration > 0) {
            duration--;
        }
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return Math.max(0, rawDamage - damageReduction);
    }

    @Override
    public boolean isExpired() {
        return duration <= 0;
    }

    @Override
    public String toString() {
        return "Shield(-" + damageReduction + " Dmg, " + duration + " turns)";
    }
}