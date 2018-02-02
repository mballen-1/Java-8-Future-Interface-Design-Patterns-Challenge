public class Agent {

    String name;
    int id;
    int agent_type; // 3 = Cashier , 2 = supervisor, 1 = Director

    public Agent(String name, int id, int agent_type) {
        this.name = name;
        this.id = id;
        this.agent_type = agent_type;
    }

}
