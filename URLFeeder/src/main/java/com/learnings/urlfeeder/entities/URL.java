package com.learnings.urlfeeder.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "url")
public class URL  implements Serializable {

    @Id
    String id;

    String url;

    @Column(name = "times_processed")
    Integer timesProcessed;

    @Column(name = "content_type")
    String contentType;

    @Column(name = "last_processed")
    Timestamp lastProcessed;

    @Column(name = "created_date")
    Timestamp createdDate;
}
