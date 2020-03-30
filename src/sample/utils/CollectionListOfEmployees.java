package sample.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.interfaces.ListOfEmplyees;
import sample.objects.Employee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;


public class CollectionListOfEmployees implements ListOfEmplyees {

    private ObservableList<Employee> employeeList = FXCollections.observableArrayList();
    private static final String PATH_TO_FILE = "./src/sample/resources/IOfile.txt";


    @Override
    public void add(Employee employee) {
        employeeList.add(employee);
    }

    @Override
    public void delete(Employee employee) {
        employeeList.remove(employee);
    }

    public ObservableList<Employee> getEmployeeList() {
        return employeeList;
    }

    public void writeToFile()
    {
        try {
            File file = new File(PATH_TO_FILE);

            if(!file.exists())
                file.createNewFile();

            PrintWriter pw = new PrintWriter(file);
            for(Employee employee : employeeList)
            {
                pw.println(employee.getFullName());
                pw.println(employee.getPhone());
                pw.println(employee.getPassport());
                pw.println(employee.getSalary());
                pw.println();
            }

            pw.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void readFromFile(){
        try{
            File file = new File(PATH_TO_FILE);
            int i = 0;
            if(!file.exists())
                file.createNewFile();

            BufferedReader br = new BufferedReader(new FileReader(file));
            String currentLine;
            while ((currentLine = br.readLine()) != null){
                employeeList.add(new Employee(currentLine, br.readLine(), br.readLine(), Double.parseDouble(br.readLine())));
                br.readLine();
                ++i;
            }
            br.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }



    }
}
