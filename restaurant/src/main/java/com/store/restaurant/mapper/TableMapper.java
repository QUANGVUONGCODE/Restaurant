package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.TableRequest;
import com.store.restaurant.dto.response.TableResponse;
import com.store.restaurant.entity.Table;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TableMapper {
    Table mapToTable(TableRequest tableRequest);
    TableResponse mapTableResponse(Table table);
    void updateTable(TableRequest tableRequest, @MappingTarget Table table);
}
