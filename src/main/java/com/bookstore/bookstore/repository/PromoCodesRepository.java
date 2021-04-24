package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.model.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromoCodesRepository extends JpaRepository<PromoCode, Long> {

    List<PromoCode> findByPromocode(String promoCode);
}
