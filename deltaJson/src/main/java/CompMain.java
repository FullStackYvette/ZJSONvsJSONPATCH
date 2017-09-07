import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class CompMain {

    public static void main(String[] args) throws Exception {
        JsonComp jsonComp = new JsonComp();

        String fileName = "result.txt";
        File output = new File(fileName);
        if (output.exists()) {
            output.delete();
        }

        output.createNewFile();
        FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(jsonComp.experimentSet());
        bw.close();
        fw.close();

    }
}
