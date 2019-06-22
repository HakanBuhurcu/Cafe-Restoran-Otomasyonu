package sample;

/**
 * Created by furkankaplan on 9.08.2017.
 */
public class CafeTable {
   private int tableID  = 0;

   public CafeTable(int tableID){
       this.tableID = tableID;
   }

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }
}
