package tests.lyceum.apitests.generics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.time.ZoneId;

public abstract class BaseService<T, ID, DTO> {

    private final JpaRepository<T, ID> repository;

    protected BaseService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    public List<DTO> getAllDTO() {

        List<T> entities = repository.findAll();
        return entities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<T> getById(ID id) {
        return repository.findById(id);
    }

    public Optional<DTO> getDTOById(ID id) {
        return repository.findById(id).map(this::convertToDTO);
    }

    public T create(T entity) {
        return repository.save(entity);
    }

    public DTO update(ID id, Map<String, Object> updates) {
        if (id == null || updates == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID and updates cannot be null");
        }

        T existingEntity = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));

        updateFields(existingEntity, updates);
        repository.save(existingEntity);

        return convertToDTO(existingEntity);
    }

    public void delete(ID id) {
        repository.deleteById(id);
    }

    protected abstract DTO convertToDTO(T entity);

    private void updateFields(Object entity, Map<String, Object> updates) {
        Class<?> clazz = entity.getClass();
        updates.forEach((name, value) -> {
            try {
                Field field = clazz.getDeclaredField(name);
                field.setAccessible(true);
                Object convertedValue = convertValueToRequiredType(field, value);
                field.set(entity, convertedValue);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("Field not found", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error accessing field", e);
            }
        });
    }

    private Object convertValueToRequiredType(Field field, Object value) {
        Class<?> fieldType = field.getType();

        if (fieldType.isEnum() && value instanceof String) {
            return Enum.valueOf((Class<Enum>) fieldType, (String) value);
        } else if (fieldType.equals(Long.class) && value instanceof Integer) {
            return Long.valueOf((Integer) value);
        } else if (fieldType.equals(Double.class) && value instanceof Float) {
            return Double.valueOf((Float) value);
        } else if (fieldType.equals(BigDecimal.class) && value instanceof String) {
            return new BigDecimal((String) value);
        } else if (fieldType.equals(LocalDateTime.class) && value instanceof String) {
            return parseLocalDateTime((String) value);
        } else if (fieldType.equals(String.class) && value != null) {
            return value.toString();
        } else if (fieldType.equals(Date.class) && value instanceof String) {
            return parseDate((String) value);
        }
        return value;
    }

    private LocalDateTime parseLocalDateTime(String dateTimeString) {
        List<String> dateTimeFormats = Arrays.asList(
                "yyyy-MM-dd'T'HH:mm:ss.SSSX", // Формат с суффиксом Z или часовой зоной
                "yyyy-MM-dd'T'HH:mm:ss.SSS",
                "yyyy-MM-dd'T'HH:mm:ss",
                "yyyy-MM-dd'T'HH:mm",
                "yyyy-MM-dd HH:mm:ss",
                "yyyy-MM-dd"
        );

        for (String format : dateTimeFormats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format)
                        .withZone(ZoneId.systemDefault());
                return LocalDateTime.parse(dateTimeString, formatter);
            } catch (DateTimeParseException e) {
                // Игнорируем ошибку и продолжаем со следующим форматом
            }
        }
        throw new RuntimeException("Failed to parse LocalDateTime: " + dateTimeString);
    }

    private Date parseDate(String dateString) {
        List<String> dateFormats = Arrays.asList(
                "yyyy-MM-dd'T'HH:mm:ss.SSSX",
                "yyyy-MM-dd'T'HH:mm:ss.SSS",
                "yyyy-MM-dd'T'HH:mm:ss",
                "yyyy-MM-dd'T'HH:mm",
                "yyyy-MM-dd",
                "yyyy/MM/dd"
        );

        for (String format : dateFormats) {
            try {
                return new SimpleDateFormat(format).parse(dateString);
            } catch (ParseException e) {
            }
        }
        throw new RuntimeException("Failed to parse date: " + dateString);
    }
}
