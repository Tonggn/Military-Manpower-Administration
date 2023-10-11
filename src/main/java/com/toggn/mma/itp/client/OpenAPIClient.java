package com.toggn.mma.itp.client;

import org.jsoup.nodes.Document;

public interface OpenAPIClient {

    String ALL_ROWS_SIZE = "99999";
    int MAXIMUM_TRY_COUNT = 5;
    int TIMEOUT_MILLI_SEC = 60 * 1000;
    int MAX_BODY_SIZE = 0;

    Document request();
}
