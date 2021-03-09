package Classes;

public class BusinessCat {
    private String yelp_businessId;
    public String getYelp_businessId() {
        String bid = yelp_businessId;
        return bid;
    }
    public void setYelp_businessId(String yelp_businessId) {
        String bid = yelp_businessId;
        this.yelp_businessId = bid;
    }

    private String catName;
    public String getCatName() {
        String cat = catName;
        return cat;
    }
    public void setCatName(String catName) {
        String cat = catName;
        this.catName = cat;
    }

}
