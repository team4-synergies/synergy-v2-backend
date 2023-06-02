package com.synergies.synergyv2.common;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDto<T> {
    // 페이징 content
    private List<T> content;

    // 총 페이지 번호
    private int totalPage;

    // 현재 페이지 번호
    private int page;
    // 목록 사이즈
    private int size;
    // 게시글 총갯수
    private long totalCount;

    // 화면에서 시작 페이지 번호, 끝 페이지 번호
    private int start, end;

    // 이전, 다음 링크여부
    private boolean prev, next;

    // 화면에 보여줄 페이지 번호 목록 리스트
    private List<Integer> pageList;

    public PageResponseDto(Pageable pageable, Page<T> pageInfo) {
        content = pageInfo.getContent();
        totalPage = pageInfo.getTotalPages();	   // 페이지 총 갯수
        totalCount = pageInfo.getTotalElements();  // 로우 총 갯수
        makePageList(pageable);
    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1;                       // 0부터 시작하므로 1을 추가
        this.size = pageable.getPageSize();                             // 한 페이지에 포함될 row 의 최대개수, ex) size = 10, 화면에 보여줄 항목개수가 10개다.

        int tempEnd = (int) (Math.ceil(page / (double) size)) * size;   // 현재 페이지를 기준으로 화면에 출력되어야 하는 마지막 페이지 번호를 우선 처리, ex) size = 5, page = 6이다! => tempEnd = 10 인것!

        start = tempEnd - (size - 1);                                   // start = 6 = 10 - (5 - 1)
        end = Math.min(totalPage, tempEnd);                             // 실제 데이터가 부족한 경우를 위해 마지막 페이지 번호는 전체 데이터의 개수를 이용해서 다시 계산
        // end = (6, 10) = 6

        prev = start > 1;                                               // 6 > 1 => true
        next = totalPage > tempEnd;                                     // 6 > 10 => false

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());          // 현재 보여줄 목록 ex) 마지막 페이지에 들어갔다. 6
    }

}