package io.github.recipestore.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("USER")
data class User(
    @Id
    @Column("USER_ID")
    var id: Long?,

    @Column("NAME")
    var name: String,

    @JsonIgnore
    @Column("PASSWORD")
    var password: String,

    @Column("CREATED")
    var created: LocalDateTime = LocalDateTime.now(),

    @Column("MODIFIED")
    var modified: LocalDateTime = LocalDateTime.now(),

    @Transient
    var roles: Set<Role> = mutableSetOf()
    )