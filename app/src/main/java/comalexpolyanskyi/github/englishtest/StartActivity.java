package comalexpolyanskyi.github.englishtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

import comalexpolyanskyi.github.englishtest.fragments.ResultFragment;
import comalexpolyanskyi.github.englishtest.fragments.TestFragment;


public class StartActivity extends FragmentActivity implements ResultFragment.FragmentCallback, TestFragment.FragmentCallback{
    private Fragment contentFragment;
    private TestFragment testFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
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
}
