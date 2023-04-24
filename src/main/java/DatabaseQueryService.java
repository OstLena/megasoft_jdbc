import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class DatabaseQueryService implements DatabaseService {
    public static void main(String[] args) {

        DatabaseQueryService databaseQueryService = new DatabaseQueryService();

        List<MaxProjectCountClient> maxProjectsClient = databaseQueryService.findQueryResult("sql/find_max_projects_client.sql", rs -> {
            try {
                String name = rs.getString("name");
                int projectCount = rs.getInt("project_count");
                return new MaxProjectCountClient(name, projectCount);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        List<MaxSalaryWorker> maxSalaryWorkers = databaseQueryService.findQueryResult("sql/find_max_salary_worker.sql", rs -> {
            try {
                String name = rs.getString("name");
                BigDecimal salary = rs.getBigDecimal("salary");
                return new MaxSalaryWorker(name, salary);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        List<LongestProject> longestProjects = databaseQueryService.findQueryResult("sql/find_longest_project.sql", rs -> {
            try {
                Long id = rs.getLong("id");
                int monthCount = rs.getInt("month_count");
                return new LongestProject(id, monthCount);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


        List<Worker> workers = databaseQueryService.findQueryResult("sql/find_youngest_eldest_workers.sql", rs -> {
            try {
                String type = rs.getString("type");
                String name = rs.getString("name");
                Date birthday = rs.getDate("birthday");
                return new Worker(type, name, birthday);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        List<Project> projects = databaseQueryService.findQueryResult("sql/print_project_prices.sql", rs -> {
            try {
                Long id = rs.getLong("project_id");
                BigDecimal price = rs.getBigDecimal("price").setScale(2);
                return new Project(id, price);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        maxProjectsClient.forEach(System.out::println);
        maxSalaryWorkers.forEach(System.out::println);
        longestProjects.forEach(System.out::println);
        workers.forEach(System.out::println);
        projects.forEach(System.out::println);

    }

    <T> List<T> findQueryResult(String path, Function<ResultSet, T> map) {
        String query = getSQLScript(path);
        try (Connection conn = Database.getInstance().getConnection()) {
            System.out.println(conn);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            List<T> result = new ArrayList<>();
            while (rs.next()) {
                T pojo = map.apply(rs);
                result.add(pojo);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
