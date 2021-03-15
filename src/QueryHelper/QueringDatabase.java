package QueryHelper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Classes.Attribute;
import Classes.Business;
import Classes.BusinessCat;
import Classes.BusinessSubCat;
import Classes.Reviews;
import Classes.YelpUser;

import static java.lang.Class.forName;
import static java.lang.System.err;
import static java.lang.System.out;

public class QueringDatabase {
    public static final String SYSTEM = "system";
    public static final String ORACLE = "oracle";
    public static final String YYYY_MM = "yyyy-MM";
    public final String dbInstance = "oracle.jdbc.driver.OracleDriver";
    public String resultQuery;
    public String userResultQuery;
    public Connection conn;

    public QueringDatabase() {

    }

    public void ConnectDatabase() {
        try {
            forName(dbInstance);
        } catch (Exception e) {
            String x = "Error in Loading the jdbc driver.";
            err.println(x);
        }
        try {
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            conn = DriverManager.getConnection(url, SYSTEM, ORACLE);
        } catch (Exception e) {
            String x = "Not able to reach the network";
            err.println(x);
        }
    }

    public void CloseDatabaseConnection() {
        try {
            conn.close();
        } catch (Exception e) {
            String connection_to_database_was_unsuccessful = "Connection to database was unsuccessful";
            err.println(connection_to_database_was_unsuccessful);
        }
    }

    /**
     * Insert data into users
     *
     * @param yelpUsers list of yelp 3users
     */
    public void InsertDataIntoUsersTable(List<YelpUser> yelpUsers) {
        PreparedStatement query;
        try {
            String sql = "INSERT INTO Y_USERS " +
                    "(yelp_userId , name , yelpingStarted,  funnyVotes, " +
                    "usefulVotes, coolVotes, totalVotes, reviewCount , " +
                    "fansCount, avgStars ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            query = conn.prepareStatement(sql);

            int i = 0;
            while (i < yelpUsers.size()) {
                query.setString(1, yelpUsers.get(i).getUserId());
                query.setString(2, yelpUsers.get(i).getName());

                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
                java.util.Date date = sdf1.parse(yelpUsers.get(i).getYelpingStarted());
                Date sqlYelpSince = new Date(date.getTime());
                query.setDate(3, sqlYelpSince);
                query.setInt(4, yelpUsers.get(i).getFunnyVotes());
                query.setInt(5, yelpUsers.get(i).getUsefulVotes());
                query.setInt(6, yelpUsers.get(i).getCoolVotes());
                query.setInt(7, yelpUsers.get(i).getVotesTotal());
                query.setInt(8, yelpUsers.get(i).getReviewCnt());
                query.setInt(9, yelpUsers.get(i).getFansCount());
                query.setFloat(10, yelpUsers.get(i).getAvgStars());
                query.addBatch();
                i++;
            }

            query.executeBatch();
            query.close();
            String x = "Row inserted.";
            out.println(x);

        } catch (SQLException | ParseException e) {
            String there_is_error_in_query = "There is error in query";
            err.println(there_is_error_in_query);
        }
    }

    /**
     * Insert data for user friends
     *
     * @param yelpUsers list of users
     */
    public void InsertDataIntoUserFriendsTable(List<YelpUser> yelpUsers) {
        try {
            String sql = "INSERT INTO Y_UserFriends " +
                    "(yelp_userId , yelp_friend_id ) " +
                    "VALUES (?, ?)";
            PreparedStatement query = conn.prepareStatement(sql);

            int i = 0;
            while (i < yelpUsers.size()) {
                int j = 0;
                while (j < yelpUsers.get(i).getFriendsList().size()) {
                    String userId = yelpUsers.get(i).getUserId();
                    query.setString(1, userId);
                    String yelp_userId = yelpUsers.get(i).getFriendsList().get(j).getYelp_userId();
                    query.setString(2, yelp_userId);
                    query.addBatch();
                    j++;
                }
                i++;
            }
            query.executeBatch();
            String x = "Rows Inserted.";
            out.println(x);

        } catch (SQLException e) {
            String there_is_error_in_query = "There is error in query";
            err.println(there_is_error_in_query);
        }
    }


