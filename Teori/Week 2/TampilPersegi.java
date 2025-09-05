public class TampilPersegi {
    public static void main(String[] args) {

        Persegi p = new Persegi();

        p.sisi = 10;

        System.out.println("===== Data Persegi =====");
        p.dataPersegi();
        System.out.println("Luas Persegi: " + p.luasPersegi());
        System.out.println("Keliling Persegi: " + p.kelilingPersegi());
    }
}