package com.readingisgood.ReadingIsGood.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update BookEntity b set b.stock = :stock where b.id = :id")
    public void updateStock(@Param("id") Long id, @Param("stock") Integer stock);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<BookEntity> findById(Long id);

}
