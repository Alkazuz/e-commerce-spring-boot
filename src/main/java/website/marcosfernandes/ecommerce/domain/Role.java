package website.marcosfernandes.ecommerce.domain;

public enum Role {
    ADMIN("Admin"),
    CUSTOMER("Customer"),
    SELLER("Seller");

    private final String name;
    Role (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
