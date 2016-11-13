package com.iigeo.ssm.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@Component("systemUtil")
public class SystemUtil {
	@Value("#{system[ips.durid]}")
	public String ips;
}