    public void InsertDataIntoBusiness(List<Business> yelpBusiness) {
        try {
            String sql = "INSERT INTO  Y_BUSINESS(y_businessId, name," +
                    " address, city, state, reviewCount, stars) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement query1 = conn.prepareStatement(sql);
            String sql1 = "INSERT INTO Y_BUSINESS_HOURS(y_businessId, day, " +
                    "from_time, to_time) VALUES(?, ?, ?, ?)";
            PreparedStatement query2 = conn.prepareStatement(sql1);
            String sql2 = "INSERT INTO Y_BUSINESS_CAT(y_businessId, category) " +
                    "VALUES(?, ?)";
            PreparedStatement query3 = conn.prepareStatement(sql2);
            String sql3 = "INSERT INTO Y_BUSINESS_SUBCAT(y_businessId, subcategory) " +
                    " VALUES(?,?)";
            PreparedStatement query4 = conn.prepareStatement(sql3);
            String sql4 = "INSERT INTO Y_BUSINESS_ATTR(y_businessId, attrName , attrValue)  " +
                    "VALUES(?,?,?)";
            PreparedStatement query5 = conn.prepareStatement(sql4);

            Iterator<Business> iterator = yelpBusiness.iterator();
            while (true) {
                if (!iterator.hasNext()) break;
                Business business = iterator.next();
                String yelp_businessId = business.getYelp_businessId();
                query1.setString(1, yelp_businessId);
                String name = business.getName();
                query1.setString(2, name);
                String completeAddr = business.getCompleteAddr();
                query1.setString(3, completeAddr);
                String city = business.getCity();
                query1.setString(4, city);
                String state = business.getState();
                query1.setString(5, state);
                int reviewCount = business.getReviewCount();
                query1.setInt(6, reviewCount);
                float stars = business.getStars();
                query1.setFloat(7, stars);
                query1.addBatch();

                List<BusinessCat> businessCategories;
                businessCategories = business.getCategories();
                Iterator<BusinessCat> iter;
                iter = businessCategories.iterator();
                while (iter.hasNext()) {
                    BusinessCat bcat = iter.next();
                    String yelp_businessId1 = bcat.getYelp_businessId();
                    query3.setString(1, yelp_businessId1);
                    String catName = bcat.getCatName();
                    query3.setString(2, catName);
                    query3.addBatch();
                }

                List<BusinessSubCat> businessSubCats;
                businessSubCats = business.getSubCat();
                for (Iterator<BusinessSubCat> it = businessSubCats.iterator(); it.hasNext(); ) {
                    BusinessSubCat bsubcat = it.next();
                    String yelp_businessId1 = bsubcat.getYelp_businessId();
                    query4.setString(1, yelp_businessId1);
                    String subCatName = bsubcat.getSubCatName();
                    query4.setString(2, subCatName);
                    query4.addBatch();
                }

                List<Attribute> attributes;
                attributes = business.getAttributes();
                for (Iterator<Attribute> it = attributes.iterator(); it.hasNext(); ) {
                    Attribute attr = it.next();
                    String yelp_businessId1 = attr.getYelp_businessId();
                    query5.setString(1, yelp_businessId1);
                    String attrName = attr.getAttrName();
                    query5.setString(2, attrName);
                    String attrValue = attr.getAttrValue();
                    query5.setString(3, attrValue);
                    query5.addBatch();
                }

            }
            int[] ints = query1.executeBatch();
            int[] ints1 = query2.executeBatch();
            int[] ints2 = query3.executeBatch();
            int[] ints3 = query4.executeBatch();
            int[] ints4 = query5.executeBatch();
            String x = "Data is inserted into Business Table";
            out.println(x);

        } catch (SQLException se) {
            String query_did_not_execute_properly = "Query did not execute properly";
            err.println(query_did_not_execute_properly);
            se.printStackTrace();
        } catch (Exception e) {
            String query_did_not_execute_properly = "Query did not execute properly";
            err.println(query_did_not_execute_properly);
            e.printStackTrace();
        }
    }


