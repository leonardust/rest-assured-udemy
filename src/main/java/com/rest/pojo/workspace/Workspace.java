package com.rest.pojo.workspace;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;

@JsonIgnoreProperties(value = "id", allowSetters = true)
public class Workspace {

  // deserialization -> allow setters
  // serialization   -> allow getters
  @JsonIgnore //this annotation ignore property during serialization and deserialization
  private int i;
  //@JsonInclude(JsonInclude.Include.NON_NULL) // this annotation ignore property during serialization only
  private String id;

  @JsonIgnore
  private HashMap<String, String> myHashMap;
  private String name;
  private String type;
  private String description;

  public Workspace() {
  }

  public Workspace(String name, String type, String description) {
    this.name = name;
    this.type = type;
    this.description = description;
  }

  public int getI() {
    return i;
  }

  public void setI(int i) {
    this.i = i;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public HashMap<String, String> getMyHashMap() {
    return myHashMap;
  }

  public void setMyHashMap(HashMap<String, String> myHashMap) {
    this.myHashMap = myHashMap;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
