package com.github.njsuarez.prices.infrastructure.adapter.repository;

import com.github.njsuarez.prices.application.port.output.GetPricesListPort;
import com.github.njsuarez.prices.domain.model.Price;
import com.github.njsuarez.prices.infrastructure.adapter.repository.dao.PriceAssembler;
import com.github.njsuarez.prices.infrastructure.adapter.repository.dao.PriceDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public interface PricesListJpaRepository extends CrudRepository<PriceDao, Long>, GetPricesListPort {

    @Override
    default List<Price> getPriceList(Integer brandId, Integer productId, LocalDateTime date) {
        Iterable<PriceDao> pricesDao = findAllByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(brandId,
                productId, date, date);
        return StreamSupport.stream(pricesDao.spliterator(), false).map(PriceAssembler::assemble)
                .collect(Collectors.toList());
    }

    Iterable<PriceDao> findAllByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(Integer brandId,
         Integer productId, LocalDateTime startDate, LocalDateTime endDate);


}