    public void InsertDataIntoReviewsTable(List<Reviews> yelpReviews) {
        try {
            PreparedStatement query;
            String sql = "INSERT INTO Y_REVIEWS " +
                    "(yelp_reviewId, stars, datePublish, text, y_businessId, yelp_userId,  funnyVotes, votes_cool, votes_useful, totalVotes )" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            query = conn.prepareStatement(sql);
            int i = 0;
            int yelpReviewsSize = yelpReviews.size();
            if (i < yelpReviewsSize) {
                do {
                    Reviews review;
                    review = yelpReviews.get(i);
                    String yelp_reviewId = review.getYelp_reviewId();
                    query.setString(1, yelp_reviewId);
                    int stars = review.getStars();
                    query.setInt(2, stars);
                    SimpleDateFormat sdf1;
                    sdf1 = new SimpleDateFormat(YYYY_MM);
                    String date1 = review.getDate();
                    java.util.Date date = sdf1.parse(date1);
                    long time = date.getTime();
                    Date publishDate = new Date(time);
                    query.setDate(3, publishDate);
                    String text = review.getText();
                    query.setString(4, text);
                    String yelp_businessId = review.getYelp_businessId();
                    query.setString(5, yelp_businessId);
                    String yelp_userId = review.getYelp_userId();
                    query.setString(6, yelp_userId);
                    int funnyVotes = review.getFunnyVotes();
                    query.setInt(7, funnyVotes);
                    int coolVotes = review.getCoolVotes();
                    query.setInt(8, coolVotes);
                    int usefulVotes = review.getUsefulVotes();
                    query.setInt(9, usefulVotes);
                    int totalVotes = review.getTotalVotes();
                    query.setInt(10, totalVotes);
                    query.addBatch();
                    i++;
                } while (i < yelpReviewsSize);
            }
            query.executeBatch();
            out.println("Rows inserted into " +
                    "Reviews table");

        } catch (SQLException e) {
            String x = "Query did not " +
                    "execute properly" + e.getMessage();
            err.println(x);
        } catch (ParseException e) {
            String x = "Query did not " +
                    "execute properly" + e.getMessage();
            err.println(x);
        }
    }


    public ArrayList<Attribute> getAttributesFromTable(ArrayList<String> subCatSelected,
                                                       ArrayList<String> catSelected,
                                                       String andOrCondition) {
        ArrayList<Attribute> attributes;
        attributes = new ArrayList<>();
        try {
            StringBuilder query;
            switch (andOrCondition) {
                case "AND":
                    StringBuilder subQuery;
                    subQuery = new StringBuilder("(SELECT y_businessId FROM Y_BUSINESS_CAT" +
                            " WHERE  category = '" + catSelected.get(0) +
                            "')");
                    if (catSelected.size() <= 1) {
                    } else {
                        int i = 1;
                        while (i < catSelected.size()) {
                            subQuery.append(" INTERSECT (SELECT y_businessId FROM Y_BUSINESS_CAT WHERE  " +
                                    "category = '")
                                    .append(catSelected.get(i)).append("' )");
                            i++;
                        }
                    }
                    String str = "SELECT DISTINCT ba.attrName FROM Y_BUSINESS_ATTR ba " +
                            "JOIN Y_BUSINESS_SUBCAT bs on bs.y_businessId = ba.y_businessId " +
                            "JOIN Y_BUSINESS_CAT BC on ba.y_businessId = BC.y_businessId" +
                            " WHERE ba.y_businessId IN ( " + subQuery
                            + " )";
                    query = new StringBuilder(str);

                    break;
                default:

                    String str1 = "SELECT DISTINCT ba.attrName FROM Y_BUSINESS_ATTR ba " +
                            "JOIN Y_BUSINESS_SUBCAT bs on bs.y_businessId = ba.y_businessId " +
                            "JOIN Y_BUSINESS_CAT BC on ba.y_businessId = BC.y_businessId WHERE bc.category = ";
                    query = new StringBuilder(str1);
                    query.append("'").append(catSelected.get(0)).append("' ");

                    if (catSelected.size() <= 1) {
                    } else {
                        int i = 1;
                        if (i >= catSelected.size()) {
                        } else {
                            do {
                                query.append(andOrCondition)
                                        .append(" bc.category = '")
                                        .append(catSelected.get(i)).append("' ");
                                i++;
                            } while (i < catSelected.size());
                        }
                    }
                    break;
            }

            query.append(" AND bs.subcategory = '")
                    .append(subCatSelected.get(0))
                    .append("' ");
            if (subCatSelected.size() <= 1) {
            } else {
                int i = 1;
                while (i < subCatSelected.size()) {
                    query.append(andOrCondition)
                            .append(" bs.subcategory = '")
                            .append(subCatSelected.get(i))
                            .append("' ");
                    i++;
                }
            }
            out.println(query);

            ResultSet rs;
            PreparedStatement query2;
            String sql = query.toString();
            query2 = conn.prepareStatement(sql);
            rs = query2.executeQuery();

            if (rs.next()) {
                do {
                    String string = rs.getString(1);
                    out.println(string);
                    Attribute attr;
                    attr = new Attribute();
                    attr.setAttrName(string);
                    attributes.add(attr);
                } while (rs.next());
            }
        } catch (SQLException se) {
            String query_did_not_execute_properly = "Query did not execute properly";
            err.println(query_did_not_execute_properly);
            se.printStackTrace();
        } catch (Exception e) {
            String query_did_not_execute_properly = "Query did not execute properly";
            err.println(query_did_not_execute_properly);
            e.printStackTrace();
        }
        return attributes;
    }

