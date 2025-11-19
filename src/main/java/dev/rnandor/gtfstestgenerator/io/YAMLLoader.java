package dev.rnandor.gtfstestgenerator.io;

import dev.rnandor.gtfstestgenerator.model.yaml.*;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.InputStream;

public final class YAMLLoader {
    private YAMLLoader() {}

    public static YAMLRoot loadYAML(InputStream is) {
        var loaderOptions = new LoaderOptions();
        loaderOptions.setAllowDuplicateKeys(true);
        var constructor = new Constructor(YAMLRoot.class, loaderOptions);

        var dumperOptions = new DumperOptions();
        var representer = new Representer(dumperOptions);
        representer.getPropertyUtils().setSkipMissingProperties(true);

        return new Yaml(constructor, representer, dumperOptions).loadAs(is, YAMLRoot.class);
    }
}
