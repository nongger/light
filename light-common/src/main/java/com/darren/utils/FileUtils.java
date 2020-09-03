package com.darren.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * Created by Darren on 2017/7/25.
 */
public class FileUtils {

    public static String getFileContent(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource(filePath).getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
