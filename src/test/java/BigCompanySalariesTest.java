import model.Employee;
import org.junit.jupiter.api.Test;
import service.CompanyAnalyzer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BigCompanySalariesTest {

    @Test
    void managerTooLowSalaryDetected() {
        Employee ceo = new Employee(1, "John", "Anderson", 100000, null);
        Employee manager = new Employee(2, "Maria", "One", 40000, 1);
        Employee e1 = new Employee(3, "Eduardo", "Luna", 40000, 2);
        Employee e2 = new Employee(4, "Bern", "Bunny", 50000, 2);

        CompanyAnalyzer analyzer = new CompanyAnalyzer();
        var violations = analyzer.findSalaryViolation(List.of(ceo, manager, e1, e2));

        assertEquals(1, violations.size());
        assertEquals(2, violations.get(0).getManager().getId());
        assertEquals("TOO_LOW", violations.get(0).getType().name());
    }

    @Test
    void managerTooHighSalaryDetected() {
        Employee ceo = new Employee(1, "John", "Anderson", 100000, null);
        Employee manager = new Employee(2, "Maria", "One", 100000, 1);
        Employee e1 = new Employee(3, "Eduardo", "Luna", 40000, 2);
        Employee e2 = new Employee(4, "Bern", "Bunny", 50000, 2);

        CompanyAnalyzer analyzer = new CompanyAnalyzer();
        var violations = analyzer.findSalaryViolation(List.of(ceo, manager, e1, e2));

        assertEquals(2, violations.get(0).getManager().getId());
        assertEquals("TOO_HIGH", violations.get(0).getType().name());
    }

    @Test
    void tooManyManagersDetected() {
        Employee e1 = new Employee(1, "John", "Anderson", 100000, null);
        Employee e2 = new Employee(2, "Maria", "One", 90000, 1);
        Employee e3 = new Employee(3, "Eduardo", "Luna", 80000, 2);
        Employee e4 = new Employee(4, "Jair", "Cuba", 70000, 3);
        Employee e5 = new Employee(5, "Kenny", "Horna", 60000, 4);
        Employee e6 = new Employee(6, "Xiomara", "Tarrillo", 50000, 5);
        Employee e7 = new Employee(7, "Enrique", "Luna", 40000, 6);

        CompanyAnalyzer analyzer = new CompanyAnalyzer();
        var violations = analyzer.findManagerViolation(List.of(e1, e2, e3, e4, e5, e6, e7));
        assertTrue(violations.stream().anyMatch(v -> v.getEmployee().getId() == 7));
    }

    @Test
    void oneManagerMaximumAbove() {
        Employee e1 = new Employee(1, "John", "Anderson", 100000, null);
        Employee e2 = new Employee(2, "Maria", "One", 90000, 1);
        Employee e3 = new Employee(3, "Eduardo", "Luna", 80000, 2);
        Employee e4 = new Employee(4, "Jair", "Cuba", 70000, 3);
        Employee e5 = new Employee(5, "Kenny", "Horna", 60000, 4);
        Employee e6 = new Employee(6, "Xiomara", "Tarrillo", 50000, 5);

        CompanyAnalyzer analyzer = new CompanyAnalyzer();
        var violations = analyzer.findManagerViolation(List.of(e1, e2, e3, e4, e5, e6));
        assertEquals(5, violations.get(0).getManagersBetween());
    }

    @Test
    void managerMaximumAbove() {
        Employee e1 = new Employee(1, "John", "Anderson", 100000, null);
        Employee e2 = new Employee(2, "Maria", "One", 90000, 1);
        Employee e3 = new Employee(3, "Eduardo", "Luna", 80000, 2);
        Employee e4 = new Employee(4, "Jair", "Cuba", 70000, 3);
        Employee e5 = new Employee(5, "Kenny", "Horna", 60000, 4);
        
        CompanyAnalyzer analyzer = new CompanyAnalyzer();
        var violations = analyzer.findManagerViolation(List.of(e1, e2, e3, e4, e5));
        assertEquals(0, violations.size());
    }

    @Test
    void missingManager() {
        Employee e1 = new Employee(1, "John", "Anderson", 100000, null);
        Employee e2 = new Employee(2, "Maria", "One", 90000, 1);
        Employee e3 = new Employee(3, "Eduardo", "Luna", 80000, 2);
        Employee e5 = new Employee(5, "Kenny", "Horna", 60000, 4);  // Manager 4 doesn't exist
        
        CompanyAnalyzer analyzer = new CompanyAnalyzer();
        assertThrows(IllegalStateException.class, () -> 
            analyzer.findManagerViolation(List.of(e1, e2, e3, e5))
        );
    }

}
