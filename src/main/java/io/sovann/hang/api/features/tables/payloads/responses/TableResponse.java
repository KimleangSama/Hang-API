package io.sovann.hang.api.features.tables.payloads.responses;

import io.sovann.hang.api.features.tables.entities.*;
import java.util.*;
import lombok.*;

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
        response.setIsGrouped(table.getIsGrouped());
        return response;
    }
}
