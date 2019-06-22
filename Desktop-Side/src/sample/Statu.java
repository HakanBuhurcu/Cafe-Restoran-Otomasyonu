package sample;

/**
 * Created by furkankaplan on 9.08.2017.
 */
public class Statu {
    private int statuID = 0;
    private String statuName = null;

    public Statu(int statuID, String statuName){
        this.statuID = statuID;
        this.statuName = statuName;
    }

    public int getStatuID() {
        return statuID;
    }

    public void setStatuID(int statuID) {
        this.statuID = statuID;
    }

    public String getStatuName() {
        return statuName;
    }

    public void setStatuName(String statuName) {
        this.statuName = statuName;
    }
}
