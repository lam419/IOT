package com.iot.api.controller;

import com.iot.api.entities.Car;
import com.iot.api.repo.CarManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    CarManagerRepository repository;

    @GetMapping("/{plate}")
    public ResponseEntity<?> getCarByPlate(@PathVariable String plate){
        Car c = repository.findCarByCarLicensePlate(plate);
        if(c != null ){
            return new ResponseEntity<Car>(c, HttpStatus.OK);
        }
        else {
            System.out.println("newCAR");
            return new ResponseEntity<>("Not found!", HttpStatus.OK);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllCarCame(){
        List<Car> cars = repository.findAll();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @DeleteMapping("/{plate}")
    public ResponseEntity<?> deleteVehicle (@PathVariable("plate")String plate){
        Car _car = repository.findCarByCarLicensePlate(plate);
        if(_car== null){
            return new ResponseEntity<>("Input id is invalid!", HttpStatus.BAD_REQUEST);
        }
        else {
            System.out.println("delete");
            System.out.println(_car.getId());
            repository.deleteCarByCarLicensePlate(plate);
            return new ResponseEntity<>("Deleted car info!", HttpStatus.OK);
        }
    }

    @PutMapping ("")
    public ResponseEntity<?> carOut(@RequestBody Car c){
        String plate = c.getLicensePlate();
        Car _car = repository.findCarByCarLicensePlate(plate);
        LocalDateTime now = LocalDateTime.now();
        System.out.println(_car.getTimeIn());
        _car.setTimeOut(now.toString());
        repository.saveAndFlush(_car);
        return new ResponseEntity<>("Updated car!", HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addCar(@RequestBody Car car){
        String plate = car.getLicensePlate();
        Car _car = repository.findCarByCarLicensePlate(plate);
        LocalDateTime now = LocalDateTime.now();

        if(_car == null ){
            System.out.println("newCAR");
            repository.save(car);
            return new ResponseEntity<>("Created!", HttpStatus.OK);

        }
        else {
            System.out.println("update-time");
            _car.setTimeIn(now.toString());
            _car.setTimeOut(null);
            repository.saveAndFlush(_car);
            return new ResponseEntity<>("Updated time go in!", HttpStatus.OK);
        }
    }


}
