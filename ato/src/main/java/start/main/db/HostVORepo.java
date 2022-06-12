package start.main.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostVORepo extends JpaRepository<HostVO, Long>{

	HostVO findByName(String name);
	
	List<HostVO> findAll();
	
}
