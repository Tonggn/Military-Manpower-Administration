package com.toggn.mma.enterprise.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.IOException;

public class EnterpriseClient {


    private static final String ALL_ROWS_SIZE = "99999";

    private EnterpriseClient() {
    }

    public static Document requestAllEnterprises(final String requestUrl, final String secretKey) {
        final String url = getRequestAllEnterprisesUri(requestUrl, secretKey);

        final Connection connect = Jsoup.connect(url);

        try {
            return Jsoup.parse(connect.get().html(), "", Parser.xmlParser());
        } catch (final IOException e) {
            throw new IllegalArgumentException("Invalid url: " + url, e);
        }
    }

    private static String getRequestAllEnterprisesUri(final String requestUrl, final String secretKey) {
        return requestUrl +
                "?ServiceKey=" +
                secretKey +
                "&numOfRows=" +
                ALL_ROWS_SIZE;
    }
}
