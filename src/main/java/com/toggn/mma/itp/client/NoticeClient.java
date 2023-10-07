package com.toggn.mma.itp.client;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NoticeClient implements OpenAPIClient {

    @Value("${mma.itp.notice.client.requestUrl}")
    private String requestUrl;
    @Value("${mma.itp.notice.client.secretKey}")
    private String secretKey;

    @Override
    public Document request() {
        final String url = requestUrl + "?ServiceKey=" + secretKey + "&numOfRows=" + ALL_ROWS_SIZE;

        final Connection connect = Jsoup.connect(url)
                .timeout(60 * 1000)
                .maxBodySize(0);

        try {
            return Jsoup.parse(connect.get().html(), "", Parser.xmlParser());
        } catch (final IOException e) {
            throw new IllegalArgumentException("Invalid url: " + url, e);
        }
    }
}
