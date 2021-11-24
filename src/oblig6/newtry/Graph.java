package oblig6.newtry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Graph {

    int N, E;
    private LinkedList<Integer>[] nodes;

    public Graph(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
        newGraph(br);
    }
    public void newGraph(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        System.out.println(N);
        //node = new Node[N];
        nodes = new LinkedList[N];
        for (int i = 0; i < N; i++) {
            //node[i] = new Node();
            //node[i].nodeNumber = i;
            nodes[i] = new LinkedList<>();

        }
        E = Integer.parseInt(st.nextToken());
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            //Edge e = new Edge(node[from], node[to]);
            //node[from].edges.add(e);
            addEdge(from, to);

        }
        // System.out.println(node[5].nodeNumber);
    }

    public void addEdge(int s, int d) {
        nodes[s].add(d);
    }
}
