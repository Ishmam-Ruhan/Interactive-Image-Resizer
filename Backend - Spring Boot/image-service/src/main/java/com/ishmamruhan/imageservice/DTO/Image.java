package com.ishmamruhan.imageservice.DTO;

import com.ishmamruhan.imageservice.Helpers.DateGenerator;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Image {

    @Id
    private String id;

    private String imageFileName;

    private String fileType;

    @Lob
    @Column(length = 1000)
    private byte[] originalImageData;

    @Lob
    @Column(length = 1000)
    private byte[] thumbnileImageData;

    private String uploadedAt = DateGenerator.getDate();
}
