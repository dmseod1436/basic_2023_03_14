package com.ll.basic.boundedContext.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.AnyKeyJavaClass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Member {
    @Id // PRIMARY KEY
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime modifyDate;
    @Column(unique = true)
    private String username;
    private String password;
}