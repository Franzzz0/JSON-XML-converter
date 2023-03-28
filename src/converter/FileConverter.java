package converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileConverter {

    public static void convertFile(File file) {
        String input = readFile(file);

        if (input.isEmpty()) {
            System.out.println("Input is Empty");
            return;
        }
        Converter converter;
        switch (input.charAt(0)) {
            case '<':
                converter = new XmlParser();
                break;
            case '{':
                converter = new JsonParser();
                break;
            default:
                throw new IllegalStateException("Unexpected value");
        }
        String converted = converter.convert(input);
        System.out.println(converted);
//        writeToFile(file, converted);
    }

    private static void writeToFile(File file, String converted) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(converted);
            System.out.println("Conversion successful.");
        } catch (IOException e) {
            System.out.println("Cannot write to file.");
        }
    }

    private static String readFile(File file) {
        StringBuilder lines = new StringBuilder();
        try (Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()) {
                lines.append(scanner.nextLine().trim());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return lines.toString();
    }
}
