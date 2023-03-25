package com.an.visma;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Utils {

    public static String readFileToString(String fileName) throws IOException {
        var filePath = Path.of(new File("").getAbsolutePath() + "/" + fileName);
        return Files.readString(filePath);
    }

}
