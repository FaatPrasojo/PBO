package SourceCode;

public abstract class Character {
    private String name;
    private int health;
    private int attackPower;

    public Character(String name, int health, int attackPower){
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    public String getName(){return name;};
    public int getHealth(){return health;};
    public int getAttackPower(){return attackPower;}; // <-- Ditambahkan
    public void setHealth(int health){this.health = health;};

    public void takeDamage(int damage){
        health -= damage;
        if (health < 0)health = 0;
            System.out.println(name + " took " + damage + " damage! Remaining health : " + health);    
    }
    public abstract void attack(Character target);
}   