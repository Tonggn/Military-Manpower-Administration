package com.toggn.mma.support.fixture;

import com.toggn.mma.itp.enterprise.parser.dto.EnterpriseParseResponse;
import com.toggn.mma.itp.notice.parser.dto.NoticeParseResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DocumentFixture {

    public static Document enterprisesDocument(final EnterpriseParseResponse... responses) {
        final String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><response><body><items>" +
                        Arrays.stream(responses)
                                .map(response -> "<item>" +
                                        "<eopcheNm>" + response.name() + "</eopcheNm>" +
                                        "<eopjongGbcd>" + response.businessTypeCode() + "</eopjongGbcd>" +
                                        "<hmpgAddr>" + response.websiteUrl() + "</hmpgAddr>" +
                                        "<eopcheAddr>" + response.address() + "</eopcheAddr>" +
                                        "</item>")
                                .collect(Collectors.joining()) +
                        "</items></body></response>";
        return Jsoup.parse(xml, "", Parser.xmlParser());
    }

    public static Document noticesDocument(final NoticeParseResponse... responses) {
        final String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><response><body><items>" +
                        Arrays.stream(responses)
                                .map(response -> "<item>" +
                                        "<cyjemokNm>" + response.title() + "</cyjemokNm>" +
                                        "<ddeopmuNm>" + response.task() + "</ddeopmuNm>" +
                                        "<eopjongGbcd>" + response.businessCode() + "</eopjongGbcd>" +
                                        "<bokrihs>" + response.welfare() + "</bokrihs>" +
                                        "<gyjogeonCd>" + response.salaryCode() + "</gyjogeonCd>" +
                                        "<geunmujy>" + response.serviceAddress() + "</geunmujy>" +
                                        "<cjhakryeok>" + response.highestEducationLevel() + "</cjhakryeok>" +
                                        "<gyeongryeokGbcdNm>" + response.experienceDivision() + "</gyeongryeokGbcdNm>" +
                                        "<yeokjongBrcd>" + response.serviceStatusCode() + "</yeokjongBrcd>" +
                                        "<yowonGbcd>" + response.agentCode() + "</yowonGbcd>" +
                                        "<eopcheNm>" + response.enterpriseName() + "</eopcheNm>" +
                                        "<cygonggoNo>" + response.noticeNumber() + "</cygonggoNo>" +
                                        "<ccdatabalsaengDtm>" + localDateToString(response.createdDate()) + "</ccdatabalsaengDtm>" +
                                        "<cjdatabyeongyeongDtm>" + localDateToString(response.updatedDate()) + "</cjdatabyeongyeongDtm>" +
                                        "<magamDt>" + localDateToString(response.deadlineDate()) + "</magamDt>" +
                                        "</item>")
                                .collect(Collectors.joining()) +
                        "</items></body></response>";
        return Jsoup.parse(xml, "", Parser.xmlParser());
    }

    private static String localDateToString(final LocalDate date) {
        return String.format("%04d%02d%02d", date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }
}
