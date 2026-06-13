package tw.edu.fju.miniclinic.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Converter
public class LocalDateConverter implements AttributeConverter<LocalDate, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String convertToDatabaseColumn(LocalDate attribute) {
        return attribute != null ? attribute.format(FORMATTER) : null;
    }

    @Override
    public LocalDate convertToEntityAttribute(String dbData) { // 🎯 修正：回傳型態必須是 LocalDate！
        return dbData != null ? LocalDate.parse(dbData, FORMATTER) : null;
    }
}