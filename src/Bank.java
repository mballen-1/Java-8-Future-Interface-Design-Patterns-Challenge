import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class Bank {

    public static List<Client> clientsList = new ArrayList<>();
    public static Queue<Agent> agentsList = new PriorityQueue<>();

    Queue<Agent> agentPriorityQueue = new PriorityQueue<>(1000, new Comparator<Agent>() {
        @Override
        public int compare( Agent e1, Agent e2) {
            int result = new Double(e1.agent_type).compareTo( new Double( e2.getCGPA()));
            if (result !=  0) {
                //System.out.println("result by cgpa: " + result);
                //System.out.println(e1.getName() + "vs" + e2.getName());
                return -result;
            } else {

                int new_res = e1.getName().compareTo( e2.getName());

                if(new_res != 0){
                    //System.out.println("result by name: " + new_res);
                    //System.out.println(e1.getName() + "vs" + e2.getName());
                    return new_res;
                }
                else{

                    int last = new Integer( e2.getID()).compareTo( new Integer(e1.getID()));
                    //System.out.println("result by id: " + last);
                    //System.out.println(e1.getName() +" " + e1.getID() + " vs " + e2.getName() +" " + e2.getID() );
                    return -last;
                }
            }
        }
    });





    public static String names[] = {"Karlos", "Katherine", "Gabriela", "Pedro", "Juana", "Tito", "Fernanda", "William", "Monica", "Edilberto"};
    public static String last_names[] = {"Rojas", "Umaña", "Quesada", "Vivas", "Lule", "Sanchez", "Niño", "Garcia", "Alvarez", "Moreno"};
    public static String operations[] = {"Deposit,", "Withdrawal,", "SolveIssue,"};

    public static void main(String[] args) {
        //Utilities
        Random random_generator = new Random();
        Scanner reader = new Scanner(System.in);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        //Clients creation
        for(int i=0; i< 30; ++i )
            clientsList.add(new Client(names[random_generator.nextInt()*10] + last_names[random_generator.nextInt()*10], i,random_generator.nextFloat()* 1000 , operations[random_generator.nextInt()*3]+random_generator.nextInt()*2500,true ));

        //Agents creation
        for(int i=0; i< 10; ++i )
            agentsList.add(new Agent(names[random_generator.nextInt()*10] + last_names[random_generator.nextInt()*10], i ,random_generator.nextInt()*3));


        for(int k = 0; k< random_generator.nextInt() + 20; ++k) {
            Supplier<String> dispatcher = new Dispatcher(clientsList.remove(0), agentsList.poll());
            CompletableFuture
                    .supplyAsync(dispatcher, executorService)
                    .thenAccept(result -> System.out.println(result));
        }
            executorService.shutdown();
    }
}
