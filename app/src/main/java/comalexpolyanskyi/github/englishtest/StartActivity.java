package comalexpolyanskyi.github.englishtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import comalexpolyanskyi.github.englishtest.fragments.ResultFragment;
import comalexpolyanskyi.github.englishtest.fragments.TestFragment;
import comalexpolyanskyi.github.englishtest.managers.DataManager;
import comalexpolyanskyi.github.englishtest.utils.CheckNetworkConnection;
import comalexpolyanskyi.github.englishtest.utils.PostOfWall;
import comalexpolyanskyi.github.englishtest.utils.Token;


public class StartActivity extends FragmentActivity implements ResultFragment.FragmentCallback, TestFragment.FragmentCallback, PostOfWall.PostCallbacks{
    private Fragment contentFragment;
    private TestFragment testFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if(CheckNetworkConnection.isConnectionAvailable(this)) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (savedInstanceState != null) {
                if (savedInstanceState.containsKey("content")) {
                    String content = savedInstanceState.getString("content");
                    if (content.equals(ResultFragment.ARG_ITEM_ID)) {
                        if (fragmentManager
                                .findFragmentByTag(ResultFragment.ARG_ITEM_ID) != null) {
                            contentFragment = fragmentManager
                                    .findFragmentByTag(ResultFragment.ARG_ITEM_ID);
                        }
                    }
                }
                if (fragmentManager.findFragmentByTag(TestFragment.ARG_ITEM_ID) != null) {
                    testFragment = (TestFragment) fragmentManager
                            .findFragmentByTag(TestFragment.ARG_ITEM_ID);
                    contentFragment = testFragment;
                }
            } else {
                testFragment = new TestFragment();
                switchContent(testFragment, TestFragment.ARG_ITEM_ID);
            }
        }else{
            startActivity(new Intent());
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (contentFragment instanceof TestFragment) {
            outState.putString("content", TestFragment.ARG_ITEM_ID);
        } else {
            outState.putString("content", ResultFragment.ARG_ITEM_ID);
        }
        super.onSaveInstanceState(outState);
    }
    @Override
    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.content_frame, fragment, tag);
            transaction.commit();
            contentFragment = fragment;
        }
}
    public void onPostButton(View view){
        if(Token.isLogget()){
            String message = "Поздравляем!!!! Вы набрали "+ DataManager.getRightAnswer()*100+" баллов!!!";
            new PostOfWall(this).execute(message);
        }else{
            startActivityForResult(new Intent(this, VkLoginActivity.class), 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String message = "Поздравляем!!!! Вы набрали "+ DataManager.getRightAnswer()*100+" баллов!!!";
        new PostOfWall(this).execute(message);
    }

    @Override
    public void onError() {
        ((TextView)findViewById(R.id.text_result)).setText("Неудача");
    }

    @Override
    public void onSuccess() {
        ((TextView)findViewById(R.id.text_result)).setText("Запись успешно размещена");
    }
}
