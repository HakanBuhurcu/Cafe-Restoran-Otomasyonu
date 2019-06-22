package sample;

/**
 * Created by TOSHIBA S70 on 4.10.2017.
 */
public class Branch {

    private int BranchCode  ;
    private String BranchName = "";
    private String BranchAdress = "";
    private String BranchPhone = "";

    public Branch(int bcode, String bname, String badress, String bPhone){
        this.BranchCode = bcode;
        this.BranchName = bname;
        this.BranchAdress = badress;
        this.BranchPhone = bPhone;

    }

    public int getBranchCode(){
        return BranchCode;
    }
    public String getBranchName(){
        return  BranchName;
    }
    public String getBranchAdress(){
        return  BranchAdress;
    }
    public String getBranchPhone(){
        return BranchPhone;
    }
    public void setBranchCode(int BranchCode){
        this.BranchCode = BranchCode;
    }
    public void setBranchName(String BranchName){
        this.BranchName = BranchName;
    }
    public void setBranchAdress(String BranchAdress){
        this.BranchAdress = BranchAdress;
    }
    public void setBranchPhone(String BranchPhone){
        this.BranchPhone = BranchPhone;
    }

}