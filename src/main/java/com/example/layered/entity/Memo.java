package com.example.layered.entity;
// 1. 메모 생성 API 리팩토링 완료
// 2. 메모 목록 조회 API 리팩토링 완료
// 3. 메모 단건 조회 API 리팩토링 완료

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor

public class Memo {
    /*
    [1] 사용 목적: repository 영역에서 식별자 값 설정
    [2] 주의 사항: 변경해야 하는 값만 변경할 수 있도록 필드 위에 쓰자
     */
    @Setter
    private Long id;
    private String title;
    private String contents;

    // Memo 객체를 생성할 수 있는 생성자 추가하기
    public Memo (String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    /*
    Q. 왜 매개변수를 다 갈라놓는가?
    A. 재사용성이 높아지기 때문에
     */
    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
        /*
        [수정 전]
        [a] public void update(MemoRequestDto memoRequestDto)
        [b] this.title = memoRequestDto.getTitle();
        [c] this.contents = memoRequestDto.getContents();
        [수정 후]
        [a] public void update(String title, String contents)
        [b] this.title = title;
        [c] this.contents = contents;
         */
    }

    public void updateTitle(String title) {
        this.title = title;
        /*
        [수정 전]
        [a] public void updateTitle(MemoRequestDto memoRequestDto)
        [b] this.title = memoRequestDto.getTitle();
        [수정 후]
        [a] public void updateTitle(String title)
        [b] this.title = title;
         */
    }
}