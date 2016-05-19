package org.fairytail.books.events;

public class OnLoadMoreEvent {
    public final int itemsCount;

    public OnLoadMoreEvent(int itemsCount) {
        this.itemsCount = itemsCount;
    }

}
