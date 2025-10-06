package HewanOOP4;

class Kucing extends Hewan {
    private String warnaBulu;

    public Kucing(String nama, int umur, String warnaBulu) {
        super(nama, umur);
        this.warnaBulu = warnaBulu;
    }

    public void info() {
        super.info(); // Menggunakan 'super' untuk mengakses metode dari parent class
        System.out.println("Warna bulu: " + warnaBulu);
    }
}