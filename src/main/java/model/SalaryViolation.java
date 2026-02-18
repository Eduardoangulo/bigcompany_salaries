package model;

public class SalaryViolation {

    public enum Type { TOO_LOW, TOO_HIGH }

    private final Employee manager;
    private final double avgSubordinateSalary;
    private final double minimum;
    private final double maximum;
    private final double difference;
    private final Type type;

    public SalaryViolation(Employee manager,
                           double avgSubordinateSalary,
                           double minimum,
                           double maximum,
                           double difference,
                           Type type) {
        this.manager = manager;
        this.avgSubordinateSalary = avgSubordinateSalary;
        this.minimum = minimum;
        this.maximum = maximum;
        this.difference = difference;
        this.type = type;
    }


    public Employee getManager() {
        return manager;
    }

    public double getAvgSubordinateSalary() {
        return avgSubordinateSalary;
    }

    public double getMinimum() {
        return minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public double getDifference() {
        return difference;
    }

    public Type getType() {
        return type;
    }
}
