package com.optigra.funnypictures.queries;



/**
 * Class, that contains all queries for databases.
 * @author ivanursul
 *
 */
public class Queries {
	
	private static final String FIND_PICTURES_QUERY_NAME = "Pictures.findPictures";
	private static final String FIND_PICTURES_QUERY = "SELECT p FROM Picture p ORDER BY p.id DESC";
	
	private static final String FIND_FUNNY_PICTURES_QUERY_NAME = "FunnyPictures.findPictures";
	private static final String FIND_FUNNY_PICTURES_QUERY = "SELECT p FROM FunnyPicture p ORDER BY p.id DESC";
	
	private static final String FIND_FUNNY_PICTURES_BY_PIC_QUERY_NAME = "FunnyPictures.findFunniesByPicture";
	private static final String FIND_FUNNY_PICTURES_BY_PIC_QUERY = "SELECT p FROM FunnyPicture p WHERE p.picture = :picture";
	
	public static final String FIND_THUMBNAILS_BY_PICTURE_QUERY_NAME = "PictureThumbnails.findThumbnailsByPicture";
	public static final String FIND_THUMBNAILS_BY_PICTURE_QUERY = "SELECT t FROM PictureThumbnail t "
			+ "WHERE t.picture.id IN :pictureList AND t.thumbnailType = :type";
	
	public static final String GET_PICTURE_THUMBNAILS_QUERY_NAME = "PictureThumbnails.findThumbnails";
	public static final String GET_PICTURE_THUMBNAILS_QUERY = "SELECT t FROM PictureThumbnail t WHERE t.thumbnailType = :type ORDER BY t.picture.id DESC";

	public static final String GET_FUNNY_PICTURE_THUMBNAILS_QUERY_NAME = "FunnyPictureThumbnails.findThumbnails";
	public static final String GET_FUNNY_PICTURE_THUMBNAILS_QUERY = "SELECT t FROM FunnyPictureThumbnail t "
			+ "WHERE t.thumbnailType = :type ORDER BY t.funnyPicture.id DESC";
	
	private static final String FIND_FUNNY_PICTURE_THUMBNAILS_BY_PIC_QUERY_NAME = "FunnyPictureThumbnails.findThumbnailsByPicture";
	private static final String FIND_FUNNY_PICTURE_THUMBNAILS_BY_PIC_QUERY = "SELECT t FROM FunnyPictureThumbnail t "
			+ "WHERE t.funnyPicture.picture = :picture AND t.thumbnailType = :type  ORDER BY t.id DESC";
	
	public static final String GET_RANDOM_PICTURE_THUMBNAILS_QUERY_NAME = "RandomPictureThumbnailView.findThumbnails";
	public static final String GET_RANDOM_PICTURE_THUMBNAILS_QUERY = "SELECT t FROM RandomPictureThumbnailView t ORDER BY t.id DESC";

	public static final String GET_USER_BY_EMAIL_QUERY_NAME = "User.findByEmail";
	public static final String GET_USER_BY_EMAIL_QUERY = "SELECT u FROM User u WHERE u.email = :email";

	private static final String FIND_FUNNY_PICTURE_THUMBNAIL_BY_FUNNY_PICTURE_QUERY_NAME = "FunnyPictureThumbnails.findThumbnailsByPicture";
	private static final String FIND_FUNNY_PICTURE_THUMBNAIL_BY_FUNNY_PICTURE_QUERY = "SELECT t FROM FunnyPictureThumbnail t "
			+ "WHERE t.funnyPicture.id = :funnyPictureId AND t.thumbnailType = :type";
	
	
	public static final Queries FIND_PICTURES = new Queries(FIND_PICTURES_QUERY_NAME, FIND_PICTURES_QUERY);
	public static final Queries FIND_FUNNY_PICTURES = new Queries(FIND_FUNNY_PICTURES_QUERY_NAME, FIND_FUNNY_PICTURES_QUERY);
	public static final Queries FIND_FUNNY_PICTURES_BY_PICTURE = new Queries(FIND_FUNNY_PICTURES_BY_PIC_QUERY_NAME, FIND_FUNNY_PICTURES_BY_PIC_QUERY);
	public static final Queries FIND_THUMBNAILS_BY_PICTURE = new Queries(FIND_THUMBNAILS_BY_PICTURE_QUERY_NAME, FIND_THUMBNAILS_BY_PICTURE_QUERY);
	public static final Queries GET_PICTURE_THUMBNAILS = new Queries(GET_PICTURE_THUMBNAILS_QUERY_NAME, GET_PICTURE_THUMBNAILS_QUERY);
	public static final Queries GET_FUNNY_PICTURE_THUMBNAILS = new Queries(GET_FUNNY_PICTURE_THUMBNAILS_QUERY_NAME, GET_FUNNY_PICTURE_THUMBNAILS_QUERY);
	public static final Queries FIND_FUNNY_PICTURE_THUMBNAILS_BY_PICTURE = new Queries(FIND_FUNNY_PICTURE_THUMBNAILS_BY_PIC_QUERY_NAME, 
			FIND_FUNNY_PICTURE_THUMBNAILS_BY_PIC_QUERY);
	public static final Queries FIND_FUNNY_PICTURE_THUMBNAILS_BY_FUNNY_PICTURE = new Queries(FIND_FUNNY_PICTURE_THUMBNAIL_BY_FUNNY_PICTURE_QUERY_NAME,
			FIND_FUNNY_PICTURE_THUMBNAIL_BY_FUNNY_PICTURE_QUERY);
	public static final Queries GET_RANDOM_PICTURE_THUMBNAILS = new Queries(GET_RANDOM_PICTURE_THUMBNAILS_QUERY_NAME, GET_RANDOM_PICTURE_THUMBNAILS_QUERY);
	public static final Queries GET_USER_BY_EMAIL = new Queries(GET_USER_BY_EMAIL_QUERY_NAME, GET_USER_BY_EMAIL_QUERY);
	
	

    private final String queryName;
    private final String query;

    /**
     * Constructor, that forms Queries object by name of query and query.
     * @param queryName
     * @param query
     */
    public Queries(final String queryName, final String query) {
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
