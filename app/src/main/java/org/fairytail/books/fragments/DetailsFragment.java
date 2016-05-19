package org.fairytail.books.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.fairytail.books.Constants;
import org.fairytail.books.R;
import org.fairytail.books.models.Book;

import butterknife.BindView;
import io.realm.Realm;

public class DetailsFragment extends BaseFragment {

    @BindView(R.id.book_cover)
    ImageView bookCover;
    @BindView(R.id.details_book_title)
    TextView detailsBookTitle;
    @BindView(R.id.details_book_authors)
    TextView detailsBookAuthors;
    @BindView(R.id.book_description)
    TextView bookDescription;

    private Book book;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        book = (Book) getArguments().getSerializable(Constants.BOOK_KEY);

        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (book != null) {
            detailsBookTitle.setText(book.getVolumeInfo().getTitle());
            if (book.getVolumeInfo().getAuthors() != null) {
                detailsBookAuthors.setText(book.getVolumeInfo().getAuthors().get(0).getString());
            }
            if (book.getVolumeInfo().getDescription() != null) {
                bookDescription.setText(book.getVolumeInfo().getDescription());
            }
            if (book.getVolumeInfo().getImageLinks() != null) {
                Glide.with(this)
                        .load(book.getVolumeInfo().getImageLinks().getThumbnail())
                        .fitCenter()
//                    .placeholder(R.drawable.loading_spinner)
                        .crossFade()
                        .into(bookCover);
            }
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem search = menu.findItem(R.id.action_search);
        search.setVisible(false);
        MenuItem add = menu.findItem(R.id.action_add);
        add.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
            case R.id.action_add:
                addToCart(book);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addToCart(Book book) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(book);
        realm.commitTransaction();
        realm.close();
    }

}
