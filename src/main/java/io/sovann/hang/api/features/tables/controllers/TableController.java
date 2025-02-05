package io.sovann.hang.api.features.tables.controllers;

import io.sovann.hang.api.constants.*;
import io.sovann.hang.api.features.commons.controllers.*;
import io.sovann.hang.api.features.commons.payloads.*;
import io.sovann.hang.api.features.tables.payloads.requests.*;
import io.sovann.hang.api.features.tables.payloads.responses.*;
import io.sovann.hang.api.features.tables.services.*;
import lombok.*;
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
}
