package com.ChairShop.utils.enum_convertor;


import com.ChairShop.service.model.IamServiceUserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;

@Converter
public class UserRoleTypeConvertor implements AttributeConverter<IamServiceUserRole, String> {

    @Override
    public String convertToDatabaseColumn(IamServiceUserRole iamServiceUserRole) {
        return iamServiceUserRole.name();
    }

    @Override
    public IamServiceUserRole convertToEntityAttribute(String s) {
        return IamServiceUserRole.fromName(s);
    }
}
