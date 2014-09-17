package com.optigra.funnypictures.queries;




public class Queries {
	
	// Heavy CONDITIONS
    private static final String LOCATION_CONDITION = "((3959*acos(cos(radians(:latitude))*cos(radians(l.latitude))*cos(radians(l.longitude)-radians(:longitude))+sin(radians(:latitude))*sin(radians(l.latitude))))/0.62137)<=:distance";
    
    // Constants 
    public static final String FIND_USERS_QUERY_NAME = "User.findUsers";
    public static final String FIND_USERS_QUERY = "SELECT u FROM User u";
    
    public static final String FIND_USERS_BY_WORD_QUERY_NAME = "User.findUsersByWord";
	public static final String FIND_USERS_BY_WORD_QUERY = "SELECT u FROM User u WHERE u.name like :word OR u.email like :word OR u.surname like :word OR phoneNumber like :word OR CONCAT(u.name, ' ', u.surname) like :word";
    
    public static final String FIND_USER_BY_EMAIL_QUERY_NAME = "User.findUserByEmail";
    public static final String FIND_USER_BY_EMAIL_QUERY = "SELECT u FROM User u where email = :email";

	public static final String FIND_USER_BY_MERCHANDISER_QUERY_NAME = "User.findMerchandiserAdminByMerchandiser";
	public static final String FIND_USER_BY_MERCHANDISER_QUERY = "SELECT ma.user FROM MerchandiserAdmin ma WHERE ma.merchandiser = :merchandiser";

    public static final String FIND_USER_BY_LOGIN_QUERY_NAME = "User.findUserByLogin";
    public static final String FIND_USER_BY_LOGIN_QUERY = "SELECT u FROM User u where email = :login OR phoneNumber = :login";

    public static final String FIND_USERS_BY_ROLE_QUERY_NAME = "User.findUsersByRole";
    public static final String FIND_USERS_BY_ROLE_QUERY = "from User u where u.role = :role";
    
    public static final String FIND_USER_BY_ARTICLE_NUMBER_QUERY_NAME = "User.findUserByArticleNumber";
    public static final String FIND_USER_BY_ARTICLE_NUMBER_QUERY = "SELECT uc.user FROM UserCard uc WHERE uc.articleNumber = :articleNumber";

    public static final String FIND_USERS_BY_EMAIL_WITH_DIFFERENT_ID_QUERY_NAME = "User.findUserByEmailWithDifferentId";
    public static final String FIND_USERS_BY_EMAIL_WITH_DIFFERENT_ID_QUERY = "from User where email = :email and id != :id";

    public static final String FIND_CONFIRMATION_BY_USER_QUERY_NAME = "Confirmation.findByUser";
    public static final String FIND_CONFIRMATION_BY_USER_QUERY = "from Confirmation where user = :user";

    public static final String FIND_FEEDS_BY_MERCHANDISER_QUERY_NAME = "Feed.findByMerchandiser";
    public static final String FIND_FEEDS_BY_MERCHANDISER_QUERY = "from Feed where merchandiser = :merchandiser and isTrended = false";
    
	public static final String FIND_FEEDS_QUERY_NAME = "Feed.findFeeds";
	public static final String FIND_FEEDS_QUERY = "SELECT f FROM Feed f WHERE f.isTrended = :trended";

	public static final String FIND_MERCHANDISERS_QUERY_NAME = "Merchandiser.findMerchandisers";
	public static final String FIND_MERCHANDISERS_QUERY = "SELECT m FROM Merchandiser m";

	public static final String FIND_DISCOUNT_MERCHANDISERS_QUERY_NAME = "Merchandiser.findDiscountMerchandisers";
	public static final String FIND_DISCOUNT_MERCHANDISERS_QUERY = "SELECT m FROM Merchandiser m WHERE m.hasDiscount = :hasDiscount";

