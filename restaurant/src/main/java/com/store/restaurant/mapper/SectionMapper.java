package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.SectionRequest;
import com.store.restaurant.entity.Section;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SectionMapper {
    Section mapToSection(SectionRequest request);
    SectionRequest toSectionResponse(Section section);
    void updateSection(SectionRequest sectionRequest, @MappingTarget Section section);
}
