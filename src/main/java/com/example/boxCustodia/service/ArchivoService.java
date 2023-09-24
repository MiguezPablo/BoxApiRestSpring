package com.example.boxCustodia.service;

import com.example.boxCustodia.dto.ArchivoDTO;
import com.example.boxCustodia.entity.Archivo;
import com.example.boxCustodia.exceptions.ErrorParametro;
import com.example.boxCustodia.exceptions.ErrorPersonalizado;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public interface ArchivoService {
    List<ArchivoDTO> subirArchivos(String hashType, List<MultipartFile> documents) throws IOException, NoSuchAlgorithmException, ErrorParametro;

    List<Archivo> obtenerTodosLosDocumentos();

    ArchivoDTO obtenerDocumentoPorHash(String hashType, String hash) ;
}
