package com.online.bookstore.repository;

import com.online.bookstore.model.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromoCodesRepository extends JpaRepository<PromoCode, Long> {

    List<PromoCode> findByPromocode(String promoCode);
}
