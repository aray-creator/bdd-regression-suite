package utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class CsvReader {

    public static Map<String, String> getRow(String fileName, String testId) {
        String path = "/testdata/" + fileName;
        try (InputStream is = CsvReader.class.getResourceAsStream(path);
             CSVParser parser = CSVFormat.DEFAULT.builder()
                     .setHeader().setSkipHeaderRecord(true).setTrim(true).build()
                     .parse(new InputStreamReader(is))) {

            for (CSVRecord record : parser) {
                if (record.get("test_id").equals(testId)) {
                    return record.toMap();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not read CSV: " + fileName, e);
        }
        throw new RuntimeException("Test ID not found in CSV: " + testId);
    }
}