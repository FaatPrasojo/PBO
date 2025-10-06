package HewanOOP2;

public class Kucing extends Hewan{
    private String ras;

    public Kucing(String nama, int umur, String ras) {
        super(nama, umur);
        this.ras = ras;
    }

    public void mengeong() {
        System.out.println("Meong..");
    }

    public void tampilkanInfo() {
        System.out.println("Nama: " + nama + ", Umur: " + umur + ", Ras: " + ras);
    }
}
