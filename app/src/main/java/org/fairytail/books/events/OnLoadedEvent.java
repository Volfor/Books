package org.fairytail.books.events;

import org.fairytail.books.models.BookItems;

public class OnLoadedEvent {
    public final BookItems items;

    public OnLoadedEvent(BookItems items) {
        this.items = items;
    }

}
