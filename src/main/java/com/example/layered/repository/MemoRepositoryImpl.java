package com.example.layered.repository;
// 1. 메모 생성 API 리팩토링 완료
// 2. 메모 목록 조회 API 리팩토링 완료
// 3. 메모 단건 조회 API 리팩토링 완료
// 4. 메모 전체 수정 API 리팩토링 완료
// 5. 메모 제목 수정 API 리팩토링 완료
// 6. 메모 삭제 API 리팩토링 완료

import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;
import org.springframework.stereotype.Repository;

import java.util.*;

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

    @Override
    public List<MemoResponseDto> findAllMemos() {
        // [1] 리스트 초기화
        List<MemoResponseDto> allMemos = new ArrayList<>();

        // [2] DB에 저장된 메모를 하나씩 꺼내서 반복문 실행해서 저장하기
        for (Memo memo: memoList.values()) {
            MemoResponseDto responseDto = new MemoResponseDto(memo);
            allMemos.add(responseDto);
        }

        // [3] 반환하기
        return allMemos;
    }

    @Override
    public Memo findMemoById(Long id) {
        return memoList.get(id);
    }

    @Override
    public void deleteMemo(Long id) {
        memoList.remove(id);
    }
}