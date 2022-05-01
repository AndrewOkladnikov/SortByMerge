package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IntFileReader implements ICustomFileReader {

    private BufferedReader bufferedReader;
    private Integer maxSize;
    private boolean isFinished = false;
    private int index=0;
    private List<String> readedStrings = new ArrayList<>();

    public int getIndex() {
        return index;
    }


    public void setIndex(int index) {
        this.index = index;
    }

    public List<String> getReadedStrings() {
        return readedStrings;
    }

    // Если передать maxSize == null, то за памятью класс следить не будет
    public IntFileReader(File file, Integer maxSize) throws FileNotFoundException {
        if (file == null) // или exist?
        {
            throw new NullPointerException(); // узнать какой exception нужен
        }
        bufferedReader = new BufferedReader(new FileReader(file));
        this.maxSize = maxSize;
    }

    public boolean isFileFinished()
    {
        return isFinished;
    }

    @Override
    public String getNextStringFromFile() throws IOException {
        var validCharactersString = new StringBuilder();

        boolean isFaulted = false;
        while (true) {
            var nextChar = bufferedReader.read();
            //bufferedReader.
            if (nextChar == -1) {
                isFinished = true;
                break;
            }
            if (!isFaulted && nextChar >= '0' && nextChar <= '9') {
                validCharactersString.append((char)nextChar);

                if (maxSize != null && validCharactersString.length() > maxSize) {
                    validCharactersString.setLength(0);
                    isFaulted = true;
                }

                continue;
            }
            if (nextChar == '\n') {
                break;
            }
            if (nextChar == 13) {
                continue;
            }

            isFaulted = true;
        }
        String resultedCharacterString= null;

        try {
            //var resultString = validCharactersString.toString();
            resultedCharacterString = validCharactersString.toString();
            if (resultedCharacterString.equals(""))
            {
                return null;
            }
            //resultedCharacterString = resultString;
        }
        catch (Exception ex)
        {
            isFaulted = true;
        }
        if (isFaulted)
        {
            return null;
        }
        return resultedCharacterString;
    }



    public List<String> readFromFile(int size) throws IOException {
        //List<String> setLoadedStringsArray = new ArrayList<String>();
        readedStrings.clear();
//        if (fileReader == null) {
//            return null;
//        }
        while (!isFileFinished() && readedStrings.size() < size) {
            try {
                var nextLoadedStringFromFile = getNextStringFromFile();
                if (nextLoadedStringFromFile == null && isFileFinished()) {
                    break;
                }
                if (nextLoadedStringFromFile != null) {
                    readedStrings.add(nextLoadedStringFromFile);
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
              }

        }

        return readedStrings;
    }
}