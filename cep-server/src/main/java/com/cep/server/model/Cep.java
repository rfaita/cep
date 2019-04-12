package com.cep.server.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TextScore;

import java.time.LocalDateTime;

@Document(collection = "cep", language = "pt")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cep {

    @Id
    private String id;
    @Indexed
    private String cep;
    @TextIndexed(weight = 2.0f)
    private String logradouro;
    @TextIndexed
    private String bairro;
    @TextIndexed
    private String cidade;
    @TextIndexed
    private String estado;
    @TextIndexed
    private String uf;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime refreshedAt;
    private Location location;
    @TextScore
    private Float score;

}
