package sample;

/**
 * Created by TOSHIBA S70 on 6.10.2017.
 */
public class Shift {

    private String tarih="";
    private String personelTc = "";
    private String personelisim = "";
    private String saat = "";

    public Shift(String tarih,String personelTc,String personelisim,String saat){
        this.tarih = tarih;
        this.personelTc = personelTc;
        this.saat = saat;
        this.personelisim = personelisim;
    }
    public String getTarih(){return tarih;}
    public String getPersonelTc(){return personelTc;}
    public String getSaat(){return saat;}
    public String getPersonelisim(){return personelisim;}

}

