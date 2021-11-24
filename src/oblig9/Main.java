package oblig9;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {


        String nodePath = "src/oblig9/Files/Oppg/noder.txt"; // noder fil
        String edgePath = "src/oblig9/Files/Oppg/kanter.txt"; // kanter
        String intressepktPath = "src/oblig9/Files/Oppg/interessepkt.txt";


        int fra = 6368906; // Kårvåg
        int fra2 = 136963; // Tampere


        int til = 6789998; //Gjemnes
        int til2 = 6861306; // Trondheim

        ALT alt = new ALT(nodePath, edgePath, intressepktPath);

        // preproseserer data og skriver til fil.
        // tar lang tid og treger bare å kjøre 1 gang
        /*
        alt.preprocess(
                "src/oblig/Files/outfiles/from_node_to_landmarks.txt",
                "src/oblig/Files/outfiles/from_landmark_to_node.txt",
                5263302,2313313, 708400, 5486883
                );
         */

        // Kjører ALT algoritme

        // leser av preprosecced data for landemerkene
        alt.readNodeLandmarkData(
                "src/oblig9/Files/outfiles/from_landmark_to_node.txt", // landemerker -> noder
                "src/oblig9/Files/outfiles/from_node_to_landmarks.txt"); // noder -> landemerker

        /*alt.altSearch(fra2, til2);
        List<String> vei = alt.getPath(alt.getNodeFromList(til2));
        for (int i = 0; i < vei.size(); i++) {
            System.out.println(vei.get(i));
        }*/
        // printer svar for ALT
        System.out.println("============ ALT ================\n");
        printAnswerALT(fra, til, alt);
        System.out.println();
        printAnswerALT(fra2,til2, alt);


        //Kjører så Djikstraalgoritme
        System.out.println("\n========== Dijkstra ==============");

        Djikstra d = new Djikstra(nodePath, edgePath, intressepktPath);

        System.out.println();

        printAnwserDjikstra(fra, til, d);
        System.out.println();

        printAnwserDjikstra(fra2, til2, d);


        System.out.println("\n========== Koordinater for de 10 bensinstasjonene nærmest Værnes Lufthavn ==============");

        List<Node> bensinstasjoner = d.xNearestGasStations(6590451, 10, 2); // Fi

        for (int i = 0; i < bensinstasjoner.size(); i++) {
            System.out.println(bensinstasjoner.get(i).writeCoordinates());
        }

        System.out.println("\n ========== Koordinater for de 10 ladestasjonene nærmest Røros ==========");

        List<Node> ladestasjoner = d.xNearestGasStations(1419364, 10, 4);

        for (int i = 0; i < ladestasjoner.size(); i++) {
            System.out.println(ladestasjoner.get(i).writeCoordinates());
        }
    }


    // printer ut svar for ALT på riktig format
    static void printAnswerALT(int start, int end, ALT alt) {
        Interessepkt startIntresse = alt.getInteressepkt(start);
        Interessepkt endInteresse = alt.getInteressepkt(end);

        int tid = alt.altSearch(start, end);
        String time = omregnTilTimer(tid);

        System.out.println("tur: " + startIntresse.getName() + "-" + endInteresse.getName()
                + ", startnode " + start + ", endnode " + end + ", Antall besøkte: " + alt.getAmountVisited() + ", " + time);

        // printer ut koordinatene til ruten
        /*
        System.out.println("Rutens koordinater: \n");
        List<String> koordinater = alt.getPath(alt.getNodeFromList(end));
        for (int i = 0; i < koordinater.size(); i++) {
            System.out.println(koordinater.get(i));
        }
        */
    }

    /**
     * Printer ut start navn og slutt navn,
     * startnode go sluttnode,
     * antall besøkte noder
     * og tiden fra startnode til sluttnode
     *
     * Antall besøkte = antall noder plukket ut av PriorityQueue
     *
     */
    static void printAnwserDjikstra(int start, int end, Djikstra d){
        Interessepkt startInteresse = d.getInteressepkt(start);
        Interessepkt endInteresse = d.getInteressepkt(end);

        d.findShortestDistance(d.getNodeFromList(start), d.getNodeFromList(end));
        int tid = d.getNodeFromList(end).getDistance();
        String time = omregnTilTimer(tid);
        System.out.println("tur: " + startInteresse.getName() + "-" + endInteresse.getName()
                + ", startnode " + start + ", endnode " + end + ", Antall besøkte: " + d.getNodesVisited() + ", " + time);

    }

    public static int omregnTilHundredel(int timer, int minutter, int sekunder) {
        int svar = 0;
        svar += sekunder * 100;
        svar += minutter * 60 * 100;
        svar += timer * 60 * 100 * 60;
        return svar;
    }

    // metode som tar inn hundredels sekunder og gjør
    // om til timer, minutter og sekunder
    public static String omregnTilTimer(long hundredeler) {
        int timer = (int) hundredeler / (100 * 60 * 60);
        int restTimer = (int) hundredeler % (100 * 60 * 60);

        int minutter = restTimer / (60 * 100);
        int restMinutter = restTimer % (60 * 100);

        int sekunder = restMinutter / 100;
        return "Timer: " + timer + ". Minutter: " + minutter + ". Sekunder: " + sekunder;
    }
}

