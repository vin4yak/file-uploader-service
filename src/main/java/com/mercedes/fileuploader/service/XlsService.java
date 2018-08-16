package com.mercedes.fileuploader.service;

import com.mercedes.fileuploader.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class XlsService {

    private final JdbcTemplate jdbcTemplate;

    public XlsService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void parseAndSaveData(MultipartFile multipartFile, String tableName) throws IOException, InvalidFormatException {
        final File file = FileUtil.convertFile(multipartFile);

        Workbook workbook = WorkbookFactory.create(file);

        Sheet sheet = workbook.getSheetAt(0);

        StringBuilder insertQuery = new StringBuilder();
        insertQuery.append("INSERT INTO ");
        insertQuery.append(tableName);
        insertQuery.append("(");

        parseXlsAndCreateTable(sheet, insertQuery, tableName);

        buildInsertQuery(sheet, insertQuery);

        jdbcTemplate.execute(insertQuery.toString());
    }

    public Map<String, Object> fetchDetails(String tableName) {
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("SELECT * FROM " + tableName + ";");

        return mapList.get(0);
    }

    public void deleteTable(String tableName) {
        jdbcTemplate.execute("DROP TABLE " + tableName + ";");
    }

    private void buildInsertQuery(Sheet sheet, StringBuilder insertQuery) {
        sheet.removeRow(sheet.getRow(0));
        sheet.forEach(row -> {
            insertQuery.append("(");
            row.forEach(cell -> {
                insertQuery.append("'");
                try {
                    insertQuery.append(cell.getStringCellValue());
                } catch (IllegalStateException ex) {
                    insertQuery.append(cell.getNumericCellValue());
                }
                insertQuery.append("'");
                insertQuery.append(",");
            });
            insertQuery.delete(insertQuery.toString().length()-1, insertQuery.toString().length());
            insertQuery.append("),");
        });
        insertQuery.delete(insertQuery.toString().length()-1, insertQuery.toString().length());
        insertQuery.append(";");

        log.info("insert query: {}", insertQuery.toString());
    }

    private void parseXlsAndCreateTable(Sheet sheet, StringBuilder insertQuery, String tableName) {
        StringBuilder createQuery = new StringBuilder();
        createQuery.append("CREATE TABLE ");
        createQuery.append(tableName);
        createQuery.append("(");

        sheet.getRow(0).forEach(cell -> {
            createQuery.append(cell.getStringCellValue().toLowerCase());
            createQuery.append(" varchar(255),");

            insertQuery.append(cell.getStringCellValue().toLowerCase());
            insertQuery.append(",");
        });

        createQuery.delete(createQuery.toString().length()-1, createQuery.toString().length());
        createQuery.append(");");

        insertQuery.delete(insertQuery.toString().length()-1, insertQuery.toString().length());
        insertQuery.append(") VALUES ");

        log.info("create query: {}", createQuery.toString());

        jdbcTemplate.execute(createQuery.toString());
    }

}
