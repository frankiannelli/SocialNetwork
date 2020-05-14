package graph;

public class SocialNetwork extends Graph {
    public SocialNetwork(int n) {
        super(n);
    }

    @Override
    public void addEdge(int source, int target)
    {
        super.addEdge(source, target);
        super.addEdge(target, source);
    }

    @Override
    public void removeEdge(int source, int target)
    {
        super.removeEdge(source, target);
        super.removeEdge(target, source);
    }
}
