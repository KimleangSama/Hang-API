package io.sovann.hang.api.features.tables.payloads.requests;

import io.sovann.hang.api.features.tables.entities.*;
import io.sovann.hang.api.features.tables.enums.*;
import lombok.*;

@Getter
@Setter
@ToString
public class CreateTableRequest {
    private String name;
    private String tableNumber;
    private String description;
    private double basePrice;
    private int capacity;
    private TableStatus status;
    private String qr;
    private Boolean isGrouped = false;

    public static Table fromRequest(CreateTableRequest request) {
        Table table = new Table();
        table.setName(request.getName());
        table.setTableNumber(request.getTableNumber());
        table.setDescription(request.getDescription());
        table.setBasePrice(request.getBasePrice());
        table.setCapacity(request.getCapacity());
        table.setStatus(request.getStatus());
        table.setQr(request.getQr());
        table.setIsGrouped(request.getIsGrouped());
        return table;
    }
}
