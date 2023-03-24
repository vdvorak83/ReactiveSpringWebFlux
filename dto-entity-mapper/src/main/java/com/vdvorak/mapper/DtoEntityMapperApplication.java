package com.vdvorak.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.vdvorak.mapper.config.CarMapperForMapStruct;
import com.vdvorak.mapper.dto.CarDto;
import com.vdvorak.mapper.dto.EngineDto;
import com.vdvorak.mapper.entity.Car;
import com.vdvorak.mapper.entity.Engine;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DtoEntityMapperApplication {

	public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
		SpringApplication.run(DtoEntityMapperApplication.class, args);
		//create Car entity
		Car car = new Car();
		car.setId(1);
		car.setMake("Honda");
		car.setNumOfSeats(4);

		Date date = Date.from(LocalDate.of(2010, 10, 10).atStartOfDay(ZoneId.systemDefault()).toInstant());
		car.setReleaseDate(date);

		//create Engine enity
		Engine engine = new Engine();
		engine.setType("V6");
		car.setSource(engine);



		//11111
		//mmapping to Dto
		 extracted0(car, getModelMapper());

		//2222222
		extracted(car);

		//33333333
		extracted1(car);

		///4444444444
		extracted2(car);
		
		//manual //555555555
		extracted3(car);


	}

	private static void extracted3(Car car) {
		long time1 = System.currentTimeMillis();
		for (int i = 0; i < 10_000_000; i++) {
			EngineDto engineDto = new EngineDto();
			engineDto.setType(car.getSource().getType());
			CarDto carDto = new CarDto();
			carDto.setId(car.getId());
			carDto.setMake(car.getMake());
			carDto.setNumOfSeats(car.getNumOfSeats());
			carDto.setRealeseDate(car.getReleaseDate());
			carDto.setEngineDto(engineDto);
		}
		long time2 = System.currentTimeMillis();
		System.out.println("Time executed Manual withot lib    :  " + getSeconds(time2 - time1));
	}

	private static void extracted2(Car car) {
		long time1 = System.currentTimeMillis();
		for (int i = 0; i < 10_000_000; i++) {
			CarDto carDto = CarMapperForMapStruct.INSTANCE.toDto(car);
			//System.out.println(carDto);
		}
		long time2 = System.currentTimeMillis();
		System.out.println("Time executed lib MapStruct   :  " + (time2 - time1));
	}

	private static void extracted1(Car car) throws IllegalAccessException, InvocationTargetException {
		long time1 = System.currentTimeMillis();
		for (int i = 0; i < 10_000_000; i++) {
			CarDto carDto = new CarDto();
			EngineDto engineDto = new EngineDto();
			BeanUtils.copyProperties(car.getSource(), engineDto);
			BeanUtils.copyProperties(car, carDto);
			carDto.setEngineDto(engineDto);
		}
		long time2 = System.currentTimeMillis();
		System.out.println("Time executed lib BeanUtils Spring  :  " + getSeconds(time2 - time1));
	}

	private static void extracted(Car car) {
		long time1 = System.currentTimeMillis();
		Mapper mapper = DozerBeanMapperBuilder.buildDefault();
		for (int i = 0; i < 10_000_000; i++) {
			CarDto carDto = mapper.map(car, CarDto.class);
		}
		long time2 = System.currentTimeMillis();
//			System.out.println("equals object is "+Objects.equals(carDto.getEngine(), car.getEngine()));
		System.out.println("Time executed lib dozer-core  :  " + getSeconds(time2 - time1));
	}

	private static void extracted0(Car car, ModelMapper modelMapper) {
		long time1 = System.currentTimeMillis();
		for (int i = 0; i < 10_000_000; i++) {
		     CarDto carDto = modelMapper.map(car, CarDto.class);
		}
		long time2 = System.currentTimeMillis();
		System.out.println("Time executed lib modelmapper:  " + getSeconds(time2-time1));
	}

	private static ModelMapper getModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		TypeMap<Car, CarDto> typeMap = modelMapper.createTypeMap(Car.class, CarDto.class);
		typeMap.addMappings(mapper -> {
			mapper.map(Car::getSource, CarDto::setEngineDto);
		} );
		return modelMapper;
	}
	private static <S,D>ModelMapper getModelMapperWithParams(Class<S> sourceType,String sourceMethod, Class<D> destinationType, String destination) {
		ModelMapper modelMapper = new ModelMapper();
		TypeMap<Car, CarDto> typeMap = modelMapper.createTypeMap(Car.class, CarDto.class);
		typeMap.addMappings(mapper -> {
			mapper.map(Car::getSource, CarDto::setEngineDto);
		} );
		return modelMapper;
	}
	private static long getSeconds(long ms) {
		return (ms/1000%60);
	}
}
