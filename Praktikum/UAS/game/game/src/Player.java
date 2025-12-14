import java.util.ArrayList;
import java.util.List;

public class Player extends Character {
    private int level;
    private List<Skill> skills = new ArrayList<>();

    public Player(String name, int maxHealth, int attackPower, int level, AttackStrategy strategy) {
        super(name, maxHealth, attackPower, strategy);
        this.level = level;
    }

    @Override
    public int attack(Character target) {
        int damage = attackStrategy.calculateDamage(attackPower, level, 0); 
        target.takeDamage(damage);
        applyStatusEffects(); // Panggil efek setelah turn selesai
        return damage;
    }

    public void addSkill(Skill skill) {
        this.skills.add(skill);
    }

    public List<Skill> getSkills() { return skills; }
    public int getLevel() { return level; }
    
    @Override
    public Player clone() throws CloneNotSupportedException {
        Player cloned = (Player) super.clone();
        cloned.skills = new ArrayList<>(this.skills);
        return cloned;
    }
    
    // Override toString untuk JComboBox
    @Override
    public String toString() { return name + " (Lvl " + level + ")"; }
}