package comalexpolyanskyi.github.englishtest;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import comalexpolyanskyi.github.englishtest.managers.DataManager;


public class StartActivity extends Activity implements DataManager.DataCallback {
    private int correctQuest = 0;
    private int correctAnswer = 0;
    private String key;
    private Boolean isRequestAccepted = false;
    private List<HashMap<String, String>> dataList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initTest();
    }
    private void initTest(){
        new DataManager(this,this);
    }
    @Override
    public void load(Boolean loading) {
        if(loading){
            findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
            findViewById(R.id.testLayout).setVisibility(View.GONE);
        }else{
            findViewById(R.id.testLayout).setVisibility(View.VISIBLE);
            findViewById(R.id.progressBar).setVisibility(View.GONE);
        }
    }
    @Override
    public void request(List data) {

        if(data.equals(null)){
            findViewById(R.id.error).setVisibility(View.VISIBLE);
            findViewById(R.id.testLayout).setVisibility(View.GONE);
        }else{
            dataList = data;
            initView(dataList.get(correctQuest));
            correctQuest++;
        }
    }
    public void onNextClick(View v){
        if(correctQuest != dataList.size()){
            nextQuestion();
        }else{
            showResult();
        }
    }
    private void showResult(){
        findViewById(R.id.testLayout).setVisibility(View.GONE);
        TextView message = (TextView) findViewById(R.id.error);
        message.setVisibility(View.VISIBLE);
        message.setText("Right "+correctAnswer+" form "+correctQuest);
    }
    private void nextQuestion(){
        HashMap data = dataList.get(correctQuest);
        if(isRequestAccepted){
            isRequestAccepted = false;
            correctQuest++;
            ((TextView)findViewById(R.id.task)).setTextColor(Color.GRAY);
            findViewById(R.id.replyButton).setVisibility(View.VISIBLE);
        }else {
            dataList.add(data);
            dataList.remove(0);
        }
        initView(data);
    }
    public void onReplyClick(View v){
       int checked = ((RadioGroup)findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
      if(checked == -1){
          Toast.makeText(this, "Не выбран не один из вариантов", Toast.LENGTH_SHORT).show();
      }else if(((RadioButton)findViewById(checked)).getText().toString().equals(key)){
          ((TextView)findViewById(R.id.task)).setTextColor(Color.GREEN);
          findViewById(R.id.replyButton).setVisibility(View.INVISIBLE);
          correctAnswer++;
          isRequestAccepted = true;
      }else{
          ((TextView)findViewById(R.id.task)).setTextColor(Color.RED);
          findViewById(R.id.replyButton).setVisibility(View.INVISIBLE);
          isRequestAccepted = true;
      }
    }
    private void initView(HashMap<String, String> dataMap){
        ((RadioButton)findViewById(R.id.radioButton)).setText(dataMap.get(getText(R.string.REQ1)));
        ((RadioButton)findViewById(R.id.radioButton2)).setText(dataMap.get(getText(R.string.REQ2)));
        ((RadioButton)findViewById(R.id.radioButton3)).setText(dataMap.get(getText(R.string.REQ3)));
        ((TextView)findViewById(R.id.task)).setText(dataMap.get(getText(R.string.QUEST)));
        ((TextView)findViewById(R.id.numberQuest)).setText(correctQuest+" from "+dataList.size());
        key = dataMap.get(getText(R.string.KEY));

    }
}
