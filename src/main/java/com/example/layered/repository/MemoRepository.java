package com.example.layered.repository;
// 1. 메모 생성 API 리팩토링 완료
// 2. 메모 목록 조회 API 리팩토링 완료

import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;

import java.util.List;

public interface MemoRepository {
    Memo saveMemo(Memo memo);
    // memo 객체는 id가 없는 상태로 전달된다.

    List<MemoResponseDto> findAllMemos();
}
