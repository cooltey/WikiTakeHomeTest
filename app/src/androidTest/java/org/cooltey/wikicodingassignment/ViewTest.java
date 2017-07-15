package org.cooltey.wikicodingassignment;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ViewTest {

    @Rule
    public ActivityTestRule<MainActivity> mainRule  = new  ActivityTestRule<>(MainActivity.class);
    @Rule
    public ActivityTestRule<GalleryActivity> galleryRule  = new  ActivityTestRule<>(GalleryActivity.class);
    @Rule
    public ActivityTestRule<SearchActivity> searchRule  = new  ActivityTestRule<>(SearchActivity.class);

    @Test
    public void ensureMainActivityIsPresent() throws Exception {
        MainActivity activity = mainRule.getActivity();
        View viewById = activity.findViewById(R.id.recycler_view);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(RecyclerView.class));

        View viewById2 = activity.findViewById(R.id.refresh_layout);
        assertThat(viewById2,notNullValue());
        assertThat(viewById2, instanceOf(SwipeRefreshLayout.class));
    }

    @Test
    public void ensureGalleryActivityIsPresent() throws Exception {

        GalleryActivity activity = galleryRule.getActivity();
        View viewById = activity.findViewById(R.id.view_pager);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(ViewPager.class));

        View viewById2 = activity.findViewById(R.id.cap_title);
        assertThat(viewById2,notNullValue());
        assertThat(viewById2, instanceOf(TextView.class));

        View viewById3 = activity.findViewById(R.id.cap_count);
        assertThat(viewById3,notNullValue());
        assertThat(viewById3, instanceOf(TextView.class));
    }

    @Test
    public void ensureSearchActivityIsPresent() throws Exception {

        SearchActivity activity = searchRule.getActivity();
        View viewById = activity.findViewById(R.id.recycler_view);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(RecyclerView.class));


        View viewById2 = activity.findViewById(R.id.refresh_layout);
        assertThat(viewById2,notNullValue());
        assertThat(viewById2, instanceOf(SwipeRefreshLayout.class));
    }
}