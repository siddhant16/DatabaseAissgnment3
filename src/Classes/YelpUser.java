package Classes;

import java.util.List;

public class YelpUser {

    private String name;
    public String getName(){
        String tempName = name;
        return tempName;
    }
    public void setName(String name){
        String tempName = name;
        this.name = tempName;
    }

    private String yelp_userId;
    public String getUserId(){
        String uid = yelp_userId;
        return uid;
    }
    public void setUserId(String yelp_userId){
        String uid = yelp_userId;
        this.yelp_userId = uid;
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

    private int funnyVotes;
    public int getFunnyVotes() {
        int tempFunny = funnyVotes;
        return tempFunny;
    }
    public void setFunnyVotes(int funnyVotes) {
        int tempFunny = funnyVotes;
        this.funnyVotes = tempFunny;
    }

    private int votesTotal;
    public void setVotesTotal(int votesTotal) {
        int tempTotal = votesTotal;
        this.votesTotal = tempTotal;
    }

    public int getVotesTotal() {
        int tempTotal = votesTotal;
        return tempTotal;
    }

    private String yelpingStarted;
    public String getYelpingStarted(){
        String started = yelpingStarted;
        return started;
    }
    public void setYelpingStarted(String yelpingStarted){
        String started = yelpingStarted;
        this.yelpingStarted = started;
    }


    private List<Friends> friendsList;
    public List<Friends> getFriendsList(){
        List<Friends> list = friendsList;
        return list;
    }
    public void setFriendsList(List<Friends> friendsList){
        List<Friends> list = friendsList;
        this.friendsList = list;
    }

    private int fansCount;
    public int getFansCount(){
        int count = fansCount;
        return count;
    }
    public void setFansCount(int fansCount){
        int count = fansCount;
        this.fansCount = count;
    }

    private float avgStars;
    public float getAvgStars(){
        float average = avgStars;
        return average;
    }
    public void setAvgStars(float avgStars){
        float average = avgStars;
        this.avgStars = average;
    }

    private int reviewCnt;
    public int getReviewCnt(){
        int count = reviewCnt;
        return count;
    }
    public void setReviewCnt(int reviewCnt){
        int count = reviewCnt;
        this.reviewCnt = count;
    }

}



