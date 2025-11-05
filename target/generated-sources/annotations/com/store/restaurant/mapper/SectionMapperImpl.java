package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.SectionRequest;
import com.store.restaurant.dto.response.SectionResponse;
import com.store.restaurant.entity.Section;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class SectionMapperImpl implements SectionMapper {

    @Override
    public Section mapToSection(SectionRequest request) {
        if ( request == null ) {
            return null;
        }

        Section.SectionBuilder section = Section.builder();

        section.name( request.getName() );

        return section.build();
    }

    @Override
    public SectionResponse toSectionResponse(Section section) {
        if ( section == null ) {
            return null;
        }

        SectionResponse.SectionResponseBuilder sectionResponse = SectionResponse.builder();

        sectionResponse.id( section.getId() );
        sectionResponse.name( section.getName() );

        return sectionResponse.build();
    }

    @Override
    public void updateSection(SectionRequest sectionRequest, Section section) {
        if ( sectionRequest == null ) {
            return;
        }

        section.setName( sectionRequest.getName() );
    }
}
