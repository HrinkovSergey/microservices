package com.home.location.mapper;

import com.home.location.domain.Location;
import com.home.location.dto.LocationDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocationMapper {
    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    LocationDto toDto(Location location);

    Location toEntity(LocationDto locationDto);
}
