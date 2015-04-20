package comalexpolyanskyi.github.englishtest.managers;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import java.util.List;

import comalexpolyanskyi.github.englishtest.data.Data;

/**
 * Created by Алексей on 21.04.2015.
 */
public class DataManager{
    public interface DataCallback {
        public void load(Boolean loading);
        public void request(List data);
    }
    public DataManager(final Context context,final DataCallback callback){
        new AsyncTask<Void, Void, List>(){
            @Override
            protected List doInBackground(Void... params) {
                callback.load(true);
                List list = null;
                Resources res = context.getResources();
                try {
                    list = new Data().getData(res);
                    //TODO имитация загрузки)
                    Thread.sleep(1500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return list;
            }
            @Override
            protected void onPostExecute(List result) {
                super.onPostExecute(result);
                callback.request(result);
                callback.load(false);
            }
        }.execute();
    }
}
