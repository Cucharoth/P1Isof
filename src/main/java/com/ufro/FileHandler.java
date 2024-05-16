package com.ufro;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private final String OUTPUT_FILE_NAME = "resultado.txt";

    public List<String> leerTxt(String nombreArchivo) {
        List<String> datos = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
            String line;
            while ((line = br.readLine()) != null) {
                datos.add(line);
            }
            br.close();
        } catch (Exception e) {

        }
        return datos;
    }

    public void printTxt(List<List<String>> finalMessage) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME))) {
            for (List<String> line : finalMessage) {
                String thisLine = "";
                for (String str : line) {
                    thisLine += str;
                }
                writer.write(thisLine.replace('+', ' ') + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
