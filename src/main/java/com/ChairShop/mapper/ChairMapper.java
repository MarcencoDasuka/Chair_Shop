package com.ChairShop.mapper;


import com.ChairShop.model.dto.chair.ChairDTO;
import com.ChairShop.model.enteties.Chair;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Objects;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {DateTimeUtils.class, Objects.class}
)

public interface ChairMapper {


//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "name", target = "name")
//    @Mapping(source = "description", target = "description")
//    @Mapping(source = "category", target = "category")
//    @Mapping(source = "price", target = "price")
//    @Mapping(source = "stock", target = "stock")
//    @Mapping(source = "material", target = "material")
//    @Mapping(source = "imageUrl", target = "imageUrl")
//    @Mapping(source = "createAt", target = "CreateAt", dateFormat = "yyyy-MM-DD'T'HH:mm:ss")
//    @Mapping(source = "updateAt", target = "updateAt", dateFormat = "yyyy-MM-DD'T'HH:mm:ss")
    ChairDTO toChairDTO(Chair chair);

}
