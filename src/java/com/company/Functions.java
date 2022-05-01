package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Functions {
    static boolean isAnyPartsFinished(int countOfFiles, int[] partPositions, List<List<String>> FileParts) {
        boolean anyPartsFinished = false;
        for (int i = 0; i < countOfFiles; i++) {
            if (FileParts.get(i) != null && partPositions[i] == FileParts.get(i).size()) {
                anyPartsFinished = true;
                break;
            }
        }
        return anyPartsFinished;
    }

    static Pair<Integer, String> GetMinimum(List<List<String>> filePartsArray, int[] partPositions, boolean isIntegerSorting) {
        Pair<Integer, String> minimum = null;

        for (int i = 0; i < filePartsArray.size(); i++) {
            if (filePartsArray.get(i) != null) {
                minimum = new Pair(i, filePartsArray.get(i).get(partPositions[i]));
                break;
            }
        }

        if (minimum == null) {
            return null;
        }

        for (int i = 0; i < filePartsArray.size(); i++) {
            if (filePartsArray.get(i) == null) {
                continue;
            }
            var currentPartValue = filePartsArray.get(i).get(partPositions[i]);
            if (!isIntegerSorting) {
                if (currentPartValue.compareTo(minimum.getValue()) < 0) {
                    minimum = new Pair(i, currentPartValue);
                }
            } else {
                if (Integer.parseInt(currentPartValue) < Integer.parseInt(minimum.getValue())) {
                    minimum = new Pair(i, currentPartValue);
                }
            }
        }
        return minimum;
    }

    static Pair<Integer, String> GetMaximum(List<List<String>> filePartsArray, int[] partPositions, boolean isIntegerSorting) {
        Pair<Integer, String> maximum = null;

        for (int i = 0; i < filePartsArray.size(); i++) {
            if (filePartsArray.get(i) != null) {
                maximum = new Pair(i, filePartsArray.get(i).get(partPositions[i]));
                break;
            }
        }

        if (maximum == null) {
            return null;
        }

        for (int i = 0; i < filePartsArray.size(); i++) {
            if (filePartsArray.get(i) == null) {
                continue;
            }
            var currentPartValue = filePartsArray.get(i).get(partPositions[i]);
            if (!isIntegerSorting) {
                if (currentPartValue.compareTo(maximum.getValue()) > 0) {
                    maximum = new Pair(i, currentPartValue);
                }
            } else {
                if (Integer.parseInt(currentPartValue) > Integer.parseInt(maximum.getValue())) {
                    maximum = new Pair(i, currentPartValue);
                }
            }
        }
        return maximum;
    }

    static void selectRightFiles(Box box) throws IOException {
        for (int i = 0; i < box.getFileNamesArray().size(); i++) {
            var file = new File(box.getFileNamesArray().get(i));
            ICustomFileReader fileReader;
            try {
                /* Формируем массив файловых ридеров */
                fileReader = getFileReader(box.isIntegerToSort(), file);
            } catch (FileNotFoundException ex) {
                //filePartsArray.add(null);
                System.out.println("Входной файл не найден: " + file.getName());
                continue;
            }
            fileReader.readFromFile(box.getPartSize());
            if (fileReader.getReadedStrings().size() == 0) {
                System.out.println("Входной файл пустой:  " + file.getName());
            }

            if (isGoodPart(fileReader.getReadedStrings(), box.isIntegerToSort(), box.isAscSorting()))
            {
                /* В этом блоке заполняем массивы правильными частями для последующей сортировки */
                box.addInReadersList(fileReader);
                continue;
            }
            System.out.println("Файл с неправильной сортировкой:  " + file.getName());
        }
    }

    static ICustomFileReader getFileReader(boolean isIntegerToSort, File namedFile) throws FileNotFoundException {
        ICustomFileReader fileReader;
        if (!isIntegerToSort) {
            fileReader = new StringFileReader(namedFile, null);
        } else {
            fileReader = new IntFileReader(namedFile, null);
        }
        fileReader.setIndex(0);
        return fileReader;
    }

    static boolean isGoodPart(List<String> valuesSetArray, boolean isIntegerSorting, boolean isAscendigSorting) {
        boolean errorPart = false;
        for (int j = 0; j < valuesSetArray.size() - 1; j++) {
            if (isAscendigSorting) {
                if (isIntegerSorting) {
                    if (Integer.parseInt(valuesSetArray.get(j)) > Integer.parseInt(valuesSetArray.get(j + 1))) {
                        return false;
                    }
                } else {
                    if (valuesSetArray.get(j).compareTo(valuesSetArray.get(j + 1)) > 0) {
                        return false;
                    }
                }
            } else {
                if (isIntegerSorting) {
                    if (Integer.parseInt(valuesSetArray.get(j)) < Integer.parseInt(valuesSetArray.get(j + 1))) {
                        return false;
                    }
                } else {
                    if (valuesSetArray.get(j).compareTo(valuesSetArray.get(j + 1)) < 0) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}