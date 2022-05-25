package com.home.company.dtogetter;

import com.home.company.dto.LocationDto;

public class LocationDtoGetterImpl extends AbstractDtoGetter<LocationDto, Long> {

    public LocationDtoGetterImpl() {
        setResponseType(LocationDto.class);
    }

    @Override
    String getParameters(Long locationId) {
        return String.valueOf(locationId);
    }
}
