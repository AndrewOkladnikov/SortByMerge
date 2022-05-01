package com.company;

import java.util.ArrayList;

public class Params {
    private String outputFilePath;
    private int filesCount;
    private boolean isSortByAscending = true;
    private boolean isIntegerToSort = true;
    private ArrayList<String> fileNamesArray;
    private int partSize;

    public int getPartSize() {
        return partSize;
    }

    public void setPartSize(int partSize) {
        this.partSize = partSize;
    }

    public Params(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].getBytes()[0] != '-') {
                outputFilePath = args[i];
                filesCount = args.length - 1 - i;
                if (filesCount < 2) {
                    System.out.println("Входных файлов должно быть не меньше 2-х!!!!");
                    return;
                }
                break;
            }
        }
        if (args[0].compareTo("-d") == 0 || args[1].compareTo("-d") == 0) {
            isSortByAscending = false;
        }

        if (args[0].compareTo("-s") == 0 || args[1].compareTo("-s") == 0) {
            isIntegerToSort = false;
        }
        fileNamesArray = GetInputFilesFromArgs(args);
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public int getFilesCount() {
        return filesCount;
    }

    public boolean isSortByAscending() {
        return isSortByAscending;
    }

    public boolean isIntegerToSort() {
        return isIntegerToSort;
    }

    public ArrayList<String> getFileNamesArray() {
        return fileNamesArray;
    }

    public ArrayList<String> GetInputFilesFromArgs(String[] args) {
        ArrayList<String> filesArray = new ArrayList<>();
        int filesAmount = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i].getBytes()[0] != '-') {
                filesAmount = args.length - 1 - i;
                break;
            }
            ;
        }
        for (int i = 1; i <= filesAmount; i++) {
            filesArray.add(args[args.length - i]);
        }
        return filesArray;
    }
}