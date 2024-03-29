package com.example.mediasoft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Данный класс содержит поля, описывающие новый товар:
 * <li>Название</li>
 * <li>Описание</li>
 * <li>Категорию</li>
 * <li>Цену</li>
 * <li>Количество</li>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProductDTO {
    /**
     * Название товара
     */
    private String name;

    /**
     * Описание товара
     */
    private String description;

    /**
     * Категория товара
     */
    private String category;

    /**
     * Цена товара
     */
    private int price;

    /**
     * Количество товара
     */
    private int count;
}
