package com.example.mediasoft.service;

import com.example.mediasoft.dto.CreateProductDTO;
import com.example.mediasoft.exception.CustomException;
import com.example.mediasoft.model.Product;
import com.example.mediasoft.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Данный класс является сервисом, реализующим логику обработки запросов, связанных с товарами на складе:
 * <li>Удаление</li>
 * <li>Получение</li>
 * <li>Изменение</li>
 * <li>Создание</li>
 */
@Service
@RequiredArgsConstructor
public class ProductService {
    /**
     * Интерфейс для взаимодействия с сущностями {@link Product}
     */
    private final ProductRepository productRepository;


    /**
     * Метод позволяет получить товары, соответствующие значению запрашиваемого параметра
     *
     * @param param      параметр, по значению которого будут искаться товары
     * @param paramValue значение параметра, по которому ищутся товары
     * @return Список объектов класса {@link Product}, содержащих информацию о товарах
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
    public List<Product> searchByParam(String param, String paramValue) throws CustomException {
        param = param.toLowerCase(Locale.ROOT);
        if (isParamInvalid(param))
            throw new CustomException("Такого параметра не существует", HttpStatus.NOT_FOUND);

        // Оставил paramValue пустой для editdate, поскольку у нас может не быть даты изменения, а нам
        // требуется найти продукты без изменения
        if (paramValue.isEmpty() && !param.equals("editdate"))
            throw new CustomException("Некорректное значение параметра", HttpStatus.BAD_REQUEST);

        return switch (param) {
            case "article" -> {
                try {
                    yield List.of(productRepository.findByArticle(checkArticle(paramValue)));
                } catch (NullPointerException ex) {
                    yield new ArrayList<>();
                }
            }
            case "name" -> productRepository.findByName(paramValue);
            case "description" -> productRepository.findByDescription(paramValue);
            case "category" -> productRepository.findByCategory(paramValue);
            case "price" -> productRepository.findByPrice(checkInt(paramValue, "Некорректная цена"));
            case "count" -> productRepository.findByCount(checkInt(paramValue, "Некорректное количество"));
            case "editdate" -> productRepository.findByEditdate(checkEditDate(paramValue));
            case "createdate" -> productRepository.findByCreatedate(checkCreateDate(paramValue));
            default -> new ArrayList<>();
        };
    }


    /**
     * Метод позволяет удалить товары, соответствующие значению запрашиваемого параметра
     *
     * @param param      параметр, по значению которого будут удаляться товары
     * @param paramValue значение параметра, по которому удаляются товары
     * @return <code>1</code>, если объекты были удалены; <code>0</code> иначе
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
    @Transactional
    public Integer deleteByParam(String param, String paramValue) throws CustomException {
        param = param.toLowerCase(Locale.ROOT);
        if (isParamInvalid(param))
            throw new CustomException("Такого параметра не существует", HttpStatus.NOT_FOUND);

        // Оставил paramValue пустой для editdate, поскольку у нас может не быть даты изменения, а нам
        // требуется удалить продукты без изменения
        if (paramValue.isEmpty() && !param.equals("editdate"))
            throw new CustomException("Некорректное значение параметра", HttpStatus.BAD_REQUEST);

        return switch (param) {
            case "article" -> productRepository.deleteByArticle(checkArticle(paramValue));
            case "name" -> productRepository.deleteByName(paramValue);
            case "description" -> productRepository.deleteByDescription(paramValue);
            case "category" -> productRepository.deleteByCategory(paramValue);
            case "price" -> productRepository.deleteByPrice(checkInt(paramValue, "Некорректная цена"));
            case "count" -> productRepository.deleteByCount(checkInt(paramValue, "Некорректное количество"));
            case "editdate" -> productRepository.deleteByEditdate(checkEditDate(paramValue));
            case "createdate" -> productRepository.deleteByCreatedate(checkCreateDate(paramValue));
            default -> 0;
        };
    }


    /**
     * Метод позволяет изменить товар с соответствующим артикулом
     *
     * @param param      параметр, значение которого необходимо изменить
     * @param paramValue значение параметра, которое будет установлено товару
     * @param article    артикул товара, у которого будет изменяться значение параметра
     * @return Объект класса {@link Product}, содержащий информацию об измененном товаре
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
    public Product editParam(String param, String paramValue, String article) throws CustomException {
        param = param.toLowerCase(Locale.ROOT);
        if (isParamInvalid(param))
            throw new CustomException("Такого параметра не существует", HttpStatus.NOT_FOUND);

        // Не знаю насколько корректно менять UUID товара, поэтому не добавил возможность для его
        // изменения
        if (param.equals("article"))
            throw new CustomException("Артикул товара изменить нельзя", HttpStatus.BAD_REQUEST);

        if (param.equals("editdate"))
            throw new CustomException("Дату изменения товара изменить нельзя", HttpStatus.BAD_REQUEST);

        if (param.equals("createdate"))
            throw new CustomException("Дату создания товара изменить нельзя", HttpStatus.BAD_REQUEST);

        if (paramValue.isEmpty())
            throw new CustomException("Некорректное значение параметра", HttpStatus.BAD_REQUEST);

        if (article.isEmpty())
            throw new CustomException("Некорректное значение артикула", HttpStatus.BAD_REQUEST);

        if (!productRepository.existsByArticle(checkArticle(article)))
            throw new CustomException("Товара с таким артикулом не существует", HttpStatus.NOT_FOUND);

        Product product = productRepository.findByArticle(checkArticle(article));

        switch (param) {
            case "name" -> product.setName(paramValue);
            case "description" -> product.setDescription(paramValue);
            case "category" -> product.setCategory(paramValue);
            case "price" -> product.setPrice(checkInt(paramValue, "Некорректная цена"));
            case "count" -> product.setCount(checkInt(paramValue, "Некорректное количество"));
        }

        LocalDateTime now = LocalDateTime.now();
        product.setEditdate(now.toLocalDate() + " " + now.toLocalTime().toString().substring(0, 8));
        return productRepository.save(product);
    }


    /**
     * Метод позволяет получить все товары на складе
     *
     * @return Список объектов класса {@link Product}, содержащих информацию обо всех товарах на складе
     */
    public List<Product> getAll() {
        return productRepository.findAll();
    }


