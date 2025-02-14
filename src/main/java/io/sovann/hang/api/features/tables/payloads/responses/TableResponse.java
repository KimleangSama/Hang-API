package io.sovann.hang.api.features.tables.payloads.responses;

import io.sovann.hang.api.features.tables.entities.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class TableResponse {
    private UUID id;
    private String name;
    private String tableNumber;
    private String description;
    private double basePrice;
    private String status;
    private int capacity;
    private String qr;
    private Boolean isGrouped = false;

    public static TableResponse fromEntity(Table table) {
        TableResponse response = new TableResponse();
        response.setId(table.getId());
        response.setName(table.getName());
        response.setTableNumber(table.getTableNumber());
        response.setDescription(table.getDescription());
        response.setBasePrice(table.getBasePrice());
        response.setStatus(table.getStatus().name());
        response.setCapacity(table.getCapacity());
        response.setQr(response.getQr());
        response.setIsGrouped(table.getIsGrouped());
        return response;
    }

    public static List<TableResponse> fromEntities(List<Table> content) {
        List<TableResponse> responses = new ArrayList<>();
        for (Table table : content) {
            responses.add(fromEntity(table));
        }
        return responses;
    }
}
