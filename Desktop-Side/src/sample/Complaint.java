package sample;

/**
 * Created by TOSHIBA S70 on 5.10.2017.
 */
public class Complaint {
    private String tarih = "";
    private String  aciklama = "";
    private String Sube ="";

    public Complaint(String tarih,String aciklama,String sube){
        this.tarih = tarih;
        this.aciklama = aciklama;
        this.Sube = sube;
    }

    public String getTarih(){
        return tarih;
    }
    public String getAciklama(){
        return aciklama;
    }
    public String getSube(){
        return  Sube;
    }
}
