package io.sovann.hang.api.features.tables.services;

import io.sovann.hang.api.features.tables.entities.Table;
import io.sovann.hang.api.features.tables.payloads.requests.CreateTableRequest;
import io.sovann.hang.api.features.tables.payloads.responses.TableResponse;
import io.sovann.hang.api.features.tables.repos.TableRepository;
import io.sovann.hang.api.features.users.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
}
