package com.ufro;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> datosEncriptados = leerTxt("src\\main\\java\\com\\ufro\\datasets\\datos_encriptados.txt");
        List<String> key = leerTxt("src\\main\\java\\com\\ufro\\datasets\\key.txt");
        List<char[][]> formatedKey = formatKey(key);
        List<List<String>> formatedData = formatData(datosEncriptados);
        List<List<int[][]>> solutionMatrix = descifrarDataPart1(formatedData, formatedKey);
        descifrarDataPart2(solutionMatrix);
        List<List<String>> finalMessage = descifrarDatapart3(solutionMatrix, formatedKey);
        printFinalMessage(finalMessage);

    }

    private static List<List<String>> descifrarDatapart3(List<List<int[][]>> solutionMatrix,
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

    private static void descifrarDataPart2(List<List<int[][]>> solutionMatrix) {
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
        System.out.println("Part3: ");
        printSolutionMatrix(solutionMatrix);
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

    private static List<List<int[][]>> descifrarDataPart1(List<List<String>> formatedData, List<char[][]> formatedKey) {
        List<List<int[][]>> solutionMatrix = new ArrayList<>();
        for (List<String> line : formatedData) {
            List<int[][]> lineList = new ArrayList<>();
            for (String section : line) {
                int[][] sectionMatrix = new int[3][section.length()];
                for (int i = 0; i < section.length(); i++) {
                    char ch = section.charAt(i);
                    solutionMatrixStep1(ch, formatedKey, sectionMatrix, i);
                }
                lineList.add(sectionMatrix);
            }
            solutionMatrix.add(lineList);
        }
        // System.out.println("Part2: ");
        // printSolutionMatrix(solutionMatrix);
        return solutionMatrix;
    }

    private static void solutionMatrixStep1(char ch, List<char[][]> formatedKey, int[][] sectionMatrix,
            int matrixHorizontalIndex) {
        for (int square = 0; square < 3; square++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (ch == formatedKey.get(square)[i][j]) {
                        // System.out.println(ch + " square: " + (square + 1) + " columna: " + (i + 1) +
                        // " fila: " + (j + 1));
                        sectionMatrix[0][matrixHorizontalIndex] = square + 1; // square
                        sectionMatrix[1][matrixHorizontalIndex] = i + 1; // fila
                        sectionMatrix[2][matrixHorizontalIndex] = j + 1; // columna
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

    private static void printFinalMessage(List<List<String>> data) {
        for (List<String> line : data) {
            String thisLine = "";
            for (String str : line) {
                thisLine += str;
            }
            System.out.println(thisLine.replace('+', ' '));
        }
    }

    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void printSolutionMatrix(List<List<int[][]>> solutionMatrix) {
        for (List<int[][]> lineList : solutionMatrix) {
            System.out.println("Line: ");
            for (int[][] sectionMatrix : lineList) {
                printMatrix(sectionMatrix);
                System.out.println();
            }
            System.out.println();
        }
    }

}