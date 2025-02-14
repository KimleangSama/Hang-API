package io.sovann.hang.api.features.tables.services;

import io.sovann.hang.api.features.tables.entities.*;
import io.sovann.hang.api.features.tables.payloads.requests.*;
import io.sovann.hang.api.features.tables.payloads.responses.*;
import io.sovann.hang.api.features.tables.repos.*;
import io.sovann.hang.api.features.users.entities.*;
import java.util.*;
import lombok.*;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TableServiceImpl {
    private final TableRepository tableRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public long count() {
        return tableRepository.count();
    }

    @CacheEvict(value = "tables", allEntries = true)
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

    @Cacheable(value = "tables")
    public List<TableResponse> listTables(User user, int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return TableResponse.fromEntities(tableRepository.findAll(pageable).getContent());
    }

    @Cacheable(value = "tables", key = "#id")
    public TableResponse getTableById(UUID id) {
        return TableResponse.fromEntity(Objects.requireNonNull(findTableEntityById(id).orElse(null)));
    }
}
