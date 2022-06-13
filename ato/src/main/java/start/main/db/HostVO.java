package start.main.db;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "hoststatus")
public class HostVO {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;
	
	@Column
	private String name;
	
	@Column
	@Pattern(regexp = "((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])([.](?!$)|$)){4}",
			message = "ip를 다시 확인하고 입력해주세요.")
	private String ip;
	
	@Column
	//@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
	// 등록/수정 시간에 필요한 날짜정보
	private String date_time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));;
	
	public void update(String name, String ip) {
		this.name = name;
		this.ip = ip;
	}
	
	
}
