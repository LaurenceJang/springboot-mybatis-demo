package com.springboot.mybatis.demo.toolkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.springboot.mybatis.demo.dao.QuestionMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@Component
@SpringBootApplication
public class QuestionUtil implements CommandLineRunner {
    @Autowired
    QuestionMapper questionMapper;
    public static String currentDateStr;
    public static String filePath = "C:\\Users\\Laure\\Desktop\\template.xlsx";

    @Override
    public void run(String... args) throws Exception {
        processExcel(filePath);
    }

    public void processExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
            //若非当日问题 修改该日期即可
            currentDateStr = dateFormat.format(currentDate);

            Iterator<Sheet> sheetIterator = workbook.iterator();

            while (sheetIterator.hasNext()) {
                Sheet sheet = sheetIterator.next();
                if (currentDateStr.equals(sheet.getSheetName())) {
                    processSheet(sheet);
                } else {
                    System.out.println("当前日期与sheetName不相等！");
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processSheet(Sheet sheet) {
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next(); // Skip header row

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            processRow(row);
        }
    }

    private void processRow(Row row) {
        // 获取单元格数据，假设第一列是参数1，第二列是参数2，以此类推
        String appname = getCellValue(row.getCell(0));
        String cluster = getCellValue(row.getCell(1));
        String service_en = getCellValue(row.getCell(2));
        String service_cn = getCellValue(row.getCell(3));
        String function = getCellValue(row.getCell(4));
        int questiontype = (int) Double.parseDouble(getCellValue(row.getCell(5)));
        String manager = getCellValue(row.getCell(6));
        Question question = new Question(appname, cluster, service_en, service_cn, function, questiontype, manager, currentDateStr);

        // 调用 Mapper 写入数据库，此处为示例代码，实际需要根据你的业务逻辑调整


        boolean success = writeToDatabase(question);

        // 根据写入数据库的结果进行相应操作
        if (!success) {
            System.out.println(question.toString());
            // 在 Excel中标记导入不成功的数据，例如标红
            markRowAsRed(row);
        }
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return "";
        }
    }

    private static final String JDBC_URL = "jdbc:mysql://111.229.46.118:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&allowMultiQueries=true";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "root";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    public boolean writeToDatabase(Question question) {
        int num = 0;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(" INSERT INTO fz_questions ( appname, cluster, service_en, service_cn, function, questiontype, manager, date) VALUES (?,?,?,?,?,?,?,?)");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            statement.setString(1, question.appname);
            statement.setString(2, question.cluster);
            statement.setString(3, question.service_en);
            statement.setString(4, question.service_cn);
            statement.setString(5, question.function);
            statement.setInt(6, question.questiontype);
            statement.setString(7, question.manager);
            statement.setString(8, question.date);

            num = statement.executeUpdate();

        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                return false;
            } else {
                return false;
            }
        }
        if (num == 1) {
            return true;
        } else {
            return false;
        }
    }

    /*public boolean writeToDatabase(Question question) {
        int num = questionMapper.insert(question);
        if (num == 1) {
            return true;
        } else {
            return false;
        }

    }*/

    private static void markRowAsRed(Row row) {
        if (row instanceof XSSFRow) {
            XSSFRow xssfRow = (XSSFRow) row;

            // 创建样式
            XSSFWorkbook workbook = xssfRow.getSheet().getWorkbook();
            CellStyle redCellStyle = workbook.createCellStyle();
            redCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
            redCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // 应用样式到整行
            for (Cell cell : xssfRow) {
                cell.setCellStyle(redCellStyle);
            }
        }
    }
    public class Question {
        public Question(String appname, String cluster, String service_en, String service_cn, String function, int questiontype, String manager, String date) {
            this.appname = appname;
            this.cluster = cluster;
            this.service_en = service_en;
            this.service_cn = service_cn;
            this.function = function;
            this.questiontype = questiontype;
            this.manager = manager;
            this.date = date;
        }
        private String appname;
        private String cluster;
        private String service_en;
        private String service_cn;
        private String function;
        private int questiontype;
        private String manager;
        private String date;

    }

}
