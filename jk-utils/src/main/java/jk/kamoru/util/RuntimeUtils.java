package jk.kamoru.util;

import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuntimeUtils {

	protected static final Logger logger = LoggerFactory.getLogger(RuntimeUtils.class);
	
	/**
	 * 프로그램 실행
	 * @param command
	 */
	public static void exec(String... command) {
		logger.trace("exec {}", Arrays.toString(command));
		try {
//			Runtime.getRuntime().exec(command);
			ProcessBuilder processBuilder = new ProcessBuilder(command);
			processBuilder.start();
		} 
		catch (IOException e) {
			throw new RuntimeException("execute error", e);
		}
	}
}
