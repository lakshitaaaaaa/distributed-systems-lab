
import java.util.*;

class Process {
    int id;
    int timestamp = 0;
    int replies = 0;
    boolean requesting = false;

    List<Integer> deferred = new ArrayList<>();

    Process(int id) {
        this.id = id;
    }

    void requestCS(Process[] processes) {
        requesting = true;
        timestamp++;

        int requestTime = timestamp;
        replies = 0;

        System.out.println("\nProcess " + id +
                           " requests Critical Section");

        for (Process p : processes) {
            if (p.id != id) {
                System.out.println("REQUEST sent from P" +
                                   id + " to P" + p.id);

                p.receiveRequest(id, requestTime);
            }
        }

        for (Process p : processes) {
            if (p.id != id) {
                if (p.deferred.contains(id)) {
                    continue;
                }
                replies++;
            }
        }

        if (replies == processes.length - 1) {
            enterCS(processes);
        }
    }

    void receiveRequest(int sender, int time) {
        boolean defer;

        if (!requesting) {
            defer = false;
        } else {
            // Compare (timestamp, process ID)
            defer = timestamp < time ||
                    (timestamp == time && id < sender);
        }

        if (defer) {
            deferred.add(sender);
            System.out.println("P" + id +
                               " defers REPLY to P" + sender);
        } else {
            System.out.println("REPLY sent from P" + id +
                               " to P" + sender);
        }
    }

    void enterCS(Process[] processes) {
        System.out.println("\nProcess " + id +
                           " ENTERS Critical Section");

        System.out.println("Process " + id +
                           " EXECUTES Critical Section");

        System.out.println("Process " + id +
                           " EXITS Critical Section");

        requesting = false;

        for (int p : deferred) {
            System.out.println("Deferred REPLY sent from P" +
                               id + " to P" + p);
        }

        deferred.clear();
    }
}

public class DistributedMutex {
    public static void main(String[] args) {

        int n = 3;

        Process[] processes = new Process[n];

        for (int i = 0; i < n; i++) {
            processes[i] = new Process(i);
        }

        processes[0].requestCS(processes);
        processes[1].requestCS(processes);
        processes[2].requestCS(processes);
    }
}
