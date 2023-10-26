package GP;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;

public class Generate {

    static int[][] f1_domain = {{-10, 10}, {0, 100}, {-1, 1}, {-1000, 1000}};
    static double[][] f2_domain = {{-Math.PI, Math.PI}, {0, 7}, {0, 100}, {-100, 100}};
    static int[][] f3_domain = {{0, 4}, {0, 9}, {0, 99}, {0, 999}};
    static int[][] f4_domain = {{0, 1}, {-10, 10}, {0, 100}, {-1000, 1000}};
    static float[][] f5_domain = {{(float) -3.14, (float)3.14}, {0, 7}, {0, 100}, {-100, 100}
    };
    static int[][] f6_domain = {{-10, 10}, {0, 100}, {-1, 1}, {-1000, 1000}};

    private static final float elements = 10;


    private static double f1(float x) {
        return 5 * Math.pow(x, 3) - 2 * Math.pow(x, 2) + 3 * x - 17;
    }

    private static double f2(double x) {
        return Math.sin(x) + Math.cos(x);
    }

    private static double f3(double x) {
        return 2 * Math.log(x + 1);
    }

    private static double f4(double x, double y) {
        return x + 2 * y;
    }

    private static double f5(float x, float y) {
        return Math.sin(x / 2) + 2 * Math.cos(x);
    }

    private static double f6(float x, float y) {
        return Math.pow(x, 2) + 3 * x * y - 7 * y + 1;
    }


    public static void generate_f1() throws IOException {
        Locale.setDefault(Locale.US);

        for (int i = 0; i < f1_domain.length; ++i) {
            double min = 0;
            double max = 0;
            ArrayList<double[]> values = new ArrayList<>();
            String filename = "f1_".concat(String.valueOf(i)).concat(".dat");
            float step = (Math.abs(f1_domain[i][0]) + Math.abs(f1_domain[i][1])) / elements;


            for (float j = f1_domain[i][0]; j < f1_domain[i][1]; j += step) {
                double value = f1(j);

                double[] temp = new double[]{j, value};
                values.add(temp);
                if (value > max) max = value;
                if (value < min) min = value;

            }
            ToFile(filename, values, min, max);
        }


    }

    public static void generate_f2() throws IOException {
        Locale.setDefault(Locale.US);

        for (int i = 0; i < f2_domain.length; ++i) {
            double min = 0;
            double max = 0;
            ArrayList<double[]> values = new ArrayList<>();
            String filename = "f2_".concat(String.valueOf(i)).concat(".dat");
            double step = (Math.abs(f2_domain[i][0]) + Math.abs(f2_domain[i][1])) / elements;


            for (double j = f2_domain[i][0]; j < f2_domain[i][1]; j += step) {
                double value = f2(j);

                double[] temp = new double[]{j, value};
                values.add(temp);
                if (value > max) max = value;
                if (value < min) min = value;

            }
            ToFile(filename, values, min, max);


        }


    }

    public static void generate_f3() throws IOException {
        Locale.setDefault(Locale.US);

        for (int i = 0; i < f3_domain.length; ++i) {
            double min = 0;
            double max = 0;
            ArrayList<double[]> values = new ArrayList<>();
            String filename = "f3_".concat(String.valueOf(i)).concat(".dat");
            double step = (Math.abs(f3_domain[i][0]) + Math.abs(f3_domain[i][1])) / elements;


            for (double j = f3_domain[i][0]; j < f3_domain[i][1]; j += step) {
                double value = f3(j);

                double[] temp = new double[]{j, value};
                values.add(temp);
                if (value > max) max = value;
                if (value < min) min = value;

            }
            ToFile(filename, values, min, max);


        }


    }
    public static void generate_f4() throws IOException {
        Locale.setDefault(Locale.US);

        for (int i = 0; i < f4_domain.length; ++i) {
            double min = 0;
            double max = 0;
            ArrayList<double[]> values = new ArrayList<>();
            String filename = "f4_".concat(String.valueOf(i)).concat(".dat");
            double step = (Math.abs(f4_domain[i][0]) + Math.abs(f4_domain[i][1])) / elements;

            for (double j = f4_domain[i][0]; j < f4_domain[i][1]; j += step) {
                double value = f4(j, j);

                double[] temp = new double[]{j, value};
                values.add(temp);
                if (value > max) max = value;
                if (value < min) min = value;
            }
            ToFile2(filename, values, min, max);
        }
    }

    public static void generate_f5() throws IOException {
        Locale.setDefault(Locale.US);

        for (int i = 0; i < f5_domain.length; ++i) {
            double min = 0;
            double max = 0;
            ArrayList<double[]> values = new ArrayList<>();
            String filename = "f5_".concat(String.valueOf(i)).concat(".dat");
            double step = (Math.abs(f5_domain[i][0]) + Math.abs(f5_domain[i][1])) / elements;

            for (float j = f5_domain[i][0]; j < f5_domain[i][1]; j += step) {
                double value = f5(j, j);

                double[] temp = new double[]{j, value};
                values.add(temp);
                if (value > max) max = value;
                if (value < min) min = value;
            }
            ToFile2(filename, values, min, max);
        }
    }

    public static void generate_f6() throws IOException {
        Locale.setDefault(Locale.US);

        for (int i = 0; i < f6_domain.length; ++i) {
            double min = 0;
            double max = 0;
            ArrayList<double[]> values = new ArrayList<>();
            String filename = "f6_".concat(String.valueOf(i)).concat(".dat");
            double step = (Math.abs(f6_domain[i][0]) + Math.abs(f6_domain[i][1])) / elements;

            for (float j = f6_domain[i][0]; j < f6_domain[i][1]; j += step) {
                double value = f6(j, j);

                double[] temp = new double[]{j, value};
                values.add(temp);
                if (value > max) max = value;
                if (value < min) min = value;
            }
            ToFile2(filename, values, min, max);
        }
    }


    public static void ToFile(String filename, ArrayList<double[]> values, double min, double max) throws IOException {
        Files.createDirectories(Paths.get("Data"));
        BufferedWriter output = new BufferedWriter(new FileWriter(filename));
        output.write(String.format("1 %.0f %.0f %.0f %.0f", elements, min, max, elements).concat("\n"));

        for (var line :
                values) {
            output.write(String.format("%.2f %.2f\n", line[0], line[1]));


        }
        output.close();


    }

    public static void ToFile2(String filename, ArrayList<double[]> values, double min, double max) throws IOException {
        Files.createDirectories(Paths.get("Data"));
        BufferedWriter output = new BufferedWriter(new FileWriter(filename));
        output.write(String.format("2 %.0f %.0f %.0f %.0f", elements, min, max, elements).concat("\n"));

        for (var line :
                values) {
            output.write(String.format("%.2f %.2f %.2f\n", line[0], line[0], line[1]));


        }
        output.close();


    }

    public static void generate() {
        try {
            generate_f1();
            generate_f2();
            generate_f3();
            generate_f4();
            generate_f5();
            generate_f6();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        generate();
    }
}

