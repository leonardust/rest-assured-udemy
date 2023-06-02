package com.rest.pojo.collection.collectionroot;

import com.rest.pojo.collection.collection.CollectionRequest;

public class CollectionRootRequest extends CollectionRootBase {

  CollectionRequest collection;

  public CollectionRootRequest() {
  }

  public CollectionRootRequest(CollectionRequest collection) {
    this.collection = collection;
  }

  public CollectionRequest getCollection() {
    return collection;
  }

  public void setCollection(CollectionRequest collection) {
    this.collection = collection;
  }
}
