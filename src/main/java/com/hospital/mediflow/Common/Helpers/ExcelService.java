package com.hospital.mediflow.Common.Helpers;

import com.hospital.mediflow.Audit.Entity.SensitiveBackup;
import com.hospital.mediflow.Audit.Repository.SensitiveBackupRepository;
import com.hospital.mediflow.Common.Exceptions.BaseException;
import com.hospital.mediflow.Common.Exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelService extends FileService {
    private final SensitiveBackupRepository backupRepository;

    public byte[] createBackupExcel() throws IOException {
        String[] columns = {"ID","entity_name","entity_id","backup_data","backup_date","retention_day"};

        try(Workbook workbook = new XSSFWorkbook();ByteArrayOutputStream out = new ByteArrayOutputStream()){
            Sheet sheet = workbook.createSheet("Sensitive_datas_"+ LocalDate.now());

            Row headerRow = sheet.createRow(0);

            for(int col =0;col<columns.length;col++){
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
            }

            List<SensitiveBackup> backupList = backupRepository.getExpiredBackups();
            int rowIdx = 1;
            for(SensitiveBackup backup : backupList){
                Row row = sheet.createRow(rowIdx);
                row.createCell(0).setCellValue(backup.getId());
                row.createCell(1).setCellValue(backup.getEntityName());
                row.createCell(2).setCellValue(backup.getEntityId());
                row.createCell(3).setCellValue(backup.getBackupData());
                row.createCell(4).setCellValue(backup.getCreatedAt());
                row.createCell(5).setCellValue(backup.getRetentionDays());
                rowIdx++;
            }
            workbook.write(out);
            return out.toByteArray();
        }
    }

    @Override
    public void saveToFile(String path, byte[] excel) {
        try {
            Files.write(
                    Path.of(path + ".xlsx"),
                    excel
            );
        } catch (IOException e) {
            throw new BaseException(e.getMessage(), ErrorCode.IO_ERROR);
        }
    }
}