    public ArrayList<BusinessSubCat> GetSubCatFromTable(ArrayList<String> catSelected,
                                                        String andOrCondition) {
        ArrayList<BusinessSubCat> subCatArrayList;
        subCatArrayList = new ArrayList<>();
        try {
            StringBuilder query;
            switch (andOrCondition) {
                case "AND":
                    StringBuilder subQuery;
                    subQuery = new StringBuilder("(SELECT y_businessId FROM Y_BUSINESS_CAT " +
                            "WHERE  category = '"
                            + catSelected.get(0)
                            + "')");
                    if (catSelected.size() <= 1) {
                    } else {
                        int i = 1;
                        while (i < catSelected.size()) {
                            subQuery.append(" INTERSECT (SELECT y_businessId FROM " +
                                    "Y_BUSINESS_CAT WHERE  " +
                                    "category = '")
                                    .append(catSelected.get(i)).append("' )");
                            i++;
                        }
                    }
                    String str = "SELECT DISTINCT bs.subcategory FROM" +
                            " Y_BUSINESS_SUBCAT bs JOIN Y_BUSINESS_CAT bc " +
                            "on bs.y_businessId = bc.y_businessId  " +
                            "WHERE bs.y_businessId IN ( " + subQuery + " )";
                    query = new StringBuilder(str);

                    break;
                default:

                    String str1 = "SELECT DISTINCT bs.subcategory FROM Y_BUSINESS_SUBCAT " +
                            "bs JOIN Y_BUSINESS_CAT bc on bs.y_businessId = bc.y_businessId  " +
                            "WHERE bc.category = ";
                    query = new StringBuilder(str1);
                    query.append("'")
                            .append(catSelected.get(0))
                            .append("' ");
                    if (catSelected.size() <= 1) {
                    } else {
                        int i = 1;
                        while (i < catSelected.size()) {
                            query.append(andOrCondition).append(" bc.category = '")
                                    .append(catSelected.get(i))
                                    .append("' ");
                            i++;
                        }
                    }
                    break;
            }
            out.println(query);
            PreparedStatement query2;
            query2 = conn.prepareStatement(query.toString());
            ResultSet rs;
            rs = query2.executeQuery();
            if (!rs.next()) {
            } else {
                do {
                    String string;
                    string = rs.getString(1);
                    out.println(string);

                    BusinessSubCat sub;
                    sub = new BusinessSubCat();
                    sub.setSubCatName(string);
                    subCatArrayList.add(sub);
                } while (rs.next());
            }
        } catch (SQLException se) {
            String query_not_executed = "Query not executed";
            err.println(query_not_executed);
            se.printStackTrace();
        } catch (Exception e) {
            String query_not_correct_ = "Query not correct ";
            err.println(query_not_correct_);
            e.printStackTrace();
        }
        return subCatArrayList;
    }


