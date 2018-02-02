import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class Bank {

    public static List<Client> clientsList = new ArrayList<>();
    public static Queue<Agent> agentsList = new PriorityQueue<>();

    public static void main(String[] args) {
        //Utilities
        Random random_generator = new Random();
        Scanner reader = new Scanner(System.in);

        //completable future requeriments
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        //clients creation

        System.out.println();

        //for(int i=0; i< )

        for(int k = 0; k< random_generator.nextInt() + 20; ++k) {
            Supplier<String> dispatcher = new Dispatcher(clientsList.remove(0), agentsList.poll());
            CompletableFuture
                    .supplyAsync(dispatcher, executorService)
                    .thenAccept(result -> System.out.println(result));
        }
            executorService.shutdown();
    }
}
