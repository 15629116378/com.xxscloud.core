package com.xxscloud.core.document

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File

class Excel {
    private val EXCEL_XLS = "xls"
    private val EXCEL_XLSX = "xlsx"

    fun getWorkbok(file: File): Workbook? {
        var workbook: Workbook? = null
        if (file.exists()) {
            if (file.name.endsWith(EXCEL_XLS)) {
                workbook = HSSFWorkbook(file.inputStream())
            } else if (file.name.endsWith(EXCEL_XLSX)) {
                workbook = XSSFWorkbook(file.inputStream())
            }
            return workbook
        }
        return null
    }

    fun getCellValue(sheet: Sheet, row: Int, column: Int): String {
        return ""
    }
}