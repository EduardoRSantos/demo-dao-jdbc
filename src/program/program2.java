
package program;

import java.util.List;
import java.util.Scanner;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;


public class program2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DepartmentDao departDao = DaoFactory.createDepartmentDao();
        
        System.out.println("\n=== TEST 1: department insert ===");
        Department dp = new Department(null,"D2");
        departDao.insert(dp);    
        System.out.println("Insert completed = " + dp.getId() + ", "+ dp.getName());
        
        System.out.println("\n=== TEST 2: department findById ===");
        Department dep = departDao.findById(1);
        System.out.println(dep);
        
        System.out.println("\n=== TEST 3: department update ===");
        Department dep1 = departDao.findById(5);
        dep.setName("D1");
        departDao.update(dep1);
        System.out.println("Update completed!");
        
        System.out.println("\n=== TEST 4: department delete ===");
        System.out.print("Insert o number for delete the department: ");
        int depId = sc.nextInt();
        departDao.deleteById(depId);
        System.out.println("Delete completed!");
        
        System.out.println("\n=== TEST 5: department findAll ===");
        List<Department> dep3 = departDao.findAll();
        dep3.forEach(System.out::println);

        sc.close();
    }
}
