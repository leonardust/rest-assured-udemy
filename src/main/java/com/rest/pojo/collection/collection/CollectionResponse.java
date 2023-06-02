package com.rest.pojo.collection.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest.pojo.collection.folder.FolderBase;
import com.rest.pojo.collection.Info;
import com.rest.pojo.collection.folder.FolderResponse;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionResponse extends CollectionBase {

  private List<FolderResponse> item;

  public CollectionResponse() {
  }

  public CollectionResponse(Info info, List<FolderResponse> item) {
    super(info);
    this.item = item;
  }

  public List<FolderResponse> getItem() {
    return item;
  }

  public void setItem(List<FolderResponse> item) {
    this.item = item;
  }
}
