package com.example.layered.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
// memo는 dto를 모른다...! dto가 바뀌더라도 영향을 안 받는다..!

@Getter
@AllArgsConstructor

public class Memo {
    /*
    [수정 전] @Setter
    [수정 후] 삭제
    [이유] 어차피 DB로 auto increment를 설정해서
     */
    private Long id;
    private String title;
    private String contents;

    public Memo (String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void updateTitle(String title) {
        this.title = title;
    }
}