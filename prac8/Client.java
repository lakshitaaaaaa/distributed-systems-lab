
import CalculatorApp.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class Client {

    public static void main(String[] args) {

        try {
            // Initialize ORB
            ORB orb = ORB.init(args, null);

            // Get Naming Service reference
            org.omg.CORBA.Object nsRef =
                orb.resolve_initial_references("NameService");

            NamingContextExt ncRef =
                NamingContextExtHelper.narrow(nsRef);

            // Find remote object
            Calculator obj =
                CalculatorHelper.narrow(
                    ncRef.resolve_str("Calculator"));

            // Invoke remote method
            int result = obj.add(10, 20);

            System.out.println("Addition Result: " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
