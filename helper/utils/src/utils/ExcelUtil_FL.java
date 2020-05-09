package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel表格工具类
 * 需要引入Apache POI jar包
 * 总结：按照需要导入jar包及相关依赖jar包。
 * poi-4.1.2.jar  (excel文件生成需要)
 * poi-examples-4.1.2.jar(官方示例，开发不需要) 
 * poi-excelant-4.1.2.jar(不需要)
 * poi-ooxml-4.1.2.jar(excel,word,ppt均需要)
 * poi-ooxml-schemas-4.1.2.jar(excel需要)
 * poi-scratchpad-4.1.2.jar(ppt,vsd,word,viso,outlook等需要)
 * @author ctyFL
 * @date 2020年5月9日
 * @version 1.0
 */
public class ExcelUtil_FL {
	
	public static final String EXCEL_XLSX = "xlsx";
	public static final String EXCEL_XLS = "xls";

	public static void readXlsxExcelByPOI(String filePath) {
		if(!FileUtil_FL.isExists(filePath)) {
			return;
		}
		try {
			XSSFWorkbook wb = (XSSFWorkbook) openWorkBook(filePath, EXCEL_XLSX);
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row = sheet.getRow(0);
			System.out.println(row.getCell(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据路径返回xlsx或是xls的文档对象
	 * @param filePath
	 * @param type
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Workbook openWorkBook(String filePath, String type) throws FileNotFoundException, IOException {
		Workbook workbook = null;
		if(FileUtil_FL.isExists(filePath)) {
			FileInputStream fis = new FileInputStream(new File(filePath));
			if(EXCEL_XLSX.equals(type)) {
				workbook = new XSSFWorkbook(fis);
			}else if(EXCEL_XLS.equals(type)) {
				workbook = new HSSFWorkbook(fis);
			}
		}
		return workbook;
	}
}
