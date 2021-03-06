package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel表格工具类
 * 需要引入Apache POI jar包
 * 按照需要导入jar包及相关依赖jar包。
 * poi-4.1.2.jar  (excel文件生成需要)
 * poi-examples-4.1.2.jar(官方示例，开发不需要) 
 * poi-excelant-4.1.2.jar(不需要)
 * poi-ooxml-4.1.2.jar(excel,word,ppt均需要)
 * poi-ooxml-schemas-4.1.2.jar(excel需要)
 * poi-scratchpad-4.1.2.jar(ppt,vsd,word,viso,outlook等需要)
 * xmlbeans-3.1.0.jar(需要)
 * @author ctyFL
 * @date 2020年5月9日
 * @version 1.0
 */
public class ExcelUtil_FL {
	
	public static final String EXCEL_XLSX = "xlsx";
	public static final String EXCEL_XLS = "xls";

	/**
	 * 读取、操作指定路径下后缀为xls的表格文件
	 * @param filePath
	 */
	public static void readXlsExcelByPOI(String filePath) {
		if(!FileUtil_FL.isExists(filePath)) {
			return;
		}
		try {
			HSSFWorkbook wb = (HSSFWorkbook) openWorkBook(filePath, EXCEL_XLS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取、操作指定路径下后缀为xlsx的表格文件
	 * @param filePath
	 */
	public static void readXlsxExcelByPOI(String filePath) {
		if(!FileUtil_FL.isExists(filePath)) {
			return;
		}
		try {
			XSSFWorkbook wb = (XSSFWorkbook) openWorkBook(filePath, EXCEL_XLSX);
			//importPropCustomerInfo_yingkouguohai(wb);
			readFromLeuchTek(wb);
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
	
	/**
	 * 营口国海项目导入客户资料SQL生成
	 * @param wb
	 */
	private static void importPropCustomerInfo_yingkouguohai(XSSFWorkbook wb) {
		String sql = "";
		XSSFSheet sheet = wb.getSheetAt(0);
		int rowsOfSheet = sheet.getPhysicalNumberOfRows();
		for(int i=1; i<rowsOfSheet; i++) {
			XSSFRow row = sheet.getRow(i);
			XSSFCell roomNoCell = row.getCell(2);
			XSSFCell customerCell = row.getCell(3);
			XSSFCell telePhoneCell = row.getCell(4);
			XSSFCell idNoCell = row.getCell(5);
			XSSFCell manageNoCell = row.getCell(6);
			
			roomNoCell.setCellType(CellType.STRING);
			customerCell.setCellType(CellType.STRING);
			telePhoneCell.setCellType(CellType.STRING);
			idNoCell.setCellType(CellType.STRING);
			manageNoCell.setCellType(CellType.STRING);
			
			String roomNo = roomNoCell.getStringCellValue().trim();
			String customer = customerCell.getStringCellValue().trim();
			String telePhone = telePhoneCell.getStringCellValue().trim();
			String idNo = idNoCell.getStringCellValue().trim();
			String manageNo = manageNoCell.getStringCellValue().trim();
			sql += "update PropCustomer set "
					+ " TELEPHONE='" + telePhone + "',IDNO='" + idNo + "',MANAGENO='" + manageNo + "' "
					+ " where ID=(select a.ID from PropCustomer a "
					+ " inner join PropRoomCustomerDetail b on a.ID = b.PROPCUSTOMER_ID "
					+ " inner join PropRoom c on c.ID = b.PROPROOM_ID "
					+ " where a.CUSTOMERNAME='" + customer + "' and c.ROOMNO='" + roomNo + "') \r\n";
		}
		FileUtil_FL.createNewTxtFile("E:/", "log1", sql);
	}
	
	private static void readFromLeuchTek(XSSFWorkbook wb) {
		XSSFSheet sheet = wb.getSheetAt(0);
		int rowsOfSheet = sheet.getPhysicalNumberOfRows();
		String dateStr = "";
		String modelStr = "";
		String pcsStr = "";
		String priceStr = "";
		for(int i=0; i<rowsOfSheet; i++) {
			XSSFCell row = sheet.getRow(i).getCell(0);
			row.setCellType(CellType.STRING);
			if("DATA".equals(row.getStringCellValue().trim())) {
				XSSFCell dateStrCell = sheet.getRow(i).getCell(1); 
				dateStrCell.setCellType(CellType.STRING);
				dateStr = dateStrCell.getStringCellValue().trim();
			}
			if("MODEL".equals(row.getStringCellValue().trim())) {
				XSSFCell modelStrCell = sheet.getRow(i).getCell(1); 
				modelStrCell.setCellType(CellType.STRING);
				modelStr = modelStrCell.getStringCellValue().trim();
			}
			if("QTY(PCS)".equals(row.getStringCellValue().trim())) {
				XSSFCell pcsStrCell = sheet.getRow(i).getCell(1); 
				pcsStrCell.setCellType(CellType.STRING);
				pcsStr = pcsStrCell.getStringCellValue().trim();
			}
			if("UNIT PRICE\n(USD)".equals(row.getStringCellValue().trim())) {
				XSSFCell priceStrCell = sheet.getRow(i).getCell(1); 
				priceStrCell.setCellType(CellType.STRING);
				priceStr = priceStrCell.getStringCellValue().trim();
			}
		}
		String result = dateStr + "," + modelStr + "," + pcsStr + "," + priceStr;
		FileUtil_FL.createNewTxtFile("E:/", "LeuchTek", result);
	}
	
}
