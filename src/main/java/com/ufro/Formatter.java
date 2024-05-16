package com.ufro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Formatter {

    public List<char[][]> formatKey(List<String> key) {
        char[][] square1 = getSquareFromKey(key, 3, 2);
        char[][] square2 = getSquareFromKey(key, 3, 12);
        char[][] square3 = getSquareFromKey(key, 3, 22);
        List<char[][]> formatedKey = new ArrayList<>();
        formatedKey.add(square1);
        formatedKey.add(square2);
        formatedKey.add(square3);
        return formatedKey;
    }

    public char[][] getSquareFromKey(List<String> key, int lineKey, int indexKey) {
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

    public void solutionMatrixStep1(char ch, List<char[][]> formatedKey, int[][] sectionMatrix,
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

    public List<List<String>> formatData(List<String> datosEncriptados) {
        List<List<String>> data = new ArrayList<>();
        for (String line : datosEncriptados) {
            data.add(Arrays.asList(line.split("(?<=\\G.{" + 7 + "})")));
        }
        return data;
    }

    public void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printFinalMessage(List<List<String>> data) {
        for (List<String> line : data) {
            String thisLine = "";
            for (String str : line) {
                thisLine += str;
            }
            System.out.println(thisLine.replace('+', ' '));
        }
    }


    public void printSolutionMatrix(List<List<int[][]>> solutionMatrix) {
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
