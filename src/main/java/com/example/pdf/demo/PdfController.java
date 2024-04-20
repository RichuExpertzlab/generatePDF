package com.example.pdf.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class PdfController {

    @Autowired
    private PdfGenerationService pdfGenerationService;

    @GetMapping("/generate-pdf")
    public String generatePdf() {
        String filename = "example.pdf";
        pdfGenerationService.generatePdf(filename, "Hello, this is a PDF generated using Apache PDFBox in Spring Boot.");
        return "PDF generated successfully! <br><a href=\"/download-pdf\">Download PDF</a>";
    }

    @GetMapping("/download-pdf")
    public ResponseEntity<Resource> downloadPdf() throws MalformedURLException {
        Path pdfPath = Paths.get("example.pdf");
        Resource resource = new UrlResource(pdfPath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
