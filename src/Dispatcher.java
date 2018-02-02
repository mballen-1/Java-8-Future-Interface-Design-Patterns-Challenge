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
    public String get(){

            System.out.println("Attending client...");
            Random random_generator = new Random();
            try{
                long range = (long)15 - (long)10 + 1;
                // compute a fraction of the range, 0 <= frac < range
                long fraction = (long)(range * random_generator.nextDouble());
                randomTime =  (int)(fraction + 10);
                System.out.println(("Attention will take: " + randomTime + "seconds!"));
                attend();
                Thread.sleep(random_generator.nextInt(randomTime));

            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            return "Client: " + this.currentClient.name + "was attended by " + this.currentAgent.name + "in: " + randomTime + "miliseconds";
        }


    }



}
