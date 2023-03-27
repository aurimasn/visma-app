package com.an.visma;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class Utils {

    /*
    Tries to read data file from project root directory if not found then from root package directory (resources)
    If started in jar then tries to read from jar's directory if not found then checks inside jar's root
     */
    public static String readFileToString(String fileName) throws IOException {
        var filePath = Path.of(new File("").getAbsolutePath() + "/" + fileName);
        try {
            String content = Files.readString(filePath);
            System.out.println("-- reading from " + filePath);
            return content;
        }catch (IOException ioe) {
            System.out.println("-- reading from " + filePath + " - NOT FOUND");
            var classloader = Thread.currentThread().getContextClassLoader();
            var url = Optional.ofNullable(classloader.getResource(fileName));
            if (url.isPresent()) {
                System.out.println("-- reading from " + url.get().getFile());
                var is = classloader.getResourceAsStream(fileName);
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            } else {
                var error = "";
                if (Optional.ofNullable(classloader.getResource(".")).isPresent())
                    error = "-- reading from " + classloader.getResource(".").getFile() + fileName +" - NOT FOUND";
                else
                    error = "-- couldn't find " +fileName;
                throw new IOException(error);
            }
        }
    }

}
