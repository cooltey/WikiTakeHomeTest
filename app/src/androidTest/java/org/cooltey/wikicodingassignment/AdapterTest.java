package org.cooltey.wikicodingassignment;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.cooltey.wikicodingassignment.model.SearchResponseItem;
import org.cooltey.wikicodingassignment.util.DatabaseHelper;
import org.cooltey.wikicodingassignment.util.RecyclerViewAdapter;
import org.cooltey.wikicodingassignment.util.SearchHistoryAdapter;
import org.cooltey.wikicodingassignment.util.ViewPagerAdapter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class AdapterTest {

    @Rule
    public ActivityTestRule<MainActivity> mainRule  = new  ActivityTestRule<>(MainActivity.class);

    private Activity mActivity;

    @Before
    public void setUp(){
        mActivity = mainRule.getActivity();
    }

    @Test
    public void recyclerViewAdapterTest() throws Exception {

        List<SearchResponseItem> list = new ArrayList<>();
        list.add(new SearchResponseItem());
        list.add(new SearchResponseItem());
        list.add(new SearchResponseItem());

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mActivity, list, "test");

        assertThat(adapter, notNullValue());
        assertTrue(adapter.getItemCount() == 3);

    }

    @Test
    public void searchHistoryAdapterTest() throws Exception {


        assertNotNull(new DatabaseHelper(mActivity).getKeywords());

        SearchHistoryAdapter adapter = new SearchHistoryAdapter(mActivity, new DatabaseHelper(mActivity).getKeywords());

        assertThat(adapter, notNullValue());
        assertTrue(adapter.getItemCount() >= 0);

    }

    @Test
    public void viewPagerAdapterTest() throws Exception {

        ArrayList<String> list = new ArrayList<>();
        list.add("test");
        list.add("test");
        list.add("test");


        ViewPagerAdapter adapter = new ViewPagerAdapter(mActivity, list, list, true);
        assertThat(adapter, notNullValue());
        assertTrue(adapter.getCount() == 3);

    }
}