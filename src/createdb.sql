CREATE TABLE Y_Users(
  yelp_userId varchar(75) primary key,
  name varchar(75),
  yelpingStarted date,
  funnyVotes int,
  usefulVotes int,
  coolVotes int,
  totalVotes number,
  reviewCount int,
  fansCount int,
  avgStars float
);

CREATE TABLE Y_UserFriends(yelp_userId varchar(75), yelp_friend_id varchar(75));

CREATE TABLE Y_Reviews(
  yelp_reviewId varchar(75) primary key,
  stars number check (
    stars between 1 and 5
    ),
  datePublish date,
  text varchar(3000),
  y_businessId varchar(75),
  yelp_userId varchar(75),
  funnyVotes number,
  votes_cool number,
  votes_useful number,
  totalVotes number
);

CREATE TABLE Y_BUSINESS(
  y_businessId varchar(75) primary key,
  name varchar(100),
  address varchar(200),
  city varchar(75),
  state varchar(10),
  reviewCount int,
  stars float
);

CREATE TABLE Y_BUSINESS_CAT(y_businessId varchar(75), category varchar(75));

CREATE TABLE Y_BUSINESS_SUBCAT(
  y_businessId varchar(75),
  subcategory varchar(75)
);

CREATE TABLE Y_BUSINESS_ATTR (
  y_businessId varchar(75),
  attrName varchar(75),
  attrValue varchar(75)
);

ALTER TABLESPACE SYSTEM ADD DATAFILE '/u01/app/oracle/oradata/XE/system1.dbf' SIZE 2400M;

ALTER TABLESPACE SYSTEM ADD DATAFILE '/u01/app/oracle/oradata/XE/syste2.dbf' SIZE 2400M;

-- create INDEX ReviewsIndex on Y_Reviews (yelp_reviewId, stars, datePublish, text, yelp_userId);
--
-- create INDEX BusinessIndex on Y_BUSINESS (y_businessId, name, city, state, stars);

CREATE INDEX ReviewsIndex on Y_Reviews (yelp_reviewId, stars, datePublish, text, yelp_userId);
CREATE INDEX BusinessIndex on Y_BUSINESS (y_businessId, name, city, state, stars);