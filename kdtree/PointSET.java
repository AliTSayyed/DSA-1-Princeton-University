package kdtree;

/* (Brute force) Write a mutable data type PointSET.java that represents a set of points in the unit square.
Implement the following API by using a red–black BST from java.util.TreeSet.
*/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;


import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null value!");
        if (!points.contains(p)) points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null value!");
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : points) {
            StdDraw.point(p.x(), p.y());
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null value!");

        if (points.isEmpty()) return null; // if there are no points

        Stack<Point2D> inRange = new Stack<>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                inRange.push(p);
            }
        }
        return inRange;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null value!");
        if (points.isEmpty()) return null; // if there are no points

        double nearestDistance = Double.POSITIVE_INFINITY;
        Point2D nearestPoint = null;
        for (Point2D point : points) {
            double distance = point.distanceSquaredTo(p);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {

    }
}
