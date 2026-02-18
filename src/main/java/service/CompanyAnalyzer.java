package service;

import model.Employee;
import model.ManagerLimit;
import model.SalaryViolation;

import java.util.*;

import static common.Constants.*;
import static common.Util.*;

public class CompanyAnalyzer {

    public List<SalaryViolation> findSalaryViolation(List<Employee> employees)
    {
        Map<Integer, Employee> byId = indexById(employees);
        Map<Integer, List<Employee>> directReports = getSubordinates(employees);

        List<SalaryViolation> violations = new ArrayList<>();

        for (Map.Entry<Integer, List<Employee>> entry : directReports.entrySet())
        {
            int managerId = entry.getKey();
            Employee manager = byId.get(managerId);

            if (manager.getManagerId() == null) continue;

            List<Employee> reports = entry.getValue();

            double avg = reports.stream().mapToDouble(Employee::getSalary).average().orElse(0);

            double minAllowed = avg * MIN_MULTIPLIER;
            double maxAllowed = avg * MAX_MULTIPLIER;

            if (manager.getSalary() < minAllowed)
            {
                violations.add(new SalaryViolation(manager, avg, minAllowed, maxAllowed, minAllowed - manager.getSalary(), SalaryViolation.Type.TOO_LOW));
            } else
                if (manager.getSalary() > maxAllowed)
                {
                    violations.add(new SalaryViolation(manager, avg, minAllowed, maxAllowed, manager.getSalary() - maxAllowed, SalaryViolation.Type.TOO_HIGH));
                }
        }

        return violations;
    }

    public List<ManagerLimit> findManagerViolation(List<Employee> employees)
    {
        Map<Integer, Employee> byId = indexById(employees);

        List<ManagerLimit> violations = new ArrayList<>();

        for (Employee employee : employees)
        {
            int managersBetween = countManagersAbove(employee, byId);
            if (managersBetween > MAX_MANAGERS_BETWEEN)
                violations.add(new ManagerLimit(employee, managersBetween, managersBetween - MAX_MANAGERS_BETWEEN));
        }

        return violations;
    }

    private int countManagersAbove(Employee employee, Map<Integer, Employee> employeesById) {
        if (employee.getManagerId() == null) {
            return 0;
        }

        int managersAbove = 0;
        Integer currentManagerId = employee.getManagerId();
        Set<Integer> visited = new HashSet<>();

        while (currentManagerId != null) {
            if (!visited.add(currentManagerId))
                throw new IllegalStateException("ERROR - Manager loop detected in reporting structure near ID: " + currentManagerId);

            Employee manager = employeesById.get(currentManagerId);
            if (manager == null)
                throw new IllegalStateException("ERROR - Manager not found in employee list for ID: " + currentManagerId);

            if (manager.getManagerId() != null) {
                managersAbove++;
            }

            currentManagerId = manager.getManagerId();
        }

        return managersAbove;
    }
}
