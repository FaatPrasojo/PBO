package HewanOOP4;

public class Anjing extends Hewan {
    private String jenisBulu;

    public Anjing(String nama, int umur, String jenisBulu) {
        super(nama, umur);
        this.jenisBulu = jenisBulu;
    }

    public String getJenisBulu() {
        return this.jenisBulu;
    }

    
    @Override
    public void info() {
        super.info();
        System.out.println("Jenis Bulu: " + this.jenisBulu);
    }
}