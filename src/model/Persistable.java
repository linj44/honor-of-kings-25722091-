package model;

public interface Persistable {
    String serialize();

    void deserialize(String data);
}
