public interface StatusEffect {
    void onTurnStart(Character self);
    void onTurnEnd(Character self);
    int modifyIncomingDamage(int rawDamage);
    boolean isExpired();
}