package com.nukang.app.advertisement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement,Long> {
    @Query(value = "select * from advertisement where start_date <= to_date(?1,'yyyy-MM-dd') and end_date>= to_date(?1,'yyyy-MM-dd')")
    List<Advertisement> selectActive(String today);
}
