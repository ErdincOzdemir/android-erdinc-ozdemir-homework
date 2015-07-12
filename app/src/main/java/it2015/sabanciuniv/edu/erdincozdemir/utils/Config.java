package it2015.sabanciuniv.edu.erdincozdemir.utils;

/**
 * Created by Erdinc on 12.07.2015.
 */
public class Config {

    public static String TOKEN;

    public static final String sharedPreferencesName = "NewsAppSharedPreferences";
    public static final String sharedPreferencesTokenKey = "TOKEN";

    public static final String username = "newsconsumer";
    public static final String password = "news2013";

    public static final String loginUrl = "http://94.138.207.51:8080/NewsApp/service/news/login/" + username + "/" + password;
    public static final String getNewsCategoriesUrl = "http://94.138.207.51:8080/NewsApp/service/news/secure/getallnewscategories?token=";
    public static final String getAllNewsUrl = "http://94.138.207.51:8080/NewsApp/service/news/secure/getall?token={0}";
    public static final String getAllNewsByCategoryUrl = "http://94.138.207.51:8080/NewsApp/service/news/secure/getbycategoryid/{0}?token={1}";
    public static final String getNewsByIdUrl = "http://94.138.207.51:8080/NewsApp/service/news/secure/getnewsbyid/{0}?token={1}";
    public static final String getCommentsByNewsIdUrl = "http://94.138.207.51:8080/NewsApp/service/news/secure/getcommentsbynewsid/{0}?token={1}";
    public static final String postCommentUrl = "http://94.138.207.51:8080/NewsApp/service/news/secure/savecomment?token={0}";

}
