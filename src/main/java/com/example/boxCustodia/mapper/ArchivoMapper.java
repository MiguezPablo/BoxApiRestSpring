package com.example.boxCustodia.mapper;

import com.example.boxCustodia.dto.ArchivoDTO;
import com.example.boxCustodia.entity.Archivo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component()
public class ArchivoMapper {

    public ArchivoDTO archivoEntity2DTO(Archivo entity, String hashType){
        ArchivoDTO dto = new ArchivoDTO();
        dto.setFileName(entity.getFileName());
        if (hashType.equals("SHA-256")){
            dto.setHash(entity.getHashSha256());}
        else{
            dto.setHash(entity.getHashSha512());}
        dto.setLastUpload(entity.getLastUpload());
        return dto;
    }

}
