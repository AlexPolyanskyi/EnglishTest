package comalexpolyanskyi.github.englishtest.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import comalexpolyanskyi.github.englishtest.R;
import comalexpolyanskyi.github.englishtest.fragments.TestFragment;
import comalexpolyanskyi.github.englishtest.managers.DataManager;

/**
 * Created by Алексей on 21.04.2015.
 */
public class MyViewPageAdapter extends PagerAdapter  {
    private FragmentActivity activity;
    private Fragment testFragment;
    private List<String> taskList;
    private List<String> wrongAnswerList;
    private View view;
    public MyViewPageAdapter(FragmentActivity activity, List<String> taskList,
                             List<String> wrongAnswerList,TestFragment testFragment) {
        this.activity = activity;
        this.testFragment = testFragment;
        this.taskList = taskList;
        this.wrongAnswerList = wrongAnswerList;
    }
    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.test_page, container, false);
        final TextView taskTxt = (TextView) view.findViewById(R.id.task);
        final RadioButton respOneRadio = (RadioButton) view.findViewById(R.id.radioButton);
        final RadioButton respTwoRadio = (RadioButton) view.findViewById(R.id.radioButton2);
        final RadioButton respThreeRadio = (RadioButton) view.findViewById(R.id.radioButton3);
        final String key = new DataManager().getTranslate(taskList.get(position));
        respOneRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                respOneRadio.setOnClickListener(null);
                respTwoRadio.setOnClickListener(null);
                respThreeRadio.setOnClickListener(null);
                int allRight = check(v, key);
                DataManager.saveViewColor(taskTxt.getText().toString(), allRight, 0);
                DataManager.saveViewText(taskTxt.getText().toString(), respOneRadio.getText().toString(),
                    respTwoRadio.getText().toString(), respThreeRadio.getText().toString());
            }
        });
        respTwoRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                respOneRadio.setOnClickListener(null);
                respTwoRadio.setOnClickListener(null);
                respThreeRadio.setOnClickListener(null);
                int allRight = check(v, key);
                new DataManager().saveViewColor(taskTxt.getText().toString(), allRight, 1);
                DataManager.saveViewText(taskTxt.getText().toString(), respOneRadio.getText().toString(),
                        respTwoRadio.getText().toString(), respThreeRadio.getText().toString());
            }
        });
        respThreeRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                respOneRadio.setOnClickListener(null);
                respTwoRadio.setOnClickListener(null);
                respThreeRadio.setOnClickListener(null);
                int allRight = check(v, key);
                new DataManager().saveViewColor(taskTxt.getText().toString(), allRight, 2);
                DataManager.saveViewText(taskTxt.getText().toString(), respOneRadio.getText().toString(),
                        respTwoRadio.getText().toString(), respThreeRadio.getText().toString());
            }
        });
        initView(position, respOneRadio, respTwoRadio, respThreeRadio, taskTxt, key);
        container.addView(view);
        return view;
    }
    private int check(View v,  String key){
        if(((RadioButton)v).getText().toString().equals(key)){
            ((RadioButton)v).setTextColor(Color.GREEN);
            return 0;
        }else{
            ((RadioButton)v).setTextColor(Color.RED);
            return 1;
        }
    }
    private void initView(int position, RadioButton rB1, RadioButton rB2, RadioButton rB3, TextView taskTxt, String key ){
        taskTxt.setText(taskList.get(position));
        List<Integer> viewDataColor = DataManager.getViewData(taskList.get(position));
        try{
            if(viewDataColor.get(0) == 0){
                rB1.setOnClickListener(null);
                rB2.setOnClickListener(null);
                rB3.setOnClickListener(null);
                switch (viewDataColor.get(1)){
                    case 0: rB1.setTextColor(Color.GREEN); break;
                    case 1: rB2.setTextColor(Color.GREEN); break;
                    case 2: rB3.setTextColor(Color.GREEN); break;
                }
            }else{
                rB1.setOnClickListener(null);
                rB2.setOnClickListener(null);
                rB3.setOnClickListener(null);
                switch (viewDataColor.get(1)){
                    case 0: rB1.setTextColor(Color.RED); break;
                    case 1: rB2.setTextColor(Color.RED); break;
                    case 2: rB3.setTextColor(Color.RED); break;
                }
            }
            List<String> viewData= DataManager.getColorData(taskList.get(position));
            initAnswer(viewData, rB1, rB2, rB3);
        }catch (Exception e){
            String wrongAnswer1;
            String wrongAnswer2;
            do{
                int wrongAnswerIndex = new Random().nextInt(wrongAnswerList.size()-1);
                wrongAnswer1 = wrongAnswerList.get(wrongAnswerIndex);
                wrongAnswerIndex = new Random().nextInt(wrongAnswerList.size()-1);
                wrongAnswer2 = wrongAnswerList.get(wrongAnswerIndex);
            } while (key.equals(wrongAnswer1) || key.equals(wrongAnswer2));
            List<String> list = Arrays.asList(wrongAnswer1, wrongAnswer2, key);
            Collections.shuffle(list);
            initAnswer(list, rB1, rB2, rB3);
        }
    }
    private void initAnswer(List<String> list, RadioButton respOneRadio, RadioButton respTwoRadio,RadioButton respThreeRadio){
        respOneRadio.setText(list.get(0));
        respTwoRadio.setText(list.get(1));
        respThreeRadio.setText(list.get(2));
    }
    @Override
    public int getCount() {
        return taskList.size() - 1;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
