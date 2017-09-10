package com.kidz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class UserGroup {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min=8, max=255, message="Group name should be between 8 and 255 characters")
    private String name;

    @Size(min=30, max=512, message="Group description should be between 20 and 512 characters")
    @Column(name="description", length=512)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
