package com.rest.pojo.collection.collectionroot;

import com.rest.pojo.collection.collection.CollectionResponse;

public class CollectionRootResponse extends CollectionRootBase {

  CollectionResponse collection;

  public CollectionRootResponse() {
  }

  public CollectionRootResponse(CollectionResponse collection) {
    this.collection = collection;
  }

  public CollectionResponse getCollection() {
    return collection;
  }

  public void setCollection(CollectionResponse collection) {
    this.collection = collection;
  }
}
