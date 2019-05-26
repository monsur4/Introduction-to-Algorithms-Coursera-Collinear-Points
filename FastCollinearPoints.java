import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

    private final List<LineSegment> list = new ArrayList<>();
    private int numberOfSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException(
                    "You cannot pass a null argument to the constructor");
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("You cannot have a point that is null");
            }
        }
        
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException(
                            "Your list contains repeated points");
                }
            }
        }
        List<Double> slopeList = new ArrayList<>();
        Arrays.sort(points);

        Point firstPoint;
        for (int i = 0; i < points.length; i++) {
            firstPoint = points[i];
            Point[] copy = Arrays.copyOfRange(points, 0, points.length);
            Arrays.sort(copy, firstPoint.slopeOrder());
            int nextPointIndex = 1;
            int collinear = 1;
            while (nextPointIndex < copy.length - 1) {
                while (firstPoint.slopeTo(copy[nextPointIndex]) == firstPoint
                        .slopeTo(copy[nextPointIndex + 1])) {
                    nextPointIndex++;
                    collinear++;
                    if (nextPointIndex >= copy.length - 1) break;
                }

                if (collinear >= 3) {
                    if (!slopeList.contains(firstPoint.slopeTo(copy[nextPointIndex]))) {
                        slopeList.add(firstPoint.slopeTo(copy[nextPointIndex]));
                        list.add(new LineSegment(firstPoint, copy[nextPointIndex]));
                        numberOfSegments++;
                    }
                }
                nextPointIndex++;
                collinear = 1;
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[list.size()];
        for (int i = 0; i < list.size(); i++) {
            segments[i] = list.get(i);
        }
        return segments;
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
