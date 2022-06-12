package start.main.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import start.main.db.HostVO;
import start.main.service.HostService;

@Controller
public class MainController {
	
	// 등록/수정 시간에 필요한 날짜정보
	String datetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	
	@Autowired
	private HostService hostservice;
	
	// 메인 완성
	@RequestMapping(value = "/main", method=RequestMethod.GET)
	public String Main(Model model) {
		model.addAttribute("Title", "호스트 메인");
		return "main";
	}
	
	// 조회 get 완성
	@RequestMapping(value = "/search", method=RequestMethod.GET)
	public String Search(Model model) {
		model.addAttribute("Title", "호스트 조회");
		return "search";
	}
	
	// 조회 post 완성
	@RequestMapping(value = "/search", method=RequestMethod.POST)
	public String SearchHost(@RequestParam("hostname") String hostname, Model model) {
		
		model.addAttribute("Title", "호스트 조회");
		HostVO getHost = new HostVO();
		
		//List<HostVO> Hosts = hostservice.findAll();
		model.addAttribute("Hosts", hostservice.findAll());
		
		getHost = hostservice.findByName(hostname);
		
		if(hostname.equals(getHost.getName())) {
			model.addAttribute("hostname", getHost.getName());
			model.addAttribute("ip", getHost.getIp());
			model.addAttribute("data_time", getHost.getDate_time());
			return "searchresult";
		}
		else
			return "searchresult";
	}
	
	// 모든 Host 조회 완성
	@RequestMapping(value = "/allsearch", method=RequestMethod.GET)
	public String allSearchHost(Model model) {
		
		model.addAttribute("Title", "호스트 전체조회");
		
		List<HostVO> Hosts = new ArrayList<>(); 
		
		Hosts = hostservice.findAll();
		model.addAttribute("Hosts", Hosts);
		
		return "allsearch";
	}
	
	// 수정 get 완성
//	@RequestMapping(value = "/update", method=RequestMethod.GET)
//	public String UpdateHost() {
//		
//		return "searchresult";
//	}
	
	// 수정 post 미완성
	@RequestMapping(value = "/update", method=RequestMethod.POST)
	public String UpdateHost(@RequestParam("hostname") String hostname, @RequestParam("ip") String ip, Model model) {
		HostVO getHost = new HostVO();
		
		getHost = hostservice.findByName(hostname);
		
		return "main";
	}
	
	// 완성
	@RequestMapping(value = "/insert", method=RequestMethod.GET)
	public String Insert(Model model) {
		model.addAttribute("Title", "호스트 등록");
		return "insert";
	}
	
	// 미완성
	@RequestMapping(value = "/insert", method=RequestMethod.POST)
	public String InsertHost(@RequestParam("hostname") String hostname, @RequestParam("ip") String ip, Model model) {
		model.addAttribute("Title", "호스트 등록");
		if(Pattern.matches("((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])([.](?!$)|$)){4}", ip)) {
			model.addAttribute("msg","호스트 등록이 완료되었습니다.");
			model.addAttribute("url","/main");
			
			HostVO host = new HostVO();
			host.setName(hostname);
			host.setIp(ip);
			host.setDate_time(datetime);
			
			hostservice.insertHost(host);
			
			return "main";
		}
		else {
			model.addAttribute("msg","ip주소가 잘못되었습니다. 다시 확인해주세요.");
			model.addAttribute("url","");
			return "";
		}
	}
	
}
