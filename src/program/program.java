
package program;

import java.util.Date;
import model.entities.Department;
import model.entities.Seller;

public class program {
    public static void main(String[] args) {
        Department obg = new Department(1,"Books");
        Seller seler = new Seller(21,"bob","bob@gmail.com",new Date(),3000.0,obg);
        System.out.println(seler);
    }
}
