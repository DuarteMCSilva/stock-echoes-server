package com.stockechoes.services.utility;

import java.io.*;
import java.util.*;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CsvReaderService {


    /**
     * Reads a csv file and returns the columns defined in metadata input;
     * */
    public List<List<String>> getTableFromCsv(InputStream file, List<String> metadata) {
        List<List<String>> table = new ArrayList<>();
        table.add(metadata);

        BufferedReader reader = new BufferedReader(new InputStreamReader(file));

        try {
            String[] headers = reader.readLine().split(",");
            List<Integer> indexList =  getMetadataIndexes(headers, metadata);

            String line;
            while((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                List<String> relevantValues = new ArrayList<>();
                for( int index: indexList) {
                    relevantValues.add(values[index]);
                }
                table.add(relevantValues);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return table;
    }


    private List<Integer> getMetadataIndexes(String[] headers, List<String> metadata) {
        List<Integer> indexList = new ArrayList<>();
        Map<String, Integer> columnIndexMap = new HashMap<>();
        for( int i = 0; i < headers.length; i++) {
            if( metadata.contains( headers[i].trim())) {
                indexList.add(i);
                columnIndexMap.put(headers[i], i);
            }
        }
        return indexList;
    }
}
