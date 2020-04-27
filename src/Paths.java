import java.util.*;

public class Paths
{
    int V;
    static int[] dist;
    int[] previous;
    static PriorityQueue<Graph.Vertex> pq;
    public static int source;


    public Paths(Graph graph, int startVertex)
    {
        V = graph.mVertexCount;
        dist = new int[V];
        previous = new int[V];
        source = startVertex;

        pq = new PriorityQueue<>(V, new compareVert());

        dist[startVertex] = 0;
        for (int i = 1; i < V; i++)
        {
            //set distance to infinity
            dist[i] = Integer.MAX_VALUE;
        }

        //adjacency list to priority queue
        for (int vert = 1; vert < graph.mGraph.size(); vert++)
        {

            LinkedList<Graph.Vertex> adj = graph.mGraph.get(vert);
            for (Iterator<Graph.Vertex> vertEnum = adj.iterator();
                 vertEnum.hasNext(); )
            {
                Graph.Vertex toVert = vertEnum.next();
                pq.add(toVert);
                System.out.println("added " + toVert.mVertId);
            }
            System.out.println();
        }

        while (!pq.isEmpty())
        {
            //System.out.println(que.peek().mDistance);
        }
    }

    private class compareVert implements Comparator<Graph.Vertex>
    {
        @Override
        public int compare(Graph.Vertex v1, Graph.Vertex v2)
        {
            if (v1.mDistance < v2.mDistance)
                return -1;
            else if (v1.mDistance > v2.mDistance)
                return 1;
            else
                return 0;

        }
    }

    public static int getNextVertex()
    {
        return pq.poll().mVertId;
    }

    public void applyRelaxation(int w)
    {
        int weight = 0;
        int distU = 0;
        int distZ = 0;
        //list all adjacent at w
        LinkedList<Graph.Vertex> adj = Graph.mGraph.get(Graph.mGraph.indexOf(w));
        for (Iterator<Graph.Vertex> vertEnum = adj.iterator(); vertEnum.hasNext(); )
        {
            weight = vertEnum.next().mDistance;
            distU = dist[w];
            distZ = dist[vertEnum.next().mVertId];

            if (distU + weight < distZ)
            {
                dist[vertEnum.next().mVertId] = distU + weight;
                pq.add(vertEnum.next());
            }
        }
    }

    public void printShortestPath(int endVertex)
    {
        System.out.println("The Shortest path to " + endVertex + ":");
        System.out.println(source + " to " + endVertex + " is " + Paths.dist[endVertex]);
    }

    public static void main(String[] args)
    {
        String grFile = args[0];
        Integer startVertex = Integer.parseInt(args[1]);
        Integer endVertex = Integer.parseInt(args[2]);
        // Create Graph Object
        Graph graph = new Graph(grFile);
        Paths paths = new Paths(graph, startVertex);
        // Go through the relaxation process taking closest vertex from PQ
        Integer w;
        while ((w = Paths.getNextVertex()) != null)
        {
            paths.applyRelaxation(w);
        }
        // Print the shortest path
        paths.printShortestPath(endVertex);
    }
}