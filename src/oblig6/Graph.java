package oblig6;

import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Graph {
    int N, E;
    Node [] node;
    LinkedList<Node>[] adj;

    public Graph(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
        newGraph(br);
    }
    public void newGraph(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        System.out.println(N);
        //node = new Node[N];
        adj = new LinkedList[N];
        for (int i = 0; i < N; i++) {
            //node[i] = new Node();
            //node[i].nodeNumber = i;
            adj[i] = new LinkedList<>();
            adj[i].add(new Node());
            adj[i].getFirst().nodeNumber = i;
        }
        E = Integer.parseInt(st.nextToken());
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            //Edge e = new Edge(node[from], node[to]);
            //node[from].edges.add(e);

        }
        System.out.println(node[5].nodeNumber);
    }

    public void addEdge(int v, int w) {

    }



    public String toString(){
        String string = "";
        System.out.println(node.length);
        for (int i = 0; i < node.length; i++) {
            string += "\nNode: " + i + ": ";
            for (int j = 0; j < node[i].edges.size(); j++) {
                string += node[i].edges.get(j).to.nodeNumber + " ";
            }
        }
        return string;
    }
}
