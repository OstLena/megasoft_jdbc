public class DatabasePopulateService implements DatabaseService {

    public static void main(String[] args) {
        DatabasePopulateService service = new DatabasePopulateService();
        service.executeScript(service.getSQLScript("sql/populate_db.sql"));
    }
}
