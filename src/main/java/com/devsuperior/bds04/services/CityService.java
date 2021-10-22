package com.devsuperior.bds04.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {

	@Autowired
	private CityRepository repository;
	
	private void convertCityToCityDTO(City entity, CityDTO dto) {
		
		entity.setName(dto.getName());
	}
	
	
	@Transactional(readOnly = true)
	public List<CityDTO> findAll() {
		List<City> list = repository.findAll(Sort.by("name"));
		return list.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public CityDTO insert(CityDTO dto) {
		City entity = new City();
		convertCityToCityDTO(entity, dto);
		entity = repository.save(entity);
		return new CityDTO(entity);		
	}
	
	@Transactional
	public CityDTO update(Long id, CityDTO dto) {
		
		try {
			City entity = repository.getById(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			
			return new CityDTO(entity);
		} catch(EntityNotFoundException error) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}
}
