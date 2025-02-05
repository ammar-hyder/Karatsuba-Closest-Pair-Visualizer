import java.io.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class pointInputGenerator {

    private static final int CANVAS_WIDTH = 1000;
    private static final int CANVAS_HEIGHT = 600;
    private static final Random random = new Random();

    public static void generateFiles() {
        List<String> fileNames = Arrays.asList(
                "sorted_by_x", "sorted_by_y", "reverse_sorted_x", "random",
                "grid", "single_line", "clustered", "duplicates", "geometricShape", "close_together"
        );

        for (String name : fileNames) {
            List<Point> points = generatePoints(name);
            savePointsToFile(name, points);
        }
    }

    public static List<Point> generatePoints(String complexity) {
        List<Point> points = new ArrayList<>();

        switch (complexity) {
            case "sorted_by_x":
                for (int i = 0; i < 100; i++) {
                    points.add(new Point(roundToTwoDecimals((i * CANVAS_WIDTH) / 100.0),
                            roundToTwoDecimals(random.nextInt(CANVAS_HEIGHT))));
                }
                points.sort(Comparator.comparingDouble(p -> p.x));
                break;

            case "sorted_by_y":
                for (int i = 0; i < 100; i++) {
                    points.add(new Point(roundToTwoDecimals(random.nextInt(CANVAS_WIDTH)),
                            roundToTwoDecimals((i * CANVAS_HEIGHT) / 100.0)));
                }
                points.sort(Comparator.comparingDouble(p -> p.y));
                break;

            case "reverse_sorted_x":
                for (int i = 0; i < 100; i++) {
                    points.add(new Point(roundToTwoDecimals((i * CANVAS_WIDTH) / 100.0),
                            roundToTwoDecimals(random.nextInt(CANVAS_HEIGHT))));
                }
                points.sort((p1, p2) -> Double.compare(p2.x, p1.x));
                break;

            case "random":
                for (int i = 0; i < 100; i++) {
                    points.add(new Point(roundToTwoDecimals(random.nextInt(CANVAS_WIDTH)),
                            roundToTwoDecimals(random.nextInt(CANVAS_HEIGHT))));
                }
                break;

            case "grid":
                int step = 50;
                for (int x = 0; x <= CANVAS_WIDTH; x += step) {
                    for (int y = 0; y <= CANVAS_HEIGHT; y += step) {
                        points.add(new Point(roundToTwoDecimals(x), roundToTwoDecimals(y)));
                    }
                }
                break;

            case "single_line":
                for (int i = 0; i < 100; i++) {
                    points.add(new Point(roundToTwoDecimals(i * 10), roundToTwoDecimals(CANVAS_HEIGHT / 2)));
                }
                break;

            case "clustered":
                Point clusterCenter = new Point(roundToTwoDecimals(CANVAS_WIDTH / 2), roundToTwoDecimals(CANVAS_HEIGHT / 2));
                for (int i = 0; i < 100; i++) {
                    points.add(new Point(roundToTwoDecimals(clusterCenter.x + random.nextInt(50) - 25),
                            roundToTwoDecimals(clusterCenter.y + random.nextInt(50) - 25)));
                }
                break;

            case "duplicates":
                for (int i = 0; i < 50; i++) {
                    points.add(new Point(roundToTwoDecimals(random.nextInt(CANVAS_WIDTH)),
                            roundToTwoDecimals(random.nextInt(CANVAS_HEIGHT))));
                }
                // Duplicate the points
                points.addAll(new ArrayList<>(points));
                break;

            case "geometric":
                for (int i = 0; i < 100; i++) {
                    double angle = (i * Math.PI * 2) / 100;
                    int radius = 200;
                    points.add(new Point(roundToTwoDecimals(CANVAS_WIDTH / 2 + radius * Math.cos(angle)),
                            roundToTwoDecimals(CANVAS_HEIGHT / 2 + radius * Math.sin(angle))));
                }
                break;

            case "close_together":
                Point base = new Point(roundToTwoDecimals(CANVAS_WIDTH / 4), roundToTwoDecimals(CANVAS_HEIGHT / 4));
                for (int i = 0; i < 100; i++) {
                    points.add(new Point(roundToTwoDecimals(base.x + random.nextDouble()),
                            roundToTwoDecimals(base.y + random.nextDouble())));
                }
                break;

            default:
                break;
        }

        return points;
    }

    public static double roundToTwoDecimals(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static void savePointsToFile(String fileName, List<Point> points) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".txt"))) {
            for (Point p : points) {
                writer.write(p.x + " " + p.y);
                writer.newLine();
            }
            System.out.println("File saved: " + fileName + ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Point {
        double x, y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        generateFiles();
    }
}
