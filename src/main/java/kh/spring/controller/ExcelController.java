package kh.spring.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kh.spring.dto.MemberDTO;
import kh.spring.service.ExcelService;

@Controller
@RequestMapping("/excel")
public class ExcelController {
	
	@Autowired
	private ExcelService service;
	
	@Autowired
	private HttpSession session;

	
	// db저장되어있는 식단 엑셀 다운로드
	@RequestMapping("excelDowload")
	public void excelDownload(String month, HttpServletResponse response) throws IOException {
		System.out.println("엑셀 다운로드");
		
		MemberDTO mdto = (MemberDTO)session.getAttribute("login");
		String school = mdto.getSchool();
		
		service.excelDownload(month, school, response);
	}
	
	// 엑셀 업로드양식 다운
	@RequestMapping("excelform")
	public void excelform(HttpServletResponse response) throws IOException {
		System.out.println("엑섹 업로드 양식 다운");
		service.excelform(response);
	}
	
	// 엑셀 업로드
	@ResponseBody
	@RequestMapping(value="excelupload", produces="text/html;charset=utf8")
	public int excelupload(MultipartFile file) throws Exception {
		System.out.println("엑셀 업로드");
		
		MemberDTO dto = (MemberDTO)session.getAttribute("login");;
		
		String realPath = session.getServletContext().getRealPath("excelupload");

		return service.excelupload(dto, file, realPath);
	}
}
