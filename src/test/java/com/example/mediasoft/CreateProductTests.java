package com.example.mediasoft;

import com.example.mediasoft.dto.CreateProductDTO;
import com.example.mediasoft.exception.ErrorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.mediasoft.util.Utils.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * В данном классе тестируется создание нового товара
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CreateProductTests {
    /**
     * Объект класса {@link MockMvc} для тестирования Spring приложения
     */
    @Autowired
    private MockMvc mockMvc;


    /**
     * Тестируется создание товара при null теле.
     * Выдается ошибка, поскольку тело null
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void createProductWithEmptyBody() throws Exception {
        this.mockMvc.perform(
                post("/product/createProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(null))
        ).andExpect(status().isBadRequest());
    }


    /**
     * Тестируется создание товара при неуказанном названии.
     * Выдается ошибка, поскольку название - null
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void createProductWithEmptyName1() throws Exception {
        this.mockMvc.perform(
                        post("/product/createProduct")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(new CreateProductDTO(
                                        null,
                                        "Описание",
                                        "Категория",
                                        10,
                                        10)))
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Не указано название товара"))));
    }


    /**
     * Тестируется создание товара при пустом названии.
     * Выдается ошибка, поскольку название - пустая строка
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void createProductWithEmptyName2() throws Exception {
        this.mockMvc.perform(
                        post("/product/createProduct")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(new CreateProductDTO(
                                        "",
                                        "Описание",
                                        "Категория",
                                        10,
                                        10)))
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Не указано название товара"))));
    }


    /**
     * Тестируется создание товара при неуказанном описании.
     * Выдается ошибка, поскольку название - null
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void createProductWithEmptyDescription1() throws Exception {
        this.mockMvc.perform(
                        post("/product/createProduct")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(new CreateProductDTO(
                                        "Название",
                                        null,
                                        "Категория",
                                        10,
                                        10)))
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Не указано описание товара"))));
    }


    /**
     * Тестируется создание товара при пустом описании.
     * Выдается ошибка, поскольку название - пустая строка
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void createProductWithEmptyDescription2() throws Exception {
        this.mockMvc.perform(
                        post("/product/createProduct")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(new CreateProductDTO(
                                        "Название",
                                        "",
                                        "Категория",
                                        10,
                                        10)))
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Не указано описание товара"))));
    }


    /**
     * Тестируется создание товара при неуказанной категории.
     * Выдается ошибка, поскольку категория - null
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void createProductWithEmptyCategory1() throws Exception {
        this.mockMvc.perform(
                        post("/product/createProduct")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(new CreateProductDTO(
                                        "Название",
                                        "Описание",
                                        null,
                                        10,
                                        10)))
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Не указана категория товара"))));
    }


    /**
     * Тестируется создание товара при неуказанной категории.
     * Выдается ошибка, поскольку категория - пустая строка
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void createProductWithEmptyCategory2() throws Exception {
        this.mockMvc.perform(
                        post("/product/createProduct")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(new CreateProductDTO(
                                        "Название",
                                        "Описание",
                                        "",
                                        10,
                                        10)))
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Не указана категория товара"))));
    }


    /**
     * Тестируется создание товара при неуказанной цене.
     * Выдается ошибка, поскольку цена - 0
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void createProductWithEmptyPrice() throws Exception {
        this.mockMvc.perform(
                        post("/product/createProduct")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(new CreateProductDTO(
                                        "Название",
                                        "Описание",
                                        "Категория",
                                        0,
                                        10)))
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Неверно указана цена товара"))));
    }


    /**
     * Тестируется создание товара при неуказанном количестве.
     * Выдается ошибка, поскольку количество - 0
     *
     * @throws Exception При возникновении ошибки
     */
    @Test
    void createProductWithEmptyCount() throws Exception {
        this.mockMvc.perform(
                        post("/product/createProduct")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(new CreateProductDTO(
                                        "Название",
                                        "Описание",
                                        "Категория",
                                        10,
                                        0)))
                ).andExpect(status().isBadRequest())
                .andExpect(content().json(asJsonString(new ErrorDTO("Неверно указано количество товара"))));
    }


    /**
     * Тестируется создание товара.
     * Успешное выполнение
     *
     * @throws Exception Не бросается
     */
    @Test
    void createProductSuccess() throws Exception {
        this.mockMvc.perform(
                post("/product/createProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new CreateProductDTO(
                                "Название",
                                "Описание",
                                "Категория",
                                10,
                                10)))
        ).andExpect(status().isOk());
    }
}
