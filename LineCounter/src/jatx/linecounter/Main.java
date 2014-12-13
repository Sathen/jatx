package jatx.linecounter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		if (args.length<2) {
			System.out.println("Wrong usage");
			System.exit(0);
		}
		
		/*
		 * ��������� �������:
		 */
		String[] endings = args[1].split(";");
		
		StringBuilder sb = new StringBuilder();
		sb.append(".*(");
		for (int i=0; i<endings.length; i++) {
			if (i>0) sb.append("|");
			sb.append(endings[i]);
		}
		sb.append(")$");
		
		String pattern = sb.toString();
		
		/*
		 * ���� ����� �� ��������:
		 */
		
		List<File> files = findFiles(args[0],pattern);
		
		/* 
		 * ������� ���������� ����� � ������:
		 */
		
		Integer totalCount = 0;
		
		for (File file: files) {
			System.out.print(file.getAbsolutePath());
			Integer count = linesCount(file);
			System.out.println(": "+count+" lines");
			totalCount += count;
		}
		
		/*
		 * ��������� ���������� �����:
		 */
		
		System.out.println("\nTotal: "+totalCount);

	}
	
	static List<File> findFiles(String startPath, String pattern) {
		List<File> dirs  = new ArrayList<File>();
		List<File> files = new ArrayList<File>();
		
		File startDir = new File(startPath);
		if (!startDir.exists()||!startDir.isDirectory()) {
			System.out.println("Wrong start path");
			System.exit(0);
		}
		
		/*
		 * �������������� ����� ����������:
		 */
		
		File dir = startDir;
		
		int next = 0;
		
		while (true) {			
			/*
			 * ����� � ������� ����������:
			 */
			File[] list = dir.listFiles();
			
			/*
			 * ��������� ���������� � ����� � �������������� ����������:
			 */
			
			for (int i=0; list!=null&&i<list.length; i++) {
				File tmp = list[i];
				if (tmp.isDirectory()) {
					dirs.add(tmp);
				} else if (tmp.isFile()&&tmp.getName().matches(pattern)) {
					files.add(tmp);
				}
			}
 			
			/*
			 * ��������� ����, ���� �������� ����� ������ ����������:
			 */
			
			if (next>=dirs.size()) {
				break;
			}
			
			/*
			 * ��������� � ��������� ���������� �� ������:
			 */
			
			dir = dirs.get(next);
			next++;
		}
		
		return files;
	}
	
	static Integer linesCount(File f) {
		Integer count = 0;
		
		try {
			/*
			 * ��������� ����:
			 */
			Scanner sc = new Scanner(f);
			
			/*
			 * ��������� �� ����� ������,
			 * ����������� �������, ���� ������ �� ������:
			 */
			while (sc.hasNext()) {
				if (!sc.nextLine().trim().equals("")) {
					count++;
				}
			}
			
			/*
			 * ���������:
			 */
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return count;
	}
}
