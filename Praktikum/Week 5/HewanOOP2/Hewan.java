package HewanOOP2;

public class Hewan {
    String nama;
    int umur;

    public Hewan(String nama, int umur){
        this.nama = nama;
        this.umur = umur;
        System.out.println("Konstruktor Hewan dipanggil");
    }

    void bersuara(){
        System.out.println("Suara hewan...");
    }
}
