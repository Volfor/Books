package org.fairytail.books.events;

public class OnLoadingErrorEvent {
    public final String message;

    public OnLoadingErrorEvent(String message) {
        this.message = message;
    }

}
