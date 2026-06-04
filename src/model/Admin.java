package model;

public class Admin extends Person {
    public Admin(String id, String name, String password) {
        super(id, name, password, Role.ADMIN);
    }

    @Override
    public String describeRole() {
        return "Administrator with full data-management permission";
    }
}
