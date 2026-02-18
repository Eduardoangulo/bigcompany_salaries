package service;

import model.Employee;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class EmployeeReader {
    public List<Employee> read(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);

        if (lines.isEmpty()) return List.of();

        List<Employee> employees = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(",");

            int id = Integer.parseInt(parts[0]);
            String firstName = parts[1];
            String lastName = parts[2];
            double salary = Double.parseDouble(parts[3]);

            Integer managerId = null;
            if (parts.length >= 5 && !parts[4].isBlank()) {
                managerId = Integer.parseInt(parts[4]);
            }

            employees.add(new Employee(id, firstName, lastName, salary, managerId));
        }

        return employees;
    }
}
