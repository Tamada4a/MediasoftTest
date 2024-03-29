package com.example.mediasoft.repository;

import com.example.mediasoft.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Интерфейс репозитория, работающий с сущностями {@link Product}. Является расширением {@link JpaRepository}
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    /**
     * Метод позволяет получить по артикулу объект класса {@link Product}, содержащий информацию о соответствующем товаре
     *
     * @param article артикул товара, который необходимо получить
     * @return Объект класса {@link Product}, содержащий информацию о соответствующем товаре
     */
    Product findByArticle(final UUID article);


    /**
     * Метод позволяет получить по названию товара список объектов класса {@link Product}, содержащих информацию о
     * соответствующих товарах
     *
     * @param name название товаров, которые необходимо получить
     * @return Список объектов класса {@link Product}, содержащих информацию о соответствующих товарах
     */
    List<Product> findByName(final String name);


    /**
     * Метод позволяет получить по описанию товара список объектов класса {@link Product}, содержащих информацию о
     * соответствующих товарах
     *
     * @param description описание товаров, которые необходимо получить
     * @return Список объектов класса {@link Product}, содержащих информацию о соответствующих товарах
     */
    List<Product> findByDescription(final String description);


    /**
     * Метод позволяет получить по категории товара список объектов класса {@link Product}, содержащих информацию о
     * соответствующих товарах
     *
     * @param category категория товаров, которые необходимо получить
     * @return Список объектов класса {@link Product}, содержащих информацию о соответствующих товарах
     */
    List<Product> findByCategory(final String category);


    /**
     * Метод позволяет получить по цене товара список объектов класса {@link Product}, содержащих информацию о
     * соответствующих товарах
     *
     * @param price цена, по которой необходимо получить список товаров
     * @return Список объектов класса {@link Product}, содержащих информацию о соответствующих товарах
     */
    List<Product> findByPrice(final int price);


    /**
     * Метод позволяет получить по количеству товара список объектов класса {@link Product}, содержащих информацию о
     * соответствующих товарах
     *
     * @param count количество товаров, по которому необходимо получить список товаров
     * @return Список объектов класса {@link Product}, содержащих информацию о соответствующих товарах
     */
    List<Product> findByCount(final int count);


    /**
     * Метод позволяет получить по дате последнего изменения товара список объектов класса {@link Product}, содержащих
     * информацию о соответствующих товарах
     *
     * @param editdate дата последнего изменения товаров, по которой необходимо получить список товаров
     * @return Список объектов класса {@link Product}, содержащих информацию о соответствующих товарах
     */
    List<Product> findByEditdate(final String editdate);


    /**
     * Метод позволяет получить по дате создания товара список объектов класса {@link Product}, содержащих
     * информацию о соответствующих товарах
     *
     * @param createdate дата создания товаров, по которой необходимо получить список товаров
     * @return Список объектов класса {@link Product}, содержащих информацию о соответствующих товарах
     */
    List<Product> findByCreatedate(final LocalDate createdate);


    /**
     * Метод позволяет удалить из базы данных товар по артикулу
     *
     * @param article артикул товара, который необходимо удалить
     * @return <code>1</code>, если товар был удален; <code>0</code> иначе
     */
    Integer deleteByArticle(final UUID article);


    /**
     * Метод позволяет удалить из базы данных товары по названию
     *
     * @param name название товаров, которые необходимо удалить
     * @return <code>1</code>, если товары были удалены; <code>0</code> иначе
     */
    Integer deleteByName(final String name);


    /**
     * Метод позволяет удалить из базы данных товары по описанию
     *
     * @param description описание товаров, которые необходимо удалить
     * @return <code>1</code>, если товары были удалены; <code>0</code> иначе
     */
    Integer deleteByDescription(final String description);


    /**
     * Метод позволяет удалить из базы данных товары по категории
     *
     * @param category категория товаров, которые необходимо удалить
     * @return <code>1</code>, если товары были удалены; <code>0</code> иначе
     */
    Integer deleteByCategory(final String category);


    /**
     * Метод позволяет удалить из базы данных товары по цене
     *
     * @param price цена товаров, которые необходимо удалить
     * @return <code>1</code>, если товары были удалены; <code>0</code> иначе
     */
    Integer deleteByPrice(final int price);


    /**
     * Метод позволяет удалить из базы данных товары по количеству
     *
     * @param count количество товаров, по которому необходимо удалить
     * @return <code>1</code>, если товары были удалены; <code>0</code> иначе
     */
    Integer deleteByCount(final int count);


    /**
     * Метод позволяет удалить из базы данных товары по дате последнего изменения
     *
     * @param editdate дата последнего изменения товаров, по которой необходимо удалить
     * @return <code>1</code>, если товары были удалены; <code>0</code> иначе
     */
    Integer deleteByEditdate(final String editdate);


    /**
     * Метод позволяет удалить из базы данных товары по дате создания
     *
     * @param createdate дата создания товаров, по которой необходимо удалить
     * @return <code>1</code>, если товары были удалены; <code>0</code> иначе
     */
    Integer deleteByCreatedate(final LocalDate createdate);


    /**
     * Метод позволяет определить, существует ли в базе данных товар с соответствующим артикулом
     *
     * @param article артикул товара, который необходимо проверить на наличие в базе данных
     * @return Есть ли на складе товар с таким артикулом: <code>true</code>, если да; <code>false</code> иначе
     */
    boolean existsByArticle(final UUID article);
}
