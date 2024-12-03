package com.example.layered.service;
// 1. 메모 생성 API 리팩토링 완료
// 2. 메모 목록 조회 API 리팩토링 완료
// 3. 메모 단건 조회 API 리팩토링 완료
// 4. 메모 전체 수정 API 리팩토링 완료

import com.example.layered.dto.MemoRequestDto;
import com.example.layered.dto.MemoResponseDto;

import java.util.List;

public interface MemoService {

    MemoResponseDto saveMemo(MemoRequestDto memoRequestDto);

    List<MemoResponseDto> findAllMemos();

    MemoResponseDto findMemoById (Long id);

    MemoResponseDto updateMemo (Long id, String title, String contents);
}