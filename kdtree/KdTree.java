package kdtree;

/* Write a mutable data type KdTreePractice.java that uses a 2d-tree to implement the same API as PointSET.
A 2d-tree is a generalization of a BST to two-dimensional keys. The idea is to build a BST with points in the nodes,
using the x- and y-coordinates of the points as keys in strictly alternating sequence.
*/

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private static final int X_DIMENSION = 1; // x comparison
    private static final int Y_DIMENSION = 0; // y comparison
    private Node root; // initial root of tree
    private int size; // keep track of tree size

    // Create Node class to represent the tree
    private static class Node {
        private Point2D point; // (x,y) coordinate of Node
        private int dimension; // initialize node as a X or Y dimension comparison;
        private Node left; // left child
        private Node right; // right child;

        // constructor to initialize value (avoid Null pointers).
        public Node(Point2D p) {
            point = p;
            left = null;
            right = null;
        }
    }

    // create empty tree data structure;
    public KdTree() {
        root = null;
        size = 0;
    }

    // If the set empty
    public boolean isEmpty() {
        return (root == null);
    }

    // Size of tree
    public int size() {
        return size;
    }

    // Add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Not a valid point!");
        }

        if (!contains(p)) {
            Node node = new Node(p); // insert Node with a point first, then do comparisons if it should go right or left
            root = insertNode(node, root); // recursive method to determine insertion placement
            size++; // keep track of size
        }
    }

    // Insert a node for specific parent node
    private Node insertNode(Node newNode, Node parent) {
        // creating the root
        if (parent == null) {
            newNode.dimension = X_DIMENSION;
            return newNode;
        }
        // creating children nodes
        if (comparePoints(newNode.point, parent.point, parent.dimension)) { // if new node point is less than parent node
            if (parent.left == null) {
                newNode.dimension = changeDimension(parent); // change dimension for next comparison
                parent.left = newNode; // set the child of the parent node equal to the new node (not vice versa to maintain tree structure).
            } else {
                parent.left = insertNode(newNode, parent.left); // if there is a left child already recursively call with the new parent as parent.left
            }
        } else { // if new node point is greater than parent node (same process but for the right side)
            if (parent.right == null) {
                newNode.dimension = changeDimension(parent);
                parent.right = newNode;
            } else {
                parent.right = insertNode(newNode, parent.right);
            }
        }
        return parent; // if it is the exact same point
    }

    // Get opposite dimension for child node
    private int changeDimension(Node node) {
        if (node.dimension == X_DIMENSION) {
            return Y_DIMENSION;
        }
        return X_DIMENSION;
    }

    // check if tree already has the point
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Not a valid point!");
        }
        Node node = root;
        while (true) {
            if (node == null) {
                return false;
            }
            if (node.point.equals(p)) { // compare points using the equals method
                return true;
            }
            if (comparePoints(p, node.point, node.dimension)) { // search tree for that possible point by going left or right based on comparisons
                node = node.left;
            } else {
                node = node.right;
            }
        }
    }

    // compare points (x,y) based on their dimension (what level they are at on the tree)
    private boolean comparePoints(Point2D point, Point2D nodePoint, int dimension) {
        if (dimension == 1) {
            return point.x() < nodePoint.x();
        } else {
            return point.y() < nodePoint.y();
        }
    }

    // Draw all points to standard draw
    public void draw() {
        drawNode(root, 0, 1, 0, 1); // initial point and boundaries
    }

    private void drawNode(Node node, double xMin, double xMax, double yMin, double yMax) {
        if (node == null) {
            return;
        }
        drawPoint(node.point); // draw current node point
        drawLine(node, xMin, xMax, yMin, yMax); // draw line through current node

        // based on the current dimensions and if the point is to the right or left, pass in the new line boundaries
        if (node.dimension == X_DIMENSION) {
            drawNode(node.left, xMin, node.point.x(), yMin, yMax);
            drawNode(node.right, node.point.x(), xMax, yMin, yMax);
        } else {
            drawNode(node.left, xMin, xMax, yMin, node.point.y());
            drawNode(node.right, xMin, xMax, node.point.y(), yMax);
        }
    }

    // Draw a point
    private void drawPoint(Point2D point) {
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);

        point.draw();
    }

    // Draw line based on x or y dimensions
    private void drawLine(Node node, double xMin, double xMax, double yMin, double yMax) {
        StdDraw.setPenRadius(); // reset pen

        if (node.dimension == X_DIMENSION) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(), yMin, node.point.x(), yMax); // draw a vertical line in between y boundaries
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(xMin, node.point.y(), xMax, node.point.y()); // draw a horizontal line in between x boundaries
        }
    }

    // All points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Not a valid rectangle!");
        }

        // store points in the rectangle
        Stack<Point2D> insidePoints = new Stack<>();
        rangeSearch(root, rect, insidePoints);
        return insidePoints;
    }

    // Check points inside the rect
    private void rangeSearch(Node node, RectHV rect, Stack<Point2D> insidePoints) {
        if (node == null) {
            return;
        }
        // if the point is in the rectangle add it to the list
        if (rect.contains(node.point)) {
            insidePoints.push(node.point);
        }

        // if the point is not in the rectangle, check for other points that could be inside the rectangle
        if (node.dimension == X_DIMENSION) {
            if (rect.xmin() < node.point.x()) { // if the x min of the rectangle is less than the point search left
                rangeSearch(node.left, rect, insidePoints);
            }
            if (rect.xmax() >= node.point.x()) { // if the x max of the rectangle is greater than the point search right
                rangeSearch(node.right, rect, insidePoints);
            }
        } else {
            if (rect.ymin() < node.point.y()) { // if the x min of the rectangle is less than the point search down
                rangeSearch(node.left, rect, insidePoints);
            }
            if (rect.ymax() >= node.point.y()) { // if the x max of the rectangle is greater than the point search up
                rangeSearch(node.right, rect, insidePoints);
            }
        }
    }

    // A nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Not a valid point!");
        }
        if (isEmpty()) {
            return null;
        }
        return nearestNode(p, root, root.point); // start by comparing the root to its own point
    }


    private Point2D nearestNode(Point2D point, Node node, Point2D nearest) {
        if (node == null) {
            return nearest;
        }
        // start by checking if the current node's point's distance is less than the current nearest point
        if (node.point.distanceSquaredTo(point) < nearest.distanceSquaredTo(point)) {
            nearest = node.point; // update point if true
        }

        Point2D potentialPoint;
        Node firstSubTree;
        Node secondSubTree;

        if (node.dimension == X_DIMENSION) {
            potentialPoint = new Point2D(node.point.x(), point.y());
            if (point.x() < node.point.x()) { // prioritize going left first
                firstSubTree = node.left;
                secondSubTree = node.right;
            } else { // prioritize going right first
                firstSubTree = node.right;
                secondSubTree = node.left;
            }
        } else {
            potentialPoint = new Point2D(point.x(), node.point.y());
            if (point.y() < node.point.y()) { // prioritize going left first (down)
                firstSubTree = node.left;
                secondSubTree = node.right;
            } else { // prioritize going right first (up)
                firstSubTree = node.right;
                secondSubTree = node.left;
            }
        }
        nearest = nearestNode(point, firstSubTree, nearest); // quick check with firstSubTree (path 1)

        // if the quick check actually does not give a closer point, go through the secondSubTree (path 2)
        if (potentialPoint.distanceSquaredTo(point) < nearest.distanceSquaredTo(point)) {
            nearest = nearestNode(point, secondSubTree, nearest);
        }
        return nearest;
    }


    public static void main(String[] args) {

    }
}
