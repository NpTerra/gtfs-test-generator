package dev.rnandor.gtfstestgenerator;

import dev.rnandor.gtfstestgenerator.io.TestCaseWriter;
import dev.rnandor.gtfstestgenerator.io.YAMLLoader;
import dev.rnandor.gtfstestgenerator.mapper.RootMapper;
import dev.rnandor.gtfstestgenerator.model.yaml.YAMLRoot;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Comparator;

public class GTFSApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(GTFSApplication.class.getResource("ui/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);*/

        File testDir = new File("tests");
        if(!testDir.exists())
            testDir.mkdir();

        purgeDirectory(Path.of("generated"));

        Files.copy(getClass().getResource("examples/input.yaml").openStream(), testDir.toPath().resolve("example.yaml"), StandardCopyOption.REPLACE_EXISTING);

        var testsDir = new File("tests");
        for(var testFile : testsDir.listFiles()) {
            if(testFile.isDirectory()) continue;
            if(!testFile.getName().endsWith(".yaml")) continue;

            YAMLRoot yaml = null;
            try(var root = Files.newInputStream(testFile.toPath())) {
                yaml = YAMLLoader.loadYAML(root);
                System.out.printf("Loaded YAML root: %s%n", yaml.toString());
            }
            catch (Exception e) {
                System.err.printf("Failed to load YAML root.%nError: %s%n", e.getMessage());
                System.exit(1);
            }

            var test = RootMapper.parseYAML(yaml);
            TestCaseWriter.writeTestCaseToFile(test);
        }

        stage.show();
        stage.close();
    }

    private void purgeDirectory(Path dir) throws IOException {
        if(!Files.exists(dir)) return;

        try(var stream = Files.walk(dir)) {
            stream.sorted(Comparator.reverseOrder()) // delete children first
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        }
    }
}
