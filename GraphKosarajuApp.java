package graphkosarajuapp;

public class GraphKosarajuApp {

    public static void main(String[] args) {

        int[][] a1 = {
            {},
            {2},
            {3},
            {4},
            {1},
            {3, 6},
            {8},
            {5, 6, 9},
            {},
            {},};

        Graph G = new Graph(a1);

        int[] component = G.kosaraju();

        System.out.println("Component:");
        for (int i = 0; i < component.length; i++) {
            System.out.print("for vertex " + i + " component is " + component[i]);
            System.out.println("");
        }

    }

}
