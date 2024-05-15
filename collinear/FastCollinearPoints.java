package collinear;
/*
Given a point p, the following method determines whether p participates in a set of 4 or more collinear points:
1. Think of p as the origin.
2. For each other point q, determine the slope it makes with p.
3. Sort the points according to the slopes they make with p.
4. Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p.
If so, these points, together with p, are collinear. (Fast method)
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private int segmentCount; // keep track of number of line segments
    private LineSegment[] collinearSegments;
    private Segment[] collinearSegmentsInternal;

    // finds all line segments containing 4 or more points with equal slopes
    public FastCollinearPoints(Point[] points) {
        checkInput(points); // check if any points are null or duplicates
        segmentCount = 0; // initialize line segment count to 0
        collinearSegments = new LineSegment[points.length]; // create new line segment array
        collinearSegmentsInternal = new Segment[points.length];

        // for each point create an array that sorts the points based on their slope with respect to p
        for (Point p : points) {
            Point[] slopesToP = getSlopesTo(p, points);
            // for each slopesToP array, determine if there are 4 or more equal slope values
            for (int j = 0; j < slopesToP.length - 2; j++) {
                Point q = slopesToP[j];
                Point[] candidatePoints = Arrays.copyOfRange(slopesToP, j + 1, slopesToP.length); // create array of all other points
                Point[] collinearSequence = findCollinearSequence(p, q, candidatePoints); //

                // if there are 4 or more numbers that have the same slope with respect to the point p, add a new line segment
                if (collinearSequence.length >= 4) {
                    addSegment(collinearSequence); // add the line segment p - q - r - s... in order as p -> s (or what ever the last point is).
                    break;
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segmentCount;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOfRange(collinearSegments, 0, numberOfSegments());
    }

    // method to check if the point array or points are null and if any duplicate points exist.
    public void checkInput(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Points passed is null"); // check for null argument
        if (points.length == 0) throw new IllegalArgumentException("Points is empty"); // check for points in the array
        for (Point point : points) // check for any individual null points
            if (point == null) throw new IllegalArgumentException("Points passed contains null points");
        for (int i = 0; i < points.length; i++) // check no 2 points are equal
            for (int j = i + 1; j < points.length; j++)
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Points passed has repeated points");
    }

    // method to compare if 3 points are collinear with each other
    private boolean areCollinear(Point p1, Point p2, Point p3) {
        return Double.compare(p1.slopeTo(p2), p1.slopeTo(p3)) == 0;
    }

    // method to sort points based on their slopes with respect to the point p
    private Point[] getSlopesTo(Point p, Point[] points) {
        Point[] slopesToP = Arrays.copyOf(points, points.length);
        Arrays.sort(slopesToP, p.slopeOrder());
        return Arrays.copyOfRange(slopesToP, 1, slopesToP.length); // start at index 1 since index 0 will be the same point
    }

    // method to determine if any candidate point has the same slope as the reference point;
    private Point[] findCollinearSequence(Point p, Point q, Point[] candidatePoints) {
        Stack<Point> collinearPoints = new Stack<>();
        collinearPoints.push(p);
        collinearPoints.push(q);
        for (Point r : candidatePoints) {
            if (areCollinear(p, collinearPoints.peek(), r)) {
                collinearPoints.push(r);
            } else {
                break;
            }
        }
        Point[] collinearPointsArray = new Point[collinearPoints.size()];

        int index = collinearPoints.size() - 1;
        while (!collinearPoints.isEmpty()) {
            collinearPointsArray[index--] = collinearPoints.pop();
        }
        return collinearPointsArray;
    }

    // method to add a line segment that has collinear points
    private void addSegment(Point[] points) {
        Arrays.sort(points); // first sort the points
        Segment segment = new Segment(points[0], points[points.length - 1]);
        if (!isIncluded(segment)) { // make sure there are no duplicates
            collinearSegmentsInternal[segmentCount] = segment; // add segment to internal array
            collinearSegments[segmentCount] = new LineSegment(segment.minPoint, segment.maxPoint); // create new line segment if it is not already there and add it to the array.
            segmentCount++; // increment line segment count
        }
    }

    // method to determine if 2 segments are the same (avoid adding duplicates)
    private boolean isIncluded(Segment s) {
        for (int i = 0; i < segmentCount; i++)
            if (s.same(collinearSegmentsInternal[i]))
                return true;
        return false;
    }

    // private class used to create a min and max point for a specific line segment. Used to avoid duplicate line segment additions.
    private class Segment {
        public Point minPoint, maxPoint;

        public Segment(Point minPoint, Point maxPoint) {
            this.minPoint = minPoint;
            this.maxPoint = maxPoint;
        }

        // method to compare segments to each other, make sure they are not the same (avoid overriding equals method).
        public boolean same(Segment other) {
            return ((minPoint.compareTo(other.minPoint) == 0) && (maxPoint.compareTo(other.maxPoint) == 0));
        }

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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
