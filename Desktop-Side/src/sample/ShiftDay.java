package sample;


public class ShiftDay {

    String shiftDay = null;
    int groupNo = 0;
    String shiftHourRange = null;

    public ShiftDay(String shiftDay,int groupNo,String shiftHourRange){
        this.shiftDay = shiftDay;
        this.groupNo = groupNo;
        this.shiftHourRange = shiftHourRange;
    }

    public String getShiftDay() {
        return shiftDay;
    }

    public void setShiftDay(String shiftDay) {
        this.shiftDay = shiftDay;
    }

    public int getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(int groupNo) {
        this.groupNo = groupNo;
    }

    public String getShiftHourRange() {
        return shiftHourRange;
    }

    public void setShiftHourRange(String shiftHourRange) {
        this.shiftHourRange = shiftHourRange;
    }
}
