package com.hugopalma.springinitializrmcp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class FileAndDownloadUtils {
    /**
     * Download the file from the given URL to the given destination path.
     *
     * @param artifactId            the id of the artefact to download.
     * @param url                   the url of the file to download.
     * @param destinationFolderPath the path where the file will be downloaded.
     * @return the path of the downloaded file.
     * @throws Exception if an error occurs during the download.
     */
    public static Path downloadFile(String artifactId, String url, String destinationFolderPath) throws Exception {
        Path destinationFolder = Paths.get(destinationFolderPath);
        Files.createDirectories(destinationFolder);

        try (HttpClient client = HttpClient.newHttpClient();
             InputStream in = client.send(
                     HttpRequest.newBuilder().uri(URI.create(url)).build(),
                     HttpResponse.BodyHandlers.ofInputStream()).body()) {

            // Simple approach: use a fixed filename or extract from URL
            String filename = artifactId + ".zip";
            Path filePath = destinationFolder.resolve(filename);
            Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath;
        }
    }

    /**
     * Extracts a zip file and returns the path where it was extracted.
     *
     * @param zipFilePath Path to the zip file to extract
     * @return Path to the directory where the zip contents were extracted
     * @throws IOException              if an I/O error occurs during extraction
     * @throws IllegalArgumentException if the zip file doesn't exist or isn't a file
     */
    public static Path extractZip(Path zipFilePath) throws IOException {
        // Validate input
        if (!Files.exists(zipFilePath)) {
            throw new IllegalArgumentException("Zip file does not exist: " + zipFilePath);
        }
        if (!Files.isRegularFile(zipFilePath)) {
            throw new IllegalArgumentException("Path is not a file: " + zipFilePath);
        }

        // Get the directory where the zip file is located
        Path zipFileDirectory = zipFilePath.getParent();
        if (zipFileDirectory == null) {
            zipFileDirectory = Paths.get("").toAbsolutePath();
        }

        // Create extraction directory based on zip file name (without .zip extension)
        String zipFileName = zipFilePath.getFileName().toString();
        String extractionDirName = zipFileName.replaceAll("\\.zip$", "");
        Path extractionPath = zipFileDirectory.resolve(extractionDirName);

        // Extract the zip file
        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFilePath))) {
            ZipEntry entry;

            while ((entry = zipInputStream.getNextEntry()) != null) {
                Path entryPath = extractionPath.resolve(entry.getName());

                // Security check: prevent zip slip attacks
                if (!entryPath.normalize().startsWith(extractionPath.normalize())) {
                    throw new IOException("Entry is outside of the target directory: " + entry.getName());
                }

                if (entry.isDirectory()) {
                    // Create directory
                    Files.createDirectories(entryPath);
                } else {
                    // Create parent directories if they don't exist
                    Files.createDirectories(entryPath.getParent());

                    // Extract file
                    Files.copy(zipInputStream, entryPath, StandardCopyOption.REPLACE_EXISTING);
                }

                zipInputStream.closeEntry();
            }
        }

        return extractionPath;
    }
}
