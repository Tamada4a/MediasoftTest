package com.example.mediasoft.controller;

import com.example.mediasoft.dto.CreateProductDTO;
import com.example.mediasoft.exception.CustomException;
import com.example.mediasoft.exception.ErrorDTO;
import com.example.mediasoft.model.Product;
import com.example.mediasoft.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Данный REST-контроллер отвечает за получение запросов, связанных с товарами на складе.<br></br>
 * Пользователь может:
 * <li>Найти/удалить/изменить товары по интересующему его параметру</li>
 * <li>Получить список всех товаров</li>
 * <li>Удалить все товары</li>
 * <li>Создать новый товар</li>
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
@Tag(name = "Product")
public class ProductController {
    /**
     * Объект класса {@link ProductService} - сервис, обрабатывающий запросы, связанные с товарами
     */
    private final ProductService productService;

    /**
     * JSON-строка для использования в качестве примера списка товаров в спецификации
     */
    private final String productListExample = "[\n{\"article\":\"15cb0ec7-4e3a-45fc-afeb-a9c5e119ef34\",\"name\":\"Fluffy bear\",\"description\":\"What a beautiful toy\",\"category\":\"Toys\",\"price\":1000,\"count\":100,\"editdate\":\"\",\"createdate\":\"2024-03-29\"}\n]";

    /**
     * JSON-строка для использования в качестве примера товара в спецификации
     */
    private final String productExample = "{\"article\":\"15cb0ec7-4e3a-45fc-afeb-a9c5e119ef34\",\"name\":\"Fluffy bear\",\"description\":\"What a beautiful toy\",\"category\":\"Toys\",\"price\":1000,\"count\":100,\"editdate\":\"\",\"createdate\":\"2024-03-29\"}";

    /**
     * JSON-строка для использования в качестве примера ошибки 400 в спецификации
     */
    private final String error400 = "{\"message\": \"Некорректное значение параметра\"}";

    /**
     * JSON-строка для использования в качестве примера ошибки 404 в спецификации
     */
    private final String error404 = "{\"message\": \"Такого параметра не существует\"}";

    /**
     * JSON-строка для использования в качестве примера создания нового товара в спецификации
     */
    private final String newProductExample = "{\"name\": \"Fluffy bear\",\"description\": \"Pretty good toy\",\"category\": \"toys\",\"price\": \"100\",\"count\": \"10\"}";


