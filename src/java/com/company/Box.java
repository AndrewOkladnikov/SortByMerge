package com.company;
//import ru.test1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.company.Functions.*;

public class Box {
    private List<ICustomFileReader> readersList = new ArrayList<>();
    private List<List<String>> partsList = new ArrayList<>();
    //private List<Element> elementList = new ArrayList<>();
    private List<String> fileNamesArray = new ArrayList<>();
    private int[] partIndexesArray;
    private Params params;
    private int partSize;
    private boolean isAscSorting;
    private boolean isIntegerToSort;
    private List<String> sortedArray = new ArrayList<String>();
    private String outputFile;

    /*Конструктор*/
    public Box(Params params) {
        this.params = params;
        isAscSorting = params.isSortByAscending();
        this.partSize = params.getPartSize();
        isAscSorting = params.isSortByAscending();
        isIntegerToSort = params.isIntegerToSort();
        fileNamesArray = params.getFileNamesArray();
        outputFile = params.getOutputFilePath();
    }

    public String getOutputFile() {
        return outputFile;
    }

    public List<String> getSortedArray() {
        //readersList.stream().filter()
        return sortedArray;
    }

    public void sorting(){
        while (!isAnyPartsFinished(getPartsListSize(), getPartIndexesArray(), getPartsList())) {
            Pair<Integer, String> minOrMaxValuePairsArray;
            if (isAscSorting()) {
                minOrMaxValuePairsArray = GetMinimum(getPartsList(), getPartIndexesArray(), isIntegerToSort());
            } else {
                minOrMaxValuePairsArray = GetMaximum(getPartsList(), getPartIndexesArray(), isIntegerToSort());
            }
            if (minOrMaxValuePairsArray == null) {
                break;
            }
            getSortedArray().add(minOrMaxValuePairsArray.getValue());
            getPartIndexesArray()[minOrMaxValuePairsArray.getKey()]++;
        }
    }

    public void fillBox() throws IOException {
        for (int i = 0; i < getPartsListSize(); i++) {
            if (getReadersList().get(i) == null || getReadersList().get(i).isFileFinished()) {
                if (getPartsList().get(i) != null && getPartIndexesArray()[i] ==  getPartsList().get(i).size()) {
                    getPartsList().set(i, null);
                }
                continue;
            }
            boolean partHasEnded = getPartIndexesArray()[i] == getPartsList().get(i).size();
            if (partHasEnded) {

                var newFilePart = getReadersList().get(i).readFromFile(getPartSize());
                //var newFilePart = box.getReadersList().get(i).getReadedStrings();
                getPartsList().set(i, (ArrayList<String>) newFilePart);
                getPartIndexesArray()[i] = 0;
            }
        }
    }

    public boolean isAscSorting() {
        return isAscSorting;
    }

    public boolean isIntegerToSort() {
        return isIntegerToSort;
    }

    public int getPartSize() {
        return partSize;
    }

    public Params getParams() {
        return params;
    }

    public int[] getPartIndexesArray() {
        return partIndexesArray;
    }

    public void setPartIndexesArray(int[] partIndexesArray) {
        this.partIndexesArray = partIndexesArray;
    }

    public List<String> getFileNamesArray() {
        return fileNamesArray;
    }

    public List<List<String>> getPartsList() {
        return partsList;
    }
//    public int getReadersListSize(){
//        return readersList.size();
//    }
    public int getPartsListSize(){
        return partsList.size();
    }

    public void addInReadersList(ICustomFileReader fReader){
        readersList.add(fReader);
        partsList.add(fReader.getReadedStrings());
    }
//    public void addInPartsList(List<String> part){
//        partsList.add(part);
//    }
    public List<ICustomFileReader> getReadersList() {
        return readersList;
    }

    public Pair<Integer, String> getMaxInt(){
        Pair<Integer, String> maximum = null;
        for (int i = 0; i < partsList.size(); i++) {
            if (partsList.get(i) != null) {
                maximum = new Pair(i,getMaximum(i));
                break;
            }
        }
        if (maximum == null) { return null; }
        for (int i = 0; i < partsList.size(); i++) {
            if (partsList.get(i) == null) {
                continue;
            }
            var currentPartValue = partsList.get(i).get(readersList.get(i).getIndex());
            if (Integer.parseInt(currentPartValue) > Integer.parseInt(maximum.getValue())) {
                maximum = new Pair(i,getMaximum(i));
            }
        }

        return maximum;
    }

    private String getMaximum(int i) {
        return partsList.get(i).get(readersList.get(i).getIndex());
    }

    private String getMinimum(int i) {
        return partsList.get(i).get(readersList.get(i).getIndex());
    }

    public Pair<Integer, String> getMinInt(){
        Pair<Integer, String> minimum=null;
        for (int i = 0; i < partsList.size(); i++) {
            if (partsList.get(i) != null) {
                minimum = new Pair(i,getMinimum(i));
                break;
            }
        }
        if (minimum == null) {
            return null;
        }
        for (int i = 0; i < partsList.size(); i++) {
            if (partsList.get(i) == null) {
                continue;
            }
            var currentPartValue = partsList.get(i).get(readersList.get(i).getIndex());
            if (Integer.parseInt(currentPartValue) < Integer.parseInt(minimum.getValue())) {
                minimum = new Pair(i,getMinimum(i));
            }
        }
        return minimum;
    }
}