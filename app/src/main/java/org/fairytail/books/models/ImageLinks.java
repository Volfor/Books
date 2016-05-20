package org.fairytail.books.models;

import java.io.Serializable;

import io.realm.RealmObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ImageLinks extends RealmObject implements Serializable {
    private String smallThumbnail;
    private String thumbnail;
}
