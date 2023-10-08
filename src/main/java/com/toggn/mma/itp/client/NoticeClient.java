package com.toggn.mma.itp.client;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class NoticeClient implements OpenAPIClient {

    private static final Logger log = getLogger(NoticeClient.class);

    @Value("${mma.itp.notice.client.requestUrl}")
    private String requestUrl;
    @Value("${mma.itp.notice.client.secretKey}")
    private String secretKey;

    @Override
    public Document request() {
        final String url = requestUrl + "?ServiceKey=" + secretKey + "&numOfRows=" + ALL_ROWS_SIZE;

        for (int i = 0; i < MAXIMUM_TRY_COUNT; i++) {
            final Connection connect = getConnection(url);

            try {
                return Jsoup.parse(connect.get().html(), "", Parser.xmlParser());
            } catch (final SocketException e) {
                log.error("NoticeClient request failed by SocketException");
            } catch (final SocketTimeoutException e) {
                log.error("NoticeClient request failed by SocketTimeoutException");
            } catch (final IOException e) {
                log.error("NoticeClient request failed by IOException");
            }
        }

        throw new RuntimeException("NoticeClient request fail");
    }

    private Connection getConnection(final String url) {
        return Jsoup.connect(url)
                .timeout(TIMEOUT_MILLI_SEC)
                .maxBodySize(MAX_BODY_SIZE);
    }
}
