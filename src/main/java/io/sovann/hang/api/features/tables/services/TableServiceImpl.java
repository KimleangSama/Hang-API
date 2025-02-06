package io.sovann.hang.api.features.tables.services;

import io.sovann.hang.api.features.tables.entities.Table;
import io.sovann.hang.api.features.tables.payloads.requests.CreateTableRequest;
import io.sovann.hang.api.features.tables.payloads.responses.TableResponse;
import io.sovann.hang.api.features.tables.repos.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TableServiceImpl {
    private final TableRepository tableRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public TableResponse createTable(CreateTableRequest request) {
        Table table = CreateTableRequest.fromRequest(request);
        table = tableRepository.save(table);
        redisTemplate.opsForValue().set("table:" + table.getId(), table);
        return TableResponse.fromEntity(table);
    }

    public Optional<Table> findTableEntityById(UUID tableId) {
        Table table = (Table) redisTemplate.opsForValue().get("table:" + tableId);
        if (table != null) {
            return Optional.of(table);
        }
        return tableRepository.findById(tableId);
    }
}
