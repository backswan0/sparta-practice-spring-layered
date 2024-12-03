package com.example.layered.controller;
// 1. 메모 생성 API 리팩토링 완료
// 2. 메모 목록 조회 API 리팩토링 완료
// 3. 메모 단건 조회 API 리팩토링 완료
// 4. 메모 전체 수정 API 리팩토링 완료
// 5. 메모 제목 수정 API 리팩토링 완료
// 6. 메모 삭제 API 리팩토링 완료

import com.example.layered.dto.MemoRequestDto;
import com.example.layered.dto.MemoResponseDto;
import com.example.layered.service.MemoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
[Controller]
[1] Presentation Layer에 있다.
[2] 맡은 일은 딱 3가지이다.
    (1) 클라이언트의 요청받기
    (2) 요청 처리를 Service Layer에 전달
    (3) Service에서 처리한 결과를 클라이언트에 응답하기
[3] 현재 책임이 너무 많으므로 분리하는 실습을 진행한다.
 */

@RestController // == @Controller + @ResponseBody
@RequestMapping("/memos") // Prefix
public class MemoController {

    /*
    [일단 기억하자!!!]
    MemoService를 사용하려면 생성자 주입으로 private final 키워드의 필드에 주입을 받아야 한다.
     */
    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @PostMapping
    public ResponseEntity<MemoResponseDto> createMemo(@RequestBody MemoRequestDto dto) {

        MemoResponseDto responseDto = memoService.saveMemo(dto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        /*
        [Service Layer 호출과 응답 추가하기]
        [수정 전]
        return new ResponseEntity<>(memoService.saveMemo(dto), HttpStatus.CREATED);
         */
    }

    @GetMapping
    public ResponseEntity<List<MemoResponseDto>> findAllMemos() {

        List<MemoResponseDto> allMemos = memoService.findAllMemos();

        return new ResponseEntity<>(allMemos, HttpStatus.OK);
        /*
        비즈니스 로직(==핵심 로직)이므로 findAllMemos 호출이 중요하다.
        비즈니스 로직은 눈에 잘 띄게, 아닌 건 잘 안 보이게 위와 같이 수정
        [수정 전]
        return new ResponseEntity<>(memoService.findAllMemos(), HttpStatus.OK);
        ➡️이 로직을 보려면 눈이 오른쪽까지 쭉 읽어야 하므로 가독성이 떨어진다.
        [코드스니펫]
        public List<MemoResponseDto> findAllMemos() {
            return memoService.findAllMemos();
        }
         */
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoResponseDto> findMemoById(@PathVariable Long id) {
        return new ResponseEntity<>(memoService.findMemoById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateMemo(
            @PathVariable Long id, @RequestBody MemoRequestDto dto
    ) {
        MemoResponseDto responseDto = memoService.updateMemo(id, dto.getTitle(), dto.getContents());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateTitle (@PathVariable Long id, @RequestBody MemoRequestDto dto
    ) {
        MemoResponseDto responseDto = memoService.updateTitle(id, dto.getTitle(), dto.getContents());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemo (@PathVariable Long id) {
        memoService.deleteMemo(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}