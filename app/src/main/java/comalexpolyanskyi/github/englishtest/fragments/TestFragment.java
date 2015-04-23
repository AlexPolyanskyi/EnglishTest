package comalexpolyanskyi.github.englishtest.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import comalexpolyanskyi.github.englishtest.R;
import comalexpolyanskyi.github.englishtest.adapter.MyViewPageAdapter;
import comalexpolyanskyi.github.englishtest.managers.DataManager;

/**
 * Created by Алексей on 21.04.2015.
 */
public class TestFragment extends Fragment {
    public static final String ARG_ITEM_ID = "test_fragment";
    private ViewPager mViewPager;
    private FragmentActivity activity;
    FragmentCallback mCallbacks;
    public interface FragmentCallback {
        public void switchContent(Fragment fragment, String tag);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        mCallbacks = (FragmentCallback) activity ;
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        DataManager dataManager = new DataManager();
        Resources resources = getResources();
        List<String> taskList = dataManager.createTaskList(resources);
        List<String> wrongAnswerList = dataManager.createWrongAnswerList(resources);
        mViewPager.setAdapter(new MyViewPageAdapter(activity, taskList, wrongAnswerList,this));
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.test_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment  = new ResultFragment();
        mCallbacks.switchContent(fragment, ResultFragment.ARG_ITEM_ID);
        return super.onOptionsItemSelected(item);
    }
}
