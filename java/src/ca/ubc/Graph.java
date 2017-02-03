package ca.ubc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;


/**
 * A directed graph with edge probabilities
 */
public class Graph {

  private static final Logger LOGGER = Logger.getLogger(Graph.class.getCanonicalName());
  private static final String SEPARATOR = " ";

  // TODO we should make those fields private
  public int n;  // num of nodes
  public ArrayList<Integer>[] neighbors;
  public ArrayList<Double>[] probs;
  public int[] outDegrees;

  @SuppressWarnings("unchecked")
  public Graph(String graphFilePath) {
    BufferedReader br = null;
    int numEdges = 0;

    try  {
      br = new BufferedReader(new FileReader(graphFilePath));
      String line;
      boolean isFirstLine = true;
      LOGGER.info("Reading graph file from " + graphFilePath);

      while ((line = br.readLine()) != null) {
        String[] data = line.split(SEPARATOR);

        if (isFirstLine) {
          // first line tells num of nodes and edges
          isFirstLine = false;
          n = Integer.parseInt(data[0]);
          //m = Integer.parseInt(data[1]);
          LOGGER.info("Number of nodes = " + n);

          neighbors = (ArrayList<Integer>[]) new ArrayList[n];
          probs = (ArrayList<Double>[]) new ArrayList[n];
          for (int i = 0; i < n; ++i) {
            neighbors[i] = new ArrayList<>();
            probs[i] = new ArrayList<>();
          }

          outDegrees = new int[n];
          Arrays.fill(outDegrees, 0);

        } else {
          // each line contains one directed edge: (u, v, p_uv)
          int u = Integer.parseInt(data[0]);
          int v = Integer.parseInt(data[1]);
          double p = Double.parseDouble(data[2]);

          neighbors[u].add(v);
          probs[u].add(p);
          outDegrees[u]++;
          numEdges++;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        br.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    LOGGER.info("Number of edges = " + numEdges);
    LOGGER.info("Finished reading graph file!");
  }

  /**
   * Print the neighbors of u and out-degree of u in the graph
   */
  public void print(int u) {
    System.out.println("Printing stats for node " + u);
    ArrayList<Integer> f = neighbors[u];
    ArrayList<Double> p = probs[u];
    for (int i = 0; i < f.size(); ++i) {
      StringBuilder sb = new StringBuilder();
      sb.append('(');
      sb.append(u);
      sb.append(", ");
      sb.append(f.get(i));
      sb.append(", ");
      sb.append(p.get(i));
      sb.append(" )");
      System.out.println(sb.toString());
    }
    System.out.println("Out-degree of node " +  u + " = " + outDegrees[u]);
  }


}
