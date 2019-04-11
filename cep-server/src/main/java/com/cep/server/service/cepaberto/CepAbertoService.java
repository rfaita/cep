package com.cep.server.service.cepaberto;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.ValidationException;

import com.cep.server.model.Cep;
import com.cep.server.dto.CidadeDTO;
import com.cep.server.dto.EstadoDTO;
import com.cep.server.repository.CepRepository;
import com.cep.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CepAbertoService {

    @Value("${cep.cepaberto.diretorio}")
    private String diretorio;
    @Value("${cep.cepaberto.estadoZip}")
    private String estadoZip;
    @Value("${cep.cepaberto.cidadeZip}")
    private String cidadeZip;

    @Autowired
    private CepRepository cepRepository;

    @CacheEvict(allEntries = true, cacheNames = {"cep"})
    public void build() throws IOException {

        List<EstadoDTO> estados = readEstados();
        List<CidadeDTO> cidades = readCidades();

        File folder = new File(diretorio);

        cepRepository.deleteAll();

        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()
                    && fileEntry.getName().endsWith(".zip")
                    && !fileEntry.getName().contains("estados")
                    && !fileEntry.getName().contains("cidades")) {

                log.info("Lendo arquivo '{}'.", fileEntry.getName());

                Optional<String> fileCep = ZipUtil.unzip(fileEntry).stream().filter((t) -> t.contains(".csv")).findFirst();
                if (!fileCep.isPresent()) {
                    throw new ValidationException("cep.csv.not.found");
                }

                Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(new FileReader(new File(fileCep.get())));
                for (CSVRecord record : records) {

                    Cep cep = new Cep();

                    cep.setCep(record.get(0));
                    cep.setLogradouro(record.get(1));
                    cep.setBairro(record.get(2));

                    Optional<CidadeDTO> c = cidades.stream().filter(cidade -> cidade.getId().equals(record.get(3))).findFirst();
                    if (!c.isPresent()) {
                        throw new ValidationException("cidade.not.found");
                    }
                    cep.setCidade(c.get().getCidade());
                    Optional<EstadoDTO> e = estados.stream().filter(estado -> estado.getId().equals(record.get(4))).findFirst();
                    if (!e.isPresent()) {
                        throw new ValidationException("estado.not.found");
                    }
                    cep.setEstado(e.get().getEstado());
                    cep.setUf(e.get().getUf());

                    cepRepository.save(cep);
                }
            }
        }

    }

    private List<CidadeDTO> readCidades() throws IOException {
        log.info("Lendo cidades.");

        File estado = new File(diretorio.concat("/").concat(cidadeZip));
        if (!estado.exists()) {
            throw new ValidationException("cidades.zip.not.found");
        }
        Optional<String> fileEstado = ZipUtil.unzip(estado).stream().filter((t) -> t.contains(".csv")).findFirst();
        if (!fileEstado.isPresent()) {
            throw new ValidationException("cidades.csv.not.found");
        }

        List<CidadeDTO> ret = new ArrayList<>();

        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(new FileReader(new File(fileEstado.get())));
        for (CSVRecord record : records) {
            String id = record.get(0);
            String cidade = record.get(1);

            ret.add(CidadeDTO.builder()
                    .id(id)
                    .cidade(cidade)
                    .build());
        }

        return ret;
    }

    private List<EstadoDTO> readEstados() throws IOException {
        log.info("Lendo estados.");

        File estado = new File(diretorio.concat("/").concat(estadoZip));
        if (!estado.exists()) {
            throw new ValidationException("estado.zip.not.found");
        }
        Optional<String> fileEstado = ZipUtil.unzip(estado).stream().filter((t) -> t.contains(".csv")).findFirst();
        if (!fileEstado.isPresent()) {
            throw new ValidationException("estado.csv.not.found");
        }

        List<EstadoDTO> ret = new ArrayList<>();

        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(new FileReader(new File(fileEstado.get())));
        for (CSVRecord record : records) {
            String id = record.get(0);
            String es = record.get(1);
            String uf = record.get(2);

            ret.add(EstadoDTO.builder()
                    .id(id)
                    .estado(es)
                    .uf(uf)
                    .build());
        }

        return ret;
    }

}
