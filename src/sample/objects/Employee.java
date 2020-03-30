package sample.objects;

import javafx.beans.property.SimpleDoubleProperty;
import sample.interfaces.EmployeeInterface;

public class Employee extends Person implements EmployeeInterface {

    private SimpleDoubleProperty salary = new SimpleDoubleProperty();


    public Employee(){

    }

    public Employee(String fullName, String phone, String passport, Double salary){
        super(fullName, phone, passport);
        this.salary.set(salary);
    }

    @Override
    public Double getSalary() {
        return salary.get();
    }

    @Override
    public boolean setSalary(Double salary) {
        this.salary.set(salary);
        return false;
    }

    public SimpleDoubleProperty salaryProperty(){return salary;}

    public boolean salaryIsCorrect(String salary){
        try{
            Double n = Double.parseDouble(salary);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public String toString() {
        return getFullName() + " " + getPhone() + " " + getPassport() + " " + getSalary();
    }
}
