package io.sovann.hang.api.features.tables.controllers;

import io.sovann.hang.api.annotations.CurrentUser;
import io.sovann.hang.api.constants.APIURLs;
import io.sovann.hang.api.features.commons.controllers.ControllerServiceCallback;
import io.sovann.hang.api.features.commons.payloads.BaseResponse;
import io.sovann.hang.api.features.commons.payloads.PageMeta;
import io.sovann.hang.api.features.tables.payloads.requests.CreateTableRequest;
import io.sovann.hang.api.features.tables.payloads.responses.TableResponse;
import io.sovann.hang.api.features.tables.services.TableServiceImpl;
import io.sovann.hang.api.features.users.securities.CustomUserDetails;
import io.sovann.hang.api.utils.SoftEntityDeletable;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(APIURLs.TABLE)
@RequiredArgsConstructor
public class TableController {
    private final TableServiceImpl tableService;
    private final ControllerServiceCallback callback;

    @PostMapping("/create")
    public BaseResponse<TableResponse> createTable(@RequestBody CreateTableRequest request) {
        return callback.execute(() -> tableService.createTable(request),
                "Table failed to create",
                null);
    }

    @GetMapping("/list")
    public BaseResponse<List<TableResponse>> listTables(
            @CurrentUser CustomUserDetails user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        SoftEntityDeletable.throwErrorIfSoftDeleted(user.getUser());
        PageMeta pageMeta = new PageMeta(page, size, tableService.count());
        return callback.execute(() -> tableService.listTables(user.getUser(), page, size),
                "Table failed to list",
                pageMeta);
    }

    @GetMapping("/{id}/get")
    public BaseResponse<TableResponse> getTableById(
            @PathVariable UUID id
    ) {
        return callback.execute(() -> tableService.getTableById(id),
                "Table failed to get",
                null);
    }
}
