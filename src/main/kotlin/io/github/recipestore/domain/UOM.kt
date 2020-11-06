package io.github.recipestore.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table


@Table("UOM")
data class UOM(
    @Id
    @Column("UOM_ID")
    var id: Long?,

    @Column("NAME")
    var name: String,

    @Column("DESCRIPTION")
    var description: String
)