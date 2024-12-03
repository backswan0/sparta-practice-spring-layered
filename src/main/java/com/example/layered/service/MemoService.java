package com.example.layered.service;
// 1. 메모 생성 API 리팩토링 완료

import com.example.layered.dto.MemoRequestDto;
import com.example.layered.dto.MemoResponseDto;

public interface MemoService {

    MemoResponseDto saveMemo(MemoRequestDto memoRequestDto);
}