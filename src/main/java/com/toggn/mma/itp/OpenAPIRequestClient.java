package com.toggn.mma.itp;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OpenAPIRequestClient {

    private static final String ALL_ROWS_SIZE = "99999";

    @Value("${mma.itp.enterprise.client.requestUrl}")
    private String requestUrl;
    @Value("${mma.itp.enterprise.client.secretKey}")
    private String secretKey;

    public Document requestAllEnterprises() {
        final String url = requestUrl + "?ServiceKey=" + secretKey + "&numOfRows=" + ALL_ROWS_SIZE;

        final Connection connect = Jsoup.connect(url).maxBodySize(0);

        try {
            return Jsoup.parse(connect.get().html(), "", Parser.xmlParser());
        } catch (final IOException e) {
            throw new IllegalArgumentException("Invalid url: " + url, e);
        }
    }
}
