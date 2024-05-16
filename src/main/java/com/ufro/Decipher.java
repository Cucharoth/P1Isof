package com.ufro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Decipher {
    List<String> datosEncriptados;
    List<String> key;
    private Formatter formatter = new Formatter();
    private FileHandler fileHandler = new FileHandler();

    public Decipher(String inputPath, String outputPath) {
        this.datosEncriptados = fileHandler.leerTxt(inputPath + "/datos_encriptados.txt");
        this.key = fileHandler.leerTxt(inputPath + "/key.txt");
    }

    public void decipher() {
        if (!this.datosEncriptados.isEmpty() && !this.key.isEmpty()) {
            List<char[][]> formatedKey = formatter.formatKey(key);
            List<List<String>> formatedData = formatter.formatData(datosEncriptados);
            List<List<int[][]>> solutionMatrix = descifrarDataPart1(formatedData, formatedKey);
            descifrarDataPart2(solutionMatrix);
            List<List<String>> finalMessage = descifrarDatapart3(solutionMatrix, formatedKey);
            fileHandler.printTxt(finalMessage);
            // formatter.printFinalMessage(finalMessage);
        } else
            System.out.println("Archivos no encontrados.");

    }

    private List<List<int[][]>> descifrarDataPart1(List<List<String>> formatedData, List<char[][]> formatedKey) {
        List<List<int[][]>> solutionMatrix = new ArrayList<>();
        for (List<String> line : formatedData) {
            List<int[][]> lineList = new ArrayList<>();
            for (String section : line) {
                int[][] sectionMatrix = new int[3][section.length()];
                for (int i = 0; i < section.length(); i++) {
                    char ch = section.charAt(i);
                    formatter.solutionMatrixStep1(ch, formatedKey, sectionMatrix, i);
                }
                lineList.add(sectionMatrix);
            }
            solutionMatrix.add(lineList);
        }
        return solutionMatrix;
    }

    private void descifrarDataPart2(List<List<int[][]>> solutionMatrix) {
        List<List<int[][]>> solutionMatrixPart2 = new ArrayList<>(); // TODO:!
        for (List<int[][]> line : solutionMatrix) {
            for (int[][] section : line) {
                int[][] copyOfSection = new int[3][section[0].length];
                for (int i = 0; i < section.length; i++) {
                    copyOfSection[i] = Arrays.copyOf(section[i], section[i].length);
                }
                for (int i = 0, oldI = 0, oldJ = 0; i < 3; i++) {
                    for (int j = 0; j < section[0].length; j++, oldI++) {
                        section[i][j] = copyOfSection[oldI][oldJ];
                        if (oldI == 2) {
                            oldI = -1;
                            oldJ++;
                        }
                    }
                }
            }
        }
        // formatter.printSolutionMatrix(solutionMatrix);
    }

    private List<List<String>> descifrarDatapart3(List<List<int[][]>> solutionMatrix,
            List<char[][]> formatedKey) {
        List<List<String>> finalMessage = new ArrayList<>();
        int square = 0, fila = 0, columna = 0;
        char ch = ' ';
        for (List<int[][]> line : solutionMatrix) {
            List<String> lineList = new ArrayList<>();
            for (int[][] section : line) {
                String word = "";
                for (int index = 0; index < section[0].length; index++) {
                    square = section[0][index];
                    fila = section[1][index];
                    columna = section[2][index];
                    ch = formatedKey.get(square - 1)[fila - 1][columna - 1];
                    word += String.valueOf(ch);
                }
                lineList.add(word);
            }

            finalMessage.add(lineList);
        }
        return finalMessage;
    }

}
