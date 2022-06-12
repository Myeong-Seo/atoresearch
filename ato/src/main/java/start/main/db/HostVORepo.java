package start.main.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostVORepo extends JpaRepository<HostVO, Long>{

	public HostVO findByName(String name);
	
}
