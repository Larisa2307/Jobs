package com.example.jobs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryModel {


    private String id;

    private Integer index;

    private String name;
    private String description;
}
