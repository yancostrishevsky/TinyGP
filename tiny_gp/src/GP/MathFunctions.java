package GP;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.DoubleUnaryOperator;
import java.util.function.DoubleBinaryOperator;
public class MathFunctions {

    // Zakresy dziedziny
    private static final double X_START = -10;
    private static final double X_END = 10.0;
    private static final double Y_START = -10.0;
    private static final double Y_END = 10.0;

    // Definicje funkcji
    public static DoubleUnaryOperator function1 = x -> roundToTwoDecimals(5 * Math.pow(x, 3) - 2 * Math.pow(x, 2) + 3 * x - 17);
    public static DoubleUnaryOperator function2 = x -> roundToTwoDecimals(Math.sin(x) + Math.cos(x));
    public static DoubleUnaryOperator function3 = x -> roundToTwoDecimals(2 * Math.log(x + 1));
    public static DoubleBinaryOperator function4 = (x, y) -> roundToTwoDecimals(x + 2 * y);
    public static DoubleBinaryOperator function5 = (x, y) -> roundToTwoDecimals(Math.sin(x / 2) + 2 * Math.cos(x));
    public static DoubleBinaryOperator function6 = (x, y) -> roundToTwoDecimals(Math.pow(x, 2) + 3 * x * y - 7 * y + 1);

    public static void main(String[] args) {
        try {
            // Tworzenie pliku .dat
            PrintWriter writer = new PrintWriter(new FileWriter("output.dat"));

            // Nagłówek
            writer.println("1 100 -5 5 101");

            // Wartości funkcji
            writeFunctionValues(writer, function1, X_START, X_END);
            //writeFunctionValues(writer, function2, X_START, X_END);
            //writeFunctionValues(writer, function3, 0, 4);
            //writeFunction2DValues(writer, function4, X_START, X_END, Y_START, Y_END);
            //writeFunction2DValues(writer, function5, -3.14, 3.14, 0, 7);
            //writeFunction2DValues(writer, function6, X_START, X_END, Y_START, Y_END);

            // Zamykanie pliku
            writer.close();
            System.out.println("Plik output.dat został wygenerowany.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Funkcja zapisująca wartości funkcji dla jednej zmiennej
    private static void writeFunctionValues(PrintWriter writer, DoubleUnaryOperator function, double start, double end) {
        for (double x = start; x <= end; x += 0.2) {
            writer.println(roundToTwoDecimals(x) + " " + function.applyAsDouble(x));
        }
    }

    // Funkcja zapisująca wartości funkcji dla dwóch zmiennych
    private static void writeFunction2DValues(PrintWriter writer, DoubleBinaryOperator function, double startX, double endX, double startY, double endY) {
        for (double x = startX; x <= endX; x += 0.1) {
            for (double y = startY; y <= endY; y += 0.1) {
                writer.println(roundToTwoDecimals(x) + " " + roundToTwoDecimals(y) + " " + function.applyAsDouble(x, y));
            }
        }
    }

    // Funkcja zaokrąglająca do dwóch miejsc po przecinku
    private static double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
