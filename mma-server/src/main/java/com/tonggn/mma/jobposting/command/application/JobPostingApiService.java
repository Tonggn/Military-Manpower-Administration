package com.tonggn.mma.jobposting.command.application;

import com.tonggn.mma.jobposting.command.domain.AgentType;
import com.tonggn.mma.jobposting.command.domain.BusinessType;
import com.tonggn.mma.jobposting.command.domain.SalaryType;
import com.tonggn.mma.jobposting.command.domain.ServiceStatusType;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JobPostingApiService {

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

  @Value("${mma.api-url}")
  private String apiUrl;

  public List<JobPostingItem> fetchItems() throws IOException {
    final Document document = requestDocument();
    return parseDocument(document);
  }

  private Document requestDocument() throws IOException {
    return Jsoup.connect(apiUrl)
        .timeout(60 * 1000)
        .maxBodySize(0)
        .get();
  }

  private List<JobPostingItem> parseDocument(final Document document) {
    return document.select("item")
        .stream()
        .map(this::mapToItem)
        .toList();
  }

  private JobPostingItem mapToItem(final Element element) {
    return new JobPostingItem(
        element.select("eopcheNm").text(),
        element.select("cygonggoNo").text(),
        element.select("cyjemokNm").text(),
        element.select("ddeopmuNm").text(),
        element.select("bokrihs").text(),
        element.select("geunmujy").text(),
        element.select("gmjybjusoCd").text(),
        element.select("cjhakryeok").text(),
        BusinessType.of(element.select("eopjongGbcd").text()),
        element.select("grNs").text(),
        element.select("gyeongryeokGbcdNm").text(),
        SalaryType.of(element.select("gyjogeonCd").text()),
        ServiceStatusType.of(element.select("yeokjongBrcd").text()),
        AgentType.of(element.select("yowonGbcd").text()),
        element.select("yuhyoYn").text().equalsIgnoreCase("Y"),
        element.select("saeopjaDrno").text(),
        parseDate(element.select("ccdatabalsaengDtm").text()),
        parseDate(element.select("magamDt").text()),
        parseDate(element.select("cjdatabyeongyeongDtm").text())
    );
  }

  private LocalDate parseDate(final String date) {
    return LocalDate.parse(date, DATE_FORMATTER);
  }
}