class Djikstra  {

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private ArrayList<Interessepkt> interessepkts;
    private int nodesVisited;

    public Djikstra(String nodeFile, String edgdeFile, String interessepktPath) throws IOException {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        interessepkts = new ArrayList<>();
        this.nodesVisited = 0;

        readFile(nodeFile, edgdeFile, interessepktPath);
        addNeigbours();

        System.out.println("size of nodes: " + nodes.size());
        System.out.println("size of edges: " + edges.size());
        System.out.println("size of interessepkts: " + interessepkts.size());

    }

    public void readFile(String nodeFile, String edgeFile, String intressepktPath) throws IOException {
        // Leser noder
        BufferedReader brNodes = new BufferedReader(new FileReader(nodeFile));
        StringTokenizer stNodes = new StringTokenizer(brNodes.readLine());
        int size = Integer.parseInt(stNodes.nextToken());
        //Da breddegrader ikke er viktig for Djikstra trenger vi ikke lese
        // mer fra filen enn antall noder.
        for (int i = 0; i < size; i++) {
            stNodes = new StringTokenizer(brNodes.readLine());
            int number = Integer.parseInt(stNodes.nextToken());
            double lo = Double.parseDouble(stNodes.nextToken());
            double la = Double.parseDouble(stNodes.nextToken());
            nodes.add(new Node(number, lo, la));
        }

        // Leser kanter
        BufferedReader brEdges = new BufferedReader(new FileReader(edgeFile));
        StringTokenizer stEdges = new StringTokenizer(brEdges.readLine());
        size = Integer.parseInt(stEdges.nextToken());
        for (int i = 0; i < size; i++) {
            stEdges = new StringTokenizer(brEdges.readLine());
            Node from = nodes.get(Integer.parseInt(stEdges.nextToken()));
            Node to = nodes.get(Integer.parseInt(stEdges.nextToken()));
            int weight = Integer.parseInt(stEdges.nextToken());
            edges.add(new Edge(from, to, weight));
        }

        BufferedReader brInteressepkt = new BufferedReader(new FileReader(intressepktPath));
        StringTokenizer stInteressepkt = new StringTokenizer(brInteressepkt.readLine());
        int intressepktSize = Integer.parseInt(stInteressepkt.nextToken());

        for (int i = 0; i < intressepktSize; i++) {
            stInteressepkt = new StringTokenizer(brInteressepkt.readLine());
            int nodeNumber = Integer.parseInt(stInteressepkt.nextToken());
            int type = Integer.parseInt(stInteressepkt.nextToken());
            nodes.get(nodeNumber).setType(type); //S

            String name = stInteressepkt.nextToken(); // funker ikke hvis navn har mellomrom

            interessepkts.add(new Interessepkt(nodeNumber, type, name));
        }

    }

