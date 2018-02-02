import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class Bank {

    public static List<Client> clientsList = new ArrayList<>();
    public static Queue<Agent> agentPriorityQueue = new PriorityQueue<>(1000, new Comparator<Agent>() {
        @Override
        public int compare( Agent e1, Agent e2) {
            return new Integer(e1.agent_type).compareTo( e2.agent_type);
        }
    });

    public static String names[] = {"Karlos", "Katherine", "Gabriela", "Pedro", "Juana", "Tito", "Fernanda", "William", "Monica", "Edilberto"};
    public static String last_names[] = {"Rojas", "Umaña", "Quesada", "Vivas", "Lule", "Sanchez", "Niño", "Garcia", "Alvarez", "Moreno"};
    public static String operations[] = {"Deposit,", "Withdrawal,", "SolveIssue,"};

    public static void main(String[] args) {
        //Utilities
        Random random_generator = new Random();
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        //Clients creation
        for(int i=0; i< 30; ++i )
            clientsList.add(new Client(names[random_generator.nextInt()*10] + last_names[random_generator.nextInt()*10], i,random_generator.nextFloat()* 1000 , operations[random_generator.nextInt()*3]+random_generator.nextInt()*2500,true ));

        //Agents creation
        for(int i=0; i< 10; ++i ){
            agentPriorityQueue.add(new Agent(names[random_generator.nextInt()*10] + last_names[random_generator.nextInt()*10], i ,random_generator.nextInt()*3));
        }


        for(int k = 0; k< random_generator.nextInt() + 20; ++k) {
            Agent current_agent = agentPriorityQueue.poll();
            Supplier<String> dispatcher = new Dispatcher(clientsList.remove(0), current_agent );
            agentPriorityQueue.add(current_agent);
            
            CompletableFuture
                    .supplyAsync(dispatcher, executorService)
                    .thenAccept(result -> System.out.println(result));
        }
            executorService.shutdown();
    }
}
