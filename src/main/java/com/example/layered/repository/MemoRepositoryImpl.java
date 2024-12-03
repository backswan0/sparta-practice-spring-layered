package com.example.layered.repository;

import com.example.layered.entity.Memo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemoRepositoryImpl implements MemoRepository{
    private final Map<Long, Memo> memoList = new HashMap<>();
}