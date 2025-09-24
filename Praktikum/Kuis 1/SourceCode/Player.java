package SourceCode;

class Player extends Character {
    private int level;
    private int maxHealth; // <-- Ditambahkan

    public Player(String name, int health, int attackPower, int level) {
        super(name, health, attackPower);
        this.level = level;
        this.maxHealth = health; // <-- Menyimpan HP awal sebagai HP maksimum
    }

    @Override
    public void attack(Character target) {
        // Menggunakan attackPower 
        int damage = super.getAttackPower() + (level * 2);
        System.out.println(getName() + " attacks " + target.getName() + " with sword! Damage: " + damage);
        target.takeDamage(damage);
    }

    // 3. Menambahkan fitur healing pada Player
    public void heal(){
        if (getHealth() <= 0) return;
        int healAmount = 25; // Jumlah HP yang dipulihkan
        int newHealth = getHealth() + healAmount;
        if (newHealth > maxHealth) {
            setHealth(maxHealth);
        } else{
            setHealth(newHealth);
        }
        System.out.println(getName() + " uses Heal! Restores " + healAmount + " HP. Current healrt : " + getHealth());
    }    
}
