package com.example.khalid.sharektest.Utils;

/**
 * Created by User on 19/10/2016.
 */
public class SearchObject {
    private  String  productname,productdes,productloction,urlProductpic;

    public SearchObject(String productname, String productdes, String productloction, String urlProductpic) {
        this.productname = productname;
        this.productdes = productdes;
        this.productloction = productloction;
        this.urlProductpic = urlProductpic;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductdes() {
        return productdes;
    }

    public void setProductdes(String productdes) {
        this.productdes = productdes;
    }

    public String getProductloction() {
        return productloction;
    }

    public void setProductloction(String productloction) {
        this.productloction = productloction;
    }

    public String getUrlProductpic() {
        return urlProductpic;
    }

    public void setUrlProductpic(String urlProductpic) {
        this.urlProductpic = urlProductpic;
    }
}
