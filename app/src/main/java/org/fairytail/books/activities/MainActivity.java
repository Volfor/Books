package org.fairytail.books.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.fairytail.books.R;
import org.fairytail.books.events.OnLoadMoreEvent;
import org.fairytail.books.events.OnLoadedEvent;
import org.fairytail.books.events.OnLoadingMoreStartEvent;
import org.fairytail.books.events.OnLoadingStartEvent;
import org.fairytail.books.fragments.MainFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progress_bar)
    MaterialProgressBar progressBar;

    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new MainFragment(), MainFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_books));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                MainActivity.this.query = query;
                EventBus.getDefault().post(new OnLoadingStartEvent(query));
                progressBar.setVisibility(View.VISIBLE);
                return false;
            }
        });
        return true;
    }

    @Subscribe
    public void onLoadMore(OnLoadMoreEvent e) {
        EventBus.getDefault().post(new OnLoadingMoreStartEvent(query, e.itemsCount - 1));
    }

    @Subscribe
    public void onLoaded(OnLoadedEvent e) {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
