import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class Dispatcher implements Supplier<String> {

    Client currentClient;
    Agent currentAgent;
    public static int randomTime;
    public static long startTime = 0, endTime = 0 ,duration = 0;

    public Dispatcher(Client currentClient, Agent currentAgent) {
        this.currentClient = currentClient;
        this.currentAgent = currentAgent;
    }

    public boolean deposit(){
        if (this.currentClient.isActive == true) {
            this.currentClient.balance += Integer.parseInt(currentClient.issue.substring(8, currentClient.issue.length() - 1));
            return true;
        }
        return false;
    }

    public boolean withdraw(){
        int quantity = Integer.parseInt(currentClient.issue.substring(11, currentClient.issue.length() - 1));
        if( quantity <= currentClient.balance + 10 ) {
            System.out.println(currentClient.name +" Withdraws: " + quantity + " OK.");
            currentClient.balance -= quantity;
            return true;
        }
        else
            System.out.println("Withdraw by" + quantity );
            System.out.println(currentClient.name +" balance is: " + currentClient.balance + " Not founds available!");
            return false;
    }

    public boolean solveIssue(){
        System.out.println(currentClient.issue + "...Is being solved!");
        return true;
    }


    public boolean attend(){
        char firstCharOfIssue = currentClient.issue.charAt(0);
        if(firstCharOfIssue == ('D')|| firstCharOfIssue == 'd') {
            return withdraw();
        }
        else if
                (firstCharOfIssue == 'W' || firstCharOfIssue == 'w'){
                    return deposit();
                }
        else
            return solveIssue();
    }
    public void prepareTimeConstants(){
        System.out.println("ATTENDING CLIENT..." + currentClient.name);
        Random random_generator = new Random();
        long fraction = (long) (5* random_generator.nextDouble());
        randomTime = (int) (fraction + 10);
        System.out.println((Thread.currentThread() + " will sleep: " + randomTime+ " seconds after attention!"));
        startTime = System.currentTimeMillis();
    }

    @Override
    public String get() {

        try {
            prepareTimeConstants();
            attend();
            Thread.sleep(1000*(randomTime));
            endTime = System.currentTimeMillis();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        duration = endTime - startTime;
        System.out.println("CLIENT " + currentClient.name + " SERVED SUCCESFULLY!");
        return "Client: " + this.currentClient.name + " was attended by " + this.currentAgent.name + "(" + currentAgent.agent_type+ ")"+ "TOTAL EXECUTION TIME: " + duration/1000 + "seconds";

    }
}




