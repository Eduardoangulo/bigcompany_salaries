import model.ManagerLimit;
import model.SalaryViolation;
import service.CompanyAnalyzer;
import service.EmployeeReader;

import java.nio.file.Path;
import java.util.List;

public class App
{
    public static void main(String[] args) throws Exception
    {
        Path file;

        if (args.length > 0){
            file = Path.of(args[0]);
        }
        else {
            var resource = App.class.getClassLoader().getResource("employees.csv");
            if (resource == null) {
                throw new IllegalStateException("ERROR - employees.csv not found in resources!");
            }
            file = Path.of(resource.toURI());
        }

        EmployeeReader reader = new EmployeeReader();
        CompanyAnalyzer obj = new CompanyAnalyzer();

        var employees = reader.read(file);

        List<SalaryViolation> salaryViolations = obj.findSalaryViolation(employees);
        List<ManagerLimit> lineViolations = obj.findManagerViolation(employees);

        printSalaryViolations(salaryViolations);

        printManagerViolations(lineViolations);
    }

    private static void printSalaryViolations(List<SalaryViolation> violations) {
        System.out.println("INFO - Salary Violations ");

        System.out.println("INFO - TOO LOW ");
        violations.stream()
                .filter(v -> v.getType() == SalaryViolation.Type.TOO_LOW)
                .forEach(v -> System.out.printf(
                        "\tID: %1$d - %2$s earns %3$.2f, average subordinate salary = %4$.2f, should be minimum %5$.2f, missing %6$.2f%n",
                        v.getManager().getId(),
                        v.getManager().getFirstName() + " " + v.getManager().getLastName(),
                        v.getManager().getSalary(),
                        v.getAvgSubordinateSalary(),
                        v.getMinimum(),
                        v.getDifference()
                ));

        System.out.println("INFO - TOO HIGH ");
        violations.stream()
                .filter(v -> v.getType() == SalaryViolation.Type.TOO_HIGH)
                .forEach(v -> System.out.printf(
                        "\tID: %1$d - %2$s earns %3$.2f, average subordinate salary = %4$.2f, should be maximum %5$.2f, excess %6$.2f%n",
                        v.getManager().getId(),
                        v.getManager().getFirstName() + " " + v.getManager().getLastName(),
                        v.getManager().getSalary(),
                        v.getAvgSubordinateSalary(),
                        v.getMaximum(),
                        v.getDifference()
                ));
    }

    private static void printManagerViolations(List<ManagerLimit> violations)
    {
        System.out.println("INFO - Excessive number of managers among workers");

        violations.forEach(v -> System.out.printf(
                "\tID: %1$s - %2$s has %3$s managers between them and CEO by %4$s levels\n",
                v.getEmployee().getId(),
                v.getEmployee().getFirstName() + " " + v.getEmployee().getLastName(),
                v.getManagersBetween(),
                v.getTooManyBy()
        ));
    }
}
