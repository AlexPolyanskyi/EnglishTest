package comalexpolyanskyi.github.englishtest.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.security.auth.callback.Callback;

/**
 * Created by Алексей on 25.04.2015.
 */
public class PostOfWall extends AsyncTask<String, Void, JSONObject> {
    private PostCallbacks postCallback = null;
    private static final String URL = "https://api.vk.com/method/wall.post?&owner_id=";
    private static final String VERS = "&v=5.27";
    private static final String MESSAGE = "&message=";

    public PostOfWall(PostCallbacks callback){
        postCallback = callback;
    }
    public PostOfWall(){
    }
    public static interface PostCallbacks {
        void onError();
        void onSuccess();
    }
    @Override
    protected JSONObject doInBackground(String... params) {
        JSONObject jsonObject = null;
        try {
            URL u1 = new URL(URL+Token.USER_ID+MESSAGE+URLEncoder.encode(params[0], "UTF-8")+VERS+"&access_token="+Token.TOKEN);
            URLConnection conn = u1.openConnection();
            String jsonResult = new BufferedReader(new InputStreamReader(conn.getInputStream())).readLine();
            jsonObject = new JSONObject(jsonResult);
        }catch (Exception e){
            Log.e("gasg", e+"");
        }
        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject s) {
        if(s.isNull("response")){
            postCallback.onError();
        }else{
            postCallback.onSuccess();
        }
    }
}
