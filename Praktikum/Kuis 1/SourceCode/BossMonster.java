package SourceCode;

public class BossMonster extends Character {
    private String type;

    public BossMonster(String name, int health, int attackPower, String type) {
        super(name, health, attackPower);
        this.type = type;
    }

    @Override
    public void attack(Character target) {
        // Serangan spesial dengan damage 2x lipat dari attackPower
        int damage = getAttackPower() * 2;
        System.out.println(getName() + " (" + type + ") uses DEVASTATING STRIKE on " + target.getName() + "! Damage: " + damage);
        target.takeDamage(damage);
    }
}