    public int getNodesVisited() {
        return nodesVisited;
    }

    /**
     * legger til alle nabo noder til nodene
     */
    public void addNeigbours() {
        for (Edge e: edges) {
            // Fra noden til en enge
            Node n = e.getFrom();
            // Legger til edge i vedsiden av listen til node
            // NB: alle noder betår av en vedsiden av liste (adj_list)
            n.addNeigbour(e);

        }
    }

    // for å hente ut spesifik node fra en index
    public Node getNodeFromList(int index) {
        return nodes.get(index);
    }

    /**
     * Forbedring
     * Stopper når man kommer til sluttnode
     * @param start
     */
    public void findShortestDistance(Node start, Node end) {
        reset();
        start.setDistance(0); // start have cost 0

        PriorityQueue<Node> queue = new PriorityQueue<>();

        queue.add(start);
        start.setVisisted(true);
        while (!queue.isEmpty()) {

            Node current = queue.poll();
            nodesVisited++;
            //Hvis endenoden tas ut av køen returnerer metoden
            if (current == end){
                // System.out.println("Antall noder besøkt: " + nodesVisited);
                return;
            }
            for (Edge e: current.getAdjList()) {

                Node n = e.getTo();

                if (!n.isVisisted()) {
                    int dist = current.getDistance() + e.getWeight();

                    if (dist < n.getDistance()) {
                        // remove from queue
                        queue.remove(n);
                        // update values
                        n.setDistance(dist);
                        n.setPredeseccor(current);
                        // add to queue again with new values
                        queue.add(n);
                    }
                }
            }
            current.setVisisted(true);
        }
    }

    //Går fra endenoden til startnoden for å finne veien som ble gått.
    public List<Node> getShortestPath(Node target) {
        List<Node> reversedPath = new ArrayList<>();
        //Bactracer fra end node til startnode
        for (Node n = target; n != null; n = n.getPredeseccor() ) {
            reversedPath.add(n); // legger til i reversert rekkefølge
        }
        //Reverserer den reverserte veien for å få den riktig retning.
        List<Node> path = new ArrayList<>();
        for (int i = reversedPath.size() - 1; i >= 0 ; i--) {
            path.add(reversedPath.get(i));
        }

        Collections.reverse(reversedPath); // snur om listen

        return reversedPath;
    }

    private void reset(){
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).setDistance(Integer.MAX_VALUE / 2);
            nodes.get(i).setVisisted(false);
        }
    }
    public List<Node> xNearestGasStations(int startNode, int amount, int type) throws IOException {
        reset();
        int visited = 0;
        Node start = getNodeFromList(startNode);
        start.setDistance(0); // start have cost 0

        PriorityQueue<Node> queue = new PriorityQueue<>();
        List<Node> interesser = new ArrayList<>();
        // System.out.println("Størrelse interessepkt: " + interessepkts.size());
        queue.add(start);
        start.setVisisted(true);
        int nodesVisited = 0;
        while (!queue.isEmpty()) {

            Node current = queue.poll();
            nodesVisited++;


            for (Edge e: current.getAdjList()) {

                Node n = e.getTo();

                if (!n.isVisisted()) {
                    int dist = current.getDistance() + e.getWeight();

                    if (dist < n.getDistance()) {
                        // remove from queue
                        queue.remove(n);
                        // update values
                        n.setDistance(dist);
                        n.setPredeseccor(current);
                        // add to queue again with new values
                        queue.add(n);
                    }
                }

            }
            if (current.getType() == type){
                visited++;
                interesser.add(current);
                if (visited == amount ) {
                    return interesser;
                }
            }
            current.setVisisted(true);
        }
        return interesser;
    }

    public Interessepkt getInteressepkt (int number) {
        for (int i = 0; i < interessepkts.size() ; i++) {
            if (interessepkts.get(i).getNodeNumber() == number) {
                return interessepkts.get(i);
            }
        }
        return null;
    }
}



