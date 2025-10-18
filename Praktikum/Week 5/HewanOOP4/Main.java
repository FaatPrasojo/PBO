package HewanOOP4;

public class Main {
    public static void main(String[] args) {
        Kucing kucing = new Kucing("Milo", 2, "Oranye");
        kucing.info();
        kucing.berjalan();
        System.out.println(kucing.getNama());
        System.out.println(kucing.umur);
    
        System.out.println();
        Anjing anjing = new Anjing("Doggy", 3, "Kasar");
        anjing.info(); 
        anjing.berjalan(); 
        System.out.println("jenis bulu anjing: " + anjing.getJenisBulu());
        System.out.println("Umur anjing: " + anjing.umur);
    }
}
