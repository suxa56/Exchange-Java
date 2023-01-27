package uz.suxa.postdbservice.converter;

import uz.suxa.postdbservice.model.Ccy;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CcyAttributeConverter implements AttributeConverter<Ccy, String> {
    @Override
    public String convertToDatabaseColumn(Ccy attribute) {
        return attribute.name();
    }

    @Override
    public Ccy convertToEntityAttribute(String dbData) {
        return Ccy.valueOf(dbData);
    }
}
