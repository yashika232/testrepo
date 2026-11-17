import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A console application for managing employee records.
 */
public class EmployeeManager {
    private static final List<Employee> employees = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Employee Management ===");
            System.out.println("1. Add employee");
            System.out.println("2. Display all employees");
            System.out.println("3. Search employee by ID");
            System.out.println("4. Update employee details");
            System.out.println("5. Delete employee");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    addEmployee();
                    break;
                case "2":
                    displayEmployees();
                    break;
                case "3":
                    searchEmployee();
                    break;
                case "4":
                    updateEmployee();
                    break;
                case "5":
                    deleteEmployee();
                    break;
                case "6":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose a number from 1 to 6.");
            }
        }

        scanner.close();
    }

    private static void addEmployee() {
        System.out.print("Employee ID: ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("Employee ID cannot be empty.");
            return;
        }
        if (findEmployeeById(id) != null) {
            System.out.println("An employee with that ID already exists.");
            return;
        }

        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Department: ");
        String department = scanner.nextLine().trim();
        if (name.isEmpty() || department.isEmpty()) {
            System.out.println("Name and department cannot be empty.");
            return;
        }

        Double salary = readSalary("Salary: ");
        if (salary == null) {
            return;
        }

        employees.add(new Employee(id, name, department, salary));
        System.out.println("Employee added successfully.");
    }

    private static void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees have been added yet.");
            return;
        }

        System.out.println("\nEmployee details:");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    private static void searchEmployee() {
        System.out.print("Enter employee ID to search: ");
        String id = scanner.nextLine().trim();
        Employee employee = findEmployeeById(id);

        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
        } else {
            System.out.println(employee);
        }
    }

    private static void updateEmployee() {
        System.out.print("Enter the ID of the employee to update: ");
        String currentId = scanner.nextLine().trim();
        Employee employee = findEmployeeById(currentId);

        if (employee == null) {
            System.out.println("No employee found with ID " + currentId + ".");
            return;
        }

        System.out.print("New employee ID (press Enter to keep " + employee.getId() + "): ");
        String newId = scanner.nextLine().trim();
        if (!newId.isEmpty() && !newId.equalsIgnoreCase(employee.getId())
                && findEmployeeById(newId) != null) {
            System.out.println("An employee with that ID already exists. No changes were made.");
            return;
        }

        System.out.print("New name (press Enter to keep " + employee.getName() + "): ");
        String newName = scanner.nextLine().trim();
        System.out.print("New department (press Enter to keep " + employee.getDepartment() + "): ");
        String newDepartment = scanner.nextLine().trim();
        System.out.print("New salary (press Enter to keep " + employee.getSalary() + "): ");
        String salaryInput = scanner.nextLine().trim();

        double newSalary = employee.getSalary();
        if (!salaryInput.isEmpty()) {
            try {
                newSalary = Double.parseDouble(salaryInput);
                if (!Double.isFinite(newSalary) || newSalary < 0) {
                    System.out.println("Salary must be a non-negative number. No changes were made.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid salary. No changes were made.");
                return;
            }
        }

        employee.update(
                newId.isEmpty() ? employee.getId() : newId,
                newName.isEmpty() ? employee.getName() : newName,
                newDepartment.isEmpty() ? employee.getDepartment() : newDepartment,
                newSalary);
        System.out.println("Employee updated successfully.");
    }

    private static void deleteEmployee() {
        System.out.print("Enter the ID of the employee to delete: ");
        String id = scanner.nextLine().trim();
        Employee employee = findEmployeeById(id);

        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
            return;
        }

        employees.remove(employee);
        System.out.println("Employee deleted successfully.");
    }

    private static Double readSalary(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        try {
            double salary = Double.parseDouble(input);
            if (!Double.isFinite(salary) || salary < 0) {
                System.out.println("Salary must be a non-negative number.");
                return null;
            }
            return salary;
        } catch (NumberFormatException e) {
            System.out.println("Invalid salary. Please enter a number.");
            return null;
        }
    }

    private static Employee findEmployeeById(String id) {
        for (Employee employee : employees) {
            if (employee.getId().equalsIgnoreCase(id)) {
                return employee;
            }
        }
        return null;
    }

    private static class Employee {
        private String id;
        private String name;
        private String department;
        private double salary;

        private Employee(String id, String name, String department, double salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        private String getId() {
            return id;
        }

        private String getName() {
            return name;
        }

        private String getDepartment() {
            return department;
        }

        private double getSalary() {
            return salary;
        }

        private void update(String id, String name, String department, double salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        @Override
        public String toString() {
            return String.format("ID: %s | Name: %s | Department: %s | Salary: %.2f",
                    id, name, department, salary);
        }
    }
}
