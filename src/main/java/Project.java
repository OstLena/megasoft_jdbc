import java.math.BigDecimal;

public class Project {

    private Long id;
    private BigDecimal price;

    public Project(Long id, BigDecimal price) {
        this.id = id;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + id + '\'' +
                ", price=" + price +
                '}';
    }
}
