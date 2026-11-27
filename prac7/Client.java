
import java.rmi.Naming;

public class Client {

    public static void main(String[] args) {
        try {
            Calculator obj = (Calculator)
                Naming.lookup("rmi://localhost/Calculator");

            int sum = obj.add(10, 20);
            int difference = obj.subtract(20, 10);

            System.out.println("Addition: " + sum);
            System.out.println("Subtraction: " + difference);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
