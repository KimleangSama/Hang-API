package io.sovann.hang.api.features.tables.services;

import io.sovann.hang.api.features.tables.entities.*;
import io.sovann.hang.api.features.tables.payloads.requests.*;
import io.sovann.hang.api.features.tables.payloads.responses.*;
import io.sovann.hang.api.features.tables.repos.*;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TableServiceImpl {
    private final TableRepository tableRepository;

    public TableResponse createTable(CreateTableRequest request) {
        Table table = CreateTableRequest.fromRequest(request);
        table = tableRepository.save(table);
        return TableResponse.fromEntity(table);
    }
}