    public ArrayList<Business> FilterBusinessQuery
    (ArrayList<String> catSelected, ArrayList<String> subCatSelected,
     ArrayList<String> attributedSelected, String reviewFrom, String reviewTo,
     String starCondition, String starValue, String votesCondition,
     String votesValue, String condition) {

        ArrayList<Business> businesses;
        businesses = new ArrayList<>();
        try {
            //String query = "SELECT DISTINCT b.businessId, b.name, b.city, b.state, b.stars FROM BUSINESS b, Reviews r WHERE b.businessid = r.businessid AND b.businessid IN ";
            StringBuilder query;
            String str = "\nSELECT DISTINCT b.y_businessId, b.name, b.city, b.state, b.stars FROM Y_BUSINESS" +
                    " b, Y_Reviews r WHERE b.y_businessId = r.y_businessId AND b.y_businessId IN ";

            query = new StringBuilder(str);

            String pairingcond;
            switch (condition) {
                case "AND":
                    pairingcond = "INTERSECT";
                    break;
                default:
                    pairingcond = "UNION";
                    break;
            }

            query.append(" (");
            query.append(" \n( SELECT bc.y_businessId from Y_Business_cat bc where bc.category = '")
                    .append(catSelected.get(0))
                    .append("' ");
            switch (catSelected.size()) {
                case 1:
                    query.append(" )");
                    break;
            }
            if (catSelected.size() <= 1) {
            } else {
                int i = 1;
                while (i < catSelected.size()) {
                    query.append(pairingcond).append(" \nSELECT bc.y_businessId from " +
                            "Y_Business_cat bc where bc.category = '")
                            .append(catSelected.get(i))
                            .append("' ");
                    i++;
                }
                query.append(" )");
            }



            if (subCatSelected.size() <= 0) {
            } else {
                query.append(" INTERSECT ");
                query.append(" \n( SELECT bs.y_businessId from Y_Business_subcat bs " +
                        "where bs.subcategory = '")
                        .append(subCatSelected.get(0))
                        .append("' ");
                if (subCatSelected.size() <= 1) {
                } else {
                    int i = 1;
                    while (i < subCatSelected.size()) {
                        query.append(" INTERSECT ").append(" \nSELECT bs.y_businessId from " +
                                "Y_Business_subcat bs where bs.subcategory = '")
                                .append(subCatSelected.get(i)).append("'");
                        i++;
                    }
                }
                query.append(" )");
            }

            if (attributedSelected.size() <= 0) {
            } else {
                query.append(pairingcond);
                query.append(" \n( SELECT ba.y_businessId from Y_Business_attr ba where ba.attrName = '")
                        .append(attributedSelected.get(0))
                        .append("' ");
                if (subCatSelected.size() <= 1) {
                } else {
                    int i = 1;
                    if (i < subCatSelected.size()) {
                        do {
                            query.append(pairingcond)
                                    .append(" \nSELECT ba.y_businessId from Y_Business_attr ba " +
                                            "where ba.attrName = '")
                                    .append(attributedSelected.get(i))
                                    .append("'");
                            i++;
                        } while (i < subCatSelected.size());
                    }
                }
                query.append(" )");
            }

            query.append(" )");

            if (!reviewFrom.isEmpty()) {
                query.append(" AND r.DATEPUBLISH > '").append(reviewFrom).append("'");
            }

            if (!reviewTo.isEmpty()) {
                query.append(" AND r.DATEPUBLISH < '").append(reviewTo).append("'");
            }

            if(!starValue.isEmpty()){
                query.append(" AND r.stars ").append(starCondition).append(" ").append(Float.parseFloat(starValue));
            }

            if(!votesValue.isEmpty()){
                query.append(" AND r.totalvotes ").append(votesCondition).append(" ").append(Integer.parseInt(votesValue));
            }

            out.println(query);
            resultQuery = query.toString();

            PreparedStatement query2 = conn.prepareStatement(query.toString());

            //PreparedStatement query2 = conn.prepareStatement(query.toString());
            try (ResultSet rs = query2.executeQuery()) {
                if (rs.next()) {
                    do {
                        Business business = new Business();
                        String string = rs.getString(1);
                        business.setYelp_businessId(string);
                        String string1 = rs.getString(2);
                        business.setName(string1);
                        String string2 = rs.getString(3);
                        business.setCity(string2);
                        String string3 = rs.getString(4);
                        business.setState(string3);
                        float aFloat = rs.getFloat(5);
                        business.setStars(aFloat);
                        businesses.add(business);
                    } while (rs.next());
                }
            }
        } catch (SQLException se) {
            String query_did_not_execute_properly = "Query did not execute properly";
            err.println(query_did_not_execute_properly);
            se.printStackTrace();
        } catch (Exception e) {
            String query_did_not_execute_properly = "Query did not execute properly";
            err.println(query_did_not_execute_properly);
            e.printStackTrace();
        }
        return businesses;
    }

