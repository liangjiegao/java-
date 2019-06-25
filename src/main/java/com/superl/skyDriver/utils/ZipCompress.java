package com.superl.skyDriver.utils;


import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipCompress {
	
	private String zipFileName;	// 压缩后文件名
	private Map<Long, List<File>> fileMap;
	public ZipCompress(String zipFileName,Map<Long, List<File>> fileMap) {
		this.zipFileName = zipFileName;
		this.fileMap = fileMap;
	}

	/**
	 * 压缩
	 * @param fileList
	 * @throws IOException
	 */
	public void zip(List<File> fileList) throws IOException {

		// 压缩文件输出流，向压缩文件中写入数据
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
		// 压缩文件缓冲流，对输出流进行包装
		BufferedOutputStream bos = new BufferedOutputStream(out);
		int index = zipFileName.lastIndexOf("/");
		String base = zipFileName.substring(0, index);
		// 遍历文件，对这些文件进行压缩
		for (File file: fileList) {
			System.out.println("file.getName="+file.getName());
				compress(out, bos, file, file.getName());
//				String path = file.getPath();
//				if (path.split("/").length > 2){
//					// 是一个文件
//					compress(out, bos, file, file.getName());
//				}else {
//					// 是一个文件夹
//					base += path.split("/")[0];
//					out.putNextEntry(new ZipEntry("base"+"/"));	// 创建zip压缩进入点base
//					// 遍历文件夹里面的文件
//					for (File file1 : fileMap.get(Long.parseLong(path.split("/")[1]))) {
//
//					}
//				}
//			}
		}
		// 关闭资源
		bos.close();
		out.close();
	}
	private void compress(ZipOutputStream out, BufferedOutputStream bos, File sourceFile, String base) throws IOException {
		String path = sourceFile.getPath();
		System.out.println("base="+base);
		if (path.split("\\?").length < 2){
			// 是一个文件
			System.out.println("文件");
			// 如果是文件，直接压缩
			// 相当于创建一个指针指向要存放下一个文件的位置
			out.putNextEntry(new ZipEntry(base));
			// 要压缩文件的输入流
			FileInputStream fis = new FileInputStream(sourceFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			int tag;
			System.out.println("base="+base);
			while((tag=bis.read()) != -1) {
				bos.write(tag);
			}
			// 这里一定要flush将缓冲区的数据刷入文件中，否则有可能有一部分数据还在缓冲区中，文件写不全
			bos.flush();
			bis.close();
			fis.close();
		}else {
			// 是一个文件夹
			// 第一次的base需要分解
			if (base.split("\\?").length > 1) base = base.split("\\?")[0];
			else base = base + "\\" +sourceFile.getName().split("\\?")[0];

			out.putNextEntry(new ZipEntry(base+"\\"));	// 创建zip压缩进入点base
			// 遍历文件夹里面的文件
			System.out.println("aaaaaa="+fileMap.get(Long.parseLong(path.split("\\?")[1])));
			for (File file1 : fileMap.get(Long.parseLong(path.split("\\?")[1]))) {
				compress(out, bos, file1, base+"\\"+file1.getName());
			}
		}
	}

	/**
	 * 解压
	 * @param file
	 * @param parent
	 * @return	返回文件名，插入数据库中
	 */
	public List<File> unzip(File file, String parent){
		List<File> list = new ArrayList<>();
		try {
			// 压缩包的输入流
			ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
			BufferedInputStream bis = new BufferedInputStream(zis);
			File temp = null;
			ZipEntry entry;

			try {
				while((entry = zis.getNextEntry()) != null && !entry.isDirectory()){
					temp = new File(parent, entry.getName());
					String fileName = new Date().getTime()+temp.getName().substring(13, temp.getName().length());
                    temp = new File(parent, fileName);
//
					// 加入列表，用于返回给用户
					list.add(temp);
					FileOutputStream out = new FileOutputStream(temp);
					BufferedOutputStream bos = new BufferedOutputStream(out);
					int b;
					while((b = bis.read()) != -1){
						bos.write(b);
					}
					bos.flush();
					bos.close();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}


}
