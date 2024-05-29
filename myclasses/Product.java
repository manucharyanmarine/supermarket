package myclasses;

public class Product {
    private String id;
    private String name;
    private String category;
    private String status;
    private int quantity;
    private String price;

    public Product(String id, String name, String category, String status, int quantity, String price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.status = status;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters for all fields

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", status='" + status + '\'' +
                ", quantity=" + quantity +
                ", price='" + price + '\'' +
                '}';
    }
}