class ALT {
    public ArrayList<Node> nodes;
    public ArrayList<Edge> edges;
    public ArrayList<Interessepkt> interessepkts;


    Filehandler filehandler;
    Preproseccor preproseccor;

    public ArrayList<int[]> toLandmarks = new ArrayList<>();
    public ArrayList<int[]> fromLandmarks = new ArrayList<>();

    public ArrayList<int[]> readtoLandmarks = new ArrayList<>();
    public ArrayList<int[]> readFromLandmarks = new ArrayList<>();

    private final PriorityQueue<Node> priorityQueue;

    private int amountVisited;

    public ALT(String nodeFile, String edgeFile, String inpktFile) throws IOException {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        interessepkts = new ArrayList<>();

        // Leser av filene til oppgaven
        filehandler = new Filehandler(nodes, edges, interessepkts);

        filehandler.readNodes(nodeFile);
        filehandler.readEdges(edgeFile);
        filehandler.readIntrestPoints(inpktFile);

        // legger til nabo nodene til hver node
        addNeigbours();
        addOppoNeigbours();

        System.out.println("Antall noder: " + nodes.size());
        System.out.println("Antall kanter: " + edges.size());

        System.out.println(nodes.size());
        priorityQueue = new PriorityQueue<>(nodes.size(), new DistanceComprator());
    }

    public void readNodeLandmarkData(String readFromLandmarkPath, String readToLandmarkFile) throws IOException {

        filehandler.readFromLandmarks(readFromLandmarkPath, readFromLandmarks);
        filehandler.readToLandmarks(readToLandmarkFile, readtoLandmarks);

    }

    /**
     * legger til alle nabo noder til nodene
     */
    private void addNeigbours() {
        for (Edge e : edges) {
            // Fra noden til en enge
            Node n = e.getFrom();
            // Legger til edge i vedsiden av listen til node
            // NB: alle noder betår av en vedsiden av liste (adj_list)
            n.addNeigbour(e);

        }
    }

    /**
     * Legger naboer til node
     * når man skal bruke reverse graph
     *
     * @return
     */
    private void addOppoNeigbours() {
        for (Edge e : edges) {
            Node n = e.getTo();
            n.addOppoNeigbour(e);
        }
    }

    // for å hente ut spesifik node fra en index i nodes liste
    public Node getNodeFromList(int index) {
        return nodes.get(index);
    }

    public int getAmountVisited() {
        return amountVisited;
    }

    public void preprocess(String fromNodeToLandmarkFile, String toNodeFromLandmarkFile, int n, int s, int e, int w) throws IOException {
        preproseccor = new Preproseccor(nodes, toLandmarks, fromLandmarks, fromNodeToLandmarkFile, toNodeFromLandmarkFile);
        preproseccor.generateFromNodeToLandmarkFile(n,s,e,w);
        preproseccor.generateToNodeFromLandmarkFile(n,s,e,w);
    }


    /**
     * trenger å resette data for nodene hver gang
     * hvis man kjører skal kjøre korteste vei metoden
     * flere ganger
     */
    private void reset() {
        for (Node node : nodes) {
            node.setDistance(Integer.MAX_VALUE);
            node.setVisisted(false);
        }
    }

    //
    public int estimateLandmark(int from, int to, int landmark) {
        int landmarkToCurrent = readFromLandmarks.get(landmark)[from];
        int landmarkToTarget = readFromLandmarks.get(landmark)[to];

        int currentToLandmark = readtoLandmarks.get(landmark)[from];
        int targetToLandmark = readtoLandmarks.get(landmark)[to];

        // if negative calculation, set r1 and r2 to zero
        int r1 = Math.max(landmarkToTarget - landmarkToCurrent, 0);
        int r2 = Math.max(currentToLandmark - targetToLandmark, 0);

        return Math.max(r1, r2);
    }


