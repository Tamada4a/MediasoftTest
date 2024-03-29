package com.example.mediasoft;

import com.example.mediasoft.exception.ErrorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.mediasoft.util.Utils.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * В данном классе тестируется изменение параметра товара
 */
@SpringBootTest
@AutoConfigureMockMvc
public class EditProductByParamTests {
    /**
     * Объект класса {@link MockMvc} для тестирования Spring приложения
     */
    @Autowired
    private MockMvc mockMvc;


    /**
     * Тестируется изменение товара с несуществующим параметром.
     * Выдается ошибка, поскольку "param" - неизвестный параметр
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void editProductByNonExistentParam() throws Exception {
        this.mockMvc.perform(
                        put("/product/editByParam")
                                .param("param", "param")
                                .param("paramValue", "Tamada")
                                .param("article", "15cb0ec7-4e3a-45fc-afeb-a9c5e119ef34")
                ).andExpect(status().isNotFound())
                .andExpect(content().json(asJsonString(new ErrorDTO("Такого параметра не существует"))));
    }


    /**
     * Тестируется изменение артикула товара.
     * Выдается ошибка, поскольку запрещено изменять артикул
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void editProductByArticle() throws Exception {
        this.mockMvc.perform(
                        put("/product/editByParam")
                                .param("param", "article")
                                .param("paramValue", "15cb0ec7-4e3a-45fc-afeb-a9c5e119ef34")
                                .param("article", "15cb0ec7-4e3a-45fc-afeb-a9c5e119ef34")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Артикул товара изменить нельзя"))));
    }


    /**
     * Тестируется изменение даты изменения товара.
     * Выдается ошибка, поскольку запрещено изменять дату изменения
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void editProductByEditDate() throws Exception {
        this.mockMvc.perform(
                        put("/product/editByParam")
                                .param("param", "editdate")
                                .param("paramValue", "")
                                .param("article", "15cb0ec7-4e3a-45fc-afeb-a9c5e119ef34")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Дату изменения товара изменить нельзя"))));
    }


    /**
     * Тестируется изменение даты создания товара.
     * Выдается ошибка, поскольку запрещено изменять дату создания
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void editProductByCreateDate() throws Exception {
        this.mockMvc.perform(
                        put("/product/editByParam")
                                .param("param", "createdate")
                                .param("paramValue", "")
                                .param("article", "15cb0ec7-4e3a-45fc-afeb-a9c5e119ef34")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Дату создания товара изменить нельзя"))));
    }


    /**
     * Тестируется изменение параметра с пустым значением параметра.
     * Выдается ошибка, поскольку значение параметра - пустая строка
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void editProductByEmptyParamValue() throws Exception {
        this.mockMvc.perform(
                        put("/product/editByParam")
                                .param("param", "name")
                                .param("paramValue", "")
                                .param("article", "15cb0ec7-4e3a-45fc-afeb-a9c5e119ef34")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректное значение параметра"))));
    }


    /**
     * Тестируется изменение параметра с пустым значением артикула.
     * Выдается ошибка, поскольку не указан артикул
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void editProductByEmptyArticle() throws Exception {
        this.mockMvc.perform(
                        put("/product/editByParam")
                                .param("param", "name")
                                .param("paramValue", "Fluffy bear")
                                .param("article", "")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректное значение артикула"))));
    }


    /**
     * Тестируется изменение параметра с несуществующим артикулом.
     * Выдается ошибка, поскольку указан несуществующий артикул
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void editProductByNonExistentArticle() throws Exception {
        this.mockMvc.perform(
                        put("/product/editByParam")
                                .param("param", "name")
                                .param("paramValue", "Fluffy bear")
                                .param("article", "15cb0ec7-4e3a-45fc-afeb-a9c5e119ef30")
                ).andExpect(status().isNotFound())
                .andExpect(content().json(asJsonString(new ErrorDTO("Товара с таким артикулом не существует"))));
    }


    /**
     * Тестируется изменение параметра с некорректной ценой.
     * Выдается ошибка, поскольку указана некорректная цена
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void editProductByInvalidPrice1() throws Exception {
        this.mockMvc.perform(
                        put("/product/editByParam")
                                .param("param", "price")
                                .param("paramValue", "12a")
                                .param("article", "15cb0ec7-4e3a-45fc-afeb-a9c5e119ef34")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректная цена"))));
    }


    /**
     * Тестируется изменение параметра с некорректной ценой.
     * Выдается ошибка, поскольку указана некорректная цена
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void editProductByInvalidPrice2() throws Exception {
        this.mockMvc.perform(
                        put("/product/editByParam")
                                .param("param", "price")
                                .param("paramValue", "0")
                                .param("article", "15cb0ec7-4e3a-45fc-afeb-a9c5e119ef34")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректная цена"))));
    }


    /**
     * Тестируется изменение параметра с некорректным количеством.
     * Выдается ошибка, поскольку указано некорректное количество
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void editProductByInvalidCount1() throws Exception {
        this.mockMvc.perform(
                        put("/product/editByParam")
                                .param("param", "count")
                                .param("paramValue", "12a")
                                .param("article", "15cb0ec7-4e3a-45fc-afeb-a9c5e119ef34")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректное количество"))));
    }


    /**
     * Тестируется изменение параметра с некорректным количеством.
     * Выдается ошибка, поскольку указано некорректное количество
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void editProductByInvalidCount2() throws Exception {
        this.mockMvc.perform(
                        put("/product/editByParam")
                                .param("param", "count")
                                .param("paramValue", "-1")
                                .param("article", "15cb0ec7-4e3a-45fc-afeb-a9c5e119ef34")
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Некорректное количество"))));
    }


    /**
     * Тестируется изменение параметра.
     * Успешное выполнение
     *
     * @throws Exception Не выкидывается
     */
    @Test
    void editProductByParamSuccess() throws Exception {
        this.mockMvc.perform(
                put("/product/editByParam")
                        .param("param", "count")
                        .param("paramValue", "10")
                        .param("article", "14cb0ec7-4e3a-45fc-afeb-a9c5e119ef34")
        ).andExpect(status().isOk());
    }
}
