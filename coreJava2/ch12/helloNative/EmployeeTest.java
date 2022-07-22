/**
 * @author zhangming
 * @date 2022-07-19 00:04:34
 * <p>
 * gcc -fPIC -I $JAVA_HOME/include -I $JAVA_HOME/include/linux -shared -o libEmployee.so Employee.c
 * javac EmployeeTest.java
 * java -Djava.library.path=. EmployeeTest
 */
public class EmployeeTest {

    public static void main(String[] args) {
        Employee[] staff = new Employee[3];

        staff[0] = new Employee("Harry Hacker", 35000);
        staff[1] = new Employee("Carl Cracker", 75000);
        staff[2] = new Employee("Tony Tester", 38000);

        for (Employee e : staff) {
            e.raiseSalary(5);
        }

        for (Employee e : staff) {
            e.print();
        }
    }

}