    public int findEstimate(int from, int to) {
        int estimate = -1;
        int tempEstimate = -1;
        int length = 4; // hardkodet, avhengig av hvor mye landemerker som brukes

        for (int i = 0; i < length; i++) {
            tempEstimate = estimateLandmark(from, to, i); // landmark
            if (tempEstimate > estimate) {
                estimate = tempEstimate;
            }
        }
        return estimate;
    }

    /**
     * @param startNode
     * @param endNode
     * @return distance from startnode to endnode
     * ALT search (djikstra with landmarks)
     */
    public int altSearch(int startNode, int endNode) {
        priorityQueue.clear();
        reset();
        amountVisited = 0;
        Node start = getNodeFromList(startNode);
        Node end = getNodeFromList(endNode);
        start.setDistance(0);
        priorityQueue.add(start);

        while (!priorityQueue.isEmpty()) {

            Node polled = priorityQueue.poll();
            amountVisited++;
            polled.setVisisted(true);
            polled.setEnQueued(false);

            for (Edge edge : polled.getAdjList()) {
                Node toNode = edge.getTo();
                int newDistance = polled.getDistance() + edge.getWeight();

                if (newDistance < toNode.getDistance()) {
                    toNode.setDistance(newDistance);
                    toNode.setPredeseccor(polled); // set forgjenger
                    if (!toNode.isVisisted()) {
                        priorityQueue.remove(toNode);

                        if (toNode.getEstimatedDistance() == -1) {
                            int estimate = findEstimate(toNode.number, end.number);
                            toNode.setEstimatedDistance(estimate);
                        }
                        priorityQueue.add(toNode);
                        toNode.setEnQueued(true);
                    }
                }
                if (!toNode.isVisisted() && !toNode.isEnQueued()) {
                    priorityQueue.add(toNode);
                    toNode.setEnQueued(true);
                }
            }
            if (end.isVisisted()) {
                // System.out.println("Antall besøkte: " + amountVisited);
                break;
            }
        }
        // distance from start to endnode
        return end.getDistance();
    }

    public List<String> getPath(Node target) {
        List<Node> reversedPath = new ArrayList<>();
        //Finner veien i reversert rekkefølge
        for (Node n = target; n != null; n = n.getPredeseccor()) {
            reversedPath.add(n);
        }
        //Reverserer den reverserte veien for å få den riktig retning.
        List<Node> path = new ArrayList<>();
        for (int i = reversedPath.size() - 1; i >= 0; i--) {
            path.add(reversedPath.get(i));
        }
        System.out.println("ALT: Antall noder i pathen: " + path.size());
        List<String> koordinater = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
            koordinater.add(path.get(i).writeCoordinates());
        }
        return koordinater;
    }

    protected class DistanceComprator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            // o1.findsumDistance, o2.findSumDistance
            return o1.sumDistance() - o2.sumDistance();
        }
    }

    // henter ut interessepktet basert på nodenummer, (kan ikke bruke index..)
    public Interessepkt getInteressepkt(int number) {
        for (int i = 0; i < interessepkts.size() ; i++) {
            if (interessepkts.get(i).getNodeNumber() == number) {
                return interessepkts.get(i);
            }
        }
        return null;
    }
}


class Node implements Comparable<Node> {
    int number;
    double longitude; //Lengdegrad
    double latitude; //Breddegrad
    private int distance;
    private int type;
    private boolean visisted;
    private Node predeseccor;
    private List<Edge> adjList;
    private List<Edge> oppositeAdjList;
    private int estimatedDistance = -1;
    private boolean enQueued;

    private Interessepkt interessepkt;

    //Konstruktør for bruk av ALT
    public Node(int number, double longitude, double latitude){
        this.number = number;
        this.longitude = longitude;
        this.latitude = latitude;
        this.adjList = new ArrayList<>();
        this.oppositeAdjList = new ArrayList<>();
        this.distance = Integer.MAX_VALUE;
    }

