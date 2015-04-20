package comalexpolyanskyi.github.englishtest.data;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import comalexpolyanskyi.github.englishtest.R;

/**
 * Created by Алексей on 21.04.2015.
 */


public class Data {
    private final static String KEY ="key";
    private final static String REQ1 ="req1";
    private final static String REQ2 ="req2";
    private final static String REQ3 ="req3";
    private final static String QUEST ="quest";
// TODO какой либо внешний JSON или База данных
    public List getData(Resources res){
        return createDataList(res);
    }
    private List createDataList(Resources res){
        String [] req1 = res.getStringArray(R.array.requestV1);
        String [] req2 = res.getStringArray(R.array.requestV2);
        String [] req3 = res.getStringArray(R.array.requestV3);
        String [] key = res.getStringArray(R.array.keys);
        String [] quest = res.getStringArray(R.array.quest);
        ArrayList<HashMap<String, String>> dataList = initDataList(req1, req2, req3, key, quest, res);
        Collections.shuffle(dataList);
        return dataList;
    }
    private ArrayList<HashMap<String, String>> initDataList(String[] req1, String [] req2, String [] req3,
                                                            String [] key, String [] quest, Resources res){
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        for(int i = 0; i < 18; i++)
        {
            HashMap<String, String> qAndK = new HashMap<String, String>();
            qAndK.put(res.getText(R.string.KEY).toString(), key[i]);
            qAndK.put(res.getText(R.string.REQ1).toString(), req1[i]);
            qAndK.put(res.getText(R.string.REQ2).toString(), req2[i]);
            qAndK.put(res.getText(R.string.REQ3).toString(), req3[i]);
            qAndK.put(res.getText(R.string.QUEST).toString(), quest[i]);
            list.add(qAndK);
        }
        return list;
    }
}