package com.bee.shop.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("product")
public class Product {

    @Id
    private Long id;
    private String name;
    private String description;

}
