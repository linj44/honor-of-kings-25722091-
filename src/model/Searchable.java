package model;

public interface Searchable {
    String getSearchId();

    String getSearchName();

    boolean matchesQuery(String query);
}