    public ArrayList<BusinessCat> GetDataOfAllCategories() {
        ArrayList<BusinessCat> businessCats;
        businessCats = new ArrayList<>();
        try {
            ResultSet rs;

            PreparedStatement query1;
            String sql = "SELECT DISTINCT category FROM " +
                    "Y_BUSINESS_CAT  ORDER BY category";
            query1 = conn.prepareStatement(sql);
            rs = query1.executeQuery();

            if (rs.next()) {
                String string;
                string = rs.getString(1);
                out.println(string);
                BusinessCat cat;
                cat = new BusinessCat();
                cat.setCatName(string);
                businessCats.add(cat);
                while (rs.next()) {
                    String s2 = rs.getString(1);
                    out.println(s2);

                    cat = new BusinessCat();
                    cat.setCatName(s2);
                    businessCats.add(cat);
                }
            }
        } catch (SQLException se) {
            String query_did_not_executed_properly = "Query did not executed properly";
            err.println(query_did_not_executed_properly);
            se.printStackTrace();
        } catch (Exception e) {
            String query_did_not_executed_properly_ = "Query did not executed properly ";
            err.println(query_did_not_executed_properly_);
            query_did_not_executed_properly_ = "";
            e.printStackTrace();
        }
        return businessCats;
    }


    public ArrayList<YelpUser> FilterUsersQuery(String yelpingStarted,
                                                String reviewCond, String reviewValues,
                                                String friendsCond, String friendsVal,
                                                String starsCond, String starsValue,
                                                String votesCond, String votesValues,
                                                String andOrCondition) {
        String query22 = "";
        ArrayList<YelpUser> yelpUsersLists;
        yelpUsersLists = new ArrayList<>();
        try {
            String query1 = query22;
            query1 = "SELECT DISTINCT u.name, u.yelpingStarted, u.avgStars, " +
                    "u.yelp_userId FROM Y_users u ";


            if (yelpingStarted.isEmpty()) {
            } else {
                query1 += new StringBuilder().append(" WHERE u.yelpingStarted >= '").append(yelpingStarted).append("'").toString();
            }

            if (reviewValues.isEmpty()) {
            } else {
                query1 = getConditionalQuery(andOrCondition, query1);
                query1 += new StringBuilder().append(" u.reviewCount ").append(reviewCond).append(" ").append(reviewValues).toString();
            }
            if (starsValue.isEmpty()) {
            } else {
                query1 = getConditionalQuery(andOrCondition, query1);
                query1 += new  StringBuilder().append(" u.avgStars ").append(starsCond).append(" ").append(starsValue).toString();
            }

            if (votesValues.isEmpty()) {
            } else {
                query1 = getConditionalQuery(andOrCondition, query1);
                query1 += new StringBuilder().append(" u.totalVotes ").append(votesCond).append(" ").append(votesValues).toString();
            }

            if (friendsVal.isEmpty()) {
            } else {
                query1 = getConditionalQuery(andOrCondition, query1);
                query1 += new StringBuilder()
                        .append(" u.yelp_userid in (select f.yelp_userid from Y_USERFRIENDS f")
                        .append(" having count(f.yelp_friend_id) ").append(friendsCond)
                        .append(" ").append(friendsVal)
                        .append(" group by f.yelp_userid)").toString();
            }

            out.println("--------------------QUERY----------------------\n" + query1);
            userResultQuery = query1;
            PreparedStatement query2 = conn.prepareStatement(query1);
            ResultSet rs;
            rs = query2.executeQuery();
            if (!rs.next()) {
            } else {
                do {
                    YelpUser yelpUser = new YelpUser();
                    String string;
                    string = rs.getString(1);
                    yelpUser.setName(string);
                    String string1 = rs.getString(2);
                    yelpUser.setYelpingStarted(string1);
                    float avgStars = Float.parseFloat(rs.getString(3));
                    yelpUser.setAvgStars(avgStars);
                    String string2 = rs.getString(4);
                    yelpUser.setUserId(string2);
                    yelpUsersLists.add(yelpUser);
                } while (rs.next());
            }
        } catch (SQLException se) {
            String query_did_not_execute_properly = "Query did not execute properly";
            err.println(query_did_not_execute_properly);
            se.printStackTrace();
        } catch (Exception e) {
            String query_did_not_execute_properly = "Query did not execute properly";
            err.println(query_did_not_execute_properly);
            e.printStackTrace();
        }
        return yelpUsersLists;

    }