	public static final String FIND_BONUS_MERCHANDISERS_QUERY_NAME = "Merchandiser.findBonusMerchandisers";
	public static final String FIND_BONUS_MERCHANDISERS_QUERY = "SELECT m FROM Merchandiser m WHERE m.hasBonus = :hasBonus";

    public static final String FIND_USER_MERCHANDISERS_BY_USER_QUERY_NAME = "UserMerchandiser.findUserMerchandisersByUser";
    public static final String FIND_USER_MERCHANDISERS_BY_USER_QUERY = "SELECT um FROM UserMerchandiser um WHERE um.user=:user";
	
	public static final String FIND_MERCHANDISERS_BY_NAME_QUERY_NAME = "Merchandiser.findByName";
	public static final String FIND_MERCHANDISERS_BY_NAME_QUERY = "SELECT m FROM Merchandiser m WHERE m.name like :name";
	
	public static final String FIND_MERCHANDISER_ADMIN_QUERY_NAME = "MerchandiserAdmin.findById";
	public static final String FIND_MERCHANDISER_ADMIN_QUERY = "SELECT ma FROM MerchandiserAdmin ma WHERE ma.user = :user AND ma.merchandiser = :merchandiser";
	
	public static final String FIND_MERCHANDISER_ADMIN_BY_USER_QUERY_NAME = "Merchandser.findMerchandiserAdminByUser";
	public static final String FIND_MERCHANDISER_ADMIN_BY_USER_QUERY = "SELECT ma.merchandiser FROM MerchandiserAdmin ma WHERE ma.user = :user";
	
	public static final String FIND_LOCATIONS_BY_MERCHANDISER_QUERY_NAME = "Location.findByMerchandiser";
	public static final String FIND_LOCATIONS_BY_MERCHANDISER_QUERY = "SELECT l FROM Location l WHERE merchandiser = :merchandiser";

	public static final String FIND_FEEDS_BY_USER_QUERY_NAME = "Feed.findByUser";
	public static final String FIND_FEEDS_BY_USER_QUERY = "SELECT f FROM UserMerchandiser um, Feed f WHERE um.user = :user and um.merchandiser=f.merchandiser and f.isTrended = false";

	public static final String FIND_LOCATIONS_BY_LLD_QUERY = "SELECT l FROM Location l WHERE " + LOCATION_CONDITION;
	public static final String FIND_LOCATIONS_BY_LLD_QUERY_NAME = "Location.findByLLD";

	public static final String FIND_LOCATIONS_QUERY_NAME = "Location.findLocations";
	public static final String FIND_LOCATIONS_QUERY = "SELECT l FROM Location l";
	
	public static final String FIND_CARDS_QUERY_NAME = "Cards.findCards";
	public static final String FIND_CARDS_QUERY = "SELECT c FROM Card c";

	public static final String FIND_CARD_BY_NAME_QUERY_NAME = "Cards.findCardByName";
	public static final String FIND_CARD_BY_NAME_QUERY = "SELECT c FROM Card c WHERE c.name = 'SimpleSave'";
	
	public static final String FIND_USER_MERCHANDISER_QUERY_NAME = "UserMerchandiser.findEntity";
	public static final String FIND_USER_MERCHANDISER_QUERY = "SELECT um FROM UserMerchandiser um WHERE user = :user AND merchandiser = :merchandiser";
	
    public static final String FIND_USER_CARD_QUERY_NAME = "UserCard.findEntity";
    public static final String FIND_USER_CARD_QUERY = "SELECT uc FROM UserCard uc WHERE uc.card = :card AND uc.user = :user";

    public static final String FIND_USER_CARD_BY_ARTICLE_NUMBER_QUERY_NAME = "UserCard.findEntityByArticleNumber";
    public static final String FIND_USER_CARD_BY_ARTICLE_NUMBER_QUERY = "SELECT uc FROM UserCard uc WHERE uc.articleNumber=:articleNumber";

