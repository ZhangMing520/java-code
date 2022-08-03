package helloNative;
/**
 * @author zhangming
 * @version 2022-07-19 00:04:34
 * 在对象上进行操作的本地方法。怎么从本地方法访问对象域。
 */
class Employee {
    private String name;
    private double salary;

    static {
        System.loadLibrary("Employee");
    }

    public Employee() {
    }

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    // public void raiseSalary(double byPercent) {
    // salary *= 1 + byPercent / 100;
    // }

    public native void raiseSalary(double byPercent);

    public void print() {
        System.out.println("name: " + this.name + ", salary: " + this.salary);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

}