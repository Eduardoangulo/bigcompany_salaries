package common;

import model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {

    public static Map<Integer, Employee> indexById(List<Employee> employees) {
        Map<Integer, Employee> map = new HashMap<>();
        for (Employee e : employees) {
            map.put(e.getId(), e);
        }
        return map;
    }

    public static Map<Integer, List<Employee>> getSubordinates(List<Employee> employees) {
        Map<Integer, List<Employee>> map = new HashMap<>();

        for (Employee e : employees) {
            if (e.getManagerId() != null && !e.getManagerId().equals(e.getId())) {
                map.computeIfAbsent(e.getManagerId(), k -> new ArrayList<>()).add(e);
            }
        }

        return map;
    }
}
