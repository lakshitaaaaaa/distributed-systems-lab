
import CalculatorApp.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;

public class Server {

    public static void main(String[] args) {

        try {
            // Initialize ORB
            ORB orb = ORB.init(args, null);

            // Get Root POA
            POA rootpoa = POAHelper.narrow(
                orb.resolve_initial_references("RootPOA"));

            rootpoa.the_POAManager().activate();

            // Create servant object
            CalculatorImpl obj = new CalculatorImpl();
            obj.setORB(orb);

            // Get object reference
            org.omg.CORBA.Object ref =
                rootpoa.servant_to_reference(obj);

            Calculator href = CalculatorHelper.narrow(ref);

            // Get Naming Service reference
            org.omg.CORBA.Object nsRef =
                orb.resolve_initial_references("NameService");

            NamingContextExt ncRef =
                NamingContextExtHelper.narrow(nsRef);

            // Register object
            NameComponent path[] =
                ncRef.to_name("Calculator");

            ncRef.rebind(path, href);

            System.out.println("CORBA Server is running...");

            // Wait for client requests
            orb.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
