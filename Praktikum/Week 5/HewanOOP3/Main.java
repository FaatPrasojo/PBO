package HewanOOP3;

public class Main {
    public static void main(String[] args) {
        Kucing kucing = new Kucing("Milo", 2);
        kucing.berjalan();
        kucing.menyusui();

        System.out.println();
        Anjing anjing = new Anjing("Doggy", 3, "Bulldog");
        anjing.tampilkanInfo();
        anjing.menggonggong();
    }
}
