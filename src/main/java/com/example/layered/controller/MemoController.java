package com.example.layered.controller;

import com.example.layered.dto.MemoRequestDto;
import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
[Controller]
[1] Presentation Layer에 있다.
[2] 맡은 일은 딱 3가지이다.
    (1) 클라이언트의 요청받기
    (2) 요청 처리를 Service Layer에 전달
    (3) Service에서 처리한 결과를 클라이언트에 응답하기
[3] 현재 책임이 너무 많으므로 분리하는 실습을 진행한다.
 */

@RestController
@RequestMapping("/memos")
public class MemoController {
    private final Map<Long, Memo> memoList = new HashMap<>();

    @PostMapping
    public ResponseEntity<MemoResponseDto> createMemo(@RequestBody MemoRequestDto dto) {

        // [1] 식별자가 1씩 증가하도록 작성
        Long memoId = memoList.isEmpty() ? 1: Collections.max(memoList.keySet()) +1;

        // [2] 요청받은 데이터로 Memo 객체 생성
        Memo memo = new Memo(memoId, dto.getTitle(), dto.getContents());

        // [3] Inmemory DB에 Memo 저장
        memoList.put(memoId, memo);

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.CREATED);
    }
}
