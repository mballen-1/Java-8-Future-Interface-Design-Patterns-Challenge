import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class Bank {

    public static List<Client> clientsList = new ArrayList<>();
    public static Queue<Agent> agentsList = new PriorityQueue<>();
    public static String names[] = {"Karlos", "Katherine", "Gabriela", "Pedro", "Juana", "Tito", "Fernanda", "William", "Monica", "Edilberto"};
    public static String last_names[] = {"Rojas", "Umaña", "Quesada", "Vivas", "Lule", "Sanchez", "Niño", "Garcia", "Alvarez", "Moreno"};
    public static String operations[] = {"Deposit,", "Withdrawal,", "SolveIssue,"};

    public static void main(String[] args) {
        //Utilities
        Random random_generator = new Random();
        Scanner reader = new Scanner(System.in);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        //Clients creation
        for(int i=0; i< 20; ++i )
            clientsList.add(new Client(names[random_generator.nextInt()*10] + last_names[random_generator.nextInt()*10], i,random_generator.nextFloat()* 1000 , operations[random_generator.nextInt()*3]+random_generator.nextInt()*2500,true ));

        //Agents creation
        for(int i=0; i< 20; ++i )
            agentsList.add(new Agent(names[random_generator.nextInt()*10] + last_names[random_generator.nextInt()*10], i ));


        for(int k = 0; k< random_generator.nextInt() + 20; ++k) {
            Supplier<String> dispatcher = new Dispatcher(clientsList.remove(0), agentsList.poll());
            CompletableFuture
                    .supplyAsync(dispatcher, executorService)
                    .thenAccept(result -> System.out.println(result));
        }
            executorService.shutdown();
    }
}
