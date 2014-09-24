package com.optigra.funnypictures.queries;




public class Queries {
	
	private static final String FIND_PICTURES_QUERY_NAME = "Pictures.findPictures";
	private static final String FIND_PICTURES_QUERY = "SELECT p FROM Picture p";
	
	private static final String FIND_FUNNY_PICTURES_QUERY_NAME = "FunnyPictures.findPictures";
	private static final String FIND_FUNNY_PICTURES_QUERY = "SELECT p FROM FunnyPicture p";

	public static final Queries FIND_PICTURES = new Queries(FIND_PICTURES_QUERY_NAME, FIND_PICTURES_QUERY);
	public static final Queries FIND_FUNNY_PICTURES = new Queries(FIND_FUNNY_PICTURES_QUERY_NAME, FIND_FUNNY_PICTURES_QUERY);

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