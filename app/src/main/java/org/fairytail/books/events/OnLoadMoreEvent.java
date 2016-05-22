package org.fairytail.books.events;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OnLoadMoreEvent {
    public final int itemsCount;
}
