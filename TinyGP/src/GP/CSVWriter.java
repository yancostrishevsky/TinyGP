package GP;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
    public String filename = "default.csv";
    public PrintWriter writer;


    public CSVWriter(String filename){

        this.filename = filename;
        File csvFile = new File(this.filename);
        try(FileWriter fileWriter = new FileWriter(csvFile)){
            StringBuilder line = new StringBuilder();
            line.append("Generation");
            line.append(',');
            line.append("Avg Fitness");
            line.append(',');
            line.append("Best Fitness");
            line.append(',');
            line.append("Avg Size");
            line.append(',');
            line.append("Best Individual");
            line.append(',');
            line.append("Simplified");
            line.append('\n');
            fileWriter.write(line.toString());

        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }

    }
    public void writeGeneration(int gen, double favgpop, double fbestpop, double avg_len, StringBuilder best_indiv, String simplified){
        File csvFile = new File(this.filename);
        try(FileWriter fileWriter = new FileWriter(csvFile, true)){
            StringBuilder line = new StringBuilder();
            line.append(gen);
            line.append(',');
            line.append(-favgpop);
            line.append(',');
            line.append(-fbestpop);
            line.append(',');
            line.append(-favgpop);
            line.append(',');
            line.append(best_indiv);
            line.append(',');
            line.append(simplified);
            line.append('\n');
            fileWriter.write(line.toString());

        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }

    }
}

