package com.singidunum.delivery.service;

import com.singidunum.delivery.dao.entity.OrderEntity;
import com.singidunum.delivery.dao.enums.OrderStatus;
import com.singidunum.delivery.dao.repository.OrderRepository;
import com.singidunum.delivery.dto.OrderDto;
import java.time.LocalDate;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends BaseCrudService<OrderDto, OrderEntity> {
    private final OrderRepository repository;
    private final ModelMapper mapper;
    private final CustomerService customerService;
    private final DriverService diverService;
    private final ParcelService parcelService;

    public OrderService(
        ModelMapper mapper,
        OrderRepository repository,
        CustomerService customerService,
        DriverService diverService,
        ParcelService parcelService) {
        super(repository, mapper, OrderDto.class, OrderEntity.class);
        this.repository = repository;
        this.mapper = mapper;
        this.customerService = customerService;
        this.diverService = diverService;
        this.parcelService = parcelService;
    }

    @Override
    public void create(OrderDto dto) {
        OrderEntity entity = mapper.map(dto, OrderEntity.class);
        entity.setCustomer(customerService.findById(dto.getCustomerId()));
        entity.setDriver(diverService.findById(dto.getDriverId()));
        entity.setParcel(parcelService.findById(dto.getParcelId()));
        repository.save(entity);
    }

    public List<OrderDto> getByStatus(OrderStatus status){
        return repository.findByStatus(status.toString())
            .stream()
            .map(order -> mapper.map(order, OrderDto.class))
            .toList();
    }


}