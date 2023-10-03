package com.toggn.mma.itp.notice.parser;

import com.toggn.mma.itp.notice.parser.dto.NoticeParseResponse;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NoticeParser {

    private static final String ITEM_SELECTOR = "item";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    private NoticeParser() {
    }

    public static List<NoticeParseResponse> parseAllNotices(final Document document) {
        final Elements items = document.select(ITEM_SELECTOR);

        return items.stream()
                .map(NoticeParser::parseNotice)
                .toList();
    }

    private static NoticeParseResponse parseNotice(final Element item) {
        final String title = item.select("cyjemokNm").text();
        final String task = item.select("ddeopmuNm").text();
        final String businessTypeCode = item.select("eopjongGbcd").text();
        final String welfare = item.select("bokrihs").text();
        final String salaryTypeCode = item.select("gyjogeonCd").text();
        final String serviceAddress = item.select("geunmujy").text();
        final String highestEducationLevel = item.select("cjhakryeok").text();
        final String experienceDivision = item.select("gyeongryeokGbcdNm").text();
        final String serviceStatusCode = item.select("yeokjongBrcd").text();
        final String agentTypeCode = item.select("yowonGbcd").text();
        final String enterpriseName = item.select("eopcheNm").text();
        final long noticeNumber = Long.parseLong(item.select("cygonggoNo").text());
        final LocalDate createdDate = LocalDate.parse(item.select("ccdatabalsaengDtm").text(), DATE_FORMAT);
        final LocalDate updatedDate = LocalDate.parse(item.select("cjdatabyeongyeongDtm").text(), DATE_FORMAT);
        final LocalDate deadlineDate = LocalDate.parse(item.select("magamDt").text(), DATE_FORMAT);

        return new NoticeParseResponse(title, task, businessTypeCode, welfare, salaryTypeCode, serviceAddress,
                highestEducationLevel, experienceDivision, serviceStatusCode, agentTypeCode, enterpriseName, noticeNumber,
                createdDate, updatedDate, deadlineDate);
    }
}
