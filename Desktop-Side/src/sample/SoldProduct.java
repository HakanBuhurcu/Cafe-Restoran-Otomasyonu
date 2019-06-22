package sample;

/**
 * Created by furkankaplan on 20.07.2017.
 */
public class SoldProduct {

    private String categoryName;
    private String productName;

    private double profit = 0.0;
    private int count = 0;
    private double win = 0.0;
    private String branchName;

    public SoldProduct(String categoryName, String productName, double win, double profit, int count, String branchName){
        this.categoryName = categoryName;
        this.productName = productName;
        this.win = win;
        this.profit = profit;
        this.count = count;
        this.branchName = branchName;
    }

    public String getProductName() {
        return productName;
    }
    public String getBranchName(){
        return branchName;
    }
    public double getWin(){
        return win;
    }

    public void setWin(double gelen){
        this.win = gelen;
    }

    public void setProductName(int productID)
    {
        this.productName = productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(int categoryID)
    {
        this.categoryName = categoryName;
    }

    public String getName()
    {
        return productName;
    }

    public void setName(String name) {
        this.productName = name;
    }


    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
