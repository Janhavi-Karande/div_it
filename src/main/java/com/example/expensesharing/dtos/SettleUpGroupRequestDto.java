package com.example.expensesharing.dtos;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettleUpGroupRequestDto {
    private String groupName;
    private Integer userId;
}
