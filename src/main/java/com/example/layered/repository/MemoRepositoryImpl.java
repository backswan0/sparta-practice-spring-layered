package com.example.layered.repository;
// 1. 메모 생성 API 리팩토링 완료

import com.example.layered.entity.Memo;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MemoRepositoryImpl implements MemoRepository {
    private final Map<Long, Memo> memoList = new HashMap<>();

    @Override
    public Memo saveMemo(Memo memo) {
        // [1] 식별자 만들어주기
        Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;

        // [2] 식별자 setter 메서드로 넣어주기
        memo.setId(memoId);

        // [3] 저장하기
        memoList.put(memoId, memo);

        // [4] 저장한 memo 객체 반환하기
        return memo;
    }
}