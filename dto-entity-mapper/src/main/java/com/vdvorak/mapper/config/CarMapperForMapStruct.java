package com.vdvorak.mapper.config;


import com.vdvorak.mapper.dto.CarDto;
import com.vdvorak.mapper.dto.EngineDto;
import com.vdvorak.mapper.entity.Car;
import com.vdvorak.mapper.entity.Engine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CarMapperForMapStruct {
    CarMapperForMapStruct  INSTANCE = Mappers.getMapper(CarMapperForMapStruct.class);

    @Mapping(source = "source", target = "engineDto")
    CarDto toDto(Car car);

    EngineDto toDto(Engine engine);
}
