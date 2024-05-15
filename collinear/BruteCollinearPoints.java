package collinear;
/*
Examines 4 points at a time and checks whether they all lie on the same line segment, returning all such line segments.
To check whether the 4 points p, q, r, and s are collinear,
check whether the three slopes between p and q, between p and r, and between p and s are all equal. (Brute force method)
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {

    private int count; // keep track of the number of collinear line segments
    private LineSegment[] segments; // array to store collinear line segments

    // Brute force method to find all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        validateInput(points); // make sure no points are null or duplicates
        this.count = 0; // start line segment count at 0
        this.segments = new LineSegment[points.length]; // create array to store the line segments (no duplicates allowed)

        // Quadruple for loop to check 4 points at a time.
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    // if first 3 points are collinear then check for a fourth collinear point (for efficiency).
                    if (areCollinear(points[i], points[j], points[k])) {
                        for (int h = k + 1; h < points.length; h++) {
                            if (areCollinear(points[i], points[j], points[h])) {
                                addSegment(points[i], points[j], points[k], points[h]); // if all 4 points are collinear
                            }
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return this.count;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOfRange(this.segments, 0, count);
    }

    public void validateInput(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Points passed is null"); // check for null argument
        if (points.length == 0) throw new IllegalArgumentException("Points is empty"); // check for points in the array
        for (Point point : points) // check for any individual null points
            if (point == null) throw new IllegalArgumentException("Points passed contains null points");
        for (int i = 0; i < points.length; i++) // check no 2 points are equal
            for (int j = i + 1; j < points.length; j++)
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Points passed has repeated points");
    }

    // compare slopes of points to each other to determine if they are collinear
    private boolean areCollinear(Point p1, Point p2, Point p3) {
        return Double.compare(p1.slopeTo(p2), p1.slopeTo(p3)) == 0;
    }

    // Sort the points from lowest to highest
    private void addSegment(Point p1, Point p2, Point p3, Point p4) {
        Point[] collinearPoints = {p1, p2, p3, p4};
        Arrays.sort(collinearPoints);
        Point min = collinearPoints[0];
        Point max = collinearPoints[3];
        // add ordered line segment and increment count;
        this.segments[count++] = new LineSegment(min, max);
    }


    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}

