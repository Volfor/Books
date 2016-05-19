package org.fairytail.books.models;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;

public class BookItems extends RealmObject implements Serializable {
    private int totalItems;
    private RealmList<Book> items;

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public RealmList<Book> getItems() {
        return items;
    }

    public void setItems(RealmList<Book> items) {
        this.items = items;
    }

}
