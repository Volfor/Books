package org.fairytail.books.events;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OnLoadingErrorEvent {
    public final String message;
}
