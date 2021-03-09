package Classes;

public class Attribute {

    private String yelp_businessId;
    public String getYelp_businessId() {
        String bid = yelp_businessId;
        return bid;
    }

    public void setYelp_businessId(String yelp_businessId) {
        String bid = yelp_businessId;
        this.yelp_businessId = bid;
    }

    private String attrName;
    public String getAttrName() {
        String attr = attrName;
        return attr;
    }

    public void setAttrName(String attrName) {
        String attr = attrName;
        this.attrName = attr;
    }

    private String attrValue;
    public String getAttrValue() {
        String attr = attrValue;
        return attr;
    }

    public void setAttrValue(String attrValue) {
        String attr = attrValue;
        this.attrValue = attr;
    }
}
