module dev.rnandor.gtfstestgenerator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires static lombok;
    requires org.yaml.snakeyaml;
    requires univocity.parsers;

    opens dev.rnandor.gtfstestgenerator.controllers to javafx.fxml;

    opens dev.rnandor.gtfstestgenerator.model.inner to univocity.parsers;
    opens dev.rnandor.gtfstestgenerator.model.yaml to org.yaml.snakeyaml;

    exports dev.rnandor.gtfstestgenerator;
    exports dev.rnandor.gtfstestgenerator.controllers;
    exports dev.rnandor.gtfstestgenerator.model.inner;
    exports dev.rnandor.gtfstestgenerator.model.yaml;
}