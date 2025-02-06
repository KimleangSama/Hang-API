package io.sovann.hang.api.features.carts.payloads.requests;

import io.sovann.hang.api.features.tables.entities.*;
import java.io.*;
import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
public class CreateCartQRequest implements Serializable {
    private UUID userId;
    private Table table;
}
