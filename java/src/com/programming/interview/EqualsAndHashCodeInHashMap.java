package com.programming.interview;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EqualsAndHashCodeInHashMap {

    public static void main(String[] args) {
        Map<Employee, Employee> employees = new HashMap<>();

        Employee emp1 = new Employee(1, "Employee1");
        employees.put(emp1, emp1);

        Employee emp2 = new Employee(2, "Employee2");
        Employee emp3 = new Employee(3, "Employee3");
        System.out.println(employees.get(emp2));
        System.out.println(employees.get(emp3));
    }
}

class Employee {
    private int id;
    private String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name=" + name + "}";
    }
}