    // Konstruktør for bruk av Djikstra
    public Node(int number) {
        this.number = number;
        this.distance = Integer.MAX_VALUE; // Infinite
        this.visisted = false;
        this.adjList = new ArrayList<>();
    }


    public void setType(int type){
        this.type = type;
    }

    public int getType(){return type;}
    public void setDistance(int distance) {
        this.distance = distance;
    }


    public int getDistance() {
        return distance;
    }

    public void addNeigbour(Edge edge) {
        this.adjList.add(edge);
    }

    public void addOppoNeigbour(Edge edge){
        this.oppositeAdjList.add(edge);
    }

    public boolean isVisisted() {
        return visisted;
    }

    public Node getPredeseccor() {
        return predeseccor;
    }

    public List<Edge> getAdjList() {
        return adjList;
    }

    public List<Edge> getOppositeAdjList(){
        return oppositeAdjList;
    }
    public void setVisisted(boolean visisted) {
        this.visisted = visisted;
    }

    public void setPredeseccor(Node predeseccor) {
        this.predeseccor = predeseccor;
    }

    public void setAdjList(List<Edge> adjList) {
        this.adjList = adjList;
    }

    public void setEstimatedDistance(int estimatedDistance) {
        this.estimatedDistance = estimatedDistance;
    }

    public int getEstimatedDistance() {
        return estimatedDistance;
    }

    public boolean isEnQueued() {
        return enQueued;
    }

    public void setEnQueued(boolean enQueued) {
        this.enQueued = enQueued;
    }

    public int sumDistance() {
        return distance + estimatedDistance;
    }

    public String writeCoordinates(){
        return longitude + ", " + latitude;
    }

    @Override
    public int compareTo(Node n) {
        return Integer.compare(this.distance, n.distance);
    }

    @Override
    public String toString() {
        return number + "";
    }
}

class Edge {
    private Node from;
    private Node to;
    private int weight;

    public Edge(Node from, Node to, int weight){
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }
}

class Interessepkt {
    private int nodeNumber;
    private int type;
    private String name;


    public Interessepkt(int nodeNumber, int type, String name) {
        this.nodeNumber = nodeNumber;
        this.type = type;
        this.name = name;
    }

    public int getNodeNumber() {
        return nodeNumber;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + "";
    }

}

class Preproseccor {
    private ArrayList<Node> nodes;
    private ArrayList<int[]> toLandmarks;
    private ArrayList<int[]> fromLandmarks;
    private String fromNodeToLandmarkFile;
    private String toNodeFromLandmarkFile;

    public Preproseccor(ArrayList<Node> nodes, ArrayList<int[]> toLandmarks, ArrayList<int[]> fromLandmarks, String fromNodeToLandmarkFile, String toNodeFromLandmarkFile) {
        this.nodes = nodes;
        this.toLandmarks = toLandmarks;
        this.fromLandmarks = fromLandmarks;
        this.fromNodeToLandmarkFile = fromNodeToLandmarkFile;
        this.toNodeFromLandmarkFile = toNodeFromLandmarkFile;
    }

    /**
     * Lager en fil med distansen fra startnodene
     * til alle landemerkene
     */
    public void generateFromNodeToLandmarkFile(int n, int s, int e, int w) throws IOException {
        FileWriter fileWriter = new FileWriter(fromNodeToLandmarkFile);
        PrintWriter pw = new PrintWriter(fileWriter);
        Node north = nodes.get(n);
        Node south = nodes.get(s);
        Node east = nodes.get(e);
        Node west = nodes.get(w);

        Node[] landmarks = {north, south, east, west};

        for (int i = 0; i < landmarks.length; i++) {
            landmarks[i].setDistance(0);
            int[] distances = findShortestDistanceFromAll(landmarks[i]);
            toLandmarks.add(distances);
        }
        for (int j = 0; j < nodes.size(); j++) {
            pw.write(toLandmarks.get(0)[j] + " "
                    + toLandmarks.get(1)[j] + " "
                    + toLandmarks.get(2)[j] + " "
                    + toLandmarks.get(3)[j]);
            pw.println();
        }
        pw.close();
    }

