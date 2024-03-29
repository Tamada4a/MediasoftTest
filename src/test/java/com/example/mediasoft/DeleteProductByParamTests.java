package com.example.mediasoft;

import com.example.mediasoft.exception.ErrorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.mediasoft.util.Utils.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * В данном классе тестируется удаление товара из базы данных
 */
@SpringBootTest
@AutoConfigureMockMvc
public class DeleteProductByParamTests {
    /**
     * Объект класса {@link MockMvc} для тестирования Spring приложения
     */
    @Autowired
    private MockMvc mockMvc;


    /**
     * Тестируется удаление товаров при несуществующем параметре.
     * Выдается ошибка, поскольку "param" - неизвестный параметр
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void deleteProductByNonExistentParam() throws Exception {
        this.mockMvc.perform(
                        delete("/product/deleteByParam")
                                .param("param", "param")
                                .param("paramValue", "Tamada")
                ).andExpect(status().isNotFound())
                .andExpect(content().json(asJsonString(new ErrorDTO("Такого параметра не существует"))));
    }


    /**
     * Тестируется удаление товаров при пустом значении параметра.
     * Выдается ошибка, поскольку значение параметра - пустая строка
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void deleteProductByEmptyParamValue() throws Exception {
        this.mockMvc.perform(
                        delete("/product/deleteByParam")
                                .param("param", "name")
                                .param("paramValue", "")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректное значение параметра"))));
    }


    /**
     * Тестируется удаление товаров при некорректном значении артикула.
     * Выдается ошибка, поскольку значение параметра - невалидная UUID строка
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void deleteProductByInvalidArticle() throws Exception {
        this.mockMvc.perform(
                        delete("/product/deleteByParam")
                                .param("param", "article")
                                .param("paramValue", "invalid-article-12")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректный UUID артикул"))));
    }


    /**
     * Тестируется удаление товаров при некорректном значении цены.
     * Выдается ошибка, поскольку значение параметра - невалидная для конвертации в число строка
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void deleteProductByInvalidPrice1() throws Exception {
        this.mockMvc.perform(
                        delete("/product/deleteByParam")
                                .param("param", "price")
                                .param("paramValue", "12a")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректная цена"))));
    }


    /**
     * Тестируется удаление товаров при некорректном значении цены.
     * Выдается ошибка, поскольку значение параметра - невалидная для конвертации в число строка
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void deleteProductByInvalidPrice2() throws Exception {
        this.mockMvc.perform(
                        delete("/product/deleteByParam")
                                .param("param", "price")
                                .param("paramValue", "0")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректная цена"))));
    }


    /**
     * Тестируется удаление товаров при некорректном значении количества.
     * Выдается ошибка, поскольку значение параметра - невалидная для конвертации в число строка
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void deleteProductByInvalidCount1() throws Exception {
        this.mockMvc.perform(
                        delete("/product/deleteByParam")
                                .param("param", "count")
                                .param("paramValue", "12a")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректное количество"))));
    }


    /**
     * Тестируется удаление товаров при некорректном значении количества.
     * Выдается ошибка, поскольку значение параметра - невалидная для конвертации в число строка
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void deleteProductByInvalidCount2() throws Exception {
        this.mockMvc.perform(
                        delete("/product/deleteByParam")
                                .param("param", "count")
                                .param("paramValue", "0")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректное количество"))));
    }


    /**
     * Тестируется удаление товаров при некорректном значении даты изменения товара.
     * Выдается ошибка, поскольку значение параметра - невалидная для конвертации в LocalDateTime строка
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void deleteProductByInvalidEditDate() throws Exception {
        this.mockMvc.perform(
                        delete("/product/deleteByParam")
                                .param("param", "editdate")
                                .param("paramValue", "2003-15 15:10:18")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректный формат даты и времени"))));
    }


    /**
     * Тестируется удаление товаров при некорректном значении даты создания товара.
     * Выдается ошибка, поскольку значение параметра - невалидная для конвертации в LocalDate строка
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void deleteProductByInvalidCreateDate() throws Exception {
        this.mockMvc.perform(
                        delete("/product/deleteByParam")
                                .param("param", "createdate")
                                .param("paramValue", "2003-15")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректный формат даты"))));
    }


    /**
     * Тестируется удаление товаров при наличии в базе данных.
     * Успешное выполнение
     *
     * @throws Exception Не кидается
     */
    @Test
    void deleteProductByParamSuccess1() throws Exception {
        this.mockMvc.perform(
                        delete("/product/deleteByParam")
                                .param("param", "name")
                                .param("paramValue", "Fluffy bears")
                ).andExpect(status().isOk())
                .andExpect(content().string("Объект(ы) с параметром name и значением Fluffy bears был(и) успешно удален(ы)"));
    }


    /**
     * Тестируется удаление товаров при отсутствии в базе данных.
     * Успешное выполнение
     *
     * @throws Exception Не кидается
     */
    @Test
    void deleteProductByParamSuccess2() throws Exception {
        this.mockMvc.perform(
                        delete("/product/deleteByParam")
                                .param("param", "name")
                                .param("paramValue", "Fluffy bears")
                ).andExpect(status().isOk())
                .andExpect(content().string("Объект(ы) с параметром name и значением Fluffy bears не найден(ы)"));
    }
}
