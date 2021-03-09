package Classes;

public class BusinessSubCat {
    private String subCatName;
    public String getSubCatName() {
        String subCat = subCatName;
        return subCat;
    }
    public void setSubCatName(String subCatName) {

        String subCat = subCatName;
        this.subCatName = subCat;
    }

    private String yelp_businessId;
    public String getYelp_businessId() {
        String bid = yelp_businessId;
        return bid;
    }
    public void setYelp_businessId(String yelp_businessId) {
        String bid = yelp_businessId;
        this.yelp_businessId = bid;
    }
}
