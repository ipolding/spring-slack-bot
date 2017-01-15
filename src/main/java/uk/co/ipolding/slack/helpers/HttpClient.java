package uk.co.ipolding.slack.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

public class HttpClient {

    public static Optional<byte[]> getUrl(String url) throws IOException {
        byte[] response;
        URLConnection urlConnection = new URL(url).openConnection();
        urlConnection.connect();

        try (InputStream inputStream = urlConnection.getInputStream();
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {
            int data;
            while ((data = inputStream.read()) != -1) {
                byteArrayOutputStream.write(data);
            }
            return Optional.of(byteArrayOutputStream.toByteArray());
        }
    }
}
