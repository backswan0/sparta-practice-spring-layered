package com.example.layered.controller;
// 1. 메모 생성 API 리팩토링 완료

import com.example.layered.dto.MemoRequestDto;
import com.example.layered.dto.MemoResponseDto;
import com.example.layered.service.MemoService;
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

@RestController // == @Controller + @ResponseBody
@RequestMapping("/memos") // Prefix
public class MemoController {

    /*
    [일단 기억하자!!!]
    MemoService를 사용하려면 생성자 주입으로 private final 키워드의 필드에 주입을 받아야 한다.
     */
    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @PostMapping
    public ResponseEntity<MemoResponseDto> createMemo(@RequestBody MemoRequestDto dto) {

        /*
        [Service Layer 호출과 응답 추가하기]
         */
        return new ResponseEntity<>(memoService.saveMemo(dto), HttpStatus.CREATED);
    }
}
