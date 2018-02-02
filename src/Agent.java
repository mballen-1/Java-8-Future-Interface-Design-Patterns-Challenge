public class Agent {

    String name;
    int id;

    public Agent(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public void solveIssue(String issue){
        System.out.println("Im solving a client issue!");
    }

    public void attendClient(Client client, String procedure, int quantity) {
        if(procedure.equals("Withdrawal"))
            if (client.balance >= quantity)
                client.balance -= quantity;
        else if(procedure.equals("Deposit"))
            client.balance += quantity;
        else
            solveIssue(client.issue);
    }
}