    public static final String FIND_USER_CARDS_BY_USER_QUERY_NAME = "UserCard.findUserCardsByUser";
    public static final String FIND_USER_CARDS_BY_USER_QUERY = "SELECT uc FROM UserCard uc WHERE uc.user = :user";
    
	public static final String FIND_BONUS_BY_MERCHANDISER_AND_USER_CARD_QUERY_NAME = "Bonus.findBonusByMerchandiserUserCard";
	public static final String FIND_BONUS_BY_MERCHANDISER_AND_USER_CARD_QUERY = "SELECT b FROM Bonus b WHERE merchandiser = :merchandiser AND userCard = :userCard";

	public static final String FIND_BONUSES_BY_USER_QUERY_NAME = "Bonuses.findByUser";
	public static final String FIND_BONUSES_BY_USER_QUERY = "SELECT b FROM Bonus b LEFT JOIN b.userCard uc WHERE uc.user = :user";

	public static final String FIND_COUPONS_QUERY_NAME = "Coupon.findAllCoupons";
	public static final String FIND_COUPONS_QUERY = "SELECT c FROM Coupon c";
	
	public static final String FIND_COUPONS_BY_MERCHANDISER_QUERY_NAME = "Coupon.findCouponsByMerchandiser";
	public static final String FIND_COUPONS_BY_MERCHANDISER_QUERY = "SELECT c FROM Coupon c WHERE c.merchandiser = :merchandiser";
	
	public static final String FIND_USER_COUPONS_BY_USER_AND_COUPON_QUERY_NAME = "UserCoupon.findUserCouponByUserAndCoupon";
	public static final String FIND_USER_COUPONS_BY_USER_AND_COUPON_QUERY = "SELECT uc FROM UserCoupon uc WHERE uc.user = :user AND uc.coupon = :coupon";
	
	public static final String FIND_USER_COUPONS_BY_ARTICLE_NUMBER_QUERY_NAME = "UserCoupon.findUserCouponByArticleNumber";
	public static final String FIND_USER_COUPONS_BY_ARTICLE_NUMBER_QUERY = "SELECT uc FROM UserCoupon uc WHERE uc.articleNumber = :articleNumber";
	
	public static final String FIND_USERCOUPONS_BY_USER_QUERY_NAME = "UserCoupon.findUserCouponsByUser";
	public static final String FIND_USERCOUPONS_BY_USER_QUERY = "SELECT uc FROM UserCoupon uc WHERE uc.user = :user";
	
	
	
	
	
	
	
	
	
	// Queries
    public static final Queries FIND_USERS = new Queries(FIND_USERS_QUERY_NAME, FIND_USERS_QUERY);

    public static final Queries FIND_USERS_BY_WORD = new Queries(FIND_USERS_BY_WORD_QUERY_NAME, FIND_USERS_BY_WORD_QUERY);
    
    public static final Queries FIND_USER_BY_EMAIL = new Queries(FIND_USER_BY_EMAIL_QUERY_NAME, FIND_USER_BY_EMAIL_QUERY);
    
    public static final Queries FIND_USER_BY_MERCHANDISER = new Queries(FIND_USER_BY_MERCHANDISER_QUERY_NAME, FIND_USER_BY_MERCHANDISER_QUERY);

    public static final Queries FIND_USER_BY_LOGIN = new Queries(FIND_USER_BY_LOGIN_QUERY_NAME, FIND_USER_BY_LOGIN_QUERY);
    
    public static final Queries FIND_USERS_BY_ROLE = new Queries(FIND_USERS_BY_ROLE_QUERY_NAME, FIND_USERS_BY_ROLE_QUERY);
    
    public static final Queries FIND_USER_BY_ARTICLE_NUMBER = new Queries(FIND_USER_BY_ARTICLE_NUMBER_QUERY_NAME, FIND_USER_BY_ARTICLE_NUMBER_QUERY);
    