    /**
     * @throws FileNotFoundException
     * Lager en fil med distansen til startnode
     * fra alle landemerkene
     */
    public void generateToNodeFromLandmarkFile(int n, int s, int e, int w) throws IOException {
        FileWriter outFile = new FileWriter(toNodeFromLandmarkFile);
        PrintWriter pw = new PrintWriter(outFile);

        System.out.println("Størrelse på nodeliste: " + nodes.size());
        Node north = nodes.get(n);
        Node south = nodes.get(s);
        Node east = nodes.get(e);
        Node west = nodes.get(w);

        Node[] landmarks = {north, south, east, west};

        for (int i = 0; i < landmarks.length; i++) {
            landmarks[i].setDistance(0);
            int[] distances = findShortestDistanceToAll(landmarks[i]);
            fromLandmarks.add(distances);
        }
        for (int j = 0; j < nodes.size(); j++) {
            pw.write(fromLandmarks.get(0)[j] + " "
                    + fromLandmarks.get(1)[j] + " "
                    + fromLandmarks.get(2)[j] + " "
                    + fromLandmarks.get(3)[j]);
            pw.println();
        }
        pw.close();
    }

    /**
     * trenger å resette data for nodene hver gang
     * hvis man kjører skal kjøre korteste vei metoden
     * flere ganger
     */
    private void reset() {
        for (Node node : nodes) {
            node.setDistance(Integer.MAX_VALUE);
            node.setVisisted(false);
        }
    }

    /**
     * @param start Tar inn en startnode og finner avstand
     *              til node fra start
     */
    private int[] findShortestDistanceToAll(Node start) {
        reset();
        start.setDistance(0); // start have cost 0

        PriorityQueue<Node> queue = new PriorityQueue<>();

        queue.add(start);
        start.setVisisted(true);

        while (!queue.isEmpty()) {

            Node current = queue.poll();

            for (Edge e : current.getAdjList()) {

                Node n = e.getTo();

                if (!n.isVisisted()) {
                    int dist = current.getDistance() + e.getWeight();

                    if (dist < n.getDistance()) {
                        // remove from queue
                        queue.remove(n);
                        // update values
                        n.setDistance(dist);
                        n.setPredeseccor(current);
                        // add to queue again with new values
                        queue.add(n);
                    }
                }
            }
            current.setVisisted(true);
        }
        int[] distances = new int[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            distances[i] = nodes.get(i).getDistance();
        }
        return distances;
    }


    /**
     * @param start method from Dijkstra
     * Tar inn en startnode og finner avstand
     * fra node til start
     */
    private int[] findShortestDistanceFromAll(Node start) {
        reset();
        start.setDistance(0); // start have cost 0

        PriorityQueue<Node> queue = new PriorityQueue<>();

        queue.add(start);
        start.setVisisted(true);

        while (!queue.isEmpty()) {

            Node current = queue.poll();

            for (Edge e : current.getOppositeAdjList()) {

                Node n = e.getFrom();

                if (!n.isVisisted()) {
                    int dist = current.getDistance() + e.getWeight();

                    if (dist < n.getDistance()) {
                        // remove from queue
                        queue.remove(n);
                        // update values
                        n.setDistance(dist);
                        n.setPredeseccor(current);
                        // add to queue again with new values
                        queue.add(n);
                    }
                }
            }
            current.setVisisted(true);
        }
        int[] distances = new int[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            distances[i] = nodes.get(i).getDistance();
        }
        return distances;
    }
}



class Filehandler {
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private ArrayList<Interessepkt> interessepkts;

