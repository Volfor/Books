package org.fairytail.books.models;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BookItems extends RealmObject implements Serializable {
    private int totalItems;
    private RealmList<Book> items;
}
