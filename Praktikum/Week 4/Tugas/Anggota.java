package Tugas;

public class Anggota {
    private String idAnggota;
    private String nama;

    public Anggota(String idAnggota, String nama) {
        this.idAnggota = idAnggota;
        this.nama = nama;
    }

    public String getIdAnggota() {
        return idAnggota;
    }

    public String getNama() {
        return nama;
    }
    
    public String info() {
        return String.format("ID: %s\nNama: %s", idAnggota, nama);
    }
}
