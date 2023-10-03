package com.toggn.mma.itp.enterprise.parser;

import com.toggn.mma.itp.enterprise.parser.dto.EnterpriseParseResponse;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class EnterpriseParser {

    private static final String ITEM_SELECTOR = "item";
    private static final String NAME_SELECTOR = "eopcheNm";
    private static final String BUSINESS_TYPE_CODE_SELECTOR = "eopjongGbcd";
    private static final String WEBSITE_URL_SELECTOR = "hmpgAddr";
    private static final String ADDRESS_SELECTOR = "eopcheAddr";

    private EnterpriseParser() {
    }

    public static List<EnterpriseParseResponse> parseAll(final Document document) {
        final Elements items = document.select(ITEM_SELECTOR);

        return items.stream()
                .map(EnterpriseParser::parseEnterprise)
                .toList();
    }

    private static EnterpriseParseResponse parseEnterprise(final Element item) {
        final String name = item.select(NAME_SELECTOR).text();
        final String businessTypeCode = item.select(BUSINESS_TYPE_CODE_SELECTOR).text();
        final String websiteUrl = item.select(WEBSITE_URL_SELECTOR).text();
        final String address = item.select(ADDRESS_SELECTOR).text();

        return new EnterpriseParseResponse(name, businessTypeCode, websiteUrl, address);
    }
}
