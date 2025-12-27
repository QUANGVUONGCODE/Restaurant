package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.TableRequest;
import com.store.restaurant.dto.response.TableResponse;
import com.store.restaurant.entity.Table;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class TableMapperImpl implements TableMapper {

    @Override
    public Table mapToTable(TableRequest tableRequest) {
        if ( tableRequest == null ) {
            return null;
        }

        Table.TableBuilder table = Table.builder();

        table.name( tableRequest.getName() );
        table.capacity( tableRequest.getCapacity() );
        if ( tableRequest.getStatus() != null ) {
            table.status( tableRequest.getStatus().name() );
        }

        return table.build();
    }

    @Override
    public TableResponse mapTableResponse(Table table) {
        if ( table == null ) {
            return null;
        }

        TableResponse.TableResponseBuilder tableResponse = TableResponse.builder();

        tableResponse.id( table.getId() );
        tableResponse.name( table.getName() );
        tableResponse.capacity( table.getCapacity() );
        tableResponse.status( table.getStatus() );

        return tableResponse.build();
    }

    @Override
    public void updateTable(TableRequest tableRequest, Table table) {
        if ( tableRequest == null ) {
            return;
        }

        table.setName( tableRequest.getName() );
        table.setCapacity( tableRequest.getCapacity() );
        if ( tableRequest.getStatus() != null ) {
            table.setStatus( tableRequest.getStatus().name() );
        }
        else {
            table.setStatus( null );
        }
    }
}
