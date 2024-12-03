package com.example.layered.service;
// 1. 메모 생성 API 리팩토링 완료
// 2. 메모 목록 조회 API 리팩토링 완료
// 3. 메모 단건 조회 API 리팩토링 완료
// 4. 메모 전체 수정 API 리팩토링 완료
// 5. 메모 제목 수정 APT 리팩토링 완료
// 6. 메모 삭제 API 리팩토링 완료

import com.example.layered.dto.MemoRequestDto;
import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;
import com.example.layered.repository.MemoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    @Override
    public List<MemoResponseDto> findAllMemos() {
        List<MemoResponseDto> allMemos = memoRepository.findAllMemos();
        return allMemos;
        /*
        == return memoRepository.findAllMemos();
        == 변수에 받은 다음 반환하냐, 바로 반환하냐 차이일 뿐이다.
         */
    }

    @Override
    public MemoResponseDto findMemoById(Long id) {

        Memo memoById = memoRepository.findMemoById(id);

        if (memoById == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id =" + id);
            // 뒤에 적은 메시지는 추후 배울 예정
        }

        return new MemoResponseDto(memoById);
    }

    @Override
    public MemoResponseDto updateMemo(Long id, String title, String contents) {

        Memo memoById = memoRepository.findMemoById(id);

        if (memoById == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id =" + id);
        }

        if (title == null || contents == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and contents are required values.");
        }

        memoById.update(title, contents);

        /*
        이미 데이터베이스에 저장된 memo 객체를 업데이트를 했다.
        따라서 업데이트 때문에 repository에 접근하지 않아도 된다.
         */

        return new MemoResponseDto((memoById));
    }

    @Override
    public MemoResponseDto updateTitle(Long id, String title, String contents) {
        // 수정할 memo 객체 찾아오기
        Memo memoById = memoRepository.findMemoById(id);

        if (memoById == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        if (title == null || contents != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and contents are required values.");
        }

        memoById.updateTitle(title);

        return new MemoResponseDto(memoById);
        // [주의] return memoById가 아니다.
    }

    @Override
    public void deleteMemo(Long id) {
        Memo memoById = memoRepository.findMemoById(id);

        if(memoById == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " +id);
        }

        memoRepository.deleteMemo(id);

    }
}