package com.github.njsuarez.prices.infrastructure.repository;

import com.github.njsuarez.prices.domain.model.Price;
import com.github.njsuarez.prices.domain.repository.PriceRepository;
import com.github.njsuarez.prices.infrastructure.repository.dao.PriceAssembler;
import com.github.njsuarez.prices.infrastructure.repository.dao.PriceDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public interface PriceJpaRepository extends CrudRepository<PriceDao, Long>, PriceRepository {

    @Override
    default List<Price> getPrice(Integer brandId, Integer productId, LocalDateTime date) {
        Iterable<PriceDao> pricesDao = findAllByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(brandId,
                productId, date, date);
        return StreamSupport.stream(pricesDao.spliterator(), false).map(PriceAssembler::assemble)
                .collect(Collectors.toList());
    }

    Iterable<PriceDao> findAllByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(Integer brandId,
         Integer productId, LocalDateTime startDate, LocalDateTime endDate);


}
