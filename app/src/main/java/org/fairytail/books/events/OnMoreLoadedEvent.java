package org.fairytail.books.events;

import org.fairytail.books.models.BookItems;

public class OnMoreLoadedEvent {
    public final BookItems items;

    public OnMoreLoadedEvent(BookItems items) {
        this.items = items;
    }

}
