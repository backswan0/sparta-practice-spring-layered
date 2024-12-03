package com.example.layered.dto;
// 1. 메모 생성 API 리팩토링 완료
// 2. 메모 목록 조회 API 리팩토링 완료

import com.example.layered.entity.Memo;
import lombok.Getter;

@Getter
public class MemoResponseDto {
    private long id;
    private String title;
    private String contents;

    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();
        this.title = memo.getTitle();
        this.contents = memo.getContents();
    }
}