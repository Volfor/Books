package org.fairytail.books.events;

public class OnLoadingMoreStartEvent {
    public final String keywords;
    public final int startIndex;

    public OnLoadingMoreStartEvent(String keywords, int startIndex) {
        this.keywords = keywords;
        this.startIndex = startIndex;
    }

}