    public static final Queries FIND_USERS_BY_EMAIL_WITH_DIFFERENT_ID = new Queries(FIND_USERS_BY_EMAIL_WITH_DIFFERENT_ID_QUERY_NAME, FIND_USERS_BY_EMAIL_WITH_DIFFERENT_ID_QUERY);
    
    public static final Queries FIND_CONFIRMATION_BY_USER = new Queries(FIND_CONFIRMATION_BY_USER_QUERY_NAME, FIND_CONFIRMATION_BY_USER_QUERY);

    
    
    public static final Queries FIND_FEEDS_BY_MERCHANDISER = new Queries(FIND_FEEDS_BY_MERCHANDISER_QUERY_NAME, FIND_FEEDS_BY_MERCHANDISER_QUERY);
	
    public static final Queries FIND_FEEDS = new Queries(FIND_FEEDS_QUERY_NAME, FIND_FEEDS_QUERY);
	
    public static final Queries FIND_FEEDS_BY_USER = new Queries(FIND_FEEDS_BY_USER_QUERY_NAME, FIND_FEEDS_BY_USER_QUERY);
	
    
    
	public static final Queries FIND_MERCHANDISERS = new Queries(FIND_MERCHANDISERS_QUERY_NAME, FIND_MERCHANDISERS_QUERY);

	public static final Queries FIND_DISCOUNT_MERCHANDISERS = new Queries(FIND_DISCOUNT_MERCHANDISERS_QUERY_NAME, FIND_DISCOUNT_MERCHANDISERS_QUERY);

	public static final Queries FIND_BONUS_MERCHANDISERS = new Queries(FIND_BONUS_MERCHANDISERS_QUERY_NAME, FIND_BONUS_MERCHANDISERS_QUERY);
	
	public static final Queries FIND_MERCHANDISERS_BY_NAME = new Queries(FIND_MERCHANDISERS_BY_NAME_QUERY_NAME, FIND_MERCHANDISERS_BY_NAME_QUERY);

	
	
	
	public static final Queries FIND_LOCATIONS_BY_MERCHANDISER = new Queries(FIND_LOCATIONS_BY_MERCHANDISER_QUERY_NAME, FIND_LOCATIONS_BY_MERCHANDISER_QUERY);
    
	public static final Queries FIND_LOCATIONS_BY_LLD = new Queries(FIND_LOCATIONS_BY_LLD_QUERY_NAME, FIND_LOCATIONS_BY_LLD_QUERY);
	
	public static final Queries FIND_LOCATIONS = new Queries(FIND_LOCATIONS_QUERY_NAME, FIND_LOCATIONS_QUERY);
	
	public static final Queries FIND_CARDS = new Queries(FIND_CARDS_QUERY_NAME, FIND_CARDS_QUERY);
	
	public static final Queries FIND_CARD_BY_NAME = new Queries(FIND_CARD_BY_NAME_QUERY_NAME, FIND_CARD_BY_NAME_QUERY);
    
	
	
	public static final Queries FIND_USER_MERCHANDISER = new Queries(FIND_USER_MERCHANDISER_QUERY_NAME, FIND_USER_MERCHANDISER_QUERY);

	public static final Queries FIND_USER_MERCHANDISERS_BY_USER = new Queries(FIND_USER_MERCHANDISERS_BY_USER_QUERY_NAME, FIND_USER_MERCHANDISERS_BY_USER_QUERY);

	
	
    public static final Queries FIND_USER_CARD = new Queries(FIND_USER_CARD_QUERY_NAME, FIND_USER_CARD_QUERY);

    public static final Queries FIND_USER_CARD_BY_ARTICLE_NUMBER = new Queries(FIND_USER_CARD_BY_ARTICLE_NUMBER_QUERY_NAME, FIND_USER_CARD_BY_ARTICLE_NUMBER_QUERY);

    public static final Queries FIND_USER_CARDS_BY_USER = new Queries(FIND_USER_CARDS_BY_USER_QUERY_NAME, FIND_USER_CARDS_BY_USER_QUERY);

    
    
