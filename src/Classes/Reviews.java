package Classes;

public class Reviews {

    private String yelp_userId;
    public String getYelp_userId() {
        String uid = yelp_userId;
        return uid;
    }
    public void setYelp_userId(String yelp_userId) {
        String uid = yelp_userId;
        this.yelp_userId = uid;
    }

    private String yelp_reviewId;
    public String getYelp_reviewId() {

        String rid = yelp_reviewId;
        return rid;
    }
    public void setYelp_reviewId(String yelp_reviewId) {
        String rid = yelp_reviewId;
        this.yelp_reviewId = rid;
    }

    private int stars;
    public int getStars() {
        int temp = stars;
        return temp;
    }
    public void setStars(int stars) {
        int temp = stars;
        this.stars = temp;
    }

    private String date;
    public String getDate() {
        String tempDate = date;
        return tempDate;
    }
    public void setDate(String date) {
        String tempDate = date;
        this.date = tempDate;
    }

    private String text;
    public String getText() {
        String tempText = text;
        return tempText;
    }
    public void setText(String text) {
        String tempText = text;
        this.text = tempText;
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

    private int funnyVotes;
    public int getFunnyVotes() {
        int tempFunny = funnyVotes;
        return tempFunny;
    }
    public void setFunnyVotes(int funnyVotes) {
        int tempFunny = funnyVotes;
        this.funnyVotes = tempFunny;
    }

    private int usefulVotes;
    public int getUsefulVotes() {
        int tempUseful = usefulVotes;
        return tempUseful;
    }

    public void setUsefulVotes(int usefulVotes) {
        int tempUseful = usefulVotes;
        this.usefulVotes = tempUseful;
    }

    private int coolVotes;
    public int getCoolVotes() {
        int tempCool = coolVotes;
        return tempCool;
    }
    public void setCoolVotes(int coolVotes) {
        int tempCool = coolVotes;
        this.coolVotes = tempCool;
    }

    private int totalVotes;
    public void setTotalVotes (int totalVotes) {
        int tempTotal = totalVotes;
        this.totalVotes = tempTotal;
    }
    public int getTotalVotes() {
        int tempTotal = totalVotes;
        return tempTotal;
    }
}
