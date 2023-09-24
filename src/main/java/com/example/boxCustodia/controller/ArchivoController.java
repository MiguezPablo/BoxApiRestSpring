package com.example.boxCustodia.controller;

import com.example.boxCustodia.dto.ArchivoDTO;
import com.example.boxCustodia.entity.Archivo;
import com.example.boxCustodia.exceptions.ErrorParametro;
import com.example.boxCustodia.service.ArchivoService;
import com.example.boxCustodia.exceptions.ErrorPersonalizado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class ArchivoController {

    @Autowired
    private ArchivoService archivoService;

    @PostMapping("/hash")
    public ResponseEntity<Object> subirArchivos(
        @RequestParam("hashType") String hashType,
        @RequestParam("documents") List<MultipartFile> documents) {
        try {
            List<ArchivoDTO> archivoDTOList = archivoService.subirArchivos(hashType, documents);

            LinkedHashMap<String, Object> response = new LinkedHashMap<>();
            response.put("algorithm", hashType);
            response.put("documents", archivoDTOList);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ErrorParametro ex) {
            ErrorPersonalizado error = ex.getErrorPersonalizado();
            return ResponseEntity.status(HttpStatus.valueOf(error.getStatus())).body(error);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<Object> getDocuments(
        @RequestParam(value = "hashType", required = false) String hashType,
        @RequestParam(value = "hash", required = false) String hash) {
        try {
            if (hashType == null && hash == null) {
                List<Archivo> archivos = archivoService.obtenerTodosLosDocumentos();
                if (archivos.isEmpty()) {
                    return ResponseEntity.ok().body("No existen registros en la BD");
                } else {
                    return ResponseEntity.ok().body(archivos);
                }
            } else {
                ArchivoDTO archivoDTO = archivoService.obtenerDocumentoPorHash(hashType, hash);
                return ResponseEntity.ok().body(archivoDTO);
            }
        } catch (ErrorParametro ex) {
            ErrorPersonalizado error = ex.getErrorPersonalizado();
            return ResponseEntity.status(HttpStatus.valueOf(error.getStatus())).body(error);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
