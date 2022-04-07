package com.silauras.footballmanager.dto.response;

import javax.validation.constraints.NotNull;

public class PlayerDTO {

    public PlayerDTO() {
    }

    public PlayerDTO(Integer id, String name, Integer age, Integer experience, Integer teamId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.experience = experience;
        this.teamId = teamId;
    }

    public PlayerDTO(Integer id, String name, Integer age, Integer experience) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.experience = experience;
    }

    private Integer id;
    private String name;
    @NotNull
    private Integer age;
    @NotNull
    private Integer experience;
    private Integer teamId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }
}
