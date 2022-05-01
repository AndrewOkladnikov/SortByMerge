package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static com.company.Functions.*;

public class Main {

    public static void main(String[] args) throws IOException {

        Params params = new Params(args);
        params.setPartSize(7);
        String outputFilePath = params.getOutputFilePath();

        FileWriter fileWriter = new FileWriter(outputFilePath, false);
        fileWriter.close();
        // стирает предыдущий файл

        mergeArray(params);
    }

    public static void mergeArray(Params params) throws IOException {
        Box box = new Box(params);
        selectRightFiles(box);
        box.setPartIndexesArray(new int[box.getPartsListSize()] );
        while (!allFilesAreFinished(box.getReadersList()) && !allPartsAreFinished(box.getPartsList(), box.getPartIndexesArray())) {
            box.fillBox();
            box.sorting();
            writeSortedPartToFile(box.getSortedArray(), box.getOutputFile());
            box.getSortedArray().clear();
        }
    }

    private static boolean allPartsAreFinished(List<List<String>> currentFileParts, int[] partIndexes) {
        boolean allPartsFinished = true;
        for (int i = 0; i < partIndexes.length; i++) {
            if (currentFileParts.get(i) != null && partIndexes[i] != currentFileParts.get(i).size()) {
                allPartsFinished = false;
            }
        }
        return allPartsFinished;
    }

    private static boolean allFilesAreFinished(List<ICustomFileReader> fileReadArray) {
        ICustomFileReader[] fileReadersArray = fileReadArray.toArray(new ICustomFileReader[0]);
         //       fileReadArray.toArray(fileReadersArray);
        boolean allFilesFinished = true;
        for (int i = 0; i < fileReadersArray.length; i++) {
            if (fileReadersArray[i] != null && !fileReadersArray[i].isFileFinished()) {
                allFilesFinished = false;
            }
        }

        return allFilesFinished;
    }

    public static void writeSortedPartToFile(List<String> sortedPart, String outputFilePathName) throws IOException {
        //BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePathName)));

        FileWriter fileWriter = new FileWriter(outputFilePathName, true);
        for (var v : sortedPart) {
            fileWriter.write(v);
            fileWriter.write("\n");
        }
        fileWriter.close();
    }
}