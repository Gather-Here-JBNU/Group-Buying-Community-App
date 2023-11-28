package com.example.cloudcomputingproject.categorypage;

public class CategoryItem {
    private String name; // 카테고리 이름을 저장합니다.

    // 생성자 메서드: 카테고리 아이템을 생성할 때 이름을 초기화합니다.
    public CategoryItem(String name) {
        this.name = name;
    }

    // 카테고리 이름을 반환하는 getter 메서드입니다.
    public String getName() {
        return name;
    }
}