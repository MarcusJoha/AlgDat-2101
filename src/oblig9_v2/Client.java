package Øving9;

import java.io.IOException;
import java.util.List;

public class Client {
    public static void main(String[] args) throws IOException {


        String nodePath = "src/Øving9/Files/Oppg/noder.txt";
        String edgePath = "src/Øving9/Files/Oppg/kanter.txt";
        String intressepktPath = "src/Øving9/Files/Oppg/interessepkt.txt";

        // Kjører ALT algoritme
        ALT alt = new ALT(nodePath, edgePath, intressepktPath);

        /*
        alt.preprocess(
                "src/Øving9/Files/outfiles/from_node_to_landmarks.txt",
                "src/Øving9/Files/outfiles/from_landmark_to_node.txt",
                5263302,2313313, 708400, 5486883
                );

         */

        int fra = 6861306; // Trondheim
        int fra2 = 3447384; // Stavanger
        int fra3 = 5379848; // Snåsa

        int til = 2518118; // Oslo
        int til2 = 136963; // Tampere
        int til3 = 2951840; // Mehamn

        // leser av preprosecced data
        alt.readNodeLandmarkData();

        // printer svar for ALT
        System.out.println("============ ALT ================\n");
        printAnswer(fra, til, alt);
        System.out.println();
        printAnswer(fra2,til2, alt);
        System.out.println();
        printAnswer(fra3,til3, alt);


        //Kjører så Djikstraalgoritme
        System.out.println("\n========== Dijkstra ==============");

        Djikstra d = new Djikstra(nodePath, edgePath, intressepktPath);

        d.findShortestDistance(d.getNodeFromList(fra), d.getNodeFromList(til));
        System.out.println("\nTid fra node " + fra + " til " + til + " = " + omregnTilTimer(d.getNodeFromList(til).getDistance()));
        System.out.println();

        d.findShortestDistance(d.getNodeFromList(fra2), d.getNodeFromList(til2));
        System.out.println("\nTid fra node " + fra2 + " til " + til2 + " = " + omregnTilTimer(d.getNodeFromList(til2).getDistance()));
        System.out.println();

        // todo: finne feil
        d.findShortestDistance(d.getNodeFromList(fra3), d.getNodeFromList(til3));
        System.out.println("\nTid fra node " + fra3 + " til " + til3 + " = " + omregnTilTimer(d.getNodeFromList(til3).getDistance()));


        System.out.println("\n========== Koordinater ==============");

        List<Node> steder = d.xNearestGasStations(6590451, 10, 2);

        System.out.println("Bensinsstasjoner: \n ------------");
        for (int i = 0; i < steder.size(); i++) {
            System.out.println(steder.get(i).writeCoordinates());
        }

        // printer ut rekkefølge av nodene som er kortest vei
        // System.out.println("Korteste vei fra Trondheim til Oslo: " + d.getShortestPath(d.getNodeFromList(til)));

    }


    // printer ut svar for ALT på riktig format
    static void printAnswer(int start, int end, ALT alt) {
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

    public static int omregnTilHundredel(int timer, int minutter, int sekunder) {
        int svar = 0;
        svar += sekunder * 100;
        svar += minutter * 60 * 100;
        svar += timer * 60 * 100 * 60;
        return svar;
    }

    public static String omregnTilTimer(long hundredeler) {
        int timer = (int) hundredeler / (100 * 60 * 60);
        int restTimer = (int) hundredeler % (100 * 60 * 60);

        int minutter = restTimer / (60 * 100);
        int restMinutter = restTimer % (60 * 100);

        int sekunder = restMinutter / 100;
        return "Timer: " + timer + ". Minutter: " + minutter + ". Sekunder: " + sekunder;
    }
}