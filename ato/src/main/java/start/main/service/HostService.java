package start.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import start.main.db.HostVO;
import start.main.db.HostVORepo;

@Service
public class HostService {
	
	@Autowired
	private HostVORepo hostVORepo;
	
	public List<HostVO> findAll(){
		List<HostVO> hosts = new ArrayList<>();
		hostVORepo.findAll().forEach(e -> hosts.add(e));
		return hosts;
	}
	
	public HostVO insertHost(HostVO host) {
		return hostVORepo.save(host);
	}
	
	public HostVO findByName(String name) {
		return hostVORepo.findByName(name);
	}
	
	public Optional<HostVO> findById(Long seq) {
		return hostVORepo.findById(seq);
	}
	
	public HostVO findBySeq(Long seq) {
		HostVO getHost = new HostVO();
		getHost.setSeq(seq);
		return getHost;
	}
	
	public void deleteById(Long seq) {
		Optional<HostVO> getHost = hostVORepo.findById(seq);
		HostVO delHost = new HostVO();
		if(getHost.isPresent()) {
			delHost.setSeq(seq);
		}
		hostVORepo.delete(delHost);
	}
	
}
