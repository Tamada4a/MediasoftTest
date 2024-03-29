package com.example.mediasoft;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * В данном классе тестируется получение и удаление всех товаров из базы данных
 */
@SpringBootTest
@AutoConfigureMockMvc
public class DoAllTests {
    /**
     * Объект класса {@link MockMvc} для тестирования Spring приложения
     */
    @Autowired
    private MockMvc mockMvc;


    /**
     * Тестируется получение всех товаров из базы данных.
     * Успешное выполнение
     *
     * @throws Exception Не выкидывается
     */
    @Test
    void getAllProducts() throws Exception {
        this.mockMvc.perform(get("/product/getAll")).andExpect(status().isOk());
    }


    /**
     * Тестируется удаление всех товаров из базы данных.
     * Успешное выполнение
     *
     * @throws Exception Не выкидывается
     */
    @Test
    void deleteAllProducts() throws Exception {
        this.mockMvc.perform(delete("/product/deleteAll")).andExpect(status().isOk());
    }
}
