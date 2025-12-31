package org.diegodamian.ocp17.webpage.service;

import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.parser.Parser;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class MarkdownService {

    private static final String DOCS_PATH = "classpath:docs/**/*.md";

    private final ResourcePatternResolver resourceResolver;
    private final Parser parser;
    private final HtmlRenderer renderer;

    public MarkdownService(ResourcePatternResolver resourceResolver) {
        this.resourceResolver = resourceResolver;

        List<org.commonmark.Extension> extensions = List.of(TablesExtension.create());

        this.parser = Parser.builder()
                .extensions(extensions)
                .build();
        this.renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .build();
    }

    public List<DocumentInfo> getAllDocuments() throws IOException {
        Resource[] resources = resourceResolver.getResources(DOCS_PATH);

        return Arrays.stream(resources)
                .map(this::createDocumentInfo)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(DocumentInfo::displayName))
                .toList();
    }

    private DocumentInfo createDocumentInfo(Resource resource) {
        try {
            String fileName = resource.getFilename();
            if (fileName == null) return null;

            String name = fileName
                    .replace(".md", "")
                    .replaceAll("^M[a-z0-9]+_", "")
                    .replaceAll("_", " ")
                    .trim();

            return new DocumentInfo(fileName, name, "/docs/" + fileName);
        } catch (Exception e) {
            return null;
        }
    }

    public String getHtmlContent(String fileName) throws IOException {

        // Buscamos en todas las sub carpetas
        Resource[] resources = resourceResolver.getResources("classpath:docs/**/*.md");

        for (Resource resource : resources) {
            String currentPath = resource.getURL().getPath();
            if (currentPath.endsWith("/" + fileName)) {
                String markdown = new String(
                        resource.getInputStream().readAllBytes(),
                        StandardCharsets.UTF_8
                );
                Node document = parser.parse(markdown);
                return renderer.render(document);
            }
        }

        throw new FileNotFoundException("No se encontró archivo: " + fileName);
    }

    public record DocumentInfo(String fileName, String displayName, String url) {}
}
