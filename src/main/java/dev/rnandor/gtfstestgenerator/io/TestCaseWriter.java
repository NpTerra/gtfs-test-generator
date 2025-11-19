package dev.rnandor.gtfstestgenerator.io;

import dev.rnandor.gtfstestgenerator.model.inner.TestCase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public final class TestCaseWriter {
    private TestCaseWriter() {}

    public static void writeTestCaseToFile(TestCase test) throws IOException {
        var dir = new File("generated/" + test.getName());
        if(!dir.exists())
            dir.mkdirs();
        dir.listFiles(File::delete);

        // -----------
        //   Comment
        // -----------
        Files.writeString(dir.toPath().resolve("comment.txt"), test.getDescription()+"\n");

        // -------
        //   Act
        // -------
        Files.writeString(dir.toPath().resolve("act.txt"), test.getAct()+"\n");

        // -----------
        //   Arrange
        // -----------
        GTFSModelWriter.writeModelToFile(test.getArrange(), dir.toPath(), "arrange.zip");

        // ----------
        //   Assert
        // ----------
        if(test.getException() != null)
            Files.writeString(dir.toPath().resolve("assert.txt"), test.getException()+"\n");
        else
            GTFSModelWriter.writeModelToFile(test.getExpected(), dir.toPath(), "assert.zip");
    }
}
