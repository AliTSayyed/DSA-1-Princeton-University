# Collinear Points
(Assignment files are Point.java, BruteCollinearPoints.java, and FastCollinearPoints.java)

1. Write a program that examines 4 points at a time and checks whether they all lie on the same line segment, 
returning all such line segments. To check whether the 4 points p, q, r, and s are collinear, 
check whether the three slopes between p and q, between p and r, and between p and s are all equal (brute force method).

2. Given a point p, the following method determines whether p participates in a set of 4 or more collinear points:
  - Think of p as the origin.
  - For each other point q, determine the slope it makes with p.
  - Sort the points according to the slopes they make with p.
  - Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p.
If so, these points, together with p, are collinear (fast method).
