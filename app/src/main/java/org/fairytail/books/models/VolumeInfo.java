package org.fairytail.books.models;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VolumeInfo extends RealmObject implements Serializable {
    private String title;
    private String subtitle;
    private RealmList<RealmString> authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private RealmList<RealmString> categories;
    private ImageLinks imageLinks;
}
