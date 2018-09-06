package com.mercedes.fileuploader.controller;

import com.mercedes.fileuploader.domain.EmployeeResponse;
import com.mercedes.fileuploader.service.XlsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@RestController
@RequestMapping(value = "/xls")
@Slf4j
public class XlsController {

    private final XlsService xlsService;

    private final Statement statement;

    public XlsController(XlsService xlsService, Statement statement) {
        this.xlsService = xlsService;
        this.statement = statement;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<EmployeeResponse>
    parseCsv(@RequestPart(value = "xlsfile") MultipartFile multipartFile,
             @RequestParam(value = "tableName") String tableName) throws IOException, InvalidFormatException, SQLException {
        xlsService.parseAndSaveData(multipartFile, tableName);

        return ResponseEntity.ok(new EmployeeResponse(200, "File content saved successfully!"));
    }

    @RequestMapping(value = "/fetch")
    public ResponseEntity<Map<String, Object>> fetchDetails(@RequestParam(value = "fetchTable") String tableName) throws SQLException {
        Map<String, Object> result = xlsService.fetchDetails(tableName);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<EmployeeResponse> deleteTable(@RequestParam(value = "deleteTable") String tableName) {
        xlsService.deleteTable(tableName);
        return ResponseEntity.ok(new EmployeeResponse(200, "Table " + tableName + " deleted successfully!"));
    }

    @RequestMapping(value = "/hadoop", method = RequestMethod.GET)
    public String saveToHadoop() throws SQLException {
        statement.execute("INSERT INTO t_hello_world(name) VALUES ('Hello)");
        return "Done!";
    }

}
