package HewanOOP3;

public class Mamalia extends Hewan{
    public Mamalia(String nama, int umur){
        super(nama, umur);
    }

    void menyusui(){
        System.out.println("Menyusui anaknya..");
    }
}
