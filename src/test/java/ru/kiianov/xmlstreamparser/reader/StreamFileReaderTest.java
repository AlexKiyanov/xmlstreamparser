package ru.kiianov.xmlstreamparser.reader;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StreamFileReaderTest {
    private final FileReader reader = new StreamFileReader();

    @Test
    void readShouldThrowExceptionIfFilePathIsWrong() {
        final Throwable exception = assertThrows(IllegalArgumentException.class, () -> reader.read("rgsgrtg"));
        assertEquals("Bad filePath. Please check it.", exception.getMessage());
    }

    @Test
    void readShouldNotThrowExceptionForCorrectFilePath() {
        assertDoesNotThrow(() -> reader.read("src/test/resources/test.xml"));
    }
}
