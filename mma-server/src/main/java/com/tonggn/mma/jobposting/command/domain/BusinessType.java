package com.tonggn.mma.jobposting.command.domain;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessType {
  // 11101, 철강
  // 11203, 선광제련
  STEEL(List.of("11101", "11203"), "철강"),

  // 11102, 기계
  MACHINERY(List.of("11102"), "기계"),

  // 11103, 전기
  ELECTRIC(List.of("11103"), "전기"),

  // 11104, 전자
  ELECTRONIC(List.of("11104"), "전자"),

  // 11105, 화학
  CHEMICAL(List.of("11105"), "화학"),

  // 11106, 섬유
  // 11107, 신발
  TEXTILE(List.of("11106", "11107"), "섬유"),

  // 11108, 시멘요업
  CEMENT_CERAMIC(List.of("11108"), "시멘트요업"),

  // 11109, 생활용품
  HOUSEHOLD(List.of("11109"), "생활용품"),

  // 11110, 통신기기
  TELECOMMUNICATION_EQUIPMENT(List.of("11110"), "통신기기"),

  // 11111, 정보처리
  INFORMATION(List.of("11111"), "정보처리"),

  // 11112, 게임S/W
  GAME_SOFTWARE(List.of("11112"), "게임S/W"),

  // 11113, 영상게임
  VIDEO_GAME(List.of("11113"), "영상게임"),

  // 11114, 의료의약
  MEDICAL_PHARMACEUTICAL(List.of("11114"), "의료의약"),

  // 11115, 식음료
  // 11116, 농산물가공
  // 11117, 수산물가공
  // 11118, 임산물가공
  FOOD_AND_BEVERAGE(List.of("11115", "11116", "11117", "11118"), "식음료"),

  // 11119, 동물약품
  VETERINARY_PHARMACEUTICAL(List.of("11119"), "동물약품"),

  // 11120, 애니메이션
  ANIMATION(List.of("11120"), "애니메이션"),

  // 11200, 광업
  // 11201, 석탄채굴
  // 11202, 일반광물채굴
  MINING(List.of("11200", "11201", "11202"), "광업"),

  // 11301, 에너지
  ENERGY(List.of("11301"), "에너지"),

  // 11400, 건설
  // 11401, 국내건설
  // 11402, 국외건설
  CONSTRUCTION(List.of("11400", "11401", "11402"), "건설"),

  // 11500, 해운
  // 11501, 내항화물
  // 11502, 외항화물
  // 11503, 내항선박관리
  // 11504, 외항선박관리
  // 32101, 해운업
  SHIPPING(List.of("11500", "11501", "11502", "11503", "11504", "32101"), "해운"),

  // 31101, 수산업
  // 11601, 근해
  // 11602, 원양
  FISHERY(List.of("31101", "11601", "11602"), "수산업"),

  // 12000, 방위산업체
  // 12101, 검사기관
  // 12102, 군공창
  // 12103, 군정비부대
  // 12104, 군조달기관
  // 12105, 민영방산
  DEFENSE_INDUSTRY(List.of("12000", "12101", "12102", "12103", "12104", "12105"), "방위산업체"),

  // 13000, 농어업분야
  // 13101, 후계농업민
  // 13102, 농기계수리
  // 13103, 농기계운전
  // 13201, 어민후계자
  AGRICULTURE_AND_FISHERY(List.of("13000", "13101", "13102", "13103", "13201"), "농어업분야"),

  // 14101, 기능특기자
  SKILLED_TECHNICIAN(List.of("14101"), "기능특기자"),

  // 20000, 연구기관
  // 21101, 과기원
  // 21102, 과기원부설연구소
  // 21103, 특정연구소
  // 21201, 정부출연연구소
  // 21202, 정부투자연구소
  // 21301, 기초연구연구기관
  // 21401, 국가기관 등 연구소
  // 21402, 지역혁신센터연구소
  // 21501, 대기업부설연구소
  // 21502, 중소기업부설연구소
  // 21503, 벤처기업부설연구소
  // 21504, 중견기업부설연구소
  // 21505, 연구개발서비스업연구소
  // 21601, 공공법인연구소
  // 22101, 인문사회연구기관
  // 23101, 대학원연구기관
  // 23201, 자연계대학부설연구기관
  // 23202, 인문계대학부설연구기관
  // 24101, 방산연구기관
  // 25101, (구)과기원
  // 26101, (구)학술특기자
  RESEARCH_INSTITUTE(List.of("20000", "21101", "21102", "21103", "21201", "21202", "21301", "21401",
      "21402", "21501", "21502", "21503", "21504", "21505", "21601", "22101", "23101", "23201",
      "23202", "24101", "25101", "26101"), "연구기관"),

  NONE(List.of("00000"), "미기재");

  private final List<String> code;
  private final String name;

  public static BusinessType of(final String code) {
    return Arrays.stream(values())
        .filter(value -> value.code.contains(code))
        .findFirst()
        .orElse(NONE);
  }
}
