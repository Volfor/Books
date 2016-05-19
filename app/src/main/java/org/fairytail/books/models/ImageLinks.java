package org.fairytail.books.models;

import java.io.Serializable;

import io.realm.RealmObject;

public class ImageLinks extends RealmObject implements Serializable {
    private String smallThumbnail;
    private String thumbnail;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

}
