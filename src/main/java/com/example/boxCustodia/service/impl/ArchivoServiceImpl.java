package com.example.boxCustodia.service.impl;

import com.example.boxCustodia.dto.ArchivoDTO;
import com.example.boxCustodia.entity.Archivo;
import com.example.boxCustodia.exceptions.ErrorParametro;
import com.example.boxCustodia.mapper.ArchivoMapper;
import com.example.boxCustodia.repository.ArchivoRepository;
import com.example.boxCustodia.service.ArchivoService;
import com.example.boxCustodia.exceptions.ErrorPersonalizado;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArchivoServiceImpl implements ArchivoService {

    @Autowired
    private ArchivoRepository archivoRepository;

    @Autowired
    private ArchivoMapper archivoMapper;

    @Override
    public List<ArchivoDTO> subirArchivos(String hashType, List<MultipartFile> documents) throws IOException, NoSuchAlgorithmException, ErrorParametro {
        List<ArchivoDTO> archivoDTOList = new ArrayList<>();
        if (!hashType.equals("SHA-256") && !hashType.equals("SHA-512")) {
            ErrorPersonalizado error = new ErrorPersonalizado(HttpStatus.BAD_REQUEST.value(), "El parámetro 'hashType' solo puede ser 'SHA-256' o 'SHA-512'", "/api/hash");
            throw new ErrorParametro("El parámetro 'hashType' solo puede ser 'SHA-256' o 'SHA-512'", error);
        }
        for (MultipartFile file : documents) {
            if (documents == null || documents.isEmpty() || file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
                ErrorPersonalizado error = new ErrorPersonalizado(HttpStatus.BAD_REQUEST.value(), "No ha cargado ningun archivo", "/api/hash");
                throw new ErrorParametro("No ha cargado ningun archivo", error);
            }
            byte[] fileContent = file.getBytes();
            MessageDigest sha256Digest = MessageDigest.getInstance("SHA-256");
            MessageDigest sha512Digest = MessageDigest.getInstance("SHA-512");
            byte[] sha256HashBytes = sha256Digest.digest(fileContent);
            byte[] sha512HashBytes = sha512Digest.digest(fileContent);
            String sha256HashValue = Hex.encodeHexString(sha256HashBytes).toLowerCase();
            String sha512HashValue = Hex.encodeHexString(sha512HashBytes).toLowerCase();

            Archivo archivoEntity;
            if (hashType.equals("SHA-256")) {
                archivoEntity = archivoRepository.findByHashSha256(sha256HashValue);
            } else {
                archivoEntity = archivoRepository.findByHashSha512(sha512HashValue);
            }
            if (archivoEntity != null) {
                archivoEntity.setLastUpload(LocalDateTime.now());
                archivoRepository.save(archivoEntity);
            } else {
                Archivo nuevoArchivo = new Archivo();
                nuevoArchivo.setFileName(file.getOriginalFilename());
                nuevoArchivo.setHashSha256(sha256HashValue);
                nuevoArchivo.setHashSha512(sha512HashValue);
                archivoRepository.save(nuevoArchivo);
                archivoEntity = nuevoArchivo;
            }
            ArchivoDTO archivoDTO = archivoMapper.archivoEntity2DTO(archivoEntity, hashType);
            archivoDTOList.add(archivoDTO);
        }
        return archivoDTOList;
    }

    public ArchivoDTO obtenerDocumentoPorHash(String hashType, String hash) throws ErrorParametro {
        if (hashType != null && !hashType.equals("SHA-256") && !hashType.equals("SHA-512")) {
            ErrorPersonalizado error = new ErrorPersonalizado(HttpStatus.BAD_REQUEST.value(), "El parámetro 'hashType' solo puede ser 'SHA-256' o 'SHA-512'", "/api/documents");
            throw new ErrorParametro("El parámetro 'hashType' solo puede ser 'SHA-256' o 'SHA-512'", error);
        }
        if (hash != null && hash.isEmpty()) {
            ErrorPersonalizado error = new ErrorPersonalizado(HttpStatus.BAD_REQUEST.value(), "El parámetro 'hash' esta vacio", "/api/documents");
            throw new ErrorParametro("El parámetro 'hash' esta vacio", error);
        }
        Archivo archivoEntity;
        if (hashType.equals("SHA-256")) {
            archivoEntity = archivoRepository.findByHashSha256(hash);
        } else {
            archivoEntity = archivoRepository.findByHashSha512(hash);
        }
        if (archivoEntity != null) {
            ArchivoDTO archivoDTO = archivoMapper.archivoEntity2DTO(archivoEntity, hashType);
            return archivoDTO;
        } else {
            ErrorPersonalizado error = new ErrorPersonalizado(HttpStatus.NOT_FOUND.value(), "No hay ningún documento con ese nombre", "/api/documents");
            throw new ErrorParametro("No hay ningún documento con ese nombre", error);
        }
    }

    public List<Archivo> obtenerTodosLosDocumentos() {
        List<Archivo> archivos = archivoRepository.findAll();
        return archivos;
    }
}

