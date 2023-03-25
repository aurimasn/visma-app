package com.an.visma;

import java.io.IOException;

public class InitApplication {

    public static void main(String[] args) throws IOException {
        VismaApp app = new VismaApp();
        String content = Utils.readFileToString("data.csv");
    }

}
