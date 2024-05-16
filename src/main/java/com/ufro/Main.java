package com.ufro;

public class Main {
    public static void main(String[] args) {
        String inputPath = args[0];
        String outputPath = "";

        Decipher decipher = new Decipher(inputPath, outputPath);
        decipher.decipher();
    }
}