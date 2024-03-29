package com.example.mediasoft.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Данный класс описывает сущность из таблицы <code>products</code>. Содержит информацию о товаре:
 * <li>UUID артикул - первичный ключ</li>
 * <li>Название</li>
 * <li>Описание</li>
 * <li>Категорию</li>
 * <li>Цену</li>
 * <li>Количество</li>
 * <li>Дату изменения</li>
 * <li>Дату создания</li>
 */
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    /**
     * UUID артикул товара. Первичный ключ
     */
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID article;

    /**
     * Название товара
     */
    @Column(name = "name")
    private String name;

    /**
     * Описание товара
     */
    @Column(name = "description")
    private String description;

    /**
     * Категория товара
     */
    @Column(name = "category")
    private String category;

    /**
     * Цена товара
     */
    @Column(name = "price")
    private int price;

    /**
     * Количество товара
     */
    @Column(name = "count")
    private int count;

    // Сделал строку, поскольку не придумал как получать по LocalDateTime из PostgreSQL timestamp
    /**
     * Дата последнего изменения товара
     */
    @Column(name = "editdate")
    private String editdate;

    /**
     * Дата создания товара
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "createdate")
    private LocalDate createdate;
}
