package com.ufro;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> datosEncriptados = leerTxt("ufro/src/main/java/com/ufro/datasets/datos_encriptados.txt");
        List<String> key = leerTxt("ufro/src/main/java/com/ufro/datasets/key.txt");
        for (String data : key) {
            System.out.println(data);
        }
        List<char[][]> formatedKey = formatKey(key);
        List<List<String>> formatedData = formatData(datosEncriptados);
        descifrarData(formatedData, formatedKey);
        printData(formatedData);
    }

    private static List<char[][]> formatKey(List<String> key) {
        char[][] square1 = getSquareFromKey(key, 3, 2);
        char[][] square2 = getSquareFromKey(key, 3, 12);
        char[][] square3 = getSquareFromKey(key, 3, 22);
        List<char[][]> formatedKey = new ArrayList<>();
        formatedKey.add(square1);
        formatedKey.add(square2);
        formatedKey.add(square3);
        return formatedKey;
    }

    private static char[][] getSquareFromKey(List<String> key, int lineKey, int indexKey) {
        char[][] square = new char[3][3];
        for (int i = 0, line = lineKey; i < 3; i++, line++) {
            for (int j = 0, index = indexKey; j < 3; j++) {
                square[i][j] = key.get(line).charAt(index);
                index += 2;
                // System.out.println(square[i][j]);
            }
        }
        return square;

    }

    private static void descifrarData(List<List<String>> formatedData, List<char[][]> formatedKey) {
        for (List<String> line : formatedData) {
            for (String section : line) {
                for (int i = 0; i < section.length(); i++) {
                    char ch = section.charAt(i);
                    completeMatrixStep1(ch, formatedKey);
                }
            }
        }
    }

    private static void completeMatrixStep1(char ch, List<char[][]> formatedKey) {
        for (int square = 0; square < 3; square++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (ch == formatedKey.get(square)[i][j]) {
                        System.out.println(
                                ch + " square: " + (square + 1) + " columna: " + (i + 1) + " fila: " + (j + 1));
                    }
                }
            }
        }
    }

    private static List<List<String>> formatData(List<String> datosEncriptados) {
        List<List<String>> data = new ArrayList<>();
        for (String line : datosEncriptados) {
            data.add(Arrays.asList(line.split("(?<=\\G.{" + 7 + "})")));
        }
        return data;
    }

    public static List<String> leerTxt(String nombreArchivo) {
        List<String> datos = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
            String line;
            while ((line = br.readLine()) != null) {
                datos.add(line);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("no encontrado");
        }
        return datos;

    }

    private static void printData(List<List<String>> data) {
        for (List<String> line : data) {
            System.out.println(line.toString());
        }
    }

}