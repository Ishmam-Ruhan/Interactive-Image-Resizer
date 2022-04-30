package com.ishmamruhan.imageservice.Helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private long id;
    private String imageName;
    private String Description;
}
