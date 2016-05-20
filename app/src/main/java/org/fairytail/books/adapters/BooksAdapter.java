package org.fairytail.books.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.fairytail.books.R;
import org.fairytail.books.models.Book;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
    public interface ItemClickListener {
        void onItemClick(View v, int position);
    }

    private ItemClickListener listener;
    private List<Book> items;

    public BooksAdapter(List<Book> items, ItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public void setItems(List<Book> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addItems(List<Book> items) {
        int curSize = getItemCount();
        this.items.addAll(items);
        notifyItemRangeInserted(curSize, items.size() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getLayoutPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.clearViews();
        holder.setItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.book_image)
        ImageView cover;
        @BindView(R.id.book_title)
        TextView title;
        @BindView(R.id.book_author)
        TextView author;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setItem(Book book) {
            title.setText(book.getVolumeInfo().getTitle());
            if (book.getVolumeInfo().getAuthors() != null) {
                author.setText(book.getVolumeInfo().getAuthors().get(0).getString());
            }
            if (book.getVolumeInfo().getImageLinks() != null) {
                String uri = book.getVolumeInfo().getImageLinks().getThumbnail();
                uri = uri.replace("&edge=curl", "");

                Glide.with(cover.getContext())
                        .load(uri)
                        .fitCenter()
                        .error(R.drawable.image_not_available)
                        .crossFade()
                        .into(cover);
            }
        }

        public void clearViews() {
            title.setText("");
            author.setText("");
            cover.setImageDrawable(null);
        }
    }

}
