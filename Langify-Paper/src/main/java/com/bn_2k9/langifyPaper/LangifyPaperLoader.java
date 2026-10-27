package com.bn_2k9.langifyPaper;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jspecify.annotations.NonNull;

@SuppressWarnings({"UnstableApiUsage", "unused"})
public class LangifyPaperLoader implements PluginLoader {

    @Override
    public void classloader(final @NonNull PluginClasspathBuilder builder) {
        // Add dynamically loaded libraries here
        MavenLibraryResolver resolver = new MavenLibraryResolver();
        resolver.addRepository(new RemoteRepository.Builder("central", "default", MavenLibraryResolver.MAVEN_CENTRAL_DEFAULT_MIRROR).build());

        // Library used for web communication.
        resolver.addDependency(new Dependency(new DefaultArtifact("com.fasterxml.jackson.core:jackson-databind:3.2.4"), null));

        // Libraries required by the core.
        resolver.addDependency(new Dependency(new DefaultArtifact("org.yaml:snakeyaml:2.4"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("com.zaxxer:HikariCP:7.1.0"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("org.xerial:sqlite-jdbc:3.53.4.0"), null));

        builder.addLibrary(resolver);
    }
}
