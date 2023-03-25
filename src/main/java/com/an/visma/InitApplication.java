package com.an.visma;

import java.io.IOException;

public class InitApplication {

    public static void main(String[] args) throws IOException {
        String content = Utils.readFileToString("data.csv");
        VismaApp app = new VismaApp(content);
    }

}
