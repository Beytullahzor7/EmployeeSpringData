package com.employee.entity;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
public class EmployeeController {

    @Autowired(required = false)
    IEmployee iEmployee;

    // http://localhost:8080/jpa/employee/insert
    @GetMapping("/jpa/employee/insert")
    @ResponseBody
    public String insertEmployee(){
        Employee employee = new Employee("Beytullah","Zor","IT",7500.0);
        iEmployee.save(employee);
        log.info(employee.toString());
        return "Result: " + employee;
    }

    //http://localhost:8080/jpa/employee/find/{id}
    @GetMapping("/jpa/employee/find/{id}")
    @ResponseBody
    public String findEmployee(@PathVariable(name = "id") Integer id){
        Optional<Employee> optional = iEmployee.findById(id);
        if(optional.isPresent()){
            Employee employee = optional.get();
            log.info(employee.toString());
            return "EmployeeId "+ id + employee;
        } else{
            return "Aradıgınız Id Bulunamamaktadır " + id;
        }
    }


    //http://localhost:8080/jpa/employee/update/1?name=ali&surname=aslan&department=IT&salary=8000
    @GetMapping("/jpa/employee/update/{id}")
    @ResponseBody
    public String updateEmployee(@PathVariable(name = "id") Integer id,
                                 @RequestParam(name = "name") String name,
                                 @RequestParam(name = "surname") String surname,
                                 @RequestParam(name = "department") String department,
                                 @RequestParam(name = "salary") Double salary){
        Optional<Employee> optional = iEmployee.findById(id);
        if(optional.isPresent()){
            Employee employee = optional.get();
            employee.setName(name);
            employee.setSurname(surname);
            employee.setDepartment(department);
            employee.setSalary(salary);
            iEmployee.save(employee);
            return "Updated " + employee;
        }else{
            return "Güncelleme Islemi Basarisiz " + id;
        }
    }

    //http://localhost:8080/jpa/employee/delete/1
    @GetMapping("/jpa/employee/delete/{id}")
    @ResponseBody
    public String deleteEmployee(@PathVariable(name = "id") Integer id){
        Optional<Employee> optional = iEmployee.findById(id);
        if(optional.isPresent()){
            Employee employee = optional.get();
            iEmployee.delete(employee);
            return "Calisan Silindi " + employee;
        }else {
            return "Aradıgınız id mevcut degil";
        }
    }

    //http://localhost:8080/jpa/employee/listall
    @GetMapping("/jpa/employee/listall")
    @ResponseBody
    public String selectList(){
        Iterable<Employee> iterableList = iEmployee.findAll();
        List<Employee> list = new ArrayList<>();

        for(Employee employee : iterableList){
            list.add(employee);
            log.info(employee.toString());
        }
        return "Degerler: " + list;
    }
}
