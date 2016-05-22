package org.fairytail.books.events;

import org.fairytail.books.models.BookItems;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OnLoadedEvent {
    public final BookItems items;
}
