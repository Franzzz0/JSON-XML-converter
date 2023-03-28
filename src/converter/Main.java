package converter;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        File file = new File(".\\test.txt");
//        File file = new File(".\\JSON - XML converter\\task\\src\\test.txt");

        FileConverter.convertFile(file);
    }
}
