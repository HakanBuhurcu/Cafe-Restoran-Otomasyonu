package sample;

/**
 * Created by furkankaplan on 8.08.2017.
 */
public class Employee {

    private int employeeID = 0;
    private String employeeTcNo = null;
    private String employeeName = null;
    private String employeePhone = null;
    private String employeeAddress = null;
    private String statuName = "deneme";
    private int statuID = 0;

    public Employee(String employeeName, int employeeID){
        this.employeeName = employeeName;
        this.employeeID = employeeID;
    }

    public Employee(int employeeID, String employeeTcNo, String employeeName, String employeePhone, String employeeAddress, int statuID){
        this.employeeID = employeeID;
        this.employeeTcNo = employeeTcNo;
        this.employeeName = employeeName;
        this.employeePhone = employeePhone;
        this.employeeAddress = employeeAddress;
        this.statuID = statuID;
    }

    public Employee(int employeeID, String employeeTcNo, String employeeName, String employeePhone, String employeeAddress, String statuName){
        this.employeeID = employeeID;
        this.employeeTcNo = employeeTcNo;
        this.employeeName = employeeName;
        this.employeePhone = employeePhone;
        this.employeeAddress = employeeAddress;
        this.statuName = statuName;
    }



    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public int getStatuID() {
        return statuID;
    }

    public void setStatuID(int statuID) {
        this.statuID = statuID;
    }

    public String getEmployeeTcNo() {
        return employeeTcNo;
    }

    public void setEmployeeTcNo(String employeeTcNo) {
        this.employeeTcNo = employeeTcNo;
    }

    public String getStatuName() {
        return statuName;
    }

    public void setStatuName(String statuName1) {
        this.statuName = statuName1;
    }

}
