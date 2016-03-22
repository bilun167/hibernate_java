package com.company;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeDaoImpl implements IEmployeeDAO {
    SessionFactory sessionFactory1 = new Configuration().configure("mysqlconfig1.cfg.xml").buildSessionFactory();
    SessionFactory sessionFactory2 = new Configuration().configure("mysqlconfig1.cfg.xml").buildSessionFactory();
    Session session = null;
    Transaction transaction = null;

    public void findAllEmployees() {
        ArrayList<Employee> empList = new ArrayList<>();
        try {
            session = sessionFactory1.openSession();
            transaction = session.beginTransaction();
            transaction.begin();
            Criteria crit = session.createCriteria(Employee.class);
            empList = (ArrayList<Employee>) crit.list();
            System.out.println("Records from Session1");
            for (Employee emp : empList) {
                System.out.println(emp.getEmpid() + " " + emp.getEmpname() + " " + emp.getSalary());

            }
            session.close();
            session = sessionFactory2.openSession();
            Criteria crit1 = session.createCriteria(Employee.class);
            empList = (ArrayList) crit1.list();
            System.out.println("Records from Session2");
            for (Employee emp : empList) {
                System.out.println(emp.getEmpid() + " " + emp.getEmpname() + " " + emp.getSalary());
            }
            session.close();
        } catch (Exception he) {
            he.printStackTrace();
        }
    }

    public void insertEmployee(Employee e) {
        try {
            session = sessionFactory1.openSession();
            transaction = session.beginTransaction();
            transaction.begin();
            session.save(e);
            transaction.commit();
            session.close();
            session = sessionFactory2.openSession();
            transaction = session.beginTransaction();
            transaction.begin();
            session.save(e);
            transaction.commit();
            session.close();
        } catch (HibernateException he) {
            he.printStackTrace();
        }

    }

    public static void main(String[] args) {
        int choice;
        EmployeeDaoImpl empOperations = new EmployeeDaoImpl();
        Employee e1 = new Employee();
        do {
            System.out.println("1. Insert ");
            System.out.println("2. List ");
            System.out.println("3. Exit ");
            System.out.println("Enter your choice ");
            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter the employee Number ");
                    Scanner sc1 = new Scanner(System.in);
                    int empid = sc1.nextInt();
                    System.out.println("Enter the employee Name ");
                    Scanner sc2 = new Scanner(System.in);
                    String empname = sc2.nextLine();
                    System.out.println("Enter the Salary ");
                    Scanner sc3 = new Scanner(System.in);
                    double empsal = sc3.nextDouble();
                    e1.setEmpid(empid);
                    e1.setEmpname(empname);
                    e1.setSalary(empsal);
                    empOperations.insertEmployee(e1);
                    break;
                case 2:
                    empOperations.findAllEmployees();
                    break;
            }
        } while (choice != 3);

    }
}
