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
            currentClient.balance -= quantity;
            return true;
        }
        else
            System.out.println("Not founds available!");
            return false;
    }

    public boolean solveIssue(){
        System.out.println(currentClient.issue + "Is being solved!");
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
    @Override
    public String get() {
        System.out.println("Attending client...");
        long startTime = 0;
        long endTime = 0;

        Random random_generator = new Random();
        try {
            long range = 5;
            long fraction = (long) (range * random_generator.nextDouble());
            randomTime = (int) (fraction + 10);
            System.out.println(("Attention will take: " + randomTime + "seconds!"));
            startTime = System.nanoTime();
            attend();
            Thread.sleep(random_generator.nextInt(randomTime));
            endTime = System.nanoTime();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long duration = endTime - startTime;
        return "Client: " + this.currentClient.name + "was attended by " + this.currentAgent.name + "in: " + duration + "miliseconds";

    }
}




