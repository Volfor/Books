package org.fairytail.books.events;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OnLoadingMoreStartEvent {
    public final String keywords;
    public final int startIndex;
}
