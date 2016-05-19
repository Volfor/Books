package org.fairytail.books.events;

public class OnLoadingStartEvent {
    public final String keywords;

    public OnLoadingStartEvent(String keywords) {
        this.keywords = keywords;
    }

}
