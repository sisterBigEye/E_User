package com.hlbd.electric.model;


import java.util.List;

public class Information {

  public DataShape dataShape;

  public List<Row> rows;

  public static class DataShape {
    public DataShape.FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {

      public Avatar avatar;

      public Description description;

      public HomeMashup homeMashup;

      public SystemObject isSystemObject;

      public Name name;

      public Tag tags;

      public static class Avatar {

      }

      public static class Description {

      }

      public static class HomeMashup {

      }

      public static class SystemObject {

      }

      public static class Name {

      }

      public static class Tag {

      }

    }
  }

  public static class Row {
    public String avatar;

    public String description;

    public String homeMashup;

    public boolean isSystemObject;

    public String name;

    public List<Tag> tags;

    public static class Tag {

      public String vocabulary;

      public String vocabularyTerm;

      @Override
      public String toString() {
        return "Tag{" +
                "vocabulary='" + vocabulary + '\'' +
                ", vocabularyTerm='" + vocabularyTerm + '\'' +
                '}';
      }
    }

    @Override
    public String toString() {
      return "Row{" +
              "avatar='" + avatar + '\'' +
              ", description='" + description + '\'' +
              ", homeMashup='" + homeMashup + '\'' +
              ", isSystemObject=" + isSystemObject +
              ", name='" + name + '\'' +
              ", tags=" + tags +
              '}';
    }
  }

  @Override
  public String toString() {
    return "Information{" +
            "dataShape=" + dataShape +
            ", rows=" + rows +
            '}';
  }
}
