package com.api.order.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {

    private String message;
    private boolean status;
    private Object data;
    private String serviceName;
}
