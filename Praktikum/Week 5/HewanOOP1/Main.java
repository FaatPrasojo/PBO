package HewanOOP1;

public class Main {
    public static void main(String[] args) {
        Kucing kucing = new Kucing("Milo");
        kucing.bersuara(); // Output : Meong! (karena metode di-override) 
        kucing.mengeong();

        Anjing doggy = new Anjing("Doggy");
        doggy.bersuara();
        doggy.menggonggong();
    }
}
