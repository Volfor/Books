package org.fairytail.books.models;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;

public class VolumeInfo extends RealmObject implements Serializable {
    private String title;
    private String subtitle;
    private RealmList<RealmString> authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private RealmList<RealmString> categories;
    private ImageLinks imageLinks;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public RealmList<RealmString> getAuthors() {
        return authors;
    }

    public void setAuthors(RealmList<RealmString> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RealmList<RealmString> getCategories() {
        return categories;
    }

    public void setCategories(RealmList<RealmString> categories) {
        this.categories = categories;
    }

    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

}
