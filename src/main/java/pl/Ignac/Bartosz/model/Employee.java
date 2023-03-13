package pl.Ignac.Bartosz.model;

public class Employee {
    private String name;

    private String surname;

    private int idNumber;

    public Employee() {
    }

    public Employee(String name, String surname, int idNumber) {
        this.name = name;
        this.surname = surname;
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", idNumber=" + idNumber +
                '}';
    }
}
