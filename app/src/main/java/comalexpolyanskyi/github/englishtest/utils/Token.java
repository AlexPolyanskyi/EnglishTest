package comalexpolyanskyi.github.englishtest.utils;

/**
 * Created by Алексей on 26.11.2014.
 */
public class Token {
    public static String TOKEN = "nan";
    public static String USER_ID = "nan";
    public static boolean isLogget(){
        if(TOKEN.equals("nan")){
            return false;
        }else{
            return true;
        }

    }
}
