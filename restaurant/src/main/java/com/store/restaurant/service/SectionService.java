package com.store.restaurant.service;

import com.store.restaurant.dto.request.SectionRequest;
import com.store.restaurant.dto.response.SectionResponse;
import com.store.restaurant.entity.Section;
import com.store.restaurant.exception.AppException;
import com.store.restaurant.exception.ErrorCode;
import com.store.restaurant.mapper.SectionMapper;
import com.store.restaurant.repository.SectionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class SectionService {
    SectionRepository sectionRepository;
    SectionMapper sectionMapper;

    public SectionResponse createSection(SectionRequest request){
        if(sectionRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.SECTION_EXISTS);
        }
        Section section = sectionMapper.mapToSection(request);
        return sectionMapper.toSectionResponse(sectionRepository.save(section));
    }

    public List<SectionResponse> getAllSections(){
        return sectionRepository.findAll().stream().map(sectionMapper::toSectionResponse).toList();
    }

    public SectionResponse getSectionById(Long id){
        return sectionMapper.toSectionResponse(sectionRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        ));
    }

    public SectionResponse updateSection(Long id, SectionRequest request){
        Section section = sectionRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );
        sectionMapper.updateSection(request, section);
        return sectionMapper.toSectionResponse(sectionRepository.save(section));
    }

    public void deleteSection(Long id){
        if(!sectionRepository.existsById(id)){
            throw new AppException(ErrorCode.INVALID_ID);
        }
        sectionRepository.deleteById(id);
    }
}
