package com.ishmamruhan.imageservice.DAO;


import com.ishmamruhan.imageservice.DTO.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepo extends JpaRepository<Image, String> {
}
