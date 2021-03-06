package org.fairytail.books.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.fairytail.books.Constants;
import org.fairytail.books.R;
import org.fairytail.books.adapters.BooksAdapter;
import org.fairytail.books.models.Book;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmResults;

public class CartFragment extends BaseFragment {
    @BindView(R.id.cart_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.empty_cart)
    LinearLayout emptyCart;

    private BooksAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Book> result = realm.where(Book.class).findAll();

        if (!result.isEmpty()) {
            emptyCart.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        adapter = new BooksAdapter(result, (v, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BOOK_KEY, result.get(position));
            bundle.putBoolean(Constants.IS_FROM_CART, true);

            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, detailsFragment)
                    .addToBackStack(null)
                    .commit();
        });
        recyclerView.setAdapter(adapter);
    }

}
