package com.learnings.urlfeeder.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table
public class URL  implements Serializable {
    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    String id;

    @PrimaryKeyColumn(name = "url", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    String url;

    @Column("times_processed")
    Integer timesProcessed;

    @Column("content_type")
    String contentType;

    @Column("last_processed")
    Timestamp lastProcessed;

    @Column("created_date")
    Timestamp createdDate;
}
