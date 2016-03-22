package com.company;

import com.company.Employee;

/**
 * Created by taihuynh on 22/3/16.
 */
public interface IEmployeeDAO {
    public void findAllEmployees();
    public void insertEmployee(Employee e);
}
