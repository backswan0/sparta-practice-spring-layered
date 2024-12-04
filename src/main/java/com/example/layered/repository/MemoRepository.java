package com.example.layered.repository;

import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;

import java.util.List;
import java.util.Optional;

public interface MemoRepository {
    MemoResponseDto saveMemo(Memo memo);
    /*
    [수정 전] Memo saveMemo(Memo memo);
    [수정 후] MemoResponseDto saveMemo(Memo memo);
     */

    List<MemoResponseDto> findAllMemos();

    Optional<Memo> findMemoById (Long id);
    /*
    [수정 전] Memo findMemoById (Long id);
    [수정 후] Optional<Memo> findMemoById (Long id);
             그다음 JdbcTemplateMemoRepository로 가자.
     */

    Memo findMemoByIdOrElseThrow (Long id);

    int updateMemo (Long id, String title, String contents);

    int updateTitle (Long id, String title);

    int deleteMemo (Long id);
    /*
    [수정 전] void
    [수정 후] int
     */

}