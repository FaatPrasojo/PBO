package HewanOOP3;

public class Anjing extends Mamalia {
    private String jenis;

    public Anjing(String nama, int umur, String jenis) {
        super(nama, umur);
        this.jenis = jenis;
    }

    public void menggonggong() {
        System.out.println(nama + " menggonggong: Guk! Guk!");
    }

    public void tampilkanInfo() {
        System.out.println("Nama: " + nama + ", Umur: " + umur + ", Jenis: " + jenis);
    }
}
