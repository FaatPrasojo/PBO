package HewanOOP1;

public class Kucing extends Hewan{
    public Kucing(String nama){
        super(nama);
    }

    @Override
    void bersuara(){
        System.out.println("Meong!");
    }
    
    void mengeong(){
        System.out.println("Meong!");
    }
}