    /**
     * Метод обрабатывает GET-запрос по пути "/product/searchByParam?param=...?paramValue=..." для получения
     * товаров, соответствующих значению запрашиваемого параметра
     *
     * @param param      параметр, по значению которого будут искаться товары
     * @param paramValue значение параметра, по которому ищутся товары
     * @return <code>ResponseEntity</code> со статусом 200, тело которого - список объектов класса {@link Product},
     * содержащих информацию о товарах
     * @throws CustomException В следующих случаях:
     *                         <li>Получен некорректный параметр</li>
     *                         <li>В качестве <i>paramValue</i> передана пустая строка, если <i>param</i> не равен
     *                         <i>"editdate"</i></li>
     *                         <li>Если требуется получить товар по артикулу, но значение параметра не является UUID</li>
     *                         <li>Если требуется получить товары по цене или количеству, но в качестве значения
     *                         параметра передано не число или число меньше 1</li>
     *                         <li>Если требуется получить товары по дате изменения, но значение параметра не
     *                         соответствует формату <i>yyyy-MM-dd HH:mm:ss</i></li>
     *                         <li>Если требуется получить товары по дате создания, но значение параметра не
     *                         соответствует формату <i>yyyy-MM-dd</i></li>
     */
    @GetMapping("/searchByParam")
    @Operation(
            summary = "Получить товары по значению параметра",
            description = "Получить список товаров по значению параметра",
            parameters = {
                    @Parameter(
                            name = "param",
                            in = ParameterIn.QUERY,
                            description = "Параметр, по которому будет идти поиск",
                            required = true,
                            schema = @Schema(
                                    type = "string",
                                    allowableValues = {"article", "category", "description", "price", "createdate", "editdate", "count", "name"}
                            )
                    ),
                    @Parameter(
                            name = "paramValue",
                            in = ParameterIn.QUERY,
                            description = "Значение параметра, в соответствии с которым ищутся товары",
                            required = true,
                            schema = @Schema(type = "string")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Операция выполнена успешно",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(value = productListExample)
                                    },
                                    schema = @Schema(implementation = Product.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Значение параметра не соответствует типу параметра или параметр - пустая строка (при этом параметр не \"editdate\")",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(value = error400)
                                    },
                                    schema = @Schema(implementation = ErrorDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Такого параметра не существует",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(value = error404)
                                    },
                                    schema = @Schema(implementation = ErrorDTO.class)
                            )
                    )
            }
    )
    public ResponseEntity<List<Product>> searchByParam(@RequestParam String param,
                                                       @RequestParam String paramValue) throws CustomException {
        return ResponseEntity.ok(productService.searchByParam(param, paramValue));
    }


    /**
     * Метод обрабатывает DELETE-запрос по пути "/product/deleteByParam?param=...?paramValue=..." для удаления
     * товаров, соответствующих значению запрашиваемого параметра
     *
     * @param param      параметр, по значению которого будут удаляться товары
     * @param paramValue значение параметра, по которому удаляются товары
     * @return <code>ResponseEntity</code> со статусом 200, тело которого - строка, соответствующая результату удаления:
     * <li>Если <code>1</code> - <i>"Объект(ы) с параметром %s и значением %s был(и) успешно удален(ы)"</i></li>
     * <li>Если <code>0</code> - <i>"Объект(ы) с параметром %s и значением %s не найден(ы)"</i></li>
     * @throws CustomException В следующих случаях:
     *                         <li>Получен некорректный параметр</li>
     *                         <li>В качестве <i>paramValue</i> передана пустая строка, если <i>param</i> не равен
     *                         <i>"editdate"</i></li>
     *                         <li>Если требуется удалить товар по артикулу, но значение параметра не является UUID</li>
     *                         <li>Если требуется удалить товары по цене или количеству, но в качестве значения
     *                         параметра передано не число или число меньше 1</li>
     *                         <li>Если требуется удалить товары по дате изменения, но значение параметра не
     *                         соответствует формату <i>yyyy-MM-dd HH:mm:ss</i></li>
     *                         <li>Если требуется удалить товары по дате создания, но значение параметра не
     *                         соответствует формату <i>yyyy-MM-dd</i></li>
     */
    @DeleteMapping("/deleteByParam")
    @Operation(
            summary = "Удалить товары по значению параметра",
            description = "Удалить товары по значению параметра",
            parameters = {
                    @Parameter(
                            name = "param",
                            in = ParameterIn.QUERY,
                            description = "Параметр, по которому будет идти удаление",
                            required = true,
                            schema = @Schema(
                                    type = "string",
                                    allowableValues = {"article", "category", "description", "price", "createdate", "editdate", "count", "name"}
                            )
                    ),
                    @Parameter(
                            name = "paramValue",
                            in = ParameterIn.QUERY,
                            description = "Значение параметра, в соответствии с которым удаляются товары",
                            required = true,
                            schema = @Schema(type = "string")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Операция выполнена успешно",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(value = "Объект(ы) с параметром ${param} и значением ${paramValue} был(и) успешно удален(ы)"),
                                            @ExampleObject(value = "Объект(ы) с параметром ${param} и значением ${paramValue} не найден(ы)")
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Значение параметра не соответствует типу параметра или параметр - пустая строка (при этом параметр не \"editdate\")",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(value = error400)
                                    },
                                    schema = @Schema(implementation = ErrorDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Такого параметра не существует",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(value = error404)
                                    },
                                    schema = @Schema(implementation = ErrorDTO.class)
                            )
                    )
            }
    )
    public ResponseEntity<String> deleteByParam(@RequestParam String param, @RequestParam String paramValue) throws CustomException {
        if (productService.deleteByParam(param, paramValue) == 1)
            return ResponseEntity.ok(String.format("Объект(ы) с параметром %s и значением %s был(и) успешно удален(ы)", param, paramValue));
        return ResponseEntity.ok(String.format("Объект(ы) с параметром %s и значением %s не найден(ы)", param, paramValue));
    }


    /**
     * Метод обрабатывает PUT-запрос по пути "/product/editByParam?param=...?paramValue=...?article=..." для изменения товара,
     * с соответствующим артикулом
     *
     * @param param      параметр, значение которого необходимо изменить
     * @param paramValue значение параметра, которое будет установлено товару
     * @param article    артикул товара, у которого будет изменяться значение параметра
     * @return <code>ResponseEntity</code> со статусом 200, тело которого - объект класса {@link Product}, содержащий
     * информацию об измененном товаре
     * @throws CustomException В следующих случаях:
     *                         <li>Получен некорректный параметр</li>
     *                         <li>В качестве параметра передан <i>"article"</i>, или <i>"createdate"</i>, или
     *                         <i>"editdate"</i> - их изменять
     *                         нельзя</li>
     *                         <li>В качестве <i>paramValue</i> или <i>article</i> передана пустая строка</li>
     *                         <li>Если товара с выбранным артикулом не существует</li>
     *                         <li>Если требуется изменить у товара цену или количество, но в качестве значения
     *                         параметра передано не число или переданное значение меньше 1</li>
     */
    @PutMapping("/editByParam")
    @Operation(
            summary = "Изменить значение параметра у товара",
            description = "По артикулу обратиться к товару и присвоить параметру определенное значение",
            parameters = {
                    @Parameter(
                            name = "param",
                            in = ParameterIn.QUERY,
                            description = "Параметр, по которому будет идти удаление",
                            required = true,
                            schema = @Schema(
                                    type = "string",
                                    allowableValues = {"article", "category", "description", "price", "createdate", "editdate", "count", "name"}
                            )
                    ),
                    @Parameter(
                            name = "paramValue",
                            in = ParameterIn.QUERY,
                            description = "Значение параметра, в соответствии с которым удаляются товары",
                            required = true,
                            schema = @Schema(type = "string")
                    ),
                    @Parameter(
                            name = "article",
                            in = ParameterIn.QUERY,
                            description = "Артикул товара, у которого необходимо изменить значение",
                            required = true,
                            schema = @Schema(
                                    type = "string",
                                    format = "uuid"
                            )
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Операция выполнена успешно",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(value = productExample),
                                    },
                                    schema = @Schema(implementation = Product.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "При попытке изменить артикул, дату создания, дату изменения. Если значение " +
                                    "параметра или артикул - пустая строка (при этом параметр не \"editdate\"). Если " +
                                    "целочисленное значение (цена или количество) меньше 1 или значение параметра не " +
                                    "является целочисленным, хотя параметр имеет целый тип",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(value = error400)
                                    },
                                    schema = @Schema(implementation = ErrorDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Такого параметра или товара с таким артикулом не существует",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(value = error404)
                                    },
                                    schema = @Schema(implementation = ErrorDTO.class)
                            )
                    )
            }
    )
    public ResponseEntity<Product> editByParam(@RequestParam String param, @RequestParam String paramValue,
                                               @RequestParam String article) throws CustomException {
        return ResponseEntity.ok(productService.editParam(param, paramValue, article));
    }


    /**
     * Метод обрабатывает GET-запрос по пути "/product/getAll" для получения всех товаров на складе
     *
     * @return <code>ResponseEntity</code> со статусом 200, тело которого - список объектов класса {@link Product},
     * содержащих информацию обо всех товарах на складе
     */
    @GetMapping("/getAll")
    @Operation(
            summary = "Получить список всех товаров со склада",
            description = "Получить список всех товаров со склада",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Операция выполнена успешно",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(value = productListExample)
                                    },
                                    schema = @Schema(implementation = Product.class)
                            )
                    )
            }
    )
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }


    /**
     * Метод обрабатывает DELETE-запрос по пути "/product/deleteAll" для удаления всех товаров со склада
     *
     * @return <code>ResponseEntity</code> со статусом 200, тело которого - строка
     * <i>"Все объекты были успешно удалены"</i>
     */
    @DeleteMapping("/deleteAll")
    @Operation(
            summary = "Удалить все товары со склада",
            description = "Удалить все товары со склада",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Операция выполнена успешно",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(value = "Все объекты были успешно удалены")
                                    },
                                    schema = @Schema(type = "string")
                            )
                    )
            }
    )
    public ResponseEntity<String> deleteAll() {
        productService.deleteAll();
        return ResponseEntity.ok("Все объекты были успешно удалены");
    }


    /**
     * Метод обрабатывает POST-запрос по пути "/product/createProduct" для добавления нового товара на склад
     *
     * @param createProductDTO объект класса {@link CreateProductDTO}, содержащий информацию о новом товаре
     * @return <code>ResponseEntity</code> со статусом 200, тело которого - объект класса {@link Product}, содержащий
     * информацию о новом товаре
     * @throws CustomException В следующих случаях:
     *                         <li><i>createProductDTO</i> является <code>null</code></li>
     *                         <li>Если не указано название товара</li>
     *                         <li>Если не указано описание товара</li>
     *                         <li>Если не указана категория товара</li>
     *                         <li>Если цена товара меньше 1</li>
     *                         <li>Если количество товара меньше 1</li>
     */
    @PostMapping("/createProduct")
    @Operation(
            summary = "Добавление нового товара",
            description = "Добавление нового товара",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "На склад добавляется новый товар",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = newProductExample)
                            },
                            schema = @Schema(implementation = CreateProductDTO.class)
                    ),
                    required = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Операция выполнена успешно",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(value = productExample)
                                    },
                                    schema = @Schema(implementation = Product.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Если тело запроса - null. Если не указаны - название, описание, категория, цена, или количество товара",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(value = "{\"message\":\"Не указано название товара\"}")
                                    },
                                    schema = @Schema(implementation = ErrorDTO.class)
                            )
                    )
            }
    )
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductDTO createProductDTO) throws CustomException {
        return ResponseEntity.ok(productService.createProduct(createProductDTO));
    }
}
