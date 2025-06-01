package com.hugopalma.springinitializrmcp.utils;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
}
