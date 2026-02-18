package model;

public class ManagerLimit {

    private final Employee employee;
    private final int managersBetween;
    private final int tooManyBy;

    public ManagerLimit(Employee employee, int managersBetween, int tooManyBy) {
        this.employee = employee;
        this.managersBetween = managersBetween;
        this.tooManyBy = tooManyBy;
    }


    public Employee getEmployee() {
        return employee;
    }

    public int getManagersBetween() {
        return managersBetween;
    }

    public int getTooManyBy() {
        return tooManyBy;
    }
}
