package comalexpolyanskyi.github.englishtest.managers;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewDebug;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import comalexpolyanskyi.github.englishtest.R;

/**
 * Created by Алексей on 21.04.2015.
 */
public class DataManager{
    private final static String key = "key=trnsl.1.1.20150421T110640Z.c0ac97121baaccd1.e0094f882e158082de0196da59d980ab2fd76ba4";
    private final static String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
    private final static String lang ="&lang=en-ru&format=plain";
    private static HashMap<String, List<Integer>> viewDataMap = new HashMap<String, List<Integer>>();
    private static HashMap<String, List<String>> viewColorMap = new HashMap<String, List<String>>();
    private static int rightAnswerCount = 0;
    public List createTaskList(Resources res){
        String [] quest = res.getStringArray(R.array.quest);
        List<String> taskList = Arrays.asList(quest);
        Collections.shuffle(taskList);
        return taskList;
    }
    public List createWrongAnswerList(Resources res){
        String [] req = res.getStringArray(R.array.request);
        List<String> wrongAnswerList = Arrays.asList(req);
        return wrongAnswerList;
    }
    public String getTranslate(final String wordForTranslate) {
        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String result = null;
                try {
                    URL u1 = new URL(url+key+"&text="+wordForTranslate+lang);
                    URLConnection conn = u1.openConnection();
                    String jsonResult = new BufferedReader(new InputStreamReader(conn.getInputStream())).readLine();
                    JSONObject jsonObject = new JSONObject(jsonResult);
                    JSONArray jsonArray = jsonObject.getJSONArray("text");
                    result = jsonArray.getString(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        };
        asyncTask.execute();
        String result = null;
        try {
            result = asyncTask.get().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void saveViewColor(String task, int allRight, int numberSelect){
        if(allRight == 0){
            rightAnswerCount++;
        }
        List<Integer> data = Arrays.asList(allRight, numberSelect);
        viewDataMap.put(task, data);
    }
    public static List<Integer> getViewData(String task){
        return viewDataMap.get(task);
    }
    public static int getSize(){
        return viewDataMap.size();
    }
    public static void saveViewText(String task, String arg1, String arg2, String arg3){
        List<String> data = Arrays.asList(arg1, arg2, arg3);
        viewColorMap.put(task, data);
    }
    public static List<String> getColorData(String task){
        return viewColorMap.get(task);
    }
    public static int getRightAnswer(){
        return rightAnswerCount;
    }
}
