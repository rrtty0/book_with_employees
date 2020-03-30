package sample.interfaces;

import sample.objects.Employee;

public interface ListOfEmplyees {

    //добавление записи
    void add(Employee employee);

    //редактирование записи
    void delete(Employee employee);
}
