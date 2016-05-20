package org.fairytail.books.models;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Book extends RealmObject implements Serializable {
    @PrimaryKey
    private String id;
    private VolumeInfo volumeInfo;
}
