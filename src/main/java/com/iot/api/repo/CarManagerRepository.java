package com.iot.api.repo;

import com.iot.api.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CarManagerRepository extends JpaRepository<Car, Integer> {

    @Query("select r from Car r WHERE license_plate = :plate")
    Car findCarByCarLicensePlate(@Param("plate")String  carLicensePlate);



    @Transactional
    @Modifying
    @Query("delete from Car r WHERE license_plate = :plate")
    void deleteCarByCarLicensePlate(@Param("plate")String  carLicensePlate);
}
