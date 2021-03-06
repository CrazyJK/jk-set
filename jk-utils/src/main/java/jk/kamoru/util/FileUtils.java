package jk.kamoru.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jk.kamoru.JK;
import jk.kamoru.JKException;

/**
 * commons.io.FileUtils 상속하고 필요한 기능 추가
 * @author kamoru
 *
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
	
	public static final String EXTENSION_SEPARATOR = ".";
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	
	/**
	 * 입력된 경로의 모든 파일명을 상위폴더명+일련번호로 변경
	 * @param basepath
	 */
	public static void renameToSerialNameInFolder(File basepath) {
		Assert.isTrue(basepath.isDirectory(), "It is not directory! ");
		
		Collection<File> files = FileUtils.listFiles(basepath, null, false);
		
		int count = 0;
		int digit = String.valueOf(files.size() + count).length();
		for(File file : files) {
			if (!file.renameTo(new File(file.getParentFile(), 
					String.format("%s-%s.%s", 
							file.getParentFile().getName(), 
							StringUtils.addZero(++count, digit), 
							getExtension(file).toLowerCase()))))
				System.out.println("Fail to rename : " + file.getAbsolutePath());
		}
	}
	
	public static void renameToMelon(File basepath) {
		Assert.isTrue(basepath.isDirectory());
		for (File file : FileUtils.listFiles(basepath, null, false)) {
			String name = file.getName();
			if (name.length() > 4)
				file.renameTo(new File(file.getParentFile(), name.substring(4)));
		}
	}

	/**
	 * 확장자를 뺀 파일 이름
	 * @param file
	 * @return 확장자 없는 파일명
	 */
	public static String getNameExceptExtension(File file) {
		Assert.isTrue(file.isFile(), "It is not file! - " + file.getAbsolutePath());
		return StringUtils.substringBeforeLast(file.getName(), EXTENSION_SEPARATOR);
	}

	/**
	 * 파일의 확장자
	 * @param file
	 * @return 파일 확장자
	 */
	public static String getExtension(File file) {
		Assert.isTrue(file.isFile(), "It is not file! - " + file.getAbsolutePath());
		return StringUtils.substringAfterLast(file.getName(), EXTENSION_SEPARATOR);
	}

	/**
	 * 확장자에 맞는 파일을 찾는다
	 * @param directories 찾을 디렉토리
	 * @param extensions 파일 확장자. null이면 모든 파일
	 * @param recursive 하위폴더 검색 여부
	 * @return
	 */
	public static List<File> listFiles(String[] directories, String[] extensions, boolean recursive) {
		List<File> dirFiles = new ArrayList<File>();
		for (String directory : directories)
			dirFiles.add(new File(directory));
		return listFiles(dirFiles, extensions, recursive);
	}

	/**
	 * 확장자에 맞는 파일을 찾는다
	 * @param dirFiles 찾을 폴더
	 * @param extensions 파일 확장자. null이면 모든 파일
	 * @param recursive 하위폴더 검색 여부
	 * @return
	 */
	public static List<File> listFiles(List<File> dirFiles, String[] extensions, boolean recursive) {
		List<File> list = new ArrayList<File>();
		for (File dir : dirFiles)
			if (dir.isDirectory())
				list.addAll(listFiles(dir, extensions, recursive));
			else
				throw new JKUtilException(String.format("%s is not directory", dir.getAbsolutePath()));
		return list;
	}

	/**
	 * 빈 디렉토리인지
	 * @param dir
	 * @return
	 */
	public static boolean isEmptyDirectory(File dir) {
		return listFiles(dir, null, true).isEmpty();
	}
	
	/**파일 이름 변경
	 * @param file
	 * @param name
	 * @return
	 */
	public static boolean rename(File file, String name) {
		if (file == null)
			return false;
		else 
			return file.renameTo(new File(file.getParentFile(), name + EXTENSION_SEPARATOR + getExtension(file)));
	}
	
	/**파일인지 검증. 존재하는지, 진짜 파일인지
	 * @param file
	 * @param fileDesc 파일설명
	 * @throws JKException
	 */
	public static void validateFile(File file, String fileDesc) {
		Assert.isTrue(file.exists(), fileDesc + " dose not exists");
		Assert.isTrue(file.isFile(), fileDesc + " is not a normal file");
	}
	
	/**파일인지 검증. 존재하는지, 진짜 파일인지
	 * @param file
	 * @throws JKException
	 */
	public static void validateFile(File file) {
		validateFile(file, "file");
	}
	
	/**디렉토리인지 검증. 존재하는지, 진짜 디렉토리인지
	 * @param directory
	 * @param dirDesc 디렉토리 설명
	 * @throws JKException
	 */
	public static void validateDirectory(File directory, String dirDesc) {
		Assert.isTrue(directory.exists(), dirDesc + " dose not exists");
		Assert.isTrue(directory.isDirectory(), dirDesc + " is not a directory");
	}
	
	/**디렉토리인지 검증. 존재하는지, 진짜 디렉토리인지
	 * @param directory
	 * @throws JKException
	 */
	public static void validateDirectory(File directory) {
		validateDirectory(directory, "file");
	}
	
	/**
	 * properties 형태의 파일을 읽어 Map으로 반환
	 * @see #saveFileFromMap(File, Map)
	 * @param file
	 * @return 파일이 없으면 빈 Map
	 * @throws JKUtilException
	 */
	public static Map<String, String> readFileToMap(File file) {
		validateFile(file, "file of readFileToMap");
		try {
			Map<String, String> map = new HashMap<String, String>();
			for (String str : FileUtils.readLines(file, JK.FILE_ENCODING)) {
				String[] strs = StringUtils.split(str, "=", 2);
				if (strs.length > 1)
					map.put(strs[0], strs[1]);
			}
			return map;
		} 
		catch (IOException e) {
			throw new JKUtilException("read file error", e);
		}
	}

	/**
	 * Map 내용을 file로 저장
	 * @see #readFileToMap(File)
	 * @param file
	 * @param params
	 * @throws JKUtilException
	 */
	public static void saveFileFromMap(File file, Map<String, String> params) {
		if (params == null || params.size() == 0)
			throw new JKUtilException("params is null or size 0");
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(entry.getKey().toUpperCase().trim());
			sb.append("=");
			sb.append(entry.getValue().trim());
			sb.append(LINE_SEPARATOR);
		}
		try {
			FileUtils.writeStringToFile(file, sb.toString(), JK.FILE_ENCODING);
		} catch (IOException e) {
			throw new JKUtilException("write file error", e);
		}
	}

	/**
	 * 두 경로가 같은 드라이브 문자로 시작되는지(윈도우만 해당)
	 * @param path1
	 * @param path2
	 * @return
	 */
	public static boolean compareDrive(String path1, String path2) {
		return StringUtils.equals(new File(path1).getAbsolutePath().substring(0, 1), new File(path2).getAbsolutePath().substring(0, 1));
	}

	public static void lowerExtention(File dir) {
		int count = 0;
		for (File file : FileUtils.listFiles(dir, null, true)) {
			String ext = getExtension(file);
			if (StringUtils.isAllUpperCase(ext)) {
				count++;
				System.out.format("%3s - %s", count, file.getName());
				file.renameTo(new File(file.getParentFile(), file.getName().toLowerCase()));
				System.out.format(" : %s%n", "OK");
			}
		}
	}
	
	public static void main(String[] args) {
//		FileUtils.lowerExtention(new File("/home/kamoru/Pictures/Girls/Girls"));
//		FileUtils.renameToSerialNameInFolder(new File("/home/kamoru/Pictures/Girls/Girls"));
//		File file = new File("D:\\Crazy\\Storage\\1pondo\\[1pondo][010115-001][오하시 미쿠 - 첫뒤 완전판][Miku Ohashi][2015.01.01].info");
//		System.out.println(file.getParent() == null);
//		System.out.println(file.getParentFile().getParentFile() == null);
//		System.out.println(file.getParentFile().getParentFile().getParentFile() == null);
//		System.out.println(file.getParentFile().getParentFile().getParentFile().getParentFile() == null);
//		System.out.println(file.getParentFile().getParentFile().getParentFile().getParentFile().getParentFile() == null);
//		
//		System.out.println(getRootDirectory(file));
	}

	/**
	 * 최상위 폴더를 찾는다
	 * @param file
	 * @return 윈도우면 디스크 이름
	 */
	public static File getRootDirectory(File file) {
		File parent = file;
		File root = null;
		while (true) {
			root = parent.getParentFile();
			if (root == null)
				return parent;
			else
				parent = root;
		}
	}
	
	/**파일명으로 사용할수 없는 글자를 제거한다.<br>
	 * <code>\ / : * ? " < > |</code>
	 * @param name
	 * @return
	 */
	public static String removeInvalidFilename(String name) {
		name = StringUtils.remove(name, '\\');
		name = StringUtils.remove(name, '/');
		name = StringUtils.remove(name, ':');
		name = StringUtils.remove(name, '*');
		name = StringUtils.remove(name, '?');
		name = StringUtils.remove(name, '"');
		name = StringUtils.remove(name, '<');
		name = StringUtils.remove(name, '>');
		name = StringUtils.remove(name, '|');
		return name;
	}
}
