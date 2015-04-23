package comalexpolyanskyi.github.englishtest.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import comalexpolyanskyi.github.englishtest.R;
import comalexpolyanskyi.github.englishtest.managers.DataManager;

/**
 * Created by Алексей on 21.04.2015.
 */
public class ResultFragment extends Fragment {
    public static final String ARG_ITEM_ID = "result_fragment";
    public static final String RESULT = "Ваш результат ";
    FragmentCallback mCallbacks;
    public interface FragmentCallback {
        public void switchContent(Fragment fragment, String tag);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet_result, container, false);
        ((TextView)view.findViewById(R.id.result)).setText(RESULT + DataManager.getRightAnswer() + " из " + DataManager.getSize());
        mCallbacks = (FragmentCallback) getActivity();
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.result_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment  = new TestFragment();
        mCallbacks.switchContent(fragment, TestFragment.ARG_ITEM_ID);
        return super.onOptionsItemSelected(item);
    }
}
