package com.example.layered.service;

import com.example.layered.dto.MemoRequestDto;
import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;
import com.example.layered.repository.MemoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;

    public MemoServiceImpl(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    @Override
    public MemoResponseDto saveMemo(MemoRequestDto memoRequestDto) {
        Memo memo = new Memo(memoRequestDto.getTitle(), memoRequestDto.getContents());
        return memoRepository.saveMemo(memo);
    }

    @Override
    public List<MemoResponseDto> findAllMemos() {
        List<MemoResponseDto> allMemos = memoRepository.findAllMemos();
        return allMemos;
    }

    @Override
    public MemoResponseDto findMemoById(Long id) {
        Memo memo = memoRepository.findMemoByIdOrElseThrow(id);
        /*
        Optional을 사용했기 때문에 위에서도 바꿔줘야 한다.
        [수정 전]
        Memo memoById = memoRepository.findMemoById(id);
        [수정 후]
        Optional<Memo> optionalMemo = memoRepository.findMemoById(id);
        [수정 후 2]
        Memo memo = memoRepository.findMemoByIdOrElseThrow(id);
         */

        /*
        [Optional]
        null을 직접 다루는 건 안전하지 않기 때문에 사용할 거다.
        null 값이 올 수 있는 값을 감싸는 래퍼 클래스(Wrapper 클래스)이다.
        NPE(NullPointerException)를 방지한다.

        [수정 전]
        if(memoById == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " +id);
        }
        [수정 후]
        if (optionalMemo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
        [수정 후 2]
        수정한 거 삭제
         */

        return new MemoResponseDto(memo);
        /*
        [수정 전]
        return new MemoResponseDto(memoById);
        [수정 후]
        return new MemoResponseDto(optionalMemo.get());
        [수정 후 2]
        return new MemoResponseDto(memo);
         */
    }

    // update는 성공했으나 조회를 못할 때를 대비하여 Transactional annotation을 사용한다.
    @Transactional
    @Override
    public MemoResponseDto updateMemo(Long id, String title, String contents) {

        if (title == null || contents == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and contents are required values.");
        }

        int updatedRow = memoRepository.updateMemo(id, title, contents);

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        Memo memo = memoRepository.findMemoByIdOrElseThrow(id);
        /*
        - update가 잘 되었는지 조회해야 하므로
        [수정 전]
        Optional<Memo> optionalMemo = memoRepository.findMemoById(id);
         */

        return new MemoResponseDto(memo);
        /*
        [수정 전]
        return new MemoResponseDto((memoById));
        [수정 후]
        return new MemoResponseDto(optionalMemo.get());
        [수정 후 2]
        return new MemoResponseDto(memo);
         */
    }

    @Transactional
    @Override
    public MemoResponseDto updateTitle(Long id, String title, String contents) {
        // stream 쓰지 않은 버전....ㅎㅎㅎㅎㅎㅎ
        Optional<Memo> optionalMemo = memoRepository.findMemoById(id);

        if (title == null || contents != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and contents are required values.");
        }

        int updatedRow = memoRepository.updateTitle(id, title);
        // [수정 전] memoById.updateTitle(title);

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        Optional<Memo> optionalMemo1 = memoRepository.findMemoById(id);

        return new MemoResponseDto(optionalMemo1.get());
        // [수정 전] return new MemoResponseDto(memoById);
    }

    @Override
    public void deleteMemo(Long id) {

        int deletedRow = memoRepository.deleteMemo(id);

        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}