package com.an.visma;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UtilsTest {

    @Test
    public void testVismaApp_readDataFileOk() {
        var fileName = "test_data.csv";
        var content = assertDoesNotThrow(() -> Utils.readFileToString(fileName));
        assertNotNull(content);
    }

}