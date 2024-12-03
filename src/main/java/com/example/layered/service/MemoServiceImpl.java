package com.example.layered.service;
// 1. 메모 생성 API 리팩토링 완료

import com.example.layered.dto.MemoRequestDto;
import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;
import com.example.layered.repository.MemoRepository;
import org.springframework.stereotype.Service;

/*
MemoServiceImpl의 Impl은 implements를 의미한다.
즉, MemoService 인터페이스를 구현한 구현체임을 뜻한다.
 */
@Service
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;

    public MemoServiceImpl(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    @Override
    public MemoResponseDto saveMemo(MemoRequestDto memoRequestDto) {

        Memo memo = new Memo(memoRequestDto.getTitle(), memoRequestDto.getContents());
        /*
        [1] 요청받은 데이터로 Memo 객체 생성하기
            단, id 식별자 제외
            == MemoId 식별자 계산은 repository 영역이므로
            Q. memoRequestDto.getTitle()가 오류로 뜨는 이유
            A. 요구하는 타입은 Long인데 String 타입을 제공해서
            mac 기준: option + command + v ➡️ 자동으로 변수 생성
         */

        Memo savedMemo = memoRepository.saveMemo(memo);
        /*
        [2] [1]에서 생성된 객체를 DB에 저장하기
            이때 저장된 메모라는 의미로 이름은 'savedMemo'
         */

        return new MemoResponseDto(savedMemo);
        // [주의] return savedMemo; 아니다.
    }
}