    public Filehandler(ArrayList<Node> nodes, ArrayList<Edge> edges, ArrayList<Interessepkt> interessepkts) {

        this.nodes = nodes;
        this.edges = edges;
        this.interessepkts = interessepkts;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public ArrayList<Interessepkt> getInteressepkts() {
        return interessepkts;
    }

    public void readNodes(String nodePath) {
        try {
            // Leser noder
            BufferedReader brNodes = new BufferedReader(new FileReader(nodePath));
            StringTokenizer stNodes = new StringTokenizer(brNodes.readLine());
            int size = Integer.parseInt(stNodes.nextToken());

            for (int i = 0; i < size; i++) {
                stNodes = new StringTokenizer(brNodes.readLine());
                int number = Integer.parseInt(stNodes.nextToken());
                double longitude = Double.parseDouble(stNodes.nextToken());
                double latitude = Double.parseDouble(stNodes.nextToken());
                nodes.add(new Node(number, longitude, latitude));
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void readEdges(String edgePath) {
        // Leser kanter
        try {

            BufferedReader brEdges = new BufferedReader(new FileReader(edgePath));
            StringTokenizer stEdges = new StringTokenizer(brEdges.readLine());
            int size = Integer.parseInt(stEdges.nextToken());

            for (int i = 0; i < size; i++) {
                stEdges = new StringTokenizer(brEdges.readLine());
                Node from = nodes.get(Integer.parseInt(stEdges.nextToken()));
                Node to = nodes.get(Integer.parseInt(stEdges.nextToken()));
                int weight = Integer.parseInt(stEdges.nextToken());
                edges.add(new Edge(from, to, weight));
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void readIntrestPoints(String intressepktPath) {
        // Leser interessepkt

        try {
            BufferedReader brInteressepkt = new BufferedReader(new FileReader(intressepktPath));
            StringTokenizer stInteressepkt = new StringTokenizer(brInteressepkt.readLine());
            int intressepktSize = Integer.parseInt(stInteressepkt.nextToken());

            for (int i = 0; i < intressepktSize; i++) {
                stInteressepkt = new StringTokenizer(brInteressepkt.readLine());
                int nodeNumber = Integer.parseInt(stInteressepkt.nextToken());
                int type = Integer.parseInt(stInteressepkt.nextToken());
                String name = stInteressepkt.nextToken();

                // legger til alle interessepktene
                // todo: Må finne en måte å koble interessepkt opp mot
                interessepkts.add(new Interessepkt(nodeNumber, type, name));

            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    /**
     * @throws IOException Read all preprosecced distances from each landmark to every node
     */
    public void readFromLandmarks(String path, ArrayList<int[]> readFromLandmarks) throws IOException {
        BufferedReader bfToNode = new BufferedReader(new FileReader(path));
        StringTokenizer stToNode;
        for (int i = 0; i < 4; i++) {
            readFromLandmarks.add(new int[nodes.size()]);
        }
        for (int i = 0; i < nodes.size(); i++) {
            stToNode = new StringTokenizer(bfToNode.readLine());

            for (int j = 0; j < 4; j++) { // 4 landemerker, litt hardkodet
                if (!stToNode.hasMoreTokens()) {
                    break;
                }
                String token = stToNode.nextToken();
                readFromLandmarks.get(j)[i] = Integer.parseInt(token);
            }
        }
        bfToNode.close();
    }

    /**
     * @throws IOException Read all preprosecced distances to each landmark from every node
     */
    public void readToLandmarks(String path, ArrayList<int[]> readtoLandmarks) throws IOException {
        BufferedReader bfToNode = new BufferedReader(new FileReader(path));
        StringTokenizer stToNode = null;

        for (int i = 0; i < 4; i++) { // 4 landemerker, litt hardkodet
            readtoLandmarks.add(new int[nodes.size()]);
        }
        for (int i = 0; i < nodes.size(); i++) {
            String next = bfToNode.readLine();
            if (next.isEmpty()) {
                bfToNode.close();
            } else {
                stToNode = new StringTokenizer(next);
            }

            for (int j = 0; j < 4; j++) {
                readtoLandmarks.get(j)[i] = Integer.parseInt(stToNode.nextToken());
            }
        }
        bfToNode.close();
    }
}