import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private int numberOfSegments;
    private final List<LineSegment> list = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException(
                    "You cannot pass a null argument to the constructor");
        }

        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException(
                        "You cannot have a null entry in the array passed as the constructor argument");
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

        Point[] pointsArray = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            pointsArray[i] = points[i];
        }
        Arrays.sort(pointsArray);

        for (int i = 0; i < pointsArray.length; i++) {
            for (int j = i + 1; j < pointsArray.length; j++) {
                Point lastPointOnLineSegment = pointsArray[j];
                double slope = pointsArray[i].slopeTo(pointsArray[j]);
                int count = 1;

                for (int k = j + 1; k < pointsArray.length; k++) {
                    if (slope == pointsArray[i].slopeTo(pointsArray[k])) {
                        lastPointOnLineSegment = pointsArray[k];
                        count++;
                    }
                }

                if (count == 3) {
                    list.add(new LineSegment(pointsArray[i], lastPointOnLineSegment));
                    numberOfSegments++;
                }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
