package start.main.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostVORepo extends JpaRepository<HostVO, Long>{

	Optional<HostVO> findById(Long id);
	
	HostVO findBySeq(Long id);
	
	HostVO findByName(String name);
	
	void deleteById(Long id);
	
	List<HostVO> findAll();
	
}