    private String getConditionalQuery(String andOrCondition, String query1) {
        query1 += " ";
        if(query1.contains("WHERE")){
            query1 += new StringBuilder().append(andOrCondition).toString();
        }
        else{
            query1 += new StringBuilder().append(" WHERE ").toString();
        }
        return query1;
    }

    public ArrayList<Reviews> GetUserReviewsFromTable(String yelpUserId) {
        ArrayList<Reviews> yelpReviews;
        yelpReviews = new ArrayList<>();
        try {
            String query = "SELECT b.name, r.text FROM  Y_Business b, Y_Reviews r " +
                    "where b.y_businessId = r.y_businessId AND r.yelp_userId = '" + yelpUserId + "'";

            userResultQuery = query;
            PreparedStatement query2;
            query2 = conn.prepareStatement(query);
            try (ResultSet rs = query2.executeQuery()) {
                if (!rs.next()) {
                } else {
                    do {
                        Reviews review;
                        review = new Reviews();
                        String string = rs.getString(1);
                        review.setYelp_businessId(string);
                        String string1 = rs.getString(2);
                        review.setText(string1);
                        yelpReviews.add(review);
                    } while (rs.next());
                }
            }
        } catch (SQLException se) {
            String query_did_not_execute_properly = "Query did not execute properly";
            err.println(query_did_not_execute_properly);
            se.printStackTrace();
        } catch (Exception e) {
            String query_did_not_execute_properly = "Query did not execute properly";
            err.println(query_did_not_execute_properly);
            e.printStackTrace();
        }
        return yelpReviews;
    }

    public ArrayList<Reviews> GetBusinessReviewsOutput(String yelp_businessId, String reviewFrom, String reviewTo,
                                                       String starCond,
                                                       String starValue,
                                                       String votesCond,
                                                       String votesValue) {

        String x = new StringBuilder()
                .append("-------------------BUSINESS ID-----------\n")
                .append(yelp_businessId)
                .toString();

        out.println(x);

        ArrayList<Reviews> reviewsArrayList = new ArrayList<>();
        try {
            String query;
            query = new StringBuilder()
                    .append("SELECT u.name, r.text FROM Y_REVIEWS r JOIN Y_Users")
                    .append(" u ON u.yelp_userid = r.yelp_userid WHERE r.y_businessId = '")
                    .append(yelp_businessId)
                    .append("'").toString();

            if (reviewFrom.isEmpty()) {
            } else {
                query = query + (new StringBuilder()
                        .append(" AND r.datePublish >= '")
                        .append(reviewFrom)
                        .append("'")
                        .toString());
            }

            if (reviewTo.isEmpty()) {
            } else {
                query = query + (new StringBuilder()
                        .append(" AND r.datePublish <= '")
                        .append(reviewTo).append("'").toString());
            }

            if (starValue.isEmpty()) {
            } else {
                query = query + (" AND r.stars " + starCond + " " + Float.parseFloat(starValue));
            }

            if (votesValue.isEmpty()) {
            } else {
                query = query + (" AND r.totalVotes " + votesCond + " " + Integer.parseInt(votesValue));
            }

            resultQuery = query;
            PreparedStatement query2;
            query2 = conn.prepareStatement(query);
            try (ResultSet rs = query2.executeQuery()) {
                if (!rs.next()) {
                } else {
                    do {
                        Reviews r;
                        r = new Reviews();
                        String string = rs.getString(1);
                        r.setYelp_userId(string);
                        String string1;
                        string1 = rs.getString(2);
                        r.setText(string1);
                        reviewsArrayList.add(r);
                    } while (rs.next());
                }
            }

        } catch (SQLException se) {
            String query_did_not_execute_properly = "Query did not execute properly";
            err.println(query_did_not_execute_properly);
            se.printStackTrace();
        } catch (Exception e) {
            String query_did_not_execute_properly = "Query did not execute properly";
            err.println(query_did_not_execute_properly);
            e.printStackTrace();
        }
        return reviewsArrayList;
    }

}

