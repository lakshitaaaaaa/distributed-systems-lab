
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {

    public static void main(String[] args) {
        try {
            CalculatorImpl obj = new CalculatorImpl();

            // Start RMI Registry
            LocateRegistry.createRegistry(1099);

            // Register remote object
            Naming.rebind("rmi://localhost/Calculator", obj);

            System.out.println("RMI Server is running...");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
