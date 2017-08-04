package de.host.model;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Created by yevheniia on 04.08.17
 */
class Resources {
    static Path getAncestorResource(String filename) {
        return Optional.ofNullable(new File("").getAbsoluteFile().toPath().resolve(filename).toFile())
                .filter(File::exists).map(File::toPath).orElseThrow(() -> new AssertionError("File " + filename + " does not exist"));
    }
}
