package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ICustomFileReader {
    boolean isFileFinished();
    String getNextStringFromFile() throws IOException;
    List<String> readFromFile(int i) throws IOException;
    void setIndex(int i);
    int getIndex();
    List<String> getReadedStrings();
}