package model;

public interface Authenticatable {
    String getUsername();

    boolean authenticate(String password);
}
