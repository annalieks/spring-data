package com.bsa.springdata.office;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfficeService {
    @Autowired
    private OfficeRepository officeRepository;

    public List<OfficeDto> getByTechnology(String technology) {
        return officeRepository
                .getByTechnology(technology)
                .stream()
                .map(OfficeDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<OfficeDto> updateAddress(String oldAddress, String newAddress) {
        officeRepository.updateAddress(oldAddress, newAddress);
        return officeRepository
                .findByAddress(newAddress)
                .map(OfficeDto::fromEntity);
    }
}
