package start.main.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
	
	InetAddress inet = null;
	
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
		
		// 마지막 Alive시간 확인
		String last_Alive = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		
		model.addAttribute("Title", "호스트 조회");
		HostVO getHost = new HostVO();
		
		//List<HostVO> Hosts = hostservice.findAll();
		model.addAttribute("Hosts", hostservice.findAll());
		
		getHost = hostservice.findByName(hostname);
		
		if(hostname.equals(getHost.getName())) {
			model.addAttribute("seq", getHost.getSeq());
			model.addAttribute("hostname", getHost.getName());
			model.addAttribute("ip", getHost.getIp());
			model.addAttribute("date_time", getHost.getDate_time());
			try {
				inet = InetAddress.getByName(getHost.getIp());
			} catch(UnknownHostException e) {
				e.printStackTrace();
			}
			
			try {
				if(inet.isReachable(1000)) {
					getHost.setStatus("Alive");
					model.addAttribute("status",getHost.getStatus());
					getHost.setLast_alive(last_Alive);
					model.addAttribute("last_alive",getHost.getLast_alive());
				}
				else {
					getHost.setStatus("Unreachable");
					model.addAttribute("status",getHost.getStatus());
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			return "searchresult";
		}
		else
			return "main";
	}
	
	// 모든 Host 조회 완성
	@RequestMapping(value = "/allsearch", method=RequestMethod.GET)
	public String allSearchHost(Model model) {
		
		// 마지막 Alive시간 확인
		String last_Alive = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		
		
		model.addAttribute("Title", "호스트 전체조회");
		
		List<HostVO> Hosts = new ArrayList<>(); 
		Hosts = hostservice.findAll();
		
		for(int i = 0 ; i < Hosts.size() ; i++) {
			try {
				inet = InetAddress.getByName(Hosts.get(i).getIp());
			} catch(UnknownHostException e) {
				e.printStackTrace();
			}
			
			try {
				if(inet.isReachable(1000)) {
					Hosts.get(i).setStatus("Alive");
					model.addAttribute("status",Hosts.get(i).getStatus());
					if(Hosts.get(i).getLast_alive() == null) {
						Hosts.get(i).setLast_alive(last_Alive);
					}
					model.addAttribute("last_alive",Hosts.get(i).getLast_alive());
				}
				else {
					Hosts.get(i).setStatus("Unreachable");
					model.addAttribute("status",Hosts.get(i).getStatus());
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
			hostservice.insertHost(Hosts.get(i));
		}
		model.addAttribute("Hosts", Hosts);
		
		return "allsearch";
	}
	
	// 수정 get 완성
	@RequestMapping(value = "/update", method=RequestMethod.GET)
	public String UpdateHost() {
		
		return "searchresult";
	}
	
	// 수정 post 완성
	@RequestMapping(value = "/update", method=RequestMethod.POST)
	public String UpdateHost(@RequestParam("seq") Long seq, @RequestParam("hostname") String hostname, @RequestParam("ip") String ip, Model model) {
		
		// 등록/수정 시간에 필요한 날짜정보
		String datetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		
		// 마지막 Alive시간 확인
		String last_Alive = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		
		HostVO getHost = new HostVO();
		getHost = hostservice.findBySeq(seq);
		Optional<HostVO> id = hostservice.findById(getHost.getSeq());
		
		if(id.isPresent()) {
			getHost.setName(hostname);
			getHost.setIp(ip);
			getHost.setDate_time(datetime);
			
			try {
				inet = InetAddress.getByName(getHost.getIp());
			} catch(UnknownHostException e) {
				e.printStackTrace();
			}
			
			try {
				if(inet.isReachable(1000)) {
					getHost.setStatus("Alive");
					model.addAttribute("status",getHost.getStatus());
					getHost.setLast_alive(last_Alive);
					model.addAttribute("last_alive",getHost.getLast_alive());
				}
				else {
					getHost.setStatus("Unreachable");
					model.addAttribute("status",getHost.getStatus());
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
			hostservice.insertHost(getHost);
			return "update";
		}
		else
			return "main";
		
	}
	
	// 호스트 정보 삭제 완성
	@RequestMapping(value = "/delete", method=RequestMethod.POST)
	public String DeleteHost(@RequestParam("Hosts") Optional<HostVO> host, @RequestParam("seq") Long seq) {
		
		HostVO getHost = new HostVO();
		getHost = hostservice.findBySeq(seq);
		host = hostservice.findById(getHost.getSeq());
		
		hostservice.deleteById(getHost.getSeq());
		
		return "delete";
	}
	
	// 완성
	@RequestMapping(value = "/insert", method=RequestMethod.GET)
	public String Insert(Model model) {
		model.addAttribute("Title", "호스트 등록");
		return "insert";
	}
	
	// 미완성(정규표현식 벗어난 문구 출력)
	@RequestMapping(value = "/insert", method=RequestMethod.POST)
	public String InsertHost(@RequestParam("hostname") String hostname, @RequestParam("ip") String ip, Model model) {
		
		// 등록/수정 시간에 필요한 날짜정보
		String datetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		
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
