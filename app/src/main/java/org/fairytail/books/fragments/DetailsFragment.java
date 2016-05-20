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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.fairytail.books.Constants;
import org.fairytail.books.R;
import org.fairytail.books.models.Book;
import org.fairytail.books.models.RealmString;

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
    private boolean isFromCart = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        book = (Book) getArguments().getSerializable(Constants.BOOK_KEY);
        isFromCart = getArguments().getBoolean(Constants.IS_FROM_CART);

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
                String authors = "";
                for (RealmString author : book.getVolumeInfo().getAuthors()) {
                    authors += author.getString() + "\n";
                }
                detailsBookAuthors.setText(authors);
            }
            if (book.getVolumeInfo().getDescription() != null) {
                bookDescription.setText(book.getVolumeInfo().getDescription());
            }
            if (book.getVolumeInfo().getImageLinks() != null) {
                String uri = book.getVolumeInfo().getImageLinks().getThumbnail();
                uri = uri.replace("&edge=curl", "");

                Glide.with(this)
                        .load(uri)
                        .fitCenter()
                        .error(R.drawable.image_not_available)
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
        if (isFromCart) {
            add.setVisible(false);
        } else {
            add.setVisible(true);
        }
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
        Toast.makeText(getContext(), R.string.added_to_cart, Toast.LENGTH_SHORT).show();
    }

}
