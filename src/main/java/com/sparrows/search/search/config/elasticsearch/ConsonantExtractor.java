package com.sparrows.search.search.config.elasticsearch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsonantExtractor {
    private static final char[] CHO_SUNG = {
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ',
            'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };
    public static String getConsonant(String input) {
        StringBuilder result = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (c >= 0xAC00 && c <= 0xD7A3) { // 한글 완성형 음절
                int index = (c - 0xAC00) / (21 * 28); // 초성 인덱스 계산
                result.append(CHO_SUNG[index]);
            } else {
                result.append(c); // 초성 등 비완성형 문자는 그대로 추가
            }
        }

        log.info("CONSONANT : {}", result.toString());
        return result.toString();
    }
}
