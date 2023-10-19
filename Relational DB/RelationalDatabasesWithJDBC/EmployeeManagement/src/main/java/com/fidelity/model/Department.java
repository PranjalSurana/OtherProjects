package com.fidelity.model;

import java.util.List;
import java.util.Objects;

public class Department {
    private int id;
    private String name;
    private String location;
    private List<Employee> employees;

    public Department(int id, String name, String location) {
        super();
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public Department(int id, String name, String location, List<Employee> employees) {
        super();
        this.id = id;
        this.name = name;
        this.location = location;
        this.employees = employees;
    }

    /**** Generated by Eclipse ****/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employees, id, location, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Department other = (Department) obj;
        return Objects.equals(employees, other.employees) && id == other.id && Objects.equals(location, other.location)
                && Objects.equals(name, other.name);
    }


}