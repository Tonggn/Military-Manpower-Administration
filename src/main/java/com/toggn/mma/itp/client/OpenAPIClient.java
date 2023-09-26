package com.toggn.mma.itp.client;

import org.jsoup.nodes.Document;

public interface OpenAPIClient {

    String ALL_ROWS_SIZE = "99999";

    Document request();
}
