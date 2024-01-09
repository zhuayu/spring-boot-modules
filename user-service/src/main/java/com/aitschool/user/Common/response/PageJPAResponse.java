package com.aitschool.user.Common.response;

import lombok.Data;

import java.util.List;

@Data
public class PageJPAResponse<T> {
    private List<T> list;
    private Pagination pagination;
    private int error_code;
    private String message;

    // Inner class for pagination details
    @Data
    public static class Pagination {
        private long total;
        private int count;
        private String per_page;
        private int current_page;
        private int total_pages;
    }

    // Static factory method to create and initialize the instance
    public static <T> PageJPAResponse<T> of(List<T> list, long totalElements, int numberOfElements,
                                            int size, int number, int totalPages) {
        PageJPAResponse<T> response = new PageJPAResponse<>();
        response.setList(list);

        PageJPAResponse.Pagination pagination = new PageJPAResponse.Pagination();
        pagination.setTotal(totalElements);
        pagination.setCount(numberOfElements);
        pagination.setPer_page(Integer.toString(size));
        pagination.setCurrent_page(number + 1);
        pagination.setTotal_pages(totalPages);
        response.setPagination(pagination);
        return response;
    }
}