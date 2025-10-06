package HewanOOP3;

public class Kucing extends Mamalia{
    public Kucing(String nama, int umur){
        super(nama, umur);
    }

    public void berjalan(){
        System.out.println( nama + " sedang berjalan...");
    }
}