	public static final Queries FIND_BONUS_BY_MERCHANDISER_AND_USER_CARD = new Queries(FIND_BONUS_BY_MERCHANDISER_AND_USER_CARD_QUERY_NAME, FIND_BONUS_BY_MERCHANDISER_AND_USER_CARD_QUERY);

	public static final Queries FIND_BONUSES_BY_USER = new Queries(FIND_BONUSES_BY_USER_QUERY_NAME, FIND_BONUSES_BY_USER_QUERY);

	
	
	public static final Queries FIND_MERCHANDISER_ADMIN = new Queries(FIND_MERCHANDISER_ADMIN_QUERY_NAME, FIND_MERCHANDISER_ADMIN_QUERY);

	public static final Queries FIND_MERCHANDISER_ADMIN_BY_USER = new Queries(FIND_MERCHANDISER_ADMIN_BY_USER_QUERY_NAME, FIND_MERCHANDISER_ADMIN_BY_USER_QUERY);

	public static final Queries FIND_COUPONS = new Queries(FIND_COUPONS_QUERY_NAME, FIND_COUPONS_QUERY);
	public static final Queries FIND_COUPONS_BY_MERCHANDISER = new Queries(FIND_COUPONS_BY_MERCHANDISER_QUERY_NAME, FIND_COUPONS_BY_MERCHANDISER_QUERY);

	
	public static final Queries FIND_USER_COUPONS_BY_USER_AND_COUPON = new Queries(FIND_USER_COUPONS_BY_USER_AND_COUPON_QUERY_NAME, FIND_USER_COUPONS_BY_USER_AND_COUPON_QUERY);
	public static final Queries FIND_USER_COUPONS_BY_ARTICLE_NUMBER = new Queries(FIND_USER_COUPONS_BY_ARTICLE_NUMBER_QUERY_NAME, FIND_USER_COUPONS_BY_ARTICLE_NUMBER_QUERY);
	public static final Queries FIND_USERCOUPONS_BY_USER = new Queries(FIND_USERCOUPONS_BY_USER_QUERY_NAME, FIND_USERCOUPONS_BY_USER_QUERY);

	public static final String FIND_RATINGS_BY_MERCHANDISER_QUERY = "SELECT r FROM Rating r WHERE r.merchandiser = :merchandiser";
    public static final String FIND_RATINGS_BY_MERCHANDISER_QUERY_NAME = "Rating.find_rating_by_merchnadiser";

    public static final String FIND_RATINGS_BY_USER_QUERY = "SELECT r FROM Rating r WHERE r.user = :user";
    public static final String FIND_RATINGS_BY_USER_QUERY_NAME = "Rating.find_rating_by_user";

    public static final String FIND_RATINGS_BY_MERCHANDISER_AND_USER_QUERY = "SELECT r FROM Rating r WHERE r.user = :user AND r.merchandiser = :merchandiser";
    public static final String FIND_RATINGS_BY_MERCHANDISER_AND_USER_QUERY_NAME = "Rating.find_rating_by_user_and_merchandiser";
    
	public static final Queries FIND_RATINGS_BY_MERCHANDISER = new Queries(FIND_RATINGS_BY_MERCHANDISER_QUERY_NAME, FIND_RATINGS_BY_MERCHANDISER_QUERY);
	public static final Queries FIND_RATINGS_BY_USER = new Queries(FIND_RATINGS_BY_USER_QUERY_NAME, FIND_RATINGS_BY_USER_QUERY);
	public static final Queries FIND_RATINGS_BY_MERCHANDISER_AND_USER = new Queries(FIND_RATINGS_BY_USER_QUERY_NAME, FIND_RATINGS_BY_USER_QUERY);

	

    private final String queryName;
    private final String query;

    private Queries(final String queryName, final String query) {
        this.queryName = queryName;
        this.query = query;
    }

    public String getQueryName() {
        return queryName;
    }

    public String getQuery() {
        return query;
    }
    
}