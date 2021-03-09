package Classes;

import java.util.ArrayList;

public class Business {

    private String yelp_businessId;

    public String getYelp_businessId() {
        String bid = yelp_businessId;
        return bid;
    }
    public void setYelp_businessId(String yelp_businessId) {
        String bid = yelp_businessId;
        this.yelp_businessId = bid;
    }
    private String completeAddr;

    public ArrayList<BusinessCat> getCategories() {
        ArrayList<BusinessCat> cat = categories;
        return cat;
    }

    public void setCategories(ArrayList<BusinessCat> categories) {
        ArrayList<BusinessCat> cat = categories;
        this.categories = cat;
    }
    private String city;

    ArrayList<BusinessSubCat> subCat = new ArrayList<>();
    public ArrayList<BusinessSubCat> getSubCat() {
        ArrayList<BusinessSubCat> tempSubCat = subCat;
        return tempSubCat;
    }
    public void setSubCat(ArrayList<BusinessSubCat> subCat) {
        ArrayList<BusinessSubCat> tempSubCat = subCat;
        this.subCat = tempSubCat;
    }

    public String getCompleteAddr() {
        String addr = completeAddr;
        return addr;
    }

    public void setCompleteAddr(String completeAddr) {
        String addr = completeAddr;
        this.completeAddr = addr;
    }
    ArrayList<BusinessCat> categories = new ArrayList<>();

    public String getCity() {
        String businessCIty = city;
        return businessCIty;
    }
    public void setCity(String city) {
        String businessCIty = city;
        this.city = businessCIty;
    }

    private String name;

    public String getName() {
        String tempName = name;
        return tempName;
    }
    public void setName(String name) {
        String tempName = name;
        this.name = tempName;
    }
    private String state;

    public String getState() {
        String tempState = state;
        return tempState;
    }
    public void setState(String state) {
        String tempState = state;
        this.state = tempState;
    }
    private float stars;

    public float getStars() {
        float tempStars = stars;
        return tempStars;
    }
    public void setStars(float stars) {
        float tempStars = stars;
        this.stars = tempStars;
    }

    private int reviewCount;
    public int getReviewCount() {
        int tempCnt = reviewCount;
        return tempCnt;
    }
    public void setReviewCount(int reviewCount) {
        int tempCnt = reviewCount;
        this.reviewCount = tempCnt;
    }

    ArrayList<Attribute> attributes = new ArrayList<>();
    public ArrayList<Attribute> getAttributes() {
        ArrayList<Attribute> attrTemp = attributes;
        return attrTemp;
    }
    public void setAttributes(ArrayList<Attribute> attributes) {
        ArrayList<Attribute> attrTemp = attributes;
        this.attributes = attrTemp;
    }
}
