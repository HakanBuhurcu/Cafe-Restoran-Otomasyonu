package sample;

/**
 * Created by TOSHIBA S70 on 5.10.2017.
 */
public class LogRecords {

    private String userTcNo = "";
    private String userName = "";
    private String userStatu = "";
    private String process = "";
    private String processTime = "";

    public LogRecords(String userTcNo,String userName,String userStatu,String process,String processTime){
        this.process = process;
        this.processTime = processTime;
        this.userName = userName;
        this.userTcNo = userTcNo;
        this.userStatu = userStatu;
    }

    public String getUserTcNo(){return userTcNo;}
    public String getUserName(){return  userName;}
    public  String getProcess(){return process;}
    public  String getProcessTime(){return processTime;}
    public String getUserStatu(){return  userStatu;}
}
