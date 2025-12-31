package org.diegodamian.ocp17.webpage.controller;

import org.diegodamian.ocp17.webpage.service.MarkdownService;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.ui.Model;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class DocsController {

    private final MarkdownService markdownService;

    public DocsController(MarkdownService markdownService) {
        this.markdownService = markdownService;
    }

    // Lista todo los markdown disponibles

    @GetMapping({"/", "/index", "/home"})
    public String index(Model model) throws IOException {
        model.addAttribute("documents", markdownService.getAllDocuments());
        return "index";
    }

    // Ver un markdown específico

    @GetMapping("/docs/{fileName:.+}")
    public String viewDocument(@PathVariable String fileName, Model model) throws IOException {
        String html = markdownService.getHtmlContent(fileName);
        model.addAttribute("content", html);
        model.addAttribute("title", formatTitle(fileName));
        return "view";
    }

    // Genera el pdf del markdown solicitado

    @GetMapping(value = "/docs/{fileName:.+}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> exportToPdf(@PathVariable String fileName) throws Exception {
        String html = markdownService.getHtmlContent(fileName);

        // Leer CSS desde resources/static/css/github-markdown.css y embeberlo para que OpenHTMLToPDF lo vea
        String css = "";
        try (InputStream is = getClass().getResourceAsStream("/static/css/github-markdown.css")) {
            if (is != null) {
                css = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
        }

        // Aquí puedes envolver el contenido en una plantilla más adecuada para PDF
        String fullHtml = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8"/>
                    <style>%s
                        body { margin: 4cm 2cm 2cm 4cm; font-size: 11pt; }
                        .markdown-body { box-shadow: none; }
                    </style>
                </head>
                <body class="markdown-body">
                    %s
                </body>
                </html>
                """.formatted(css, html);

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(fullHtml, null);
            builder.toStream(os);
            builder.run();

            byte[] pdfBytes = os.toByteArray();

            // Sanitizar el nombre de archivo para el header
            String safeName = fileName.replaceAll("[^a-zA-Z0-9._-]", "_");

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"%s.pdf\"".formatted(safeName))
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        }
    }

    // Métodos auxiliares

    private String formatTitle(String fileName) {
        return fileName.replace(".md", "")
                .replaceAll("^[Mm][0-9]+[_-]", "")
                .replace("_", " ")
                .trim();
    }
}