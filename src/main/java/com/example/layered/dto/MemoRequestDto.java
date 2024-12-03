package com.example.layered.dto;
// 1. 메모 생성 API 리팩토링 완료
// 2. 메모 목록 조회 API 리팩토링 완료
// 3. 메모 단건 조회 API 리팩토링 완료
// 4. 메모 전체 수정 API 리팩토링 완료

import lombok.Getter;

@Getter
public class MemoRequestDto {
    private String title;
    private String contents;
}