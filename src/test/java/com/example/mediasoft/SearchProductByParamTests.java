package com.example.mediasoft;

import com.example.mediasoft.exception.ErrorDTO;
import com.example.mediasoft.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.mediasoft.util.Utils.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * В данном классе тестируется поиск товаров по запрашиваемому значению параметра
 */
@SpringBootTest
@AutoConfigureMockMvc
public class SearchProductByParamTests {
    /**
     * Объект класса {@link MockMvc} для тестирования Spring приложения
     */
    @Autowired
    private MockMvc mockMvc;


    /**
     * Тестируется поиск товаров с несуществующим параметром.
     * Выдается ошибка, поскольку "param" - неизвестный параметр
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void searchProductByNonExistentParam() throws Exception {
        this.mockMvc.perform(
                        get("/product/searchByParam")
                                .param("param", "param")
                                .param("paramValue", "Tamada")
                ).andExpect(status().isNotFound())
                .andExpect(content().json(asJsonString(new ErrorDTO("Такого параметра не существует"))));
    }


    /**
     * Тестируется поиск товаров при пустом значении параметра.
     * Выдается ошибка, поскольку значение параметра - пустая строка
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void searchProductByEmptyParamValue() throws Exception {
        this.mockMvc.perform(
                        get("/product/searchByParam")
                                .param("param", "name")
                                .param("paramValue", "")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректное значение параметра"))));
    }


    /**
     * Тестируется поиск товаров при некорректном значении артикула.
     * Выдается ошибка, поскольку значение параметра - невалидная UUID строка
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void searchProductByInvalidArticle() throws Exception {
        this.mockMvc.perform(
                        get("/product/searchByParam")
                                .param("param", "article")
                                .param("paramValue", "invalid-article-12")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректный UUID артикул"))));
    }


    /**
     * Тестируется поиск товаров при некорректном значении цены.
     * Выдается ошибка, поскольку значение параметра - невалидная для конвертации в число строка
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void searchProductByInvalidPrice1() throws Exception {
        this.mockMvc.perform(
                        get("/product/searchByParam")
                                .param("param", "price")
                                .param("paramValue", "12a")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректная цена"))));
    }


    /**
     * Тестируется поиск товаров при некорректном значении цены.
     * Выдается ошибка, поскольку значение параметра - невалидная для конвертации в число строка
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void searchProductByInvalidPrice2() throws Exception {
        this.mockMvc.perform(
                        get("/product/searchByParam")
                                .param("param", "price")
                                .param("paramValue", "0")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректная цена"))));
    }


    /**
     * Тестируется поиск товаров при некорректном значении количества.
     * Выдается ошибка, поскольку значение параметра - невалидная для конвертации в число строка
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void searchProductByInvalidCount1() throws Exception {
        this.mockMvc.perform(
                        get("/product/searchByParam")
                                .param("param", "count")
                                .param("paramValue", "12a")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректное количество"))));
    }


    /**
     * Тестируется поиск товаров при некорректном значении количества.
     * Выдается ошибка, поскольку значение параметра - невалидная для конвертации в число строка
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void searchProductByInvalidCount2() throws Exception {
        this.mockMvc.perform(
                        get("/product/searchByParam")
                                .param("param", "count")
                                .param("paramValue", "0")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректное количество"))));
    }


    /**
     * Тестируется поиск товаров при некорректном значении даты изменения товара.
     * Выдается ошибка, поскольку значение параметра - невалидная для конвертации в LocalDateTime строка
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void searchProductByInvalidEditDate() throws Exception {
        this.mockMvc.perform(
                        get("/product/searchByParam")
                                .param("param", "editdate")
                                .param("paramValue", "2003-15 15:10:18")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректный формат даты и времени"))));
    }


    /**
     * Тестируется поиск товаров при некорректном значении даты создания товара.
     * Выдается ошибка, поскольку значение параметра - невалидная для конвертации в LocalDate строка
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void searchProductByInvalidCreateDate() throws Exception {
        this.mockMvc.perform(
                        get("/product/searchByParam")
                                .param("param", "createdate")
                                .param("paramValue", "2003-15")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректный формат даты"))));
    }


    /**
     * Тестируется поиск товаров при наличии в базе данных.
     * Успешное выполнение
     *
     * @throws Exception Не кидается
     */
    @Test
    void searchProductSuccess1() throws Exception {
        this.mockMvc.perform(
                        get("/product/searchByParam")
                                .param("param", "article")
                                .param("paramValue", "15cb0ec7-4e3a-45fc-afeb-a9c5e119ef34")
                ).andExpect(status().isOk())
                .andExpect(content().json(asJsonString(List.of(new Product(
                        UUID.fromString("15cb0ec7-4e3a-45fc-afeb-a9c5e119ef34"),
                        "Fluffy bear",
                        "What a beautiful toy",
                        "Toys",
                        1000,
                        2,
                        "",
                        LocalDate.now()))))
                );
    }


    /**
     * Тестируется поиск товаров при отсутствии в базе данных.
     * Успешное выполнение
     *
     * @throws Exception Не кидается
     */
    @Test
    void searchProductSuccess2() throws Exception {
        this.mockMvc.perform(
                        get("/product/searchByParam")
                                .param("param", "article")
                                .param("paramValue", "15cb0ec7-4e3a-45fc-afeb-a9c5e119ef35")
                ).andExpect(status().isOk())
                .andExpect(content().json(asJsonString(new ArrayList<Product>())));
    }
}
