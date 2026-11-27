
import CalculatorApp.*;
import org.omg.CORBA.ORB;

class CalculatorImpl extends CalculatorPOA {

    private ORB orb;

    public void setORB(ORB orb) {
        this.orb = orb;
    }

    public int add(int a, int b) {
        System.out.println("Addition request received");
        return a + b;
    }
}
