package com.toggn.mma.itp.enterprise.utils;

import com.toggn.mma.itp.enterprise.parser.EnterpriseParser;
import com.toggn.mma.itp.enterprise.parser.dto.EnterpriseParseResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EnterpriseParserTest {

    private static Document getDocument() {
        final File file = new File("src/test/resources/enterprises.xml");

        try {
            return Jsoup.parse(file, "UTF-8", "", Parser.xmlParser());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("parseAllEnterprises(): Jsoup으로 읽어들인 Document로부터 기업 정보를 파싱한다.")
    void 기업_정보_파싱_테스트() {
        // given
        final Document document = getDocument();

        final List<EnterpriseParseResponse> expect = List.of(
                new EnterpriseParseResponse("업체명 1", "업종코드 1", "홈페이지주소 1", "주소 1"),
                new EnterpriseParseResponse("업체명 2", "업종코드 2", "홈페이지주소 2", "주소 2"),
                new EnterpriseParseResponse("업체명 3", "업종코드 3", "홈페이지주소 3", "주소 3")
        );

        // when
        final List<EnterpriseParseResponse> actual = EnterpriseParser.parseAllEnterprises(document);

        // then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expect);
    }
}
