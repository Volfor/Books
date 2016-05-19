package org.fairytail.books.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.fairytail.books.Constants;
import org.fairytail.books.listeners.EndlessRecyclerViewScrollListener;
import org.fairytail.books.R;
import org.fairytail.books.adapters.BooksAdapter;
import org.fairytail.books.events.OnLoadMoreEvent;
import org.fairytail.books.events.OnLoadedEvent;
import org.fairytail.books.events.OnLoadingErrorEvent;
import org.fairytail.books.events.OnMoreLoadedEvent;
import org.fairytail.books.models.Book;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BooksFragment extends BaseFragment {

    @BindView(R.id.books_recycler)
    RecyclerView recyclerView;

    private BooksAdapter adapter;
    private List<Book> items = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(false);
        }

        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new BooksAdapter(items, new BooksAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.BOOK_KEY, items.get(position));

                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, detailsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                EventBus.getDefault().post(new OnLoadMoreEvent(items.size()));
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem search = menu.findItem(R.id.action_search);
        search.setVisible(true);
    }

    @Subscribe
    public void onBooksLoaded(OnLoadedEvent e) {
        items = e.items.getItems();
        Toast.makeText(getContext(), "Loaded " + items.size() + " items", Toast.LENGTH_LONG).show();

        if (items != null && !items.isEmpty()) {
            adapter.setItems(items);
        } else {
            //TODO: show nothing found image
        }
    }

    @Subscribe
    public void onMoreLoaded(OnMoreLoadedEvent e) {
        items.addAll(e.items.getItems());
        adapter.addItems(e.items.getItems());
    }

    @Subscribe
    public void onBooksLoadingFailed(OnLoadingErrorEvent e) {
        Toast.makeText(getContext(), "Error: " + e.message, Toast.LENGTH_LONG).show();
        Log.e(Constants.LOG_TAG, e.message);
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
