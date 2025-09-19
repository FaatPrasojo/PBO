package Tugas;

public class Buku {
    private String judul;
    private String penulis;
    private String penerbit;

    public Buku(String judul, String penulis, String penerbit) {
        this.judul = judul;
        this.penulis = penulis;
        this.penerbit = penerbit;
    }

    public String info() {
        return String.format("Judul\t: %s\nPenulis\t: %s\nPenerbit: %s", judul, penulis, penerbit);
    }
}
