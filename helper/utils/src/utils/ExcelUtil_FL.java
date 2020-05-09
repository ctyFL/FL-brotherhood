package utils;

import java.io.File;
import java.io.FileInputStream;

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

	public static void readXlsxExcelByPOI(String filePath) {
		if(!FileUtil_FL.isExists(filePath)) {
			return;
		}
		try {
			FileInputStream fis = new FileInputStream(new File(filePath));
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
