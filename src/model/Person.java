package model;

public abstract class Person implements Searchable, Authenticatable {
    private String id;
    private String name;
    private String password;
    private Role role;

    protected Person(String id, String name, String password, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean authenticate(String candidatePassword) {
        return password != null && password.equals(candidatePassword);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getSearchId() {
        return id;
    }

    @Override
    public String getSearchName() {
        return name;
    }

    @Override
    public boolean matchesQuery(String query) {
        if (query == null) {
            return false;
        }
        String normalized = query.trim().toLowerCase();
        return id.equalsIgnoreCase(normalized) || name.toLowerCase().contains(normalized);
    }

    public abstract String describeRole();
}
