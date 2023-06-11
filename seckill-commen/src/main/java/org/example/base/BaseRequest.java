package org.example.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class BaseRequest<T> implements Serializable {
    private String deviceType;
    private String deviceNo;
    private String version;
    private String channelId;
    private T data;
}
