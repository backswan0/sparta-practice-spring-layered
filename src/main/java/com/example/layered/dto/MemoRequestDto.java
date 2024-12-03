package com.example.layered.dto;
// 1. 메모 생성 API 리팩토링 완료
// 2. 메모 목록 조회 API 리팩토링 완료

import lombok.Getter;

@Getter
public class MemoRequestDto {
    private String title;
    private String contents;
}