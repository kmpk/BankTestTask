package com.github.kmpk.banktesttask.mapper;

import com.github.kmpk.banktesttask.to.PageResultTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper
public interface PageMapper {
    @Mapping(target = "totalPages", expression = "java(page.getTotalPages())")
    @Mapping(target = "totalItems", expression = "java(page.getTotalElements())")
    @Mapping(target = "pageNum", expression = "java(page.getNumber())")
    @Mapping(target = "content", expression = "java((List<Object>) page.getContent())")
    PageResultTo toTo(Page<?> page);
}
