package com.home.user.dtogetter;

import com.home.user.dto.LocationDto;
import org.springframework.stereotype.Component;

@Component
public class LocationDtoGetterImpl extends AbstractDtoGetter<LocationDto, Long> {

    public LocationDtoGetterImpl() {
        setResponseType(LocationDto.class);
    }

    @Override
    String getParameters(Long locationId) {
        return String.valueOf(locationId);
    }
}
