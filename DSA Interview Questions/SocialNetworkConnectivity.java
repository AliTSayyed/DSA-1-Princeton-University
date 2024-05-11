/*
Given a social network containing 𝑛 members and a log file containing 𝑚 timestamps at which times pairs of
members formed friendships, design an algorithm to determine the earliest time at which all members are connected
(i.e., every member is a friend of a friend of a friend ... of a friend). Assume that the log file is sorted by
timestamp and that friendship is an equivalence relation. The running time of your algorithm should be proportional to n.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
// create a normal path compression union find and then read in the values based on the file format.
// Keep track of when everyone is connected using a counter. When everyone is connected, print the last 2 connections, timestamp, and elapsed time.

public class SocialNetworkConnectivity {
    private int[] network;
    private int[] size;
    private int notConnected;

    public SocialNetworkConnectivity(int N){
        network = new int[N];
        size = new int[N];
        notConnected = N;

        for (int i = 0; i < N; i ++){
            network[i] = i;
            size[i] = 1;
        }
    }

    public int root(int i) {
        while(i != network[i]){
            i = network[network[i]];
        }
        return i;
    }

    public void union(int p, int q){
        int rootP = root(p);
        int rootQ = root(q);

        if (rootP == rootQ){
            return;
        }

        if(size[rootQ] < size[rootP]){
            network[rootQ] = rootP;
            size[rootP] += size[rootQ];
        } else {
            network[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }

        notConnected--;
    }

    public boolean isEveryoneConnected(){
        return notConnected == 1;
    }
    public static void main(String[] args) {
        String fileName = args[0];
        In in = new In(fileName);
        int n = in.readInt(); // first line is how many people are in the file
        Stopwatch timer = new Stopwatch();
        SocialNetworkConnectivity snc = new SocialNetworkConnectivity(n);

        while(!in.isEmpty()){
            int timeStamp = in.readInt();
            int p = in.readInt();
            int q = in.readInt();

            snc.union(p,q);
            if(snc.isEveryoneConnected()){
                StdOut.println(timeStamp + " " + p + " " + q);
                break;
            }
        }

        StdOut.println("time: " + timer.elapsedTime());
    }
}
