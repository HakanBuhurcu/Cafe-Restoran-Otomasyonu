package sample;

/**
 * Created by furkankaplan on 20.07.2017.
 */
public class Product {

    private int productID = 0;
    private int categoryID = 0;
    private String categoryName = "";
    private String name = "";
    private String costPrice = "";
    private String salePrice = "";

    public Product(String categoryName,String name,String costPrice, String salePrice,int categoryID){
        this.categoryName = categoryName;
        this.name = name;
        this.costPrice = costPrice;
        this.salePrice = salePrice;
        this.categoryID = categoryID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID =  categoryID;
    }
}