    /**
     * Метод позволяет удалить все товары со склада
     */
    public void deleteAll() {
        productRepository.deleteAll();
    }


    /**
     * Метод позволяет добавить новый товар на склад
     *
     * @param createProductDTO объект класса {@link CreateProductDTO}, содержащий информацию о новом товаре
     * @return Объект класса {@link Product}, содержащий информацию о новом товаре
     * @throws CustomException В следующих случаях:
     *                         <li><i>createProductDTO</i> является <code>null</code></li>
     *                         <li>Если не указано название товара</li>
     *                         <li>Если не указано описание товара</li>
     *                         <li>Если не указана категория товара</li>
     *                         <li>Если цена товара меньше 1</li>
     *                         <li>Если количество товара меньше 1</li>
     */
    public Product createProduct(CreateProductDTO createProductDTO) throws CustomException {
        if (createProductDTO == null)
            throw new CustomException("Тело запроса - null", HttpStatus.BAD_REQUEST);

        if (createProductDTO.getName() == null || createProductDTO.getName().isEmpty())
            throw new CustomException("Не указано название товара", HttpStatus.BAD_REQUEST);

        if (createProductDTO.getDescription() == null || createProductDTO.getDescription().isEmpty())
            throw new CustomException("Не указано описание товара", HttpStatus.BAD_REQUEST);

        if (createProductDTO.getCategory() == null || createProductDTO.getCategory().isEmpty())
            throw new CustomException("Не указана категория товара", HttpStatus.BAD_REQUEST);

        if (createProductDTO.getPrice() <= 0)
            throw new CustomException("Неверно указана цена товара", HttpStatus.BAD_REQUEST);

        if (createProductDTO.getCount() <= 0)
            throw new CustomException("Неверно указано количество товара", HttpStatus.BAD_REQUEST);

        Product product = new Product(
                generateArticle(),
                createProductDTO.getName(),
                createProductDTO.getDescription(),
                createProductDTO.getCategory(),
                createProductDTO.getPrice(),
                createProductDTO.getCount(),
                "",
                LocalDate.now());

        return productRepository.save(product);
    }


    /**
     * Метод позволяет проверить параметр на валидность
     *
     * @param param параметр, который необходимо проверить на валидность
     * @return <code>true</code>, если такой параметр существует; <code>false</code> иначе
     */
    private boolean isParamInvalid(String param) {
        return switch (param) {
            case "article", "category", "description", "price", "createdate", "editdate", "count", "name" -> false;
            default -> true;
        };
    }


    /**
     * Метод позволяет конвертировать артикул-строку в UUID
     *
     * @param article артикул-строка, который необходимо конвертировать в UUID
     * @return Объект класса {@link UUID} - конвертированный артикул-строка
     * @throws CustomException В случае, если артикул-строку невозможно конвертировать в UUID
     */
    private UUID checkArticle(String article) throws CustomException {
        try {
            return UUID.fromString(article);
        } catch (IllegalArgumentException ex) {
            throw new CustomException("Некорректный UUID артикул", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Метод позволяет конвертировать строку в число
     *
     * @param intStr      строка, которую необходимо конвертировать в число
     * @param errorString сообщение об ошибке, с которым будет выброшено исключение, если конвертация не удастся
     * @return Исходная строка, представленная в виде целочисленного значения
     * @throws CustomException В случае, если строку невозможно конвертировать в целое число или число меньше 1
     */
    private int checkInt(String intStr, String errorString) throws CustomException {
        try {
            int result = Integer.parseInt(intStr);
            if (result < 1)
                throw new CustomException(errorString, HttpStatus.BAD_REQUEST);
            return result;
        } catch (NumberFormatException ex) {
            throw new CustomException(errorString, HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Метод позволяет проверить строку на соответствие формату <i>yyyy-MM-dd HH:mm:ss</i>
     *
     * @param editDateStr строка, которую необходимо проверить на соответствие формату <i>yyyy-MM-dd HH:mm:ss</i>
     * @return Исходная строка
     * @throws CustomException В случае, если строка не представима в формате <i>yyyy-MM-dd HH:mm:ss</i>
     */
    private String checkEditDate(String editDateStr) throws CustomException {
        if (!editDateStr.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime.parse(editDateStr, formatter);
            } catch (DateTimeParseException ex) {
                throw new CustomException("Некорректный формат даты и времени", HttpStatus.BAD_REQUEST);
            }
        }
        return editDateStr;
    }


    /**
     * Метод позволяет конвертировать строку в дату
     *
     * @param dateStr строка, которую необходимо конвертировать в дату
     * @return Исходная строка, представленная в виде даты в формате <i>yyyy-MM-dd</i><
     * @throws CustomException В случае, если строку невозможно конвертировать в дату формата <i>yyyy-MM-dd</i><
     */
    private LocalDate checkCreateDate(String dateStr) throws CustomException {
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException ex) {
            throw new CustomException("Некорректный формат даты", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Метод позволяет сгенерировать UUID артикул для нового товара
     *
     * @return UUID артикул
     */
    private UUID generateArticle() {
        UUID uuid = UUID.randomUUID();
        while (productRepository.existsByArticle(uuid)) {
            uuid = UUID.randomUUID();
        }

        return uuid;
    }
}
